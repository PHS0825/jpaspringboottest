package com.kakaopay.invest.service;

import com.kakaopay.invest.constants.Constants;
import com.kakaopay.invest.constants.ReturnCode;
import com.kakaopay.invest.entity.TbCustMst;
import com.kakaopay.invest.entity.TbPrdtMst;
import com.kakaopay.invest.exception.InvestFailureException;
import com.kakaopay.invest.repository.TbCustMstRepository;
import com.kakaopay.invest.repository.TbPrdtMstRepository;
import com.kakaopay.invest.request.BuyProductRequestDto;
import com.kakaopay.invest.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InvestPurchaseService {

    @Autowired
    private TbPrdtMstRepository tbPrdtMstRepo;

    @Autowired
    private TbCustMstRepository tbCustMstRepo;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public BuyProductResponseDto BuyProduct(String custId, BuyProductRequestDto buyProductRequestDto) {
        BuyProductResponseDto retDto = new BuyProductResponseDto();

        Date nowTime = new Date();

        String prdtId = buyProductRequestDto.getPrdtId();
        Long ivstAmt = buyProductRequestDto.getIvstAmt();

        // 거래내역 insert

        // 이전에 투자한 상품인지 확인
        Optional<TbCustMst> custItem = tbCustMstRepo.findByCustIdAndPrdtId(custId, prdtId);
        if ( !custItem.isEmpty() ) {
            throw new InvestFailureException(ReturnCode.ALREADY_INVESTED);
        }

        // 상품 가져오기
        Optional<TbPrdtMst> prdtItem = tbPrdtMstRepo.findById(prdtId);
        logger.debug("tbPrdtMst : " + prdtItem.toString());
        if ( prdtItem.isEmpty() ) {
            //상품 없음
            throw new InvestFailureException(ReturnCode.NO_DATA_FOUND);
        }
        TbPrdtMst buyItem = prdtItem.get();
        logger.debug("buyItem : " + buyItem.toString());

        // 투자 가능한지 확인 로직 START

        // 상품 상태 확인
        if ( ! buyItem.getStatCd().equals(Constants.PRDT_MST_STAT_CD_OPEN.toString()) ) {
            //상품 상태 오류
            throw new InvestFailureException(ReturnCode.INVEST_FAILUE);
        }

        // 시작일자 확인
        int compStartdAt = buyItem.getStartedAt().compareTo(nowTime);
        if ( compStartdAt > 0 ) {
            //시작일자 오류
            throw new InvestFailureException(ReturnCode.NOT_INVESTABLE_DATE);
        }

        // 종료일자 확인
        int compFinishedAt = buyItem.getFinishedAt().compareTo(nowTime);
        if ( compFinishedAt < 0 ) {
            //종료일자 오류
            throw new InvestFailureException(ReturnCode.NOT_INVESTABLE_DATE);
        }

        // 금액 확인
        if ( buyItem.getAmtCur() + ivstAmt >= buyItem.getAmtTot() ) {
            //상품 투자가능금액 초과
            throw new InvestFailureException(ReturnCode.AMOUNT_EXCEEDED);
        }

        // 투자 가능한지 확인 로직 END

        // 상품 투자금 update
        int ret = tbPrdtMstRepo.updateAmtCurAndUserCur(prdtId, ivstAmt);
        if ( ret == 0 ) {
            // 투자금 초과로 투자 실패
            throw new InvestFailureException(ReturnCode.AMOUNT_EXCEEDED);
        }

        // 유저 테이블 insert
        TbCustMst insCustItem = new TbCustMst();
        insCustItem.setPrdtId(prdtId);
        insCustItem.setCustId(custId);
        insCustItem.setStatCd(Constants.USER_MST_STAT_CD_OPEN.toString());
        insCustItem.setAmtCur(ivstAmt);
        insCustItem.setStartedAt(nowTime);
        // PHS 정상여부 확인 필요
        tbCustMstRepo.save(insCustItem);

        // 거래내역 update

        return retDto;
    }
}
