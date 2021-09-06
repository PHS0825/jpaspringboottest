package com.kakaopay.invest.controller;

import com.kakaopay.invest.request.BuyProductRequestDto;
import com.kakaopay.invest.response.*;
import com.kakaopay.invest.service.InvestPurchaseService;
import com.kakaopay.invest.service.InvestSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class InvestController {

    @Autowired
    InvestSearchService investSearchService;

    @Autowired
    InvestPurchaseService investPurchaseService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /* 전체투자상품조회 API */
    @PostMapping("/selPrdtInfo")
    public SelectProductAllResponseDto SelectProductAll() {
        SelectProductAllResponseDto retDto = investSearchService.SelectProductAll();
        logger.debug("SelectProductAllResponseDto : " + retDto);
        return retDto;
    }

    /* 투자하기 API */
    @PostMapping("/buyPrdt")
    public BuyProductResponseDto BuyProduct(@RequestHeader(value = "X-USER-ID") String custId,
                                            @Valid @RequestBody BuyProductRequestDto buyProductRequestDto) {
        // 입력값 체크
        if ( custId.isBlank() )
        {
            //retMap.put("respCd", "999");
            //retMap.put("respMsg", "아이디 없음");
            //return retMap;
        }

        BuyProductResponseDto retDto = investPurchaseService.BuyProduct(custId, buyProductRequestDto);
        logger.debug("BuyProductResponseDto : " + retDto);
        return retDto;
    }

    /* 나의투자상품조회 API */
    @PostMapping("/selMyPrdt")
    public SelectMyProductResponseDto SelectProductByUserId(@RequestHeader(value = "X-USER-ID") String custId) {

        // 입력값 체크
        if ( custId.isBlank() )
        {
            //retMap.put("respCd", "999");
            //retMap.put("respMsg", "아이디 없음");
            //return retMap;
        }

        SelectMyProductResponseDto retDto = investSearchService.SelectProductByUserId(custId);
        return retDto;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommResponseDto processValidationError(MethodArgumentNotValidException ex) {
        CommResponseDto retDto = new CommResponseDto();

        BindingResult result = ex.getBindingResult();
        if (result.hasErrors()) {
            FieldError error = result.getFieldError();
            logger.debug("getDefaultMessage[" + error.getDefaultMessage() + "]");

            String message[] = error.getDefaultMessage().split("\\|");
            retDto.setRespCd(message[0]);
            retDto.setRespMsg(message[1]);
        }
        return retDto;
    }

    /*
    @PostMapping("/crdtAuth")
    public Map<String, String> test(@Valid @RequestBody CrdtAuthDTO crdtAuthDTO){

        SimpleDateFormat format1 = new SimpleDateFormat("yyMMddHHmmss");
        Date time = new Date();
        String trdDate = format1.format(time).substring(0,6);
        String trdTime = format1.format(time).substring(7,6);

        String uuid = UUID.randomUUID().toString().replace("-", "");

        TbAuthTrdLst tbAuthTrdLst = new TbAuthTrdLst();
        tbAuthTrdLst.setTrdDate(trdDate);
        tbAuthTrdLst.setTrdTime(trdTime);
        tbAuthTrdLst.setTrdUniKey(uuid);
        //tbAuthTrdLst.setTrdType(Constants.TRD_TYPE_AUTH.toString());
        tbAuthTrdLst.setMaskCardNo("123");
        tbAuthTrdLst.setEncCardNo("456");

        Long amtTot = Long.parseLong(crdtAuthDTO.getAmtTot());
        tbAuthTrdLst.setAmtTot(amtTot);

        Long amtTax = Long.parseLong(crdtAuthDTO.getAmtTax());
        if ( amtTax <= 0 )
            amtTax = amtTot / 10;
        tbAuthTrdLst.setAmtTax(amtTax);

        tbAuthTrdLst.setExpDate(crdtAuthDTO.getExpDate());
        tbAuthTrdLst.setInsMon(crdtAuthDTO.getInsMon());
        tbAuthTrdLst.setRespCd(Constants.RESP_CD_DEFAULT.toString());

        Map<String, String> retMap = investService.CrdtAuth(crdtAuthDTO);
        return retMap;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();

        Map<String, String> retMap = new HashMap<>();

        if ( result.hasErrors() ) {
            FieldError error = result.getFieldError();
            String message[] = error.getDefaultMessage().split("\\|");
            retMap.put("respCd", message[0]);
            retMap.put("respMsg", message[1]);

            logger.debug("===============================================");
            logger.debug("getDefaultMessage[" + error.getDefaultMessage() + "]");
            logger.debug("===============================================");
        }
        return retMap;
    }
     */
}
