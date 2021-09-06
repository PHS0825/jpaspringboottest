package com.kakaopay.invest.controller;

import com.kakaopay.invest.constants.ReturnCode;
import com.kakaopay.invest.exception.InvestFailureException;
import com.kakaopay.invest.response.CommResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

//exceptionHandler
@Slf4j
@ControllerAdvice
public class InvestFailureExceptionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(InvestFailureException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommResponseDto handlerManagedException(InvestFailureException e, HttpServletRequest request) {
        return new CommResponseDto(e.getReturnCode());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public CommResponseDto handleException(Exception e) {
        logger.error("[ExceptionHandler] Unexpected error occurred", e);
        return new CommResponseDto(ReturnCode.INVEST_FAILUE);
    }

    /*
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommResponseDto processValidationError(MethodArgumentNotValidException ex) {
        CommResponseDto retDto = new CommResponseDto();

        BindingResult result = ex.getBindingResult();
        if ( result.hasErrors() ) {
            FieldError error = result.getFieldError();
            logger.debug("getDefaultMessage[" + error.getDefaultMessage() + "]");

            String message[] = error.getDefaultMessage().split("\\|");
            retDto.setRespCd(message[0]);
            retDto.setRespMsg(message[1]);
        }
        return retDto;
    }
    */

}
