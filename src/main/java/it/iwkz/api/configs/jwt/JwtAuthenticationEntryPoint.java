package it.iwkz.api.configs.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.iwkz.api.payloads.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        logger.error("unauthorized error. Message - {}", e.getMessage());

        final ErrorResponse apiError = new ErrorResponse(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage(), e.getMessage());

        httpServletResponse.setContentType("application/json");
        httpServletResponse.getOutputStream()
                .println(new ObjectMapper().writeValueAsString(apiError));
    }
}
