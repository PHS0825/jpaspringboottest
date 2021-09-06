package com.kakaopay.invest.request;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuyProductRequestDto {

    /* 상품ID */
    @NotNull(message="9999|prdtId is not null")
    @Size(max=16, message="9999|prdtId size must be less than 16 charactors")
    private String prdtId;

    /* 투자금액 */
    @NotNull(message="9999|ivstAmt is not null")
    @Min(value=1, message="9999|ivstAmt is not null")
    private Long ivstAmt;

}
