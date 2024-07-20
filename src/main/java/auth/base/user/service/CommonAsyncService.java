package auth.base.user.service;

import auth.base.user.constant.AppConstant;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommonAsyncService {
    EmailService emailService;
    RestTemplate restTemplate;
    @Qualifier(AppConstant.APP_THREAD_POOL_EXECUTOR)
    TaskExecutor taskExecutor;

    @Async
    public void sendEmail(String email, String msg, String subject, boolean html) {
        taskExecutor.execute(() -> {
            try {
                emailService.sendEmail(email, msg, subject, html);
            } catch (Exception e) {
                log.error("Error sending email: {}", e.getMessage(), e);
            }
        });
    }

    @Async
    public void pushToFirebase(String url, String data, HttpMethod httpMethod) {
        log.info("Firebase URL push: {}", url);
        taskExecutor.execute(() -> callFirebase(url, data, httpMethod));
    }

    @Async
    public void deleteFirebasePath(String url) {
        log.info("Firebase URL delete: {}", url);
        taskExecutor.execute(() -> callFirebase(url, null, HttpMethod.DELETE));
    }

    private void callFirebase(String url, String data, HttpMethod httpMethod) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(data, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, httpMethod, entity, String.class);
            log.info("Firebase call result - status: {}, has body: {}", response.getStatusCode(), response.hasBody());
        } catch (Exception ex) {
            log.error("Error calling Firebase: {}", ex.getMessage(), ex);
        }
    }
}
