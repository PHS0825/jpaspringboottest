package com.kakaopay.invest.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
//@NoArgsConstructor
@AllArgsConstructor
public class SelectMyProductResponseDto extends CommResponseDto {
    public SelectMyProductResponseDto() {
        this.ProcuctItemList = new ArrayList<>();
    }

    /* 상품 리스트 */
    private ArrayList<SelectMyProductItemDto> ProcuctItemList;
}

