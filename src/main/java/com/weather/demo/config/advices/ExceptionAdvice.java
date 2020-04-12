package com.example.demo.config.advices;

import com.example.demo.exceptions.ApiError;
import com.example.demo.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.exceptions.ApiError.message;

@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @Override
    protected final ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.error("Message Not Readable for " + ((ServletWebRequest) request).getRequest().getRequestURI().toString(), ex);
        return handleExceptionInternal(ex, message(HttpStatus.BAD_REQUEST, ex), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected final ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.error("Invalid Method Argument for " + ((ServletWebRequest) request).getRequest().getRequestURI().toString(), ex);
        return handleExceptionInternal(ex, message(HttpStatus.BAD_REQUEST, ex), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        logger.error("Invalid Request Parameter for " + ((ServletWebRequest) request).getRequest().getRequestURI().toString(), ex);
        return handleExceptionInternal(ex, message(HttpStatus.BAD_REQUEST, ex), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(ex.getMethod() + " method not supported for " + ((ServletWebRequest) request).getRequest().getRequestURI().toString());
        Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
        if (!CollectionUtils.isEmpty(supportedMethods)) {
            headers.setAllow(supportedMethods);
        }

        return handleExceptionInternal(ex, message(HttpStatus.METHOD_NOT_ALLOWED, ex), headers, status, request);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        logger.error("Argument Mismatch for " + ((ServletWebRequest) request).getRequest().getRequestURI().toString(), ex);
        return handleExceptionInternal(ex, message(HttpStatus.BAD_REQUEST, ex), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }

    @ExceptionHandler(value = {ValidationException.class})
    protected final ResponseEntity<Object> handleBadRequest(final ValidationException ex, final WebRequest request) {
        logger.error("Validation Error for " + ((ServletWebRequest) request).getRequest().getRequestURI().toString(), ex);
        List<ApiError.ValidationErrors> validationErrors = ex.getValidationErrors().stream().map(error -> new ApiError.ValidationErrors(error.getErrorCode(), error.getErrorMessage())).collect(Collectors.toList());
        return handleExceptionInternal(ex, message(HttpStatus.BAD_REQUEST, ex, validationErrors), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // 500
    // catch-all-unexpected-exceptions
    @ExceptionHandler({RuntimeException.class})
    protected ResponseEntity<Object> handleInternal(final Exception ex, final WebRequest request) {
        logger.error("500 Status Code", ex);
        return handleExceptionInternal(ex, message(HttpStatus.INTERNAL_SERVER_ERROR, ex), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
