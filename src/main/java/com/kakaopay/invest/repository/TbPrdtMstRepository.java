package com.kakaopay.invest.repository;

import com.kakaopay.invest.entity.TbPrdtMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
public interface TbPrdtMstRepository extends JpaRepository<TbPrdtMst, String> {

    public List<TbPrdtMst> findByStartedAtLessThanEqualAndFinishedAtGreaterThanEqualAndStatCdOrderByPrdtIdAsc(Date startedAt, Date finishedAt, String statCd);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE TbPrdtMst c " +
            "   SET c.amtCur = c.amtCur + :ivstAmt " +
            "     , c.userCur = c.userCur + 1 " +
            " WHERE c.prdtId = :prdtId " +
            "   AND c.amtTot >= ( c.amtCur + :ivstAmt )")
    public int updateAmtCurAndUserCur(@Param("prdtId") String prdtId, @Param("ivstAmt") long ivstAmt);
}