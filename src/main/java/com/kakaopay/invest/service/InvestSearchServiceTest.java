package com.kakaopay.invest.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class InvestSearchServiceTest {

    @Test
    void selectProductAll() {
        InvestSearchService investSearchService = new InvestSearchService();
        //assertEquals(investSearchService.SelectProductAll(), 11);
        assertNotNull(investSearchService.SelectProductAll());
    }

    @Test
    void selectProductByUserId() {
    }
}