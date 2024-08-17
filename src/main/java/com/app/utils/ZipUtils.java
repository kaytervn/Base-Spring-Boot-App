package com.app.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

@Slf4j
public class ZipUtils {
    public static String zipString(String input) {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream();
             DeflaterOutputStream zip = new DeflaterOutputStream(stream, new Deflater())) {
            zip.write(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(stream.toByteArray());
        } catch (Exception e) {
            log.error("Error zipping string", e);
            return null;
        }
    }

    public static String unzipString(String input) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(input));
             InflaterInputStream inflaterStream = new InflaterInputStream(inputStream, new Inflater())) {
            return new String(inflaterStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("Error unzipping string", e);
            return null;
        }
    }
}
