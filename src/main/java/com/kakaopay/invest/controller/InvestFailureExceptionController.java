package com.kakaopay.invest.controller;
/*
public class InvestFailureExceptionController {

}
*/


import com.kakaopay.invest.constants.ReturnCode;
import com.kakaopay.invest.exception.InvestFailureException;
import com.kakaopay.invest.response.CommResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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


/*

//exception
@Getter
public class InvestFailureException extends RuntimeException{

    private ReturnCode returnCode;

    public InvestFailureException(String message, ReturnCode returnCode){
        super(message);
        this.returnCode = checkNotNull(returnCode);
    }
}



    //enum
    @Getter
    @AllArgsConstructor
    public enum ReturnCode {
        SUCCESS("0000", "Success"),

        ALREADY_IN_PROGRESS("1001", "Something is already in progress"),
        INVEST_FAILUE("1002", "invest failure occured");

        UNKNOWN_ERROR("9999", "Unexpectly unknown error has occurred");


        private String code;
        private String message;
    }




    //exceptionHandler
    @Slf4j
    @ControllerAdvice
    public class ExceptionHandler {

        @org.springframework.web.bind.annotation.ExceptionHandler(InvestFailureException.class)
        @ResponseStatus(HttpStatus.OK)
        @ResponseBody
        public CommonResponse<?> handlerManagedException(InvestFailureException e, HttpServletRequest request) {
            return new CommonResponse<>(e.getReturnCode());
        }

        @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        @ResponseBody
        public CommonResponse<?> handleException(Exception e) {
            log.error("[ExceptionHandler] Unexpected error occurred", e);
            return new CommonResponse<>(ReturnCode.UNKNOWN_ERROR);
        }
    }




    //dto
    @Setter
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class CommonResponse<T> {

        private String returnCode;
        private String returnMessage;

        public CommonResponse() {
            setReturnCode(ReturnCode.SUCCESS.getCode());
            setReturnMessage(ReturnCode.SUCCESS.getMessage());
        }

        public CommonResponse(ReturnCode returnCode) {
            this.returnCode = returnCode.getCode();
            this.returnMessage = returnCode.getMessage();
        }
    }


    //실제 서비스에서 사용
    throw new InvestFailureException(ReturnCode.ALREADY_IN_PROGRESS);
*/
