package com.kakaopay.invest.repository;

import com.kakaopay.invest.entity.TbPrdtMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface TbPrdtMstRepository extends JpaRepository<TbPrdtMst, String> {

    public List<TbPrdtMst> findByStartedAtLessThanEqualAndFinishedAtGreaterThanEqualAndStatCdOrderByPrdtIdAsc(Date startedAt, Date finishedAt, String statCd);

    @Modifying
    @Query("UPDATE TbPrdtMst c " +
            "   SET c.amtCur = c.amtCur + :ivstAmt " +
            "     , c.userCur = c.userCur + 1 " +
            " WHERE c.prdtId = :prdtId " +
            "   AND c.amtTot >= ( c.amtCur + :ivstAmt )")
    public int updateAmtCurAndUserCur(@Param("prdtId") String prdtId, @Param("ivstAmt") long ivstAmt);

    /*
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE TB_PRDT_MST AA " +
            "   SET AA.AMT_CUR = AA.AMT_CUR + :ivstAmt " +
            "     , AA.USER_CUR = AA.USER_CUR + 1 " +
            " WHERE AA.PRDT_ID = :prdtId " +
            "   AND AA.AMT_TOT >= ( AA.AMT_CUR + :ivstAmt )")
    public Optional<TbPrdtMst> updateItem(@Param("prdtId") String prdtId,
                                          @Param("ivstAmt") long ivstAmt);


    @Query( "UPDATE TB_PRDT_MST AA \n" +
            "   SET AA.AMT_CUR = AA.AMT_CUR + :ivstAmt \n" +
            "     , AA.USER_CUR = AA.USER_CUR + 1 \n" +
            "     , AA.DB_UPD_DATE = sysdate() \n" +
            "     , AA.DB_UPD_ID = :trdId \n" +
            "     , AA.DB_UPD_TRD_UNI_KEY = :trdUniKey \n" +
            " WHERE AA.PRDT_ID = :prdtId \n" +
            "   AND AA.AMT_TOT >= ( AA.AMT_CUR + :ivstAmt )")
    public Optional<TbPrdtMst> updateItem(@Param("prdtId") String prdtId,
                                          @Param("ivstAmt") long ivstAmt,
                                          @Param("trdId") String trdId,
                                          @Param("trdUniKey") String trdUniKey);
    */
}

/*
    @Query(value = "select * from failed_message s " +
            "where s.seq >= :#{#startSeq} " +
            "and s.seq < :#{#endSeq} " +
            "and s.status = :#{#status} " +
            "and s.created_date >= :#{#startDateTime} " +
            "and s.created_date <= :#{#endDateTime} " +
            "order by s.created_date"
            , nativeQuery = true)
    Page<FailedMessage> findBySeqAndStatus(@Param("startSeq") Long startSeq,
                                           @Param("endSeq") Long endSeq,
                                           @Param("status") String status,
                                           @Param("startDateTime") LocalDateTime startDateTime,
                                           @Param("endDateTime") LocalDateTime endDateTime,
                                           Pageable pageable);
 */

/*
UPDATE TB_PRDT_MST AA
   SET AA.TOTAL_INVESTING_AMOUNT += {addAmount}
     , AA.TOTAL_USER_CNT += {addCnt}
 WHERE AA.PRODUCT_ID = {prodId}
   AND AA.TOTAL_INVESTING_AMOUNT <= AA.TOTAL_INVESTING_AMOUNT + {addAmount}
;

UPDATE TB_USER_MST AA
   SET AA.TOTAL_INVESTING_AMOUNT += {addAmount}
 WHERE AA.PRODUCT_ID = {prodId}
   AND AA.USER_ID = {userId}
 */