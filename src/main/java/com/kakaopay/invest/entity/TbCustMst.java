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
@Data
@ToString
@IdClass(TbCustMstId.class)
@Table(name="TB_CUST_MST")
public class TbCustMst {

    @Id
    @Column(name = "CUST_ID", nullable = false, length = 16)
    private String custId;

    @Id
    @Column(name = "PRDT_ID", nullable = false, length = 16)
    private String prdtId;

    @Column(name = "STAT_CD", nullable = false, length = 2)
    private String statCd;

    @Column(name = "AMT_CUR")
    private Long amtCur;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STARTED_AT", nullable = false)
    private Date startedAt;

    @Temporal(TemporalType.TIMESTAMP)
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


    @PrePersist
    public void insertCreatedAt() {
        Date nowDate = new Date();

        setDbInsDate(nowDate);
        setDbInsId("APP");
        //setDbInsTrdUniKey();

        setDbUpdDate(nowDate);
        setDbUpdId("APP");
        //setDbUpdTrdUniKey();
    }
}


