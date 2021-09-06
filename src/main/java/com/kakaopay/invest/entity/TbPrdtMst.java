package com.kakaopay.invest.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

//@DynamicUpdate
@Getter
@Setter
@RequiredArgsConstructor
@Builder // 빌더를 사용할 수 있게 함
@AllArgsConstructor
//@NoArgsConstructor
@Entity
@ToString
@Table(name="TB_PRDT_MST")
public class TbPrdtMst {

    @Id
    @Column(name = "PRDT_ID", nullable = false, length = 16)
    private String prdtId;

    @Column(name = "STAT_CD", nullable = false, length = 2)
    private String statCd;

    @Column(name = "PRDT_NM", nullable = false, length = 128)
    private String prdtNm;

    @Column(name = "AMT_TOT", nullable = false)
    private Long amtTot;

    @Column(name = "AMT_CUR")
    private Long amtCur;

    @Column(name = "USER_CUR")
    private Long userCur;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STARTED_AT", nullable = false)
    private Date  startedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FINISHED_AT", nullable = false)
    private Date finishedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "DB_INS_DATE")
    private Date  dbInsDate;

    @Column(name = "DB_INS_ID", length = 16)
    private String dbInsId;

    @Column(name = "DB_INS_TRD_UNI_KEY", length = 24)
    private String dbInsTrdUniKey;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DB_UPD_DATE")
    private Date dbUpdDate;

    @Column(name = "DB_UPD_ID", length = 16)
    private String dbUpdId;

    @Column(name = "DB_UPD_TRD_UNI_KEY", length = 24)
    private String dbUpdTrdUniKey;
/*
    @PrePersist
    public void dbUpdDate() {
        //this.dbUpdDate = new Date();
        //this.dbUpdDate = LocalDateTime.now();
    }*/
}


