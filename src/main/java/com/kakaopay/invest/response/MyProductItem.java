package com.kakaopay.invest.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyProductItem {

    /* 상품 ID */
    private String prdtId;

    /* 상품명 */
    private String prdtNm;

    /* 현재 모집금액 */
    private Long totAmt;

    /* 현재 투자자 수 */
    private Long ivstAmt;

    /* 상품모집 시작일시 */
    private String startedAt;
}
