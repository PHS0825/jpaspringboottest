package com.kakaopay.invest.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvestControllerTest {

    @Test
    void selectProductAll() {
        InvestController investController = new InvestController();
        assertNotNull(investController.SelectProductAll());
    }

    @Test
    void buyProduct() {
    }

    @Test
    void selectProductByUserId() {
    }
}