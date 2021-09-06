package com.kakaopay.invest.exception;

import com.kakaopay.invest.constants.ReturnCode;
import lombok.*;

@Getter
public class InvestFailureException extends RuntimeException {

    private ReturnCode returnCode;

    public InvestFailureException(ReturnCode returnCode) {
        this.returnCode = returnCode;
    }

    public InvestFailureException(String message, ReturnCode returnCode) {
        super(message);
        this.returnCode = returnCode;
    }
}
