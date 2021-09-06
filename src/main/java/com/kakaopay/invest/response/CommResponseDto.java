package com.kakaopay.invest.response;

import com.kakaopay.invest.constants.ReturnCode;
import lombok.*;

@Data
//@Builder
//@NoArgsConstructor
public class CommResponseDto {
    public CommResponseDto() {
        setRespCd(ReturnCode.SUCCESS.getRespCd());
        setRespMsg(ReturnCode.SUCCESS.getRespMsg());
    }
    public CommResponseDto(ReturnCode returnCode) {
        setRespCd(returnCode.getRespCd());
        setRespMsg(returnCode.getRespMsg());
    }

    /* 응답코드 */
    private String respCd;

    /* 응답 메시지 */
    private String respMsg;
}
