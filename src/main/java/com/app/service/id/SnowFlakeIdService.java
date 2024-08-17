package com.app.service.id;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.hibernate.validator.constraints.Range;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SnowFlakeIdService - A distributed unique ID generator inspired by Twitter's Snowflake
 * <p>
 * Structure: 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
 * 1 bit: Unused (sign bit)
 * 41 bits: Timestamp (milliseconds since epoch)
 * 5 bits: Data center ID
 * 5 bits: Machine ID
 * 12 bits: Sequence number
 */
public class SnowFlakeIdService {
    // Singleton instance
    private static volatile SnowFlakeIdService instance;

    // Constants
    private final long twepoch = 1489111610226L; // Custom epoch
    private final long workerIdBits = 5L;
    private final long dataCenterIdBits = 5L;
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);
    private final long sequenceBits = 5L;

    // Precalculated bit shifting
    private final long workerIdShift = sequenceBits;
    private final long dataCenterIdShift = sequenceBits + workerIdBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    // Instance variables
    private long workerId;
    private long dataCenterId;
    private final AtomicLong sequence = new AtomicLong(0L);
    private long lastTimestamp = -1L;

    // Thread safety
    private final ReentrantLock lock = new ReentrantLock();

    // Private constructor for singleton
    private SnowFlakeIdService() {
    }

    /**
     * Get the singleton instance of SnowFlakeIdService
     *
     * @return SnowFlakeIdService instance
     */
    public static SnowFlakeIdService getInstance() {
        if (instance == null) {
            synchronized (SnowFlakeIdService.class) {
                if (instance == null) {
                    instance = new SnowFlakeIdService();
                }
            }
        }
        return instance;
    }

    /**
     * Set the worker ID
     *
     * @param nodeId Worker ID (0-63)
     */
    public void setNodeId(@Range(min = 0, max = 63) int nodeId) {
        if (nodeId < 0 || nodeId > maxWorkerId) {
            throw new IllegalArgumentException(String.format("NodeId must be between %d and %d", 0, maxWorkerId));
        }
        this.workerId = nodeId;
    }

    /**
     * Set the data center ID
     *
     * @param dataCenterId Data center ID (0-31)
     */
    public void setDataCenterId(@Range(min = 0, max = 32) int dataCenterId) {
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("dataCenterId can't be greater than %d or less than 0", maxDataCenterId));
        }
        this.dataCenterId = dataCenterId;
    }

    /**
     * Generate the next unique ID
     *
     * @return Unique ID
     */
    public long nextId() {
        lock.lock();
        try {
            long timestamp = timeGen();

            if (timestamp < lastTimestamp) {
                throw new RuntimeException(
                        String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
            }

            if (lastTimestamp == timestamp) {
                long seq = sequence.incrementAndGet() & sequenceMask;
                if (seq == 0) {
                    timestamp = tilNextMillis(lastTimestamp);
                }
            } else {
                sequence.set(0L);
            }

            lastTimestamp = timestamp;

            return ((timestamp - twepoch) << timestampLeftShift)
                    | (dataCenterId << dataCenterIdShift)
                    | (workerId << workerIdShift)
                    | sequence.get();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Wait until the next millisecond
     *
     * @param lastTimestamp Last timestamp
     * @return Next millisecond timestamp
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            Thread.yield();
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * Generate current timestamp in milliseconds
     *
     * @return Current timestamp
     */
    protected long timeGen() {
        return System.nanoTime() / 1_000_000;
    }

    /**
     * Generate a worker ID based on host address
     *
     * @return Generated worker ID
     */
    private static Long getWorkId() {
        try {
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            int[] ints = StringUtils.toCodePoints(hostAddress);
            int sums = 0;
            for (int b : ints) {
                sums += b;
            }
            return (long) (sums % 32);
        } catch (UnknownHostException e) {
            return RandomUtils.nextLong(0, 31);
        }
    }

    /**
     * Generate a data center ID based on host name
     *
     * @return Generated data center ID
     */
    private static Long getDataCenterId() {
        int[] ints = StringUtils.toCodePoints(SystemUtils.getHostName());
        int sums = 0;
        for (int i : ints) {
            sums += i;
        }
        return (long) (sums % 32);
    }
}