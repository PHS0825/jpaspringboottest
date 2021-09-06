package com.kakaopay.invest.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
//@NoArgsConstructor
@AllArgsConstructor
public class MyProductListResponse extends CommResponse {
    public MyProductListResponse() {
        this.ProcuctItemList = new ArrayList<>();
    }

    /* 상품 리스트 */
    private ArrayList<MyProductItem> ProcuctItemList;
}

