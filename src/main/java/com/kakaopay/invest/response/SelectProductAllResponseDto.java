package com.kakaopay.invest.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
//@NoArgsConstructor
@AllArgsConstructor
public class SelectProductAllResponseDto extends CommResponseDto {

    public SelectProductAllResponseDto() {
        this.ProcuctItemList = new ArrayList<>();
    }

    /* 상품 리스트 */
    private ArrayList<SelectProductAllItemDto> ProcuctItemList;
}

