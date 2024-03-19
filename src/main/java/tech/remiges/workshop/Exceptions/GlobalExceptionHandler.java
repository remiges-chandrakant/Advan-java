package tech.remiges.workshop.Exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import tech.remiges.workshop.Utils.CommonUtils;
import tech.remiges.workshop.WorkshopResponse.WorkshopResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WorkshopResponse> handleException(Exception ex) {
        WorkshopResponse response = new WorkshopResponse();
        response.setStatus(CommonUtils.FAIL);
        response.setStatus_Msg("Error occured" + ex.toString());
        // ex.printStackTrace();
        logger.debug(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Add more exception handling methods as needed
}
