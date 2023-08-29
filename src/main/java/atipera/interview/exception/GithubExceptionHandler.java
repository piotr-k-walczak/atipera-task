package atipera.interview.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@ControllerAdvice
public class GithubExceptionHandler {

    private final ObjectMapper objectMapper;

    public GithubExceptionHandler(@Autowired ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler({HttpClientErrorException.NotFound.class, WebClientResponseException.NotFound.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ErrorMessage userNotFoundExceptionHandling(Exception exception, WebRequest request) {
        log.info("User Not Found Handling");
        return new ErrorMessage(404, "User not found");
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    String notAcceptableMediaTypeExceptionHandling(Exception exception, WebRequest request) throws JsonProcessingException {
        log.info("Not Acceptable Media Type Handling");
        return objectMapper.writeValueAsString(
                new ErrorMessage(
                        406,
                        "The API can't produce XML"
                ));
    }

    @ExceptionHandler( { HttpClientErrorException.Forbidden.class, WebClientResponseException.Forbidden.class })
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    @ResponseBody
    ErrorMessage tooManyRequestsHandling(Exception exception, WebRequest request) {
        log.info("Too Many Requests Handling");
        return new ErrorMessage(429, "Too many requests to Github API");
    }

    private record ErrorMessage(int statusCode, String message) {}
}
