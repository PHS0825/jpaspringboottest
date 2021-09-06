package com.kakaopay.invest.controller;

import com.kakaopay.invest.constants.ReturnCode;
import com.kakaopay.invest.exception.InvestFailureException;
import com.kakaopay.invest.request.BuyProductRequest;
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
import javax.validation.constraints.NotNull;


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
    public AllProductListResponse SelectProductAll() {
        AllProductListResponse retDto = investSearchService.SelectProductAll();
        logger.debug("AllProductListResponse : " + retDto);
        return retDto;
    }

    /* 투자하기 API */
    @PostMapping("/buyPrdt")
    public BuyProductResponse BuyProduct(@NotNull @RequestHeader(value = "X-USER-ID") String custId,
                                            @Valid @RequestBody BuyProductRequest buyProductRequest) {
        // 입력값 체크
        if ( custId.isBlank() ) {
            throw new InvestFailureException(ReturnCode.FORMAT_ERROR);
        }

        BuyProductResponse retDto = investPurchaseService.BuyProduct(custId, buyProductRequest);
        logger.debug("BuyProductRequest : " + retDto);
        return retDto;
    }

    /* 나의투자상품조회 API */
    @PostMapping("/selMyPrdt")
    public MyProductListResponse SelectProductByUserId(@NotNull @RequestHeader(value = "X-USER-ID") String custId) {

        // 입력값 체크
        if ( custId.isBlank() ) {
            throw new InvestFailureException(ReturnCode.FORMAT_ERROR);
        }

        MyProductListResponse retDto = investSearchService.SelectProductByUserId(custId);
        logger.debug("MyProductListResponse : " + retDto);
        return retDto;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommResponse processValidationError(MethodArgumentNotValidException ex) {
        CommResponse retDto = new CommResponse();

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
}
