package com.app.exception.oauth2;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Map;

public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {
    public CustomOauthExceptionSerializer() {
        super(CustomOauthException.class);
    }

    @Override
    public void serialize(CustomOauthException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("code", value.getHttpErrorCode());
        gen.writeStringField("message", value.getMessage());
        gen.writeArrayFieldStart("data");
        gen.writeString(value.getOAuth2ErrorCode());
        gen.writeString(value.getMessage());
        gen.writeEndArray();
        Map<String, String> additionalInfo = value.getAdditionalInformation();
        if (additionalInfo != null) {
            additionalInfo.forEach((key, val) -> {
                try {
                    gen.writeStringField(key, val);
                } catch (IOException e) {
                    throw new RuntimeException("Error writing additional information", e);
                }
            });
        }
        gen.writeEndObject();
    }
}
