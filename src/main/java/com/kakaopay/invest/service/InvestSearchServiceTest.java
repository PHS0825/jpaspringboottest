package com.kakaopay.invest.service;


import com.kakaopay.invest.constants.ReturnCode;
import com.kakaopay.invest.exception.InvestFailureException;
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

@Ignore
@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "local")
@SpringBootTest
public class InvestSearchServiceTest {

    @Rule
    public ExpectedException expectedRule = ExpectedException.none();

    @Autowired
    InvestSearchService investSearchService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
    전체투자상품조회 - 정상 리턴 테스트
     */
    @Test
    public void selectProductAllWhenResultIsSuccess() {
        CommResponseDto ret = investSearchService.SelectProductAll();
        logger.debug("retCd : " + ret.getRespCd());
        logger.debug("retMsg : " + ret.getRespMsg());
        Assert.assertEquals(ret.getRespCd(), ReturnCode.SUCCESS.getRespCd());
    }

    /*
    나의투자상품조회 - 정상 리턴 테스트
     */
    @Test
    public void selectProductByUserIdWhenResultIsSuccess() {
        CommResponseDto ret = investSearchService.SelectProductByUserId("1001");
        logger.debug("retCd : " + ret.getRespCd());
        logger.debug("retMsg : " + ret.getRespMsg());
        Assert.assertEquals(ret.getRespCd(), ReturnCode.SUCCESS.getRespCd());
    }

    /*
    나의투자상품조회 - 투자상품 없음 리턴 테스트
     */
    @Test
    public void selectProductByUserIdWhenResultIsNoDataFound() {
        try {
            CommResponseDto ret = investSearchService.SelectProductByUserId("NoDataFoundTest");
        } catch (InvestFailureException e) {
            logger.debug("retCd : " + e.getReturnCode().getRespCd());
            logger.debug("retMsg : " + e.getReturnCode().getRespMsg());
            Assert.assertEquals(e.getReturnCode().getRespCd(), ReturnCode.NO_DATA_FOUND.getRespCd());
        }
    }
}