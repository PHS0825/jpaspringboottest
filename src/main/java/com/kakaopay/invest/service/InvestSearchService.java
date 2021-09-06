package com.kakaopay.invest.service;

import com.kakaopay.invest.constants.Constants;
import com.kakaopay.invest.constants.ReturnCode;
import com.kakaopay.invest.entity.TbCustMst;
import com.kakaopay.invest.entity.TbPrdtMst;
import com.kakaopay.invest.exception.InvestFailureException;
import com.kakaopay.invest.repository.TbPrdtMstRepository;
import com.kakaopay.invest.repository.TbCustMstRepository;
import com.kakaopay.invest.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class InvestSearchService {

    @Autowired
    private TbPrdtMstRepository tbPrdtMstRepo;

    @Autowired
    private TbCustMstRepository tbCustMstRepo;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public AllProductListResponse SelectProductAll() {
        AllProductListResponse retDto = new AllProductListResponse();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date nowTime = new Date();

        /* 전체 상품을 가져온다. */
        List<TbPrdtMst> tbPrdtMst = tbPrdtMstRepo.findByStartedAtLessThanEqualAndFinishedAtGreaterThanEqualAndStatCdOrderByPrdtIdAsc(nowTime, nowTime, Constants.PRDT_MST_STAT_CD_OPEN);

        // 상품 건수 체크
        logger.debug("tbPrdtMst : " + tbPrdtMst.size());
        if ( tbPrdtMst.size() <= 0 ) {
            throw new InvestFailureException(ReturnCode.NO_DATA_FOUND);
        }

        tbPrdtMst.forEach(s->{
            logger.debug("tbPrdtMst : " + s.toString());

            AllProductItem item = new AllProductItem();

            // 상품모집상태
            if ( s.getAmtTot() > s.getAmtCur() )
                item.setPrdtStat("모집중");
            else
                item.setPrdtStat("모집완료");

            item.setPrdtId(s.getPrdtId());  // 상품ID
            item.setPrdtNm(s.getPrdtNm());  // 상품명
            item.setTotAmt(s.getAmtTot());   // 총 모집금액
            item.setCurAmt(s.getAmtCur());   // 현재 모집금액
            item.setCurUser(s.getUserCur()); // 현재 투자자 수
            item.setStartedAt(dateFormat.format(s.getStartedAt())); // 상품모집 시작일시
            item.setFinishedAt(dateFormat.format(s.getFinishedAt()));   // 상품모집 종료일시
            retDto.getProcuctItemList().add(item);
        });
        logger.debug("retDto : " + retDto.toString());
        return retDto;
    }

    public MyProductListResponse SelectProductByUserId(String custId) {
        MyProductListResponse retDto = new MyProductListResponse();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 사용자 투자상품 가져오기
        List<TbCustMst> tbCustMst = tbCustMstRepo.findByCustIdAndStatCd(custId, Constants.USER_MST_STAT_CD_OPEN);
        logger.debug("TbCustMst : " + tbCustMst.size());
        
        // 투자한 상품 없음
        if ( tbCustMst.size() < 1 ) {
            throw new InvestFailureException(ReturnCode.NO_DATA_FOUND);
        }

        tbCustMst.forEach(s->{
            logger.debug("tbCustMst : " + s.toString());

            Optional<TbPrdtMst> tbPrdtMst = tbPrdtMstRepo.findById(s.getPrdtId());
            logger.debug("tbPrdtMst : " + tbPrdtMst.toString());
            if ( tbPrdtMst.isEmpty() ) {
                throw new InvestFailureException(ReturnCode.NO_DATA_FOUND);
            }
            TbPrdtMst prdtItem = tbPrdtMst.get();

            MyProductItem itemDto = new MyProductItem();
            itemDto.setPrdtId(s.getPrdtId());
            itemDto.setPrdtNm(prdtItem.getPrdtNm());
            itemDto.setTotAmt(prdtItem.getAmtTot());
            itemDto.setIvstAmt(s.getAmtCur());
            itemDto.setStartedAt(dateFormat.format(s.getStartedAt()));
            retDto.getProcuctItemList().add(itemDto);
        });
        logger.debug("retDto : " + retDto.toString());
        return retDto;
    }
}
