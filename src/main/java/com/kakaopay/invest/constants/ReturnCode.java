package com.kakaopay.invest.constants;

import lombok.*;

@Getter
@AllArgsConstructor
public enum ReturnCode {
        SUCCESS("0000", "Success"),

        ALREADY_IN_PROGRESS("1001", "Something is already in progress"),
        INVEST_FAILUE("1002", "invest failure occured"),
        NOT_FOUND("1003","Data not found"),

        FORMAT_ERROR("9999", "Data format error");
        //UNKNOWN_ERROR("9999", "Unexpectly unknown error has occurred");

        private String respCd;
        private String respMsg;
}
