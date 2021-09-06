package com.kakaopay.invest.response;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectProductAllItemDto {

    /* 상품 ID */
    private String prdtId;

    /* 상품 상태 */
    private String prdtStat;

    /* 상품명 */
    private String prdtNm;

    /* 총 모집금액 */
    private Long totAmt;

    /* 현재 모집금액 */
    private Long curAmt;

    /* 현재 투자자 수 */
    private Long curUser;

    /* 상품모집 시작일시 */
    private String startedAt;

    /* 상품모집 종료일시 */
    private String finishedAt;
}
