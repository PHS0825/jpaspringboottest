package com.kakaopay.invest.service;


import com.kakaopay.invest.constants.ReturnCode;
import com.kakaopay.invest.exception.InvestFailureException;
import com.kakaopay.invest.request.BuyProductRequestDto;
import com.kakaopay.invest.response.CommResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

@Ignore
@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "local")
@SpringBootTest
public class investPurchaseServiceTest {

    @Rule
    public ExpectedException expectedRule = ExpectedException.none();

    @Autowired
    InvestPurchaseService investPurchaseService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
    상품구매 - 정상 리턴 테스트
     */
    @Test
    public void BuyProductWhenResultIsSuccess() {
        String custId = Long.toString(Instant.now().toEpochMilli());
        BuyProductRequestDto buyProductRequestDto = new BuyProductRequestDto("TEST0001", 1000L);

        logger.debug("custId : " + custId);

        CommResponseDto ret = investPurchaseService.BuyProduct(custId, buyProductRequestDto);
        logger.debug("retCd : " + ret.getRespCd());
        logger.debug("retMsg : " + ret.getRespMsg());
        Assert.assertEquals(ret.getRespCd(), ReturnCode.SUCCESS.getRespCd());
    }

    /*
    상품구매 - 이미 투자한 상품 리턴 테스트
     */
    @Test
    public void BuyProductWhenResultIsAlreadyInvested() {
        String custId = Long.toString(Instant.now().toEpochMilli());
        BuyProductRequestDto buyProductRequestDto = new BuyProductRequestDto("TEST0001", 1000L);

        logger.debug("custId : " + custId);

        // 최초투자
        investPurchaseService.BuyProduct(custId, buyProductRequestDto);
        try {
            // 중복투자
            CommResponseDto ret = investPurchaseService.BuyProduct(custId, buyProductRequestDto);
        } catch (InvestFailureException e) {
            logger.debug("retCd : " + e.getReturnCode().getRespCd());
            logger.debug("retMsg : " + e.getReturnCode().getRespMsg());
            Assert.assertEquals(e.getReturnCode().getRespCd(), ReturnCode.ALREADY_INVESTED.getRespCd());
        }
    }

    /*
    상품구매 - 없는 상품 리턴 테스트
     */
    @Test
    public void BuyProductWhenResultIsNoDataFound() {
        String custId = Long.toString(Instant.now().toEpochMilli());
        BuyProductRequestDto buyProductRequestDto = new BuyProductRequestDto("NoDataFoundTest", 1000L);

        logger.debug("custId : " + custId);
        try {
            // 중복투자
            CommResponseDto ret = investPurchaseService.BuyProduct(custId, buyProductRequestDto);
        } catch (InvestFailureException e) {
            logger.debug("retCd : " + e.getReturnCode().getRespCd());
            logger.debug("retMsg : " + e.getReturnCode().getRespMsg());
            Assert.assertEquals(e.getReturnCode().getRespCd(), ReturnCode.NO_DATA_FOUND.getRespCd());
        }
    }


    /*
    상품구매 - 투자 실패 리턴 테스트
     */
    @Test
    public void BuyProductWhenResultIsInvestFailue() {
        String custId = Long.toString(Instant.now().toEpochMilli());
        BuyProductRequestDto buyProductRequestDto = new BuyProductRequestDto("TEST0002", 1000L);

        logger.debug("custId : " + custId);
        try {
            // 중복투자
            CommResponseDto ret = investPurchaseService.BuyProduct(custId, buyProductRequestDto);
        } catch (InvestFailureException e) {
            logger.debug("retCd : " + e.getReturnCode().getRespCd());
            logger.debug("retMsg : " + e.getReturnCode().getRespMsg());
            Assert.assertEquals(e.getReturnCode().getRespCd(), ReturnCode.INVEST_FAILUE.getRespCd());
        }
    }


    /*
    상품구매 - 시작일자 오류 리턴 테스트
     */
    @Test
    public void BuyProductWhenResultIsNotInvestableStartedDate() {
        String custId = Long.toString(Instant.now().toEpochMilli());
        BuyProductRequestDto buyProductRequestDto = new BuyProductRequestDto("TEST0003", 1000L);

        logger.debug("custId : " + custId);
        try {
            CommResponseDto ret = investPurchaseService.BuyProduct(custId, buyProductRequestDto);
        } catch (InvestFailureException e) {
            logger.debug("retCd : " + e.getReturnCode().getRespCd());
            logger.debug("retMsg : " + e.getReturnCode().getRespMsg());
            Assert.assertEquals(e.getReturnCode().getRespCd(), ReturnCode.NOT_INVESTABLE_DATE.getRespCd());
        }
    }

    /*
    상품구매 - 종료일자 오류 리턴 테스트
     */
    @Test
    public void BuyProductWhenResultIsNotInvestableFinishedDate() {
        String custId = Long.toString(Instant.now().toEpochMilli());
        BuyProductRequestDto buyProductRequestDto = new BuyProductRequestDto("TEST0004", 1000L);

        logger.debug("custId : " + custId);
        try {
            CommResponseDto ret = investPurchaseService.BuyProduct(custId, buyProductRequestDto);
        } catch (InvestFailureException e) {
            logger.debug("retCd : " + e.getReturnCode().getRespCd());
            logger.debug("retMsg : " + e.getReturnCode().getRespMsg());
            Assert.assertEquals(e.getReturnCode().getRespCd(), ReturnCode.NOT_INVESTABLE_DATE.getRespCd());
        }
    }

    /*
    상품구매 - 투자금액초과 오류 리턴 테스트
     */
    @Test
    public void BuyProductWhenResultIsAmountExceeded() {
        String custId = Long.toString(Instant.now().toEpochMilli());
        BuyProductRequestDto buyProductRequestDto = new BuyProductRequestDto("TEST0005", 10000000L);

        logger.debug("custId : " + custId);
        try {
            CommResponseDto ret = investPurchaseService.BuyProduct(custId, buyProductRequestDto);
        } catch (InvestFailureException e) {
            logger.debug("retCd : " + e.getReturnCode().getRespCd());
            logger.debug("retMsg : " + e.getReturnCode().getRespMsg());
            Assert.assertEquals(e.getReturnCode().getRespCd(), ReturnCode.AMOUNT_EXCEEDED.getRespCd());
        }
    }
}