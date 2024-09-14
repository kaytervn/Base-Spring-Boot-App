package com.app.service;

import com.app.dto.ApiMessageDto;
import com.app.dto.UploadFileDto;
import com.app.form.file.UploadFileForm;
import com.app.model.Permission;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiService {
    final static String[] UPLOAD_TYPES = {"LOGO", "AVATAR", "IMAGE", "DOCUMENT"};
    final static Map<String, Long> storeQRCodeRandom = new ConcurrentHashMap<>();
    final static long MAX_FILE_SIZE_MB = 5;
    @Value("${file.upload-dir}")
    String UPLOAD_DIRECTORY;
    @Autowired
    OTPService otpService;
    @Autowired
    CommonAsyncService commonAsyncService;

    public ApiMessageDto<UploadFileDto> storeFile(UploadFileForm uploadFileForm) {
        ApiMessageDto<UploadFileDto> apiMessageDto = new ApiMessageDto<>();
        if (!Arrays.asList(UPLOAD_TYPES).contains(uploadFileForm.getType().toUpperCase())) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Type is required in LOGO, AVATAR, IMAGE or DOCUMENT.");
            return apiMessageDto;
        }
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(uploadFileForm.getFile().getOriginalFilename()));
            String ext = FilenameUtils.getExtension(fileName);
            String finalFile = uploadFileForm.getType() + "_" + RandomStringUtils.randomAlphanumeric(10) + "." + ext;
            String typeFolder = File.separator + uploadFileForm.getType();
            Path fileStorageLocation = Paths.get(UPLOAD_DIRECTORY + typeFolder).toAbsolutePath().normalize();
            Files.createDirectories(fileStorageLocation);
            Path targetLocation = fileStorageLocation.resolve(finalFile);
            long fileSizeMB = uploadFileForm.getFile().getSize() / (1024 * 1024);
            if (fileSizeMB > MAX_FILE_SIZE_MB) {
                apiMessageDto.setResult(false);
                apiMessageDto.setMessage("File size exceeds the maximum limit of " + MAX_FILE_SIZE_MB + " MB.");
                return apiMessageDto;
            }
            Files.copy(uploadFileForm.getFile().getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            UploadFileDto uploadFileDto = new UploadFileDto();
            uploadFileDto.setFilePath(typeFolder + File.separator + finalFile);
            apiMessageDto.setData(uploadFileDto);
            apiMessageDto.setMessage("Upload file success");
        } catch (Exception e) {
            log.error("Error storing file", e);
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
        }
        return apiMessageDto;
    }

    public void deleteFile(String filePath) {
        File file = new File(UPLOAD_DIRECTORY + filePath);
        if (file.exists()) file.delete();
    }

    public Resource loadFileAsResource(String folder, String fileName) {
        try {
            Path fileStorageLocation = Paths.get(UPLOAD_DIRECTORY + File.separator + folder).toAbsolutePath().normalize();
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            }
        } catch (Exception ex) {
            log.error("Error loading file as resource", ex);
        }
        return null;
    }

    public InputStreamResource loadFileAsResourceExt(String folder, String fileName) {
        try {
            File file = new File(UPLOAD_DIRECTORY + File.separator + folder + File.separator + fileName);
            return new InputStreamResource(new FileInputStream(file));
        } catch (Exception ex) {
            log.error("Error loading file as resource ext", ex);
        }
        return null;
    }

    public String getOTPForgetPassword() {
        return otpService.generate(4);
    }

    public synchronized Long getOrderHash() {
        return Long.parseLong(otpService.generate(9)) + System.currentTimeMillis();
    }

    public void sendEmail(String email, String msg, String subject, boolean html) {
        commonAsyncService.sendEmail(email, msg, subject, html);
    }

    public String convertGroupToUri(List<Permission> permissions) {
        if (permissions == null) return null;
        return permissions.stream()
                .map(p -> p.getAction().trim().replace("/v1", ""))
                .collect(Collectors.joining(","));
    }

    public String getOrderStt(Long storeId) {
        return otpService.orderStt(storeId);
    }

    public synchronized boolean checkCodeValid(String code) {
        long currentTime = System.currentTimeMillis();
        storeQRCodeRandom.entrySet().removeIf(entry -> (currentTime - entry.getValue()) > 60000);
        if (storeQRCodeRandom.containsKey(code)) {
            return false;
        }
        storeQRCodeRandom.put(code, currentTime);
        return true;
    }
}