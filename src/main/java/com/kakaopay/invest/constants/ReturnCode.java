package com.kakaopay.invest.constants;

import lombok.*;

@Getter
@AllArgsConstructor
public enum ReturnCode {
        SUCCESS("0000", "Success"),
        NO_DATA_FOUND("1001","No data found"),
        ALREADY_INVESTED("1002", "already invested"),
        SOLD_OUT("1003","Sold out"),
        AMOUNT_EXCEEDED("1004","Total amount exceeded"),
        NOT_INVESTABLE_DATE("1005","Not an investable date"),
        INVEST_FAILUE("1006", "invest failure occured"),
        ALREADY_IN_PROGRESS("1009", "Something is already in progress"),

        FORMAT_ERROR("9999", "Data format error");

        private String respCd;
        private String respMsg;
}
