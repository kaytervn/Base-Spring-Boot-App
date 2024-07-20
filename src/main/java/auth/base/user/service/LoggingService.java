package auth.base.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoggingService {
    void log(HttpServletRequest request, HttpServletResponse response, Object body);
}
