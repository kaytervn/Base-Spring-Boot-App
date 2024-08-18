package com.app.mapper;

import com.app.constant.AppConstant;
import com.app.dto.KeyWrapperDto;
import com.app.utils.AESUtils;
import com.app.utils.ConvertUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ABasicMapper {

    @Named("encrypt")
    default String encrypt(String secretKey, String value) {
        return AESUtils.encrypt(secretKey, value, AppConstant.AES_ZIP_ENABLE);
    }

    @Named("decrypt")
    default String decrypt(String secretKey, String value) {
        return AESUtils.decrypt(secretKey, value, AppConstant.AES_ZIP_ENABLE);
    }

    @Named("decryptAndEncrypt")
    default String decryptAndEncrypt(KeyWrapperDto keyWrapper, String value) {
        String decryptedValue = AESUtils.decrypt(keyWrapper.getDecryptKey(), value, AppConstant.AES_ZIP_ENABLE);
        return AESUtils.encrypt(keyWrapper.getEncryptKey(), decryptedValue, AppConstant.AES_ZIP_ENABLE);
    }

    @Named("convertDoubleToString")
    default String convertDoubleToString(Double value) {
        return ConvertUtils.convertDoubleToString(value);
    }

    @Named("toLowerCase")
    default String toLowerCase(String value) {
        return value != null ? value.toLowerCase() : null;
    }
}
