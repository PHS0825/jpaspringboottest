package com.kakaopay.invest.controller;

import com.kakaopay.invest.constants.ReturnCode;
import com.kakaopay.invest.exception.InvestFailureException;
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
    public SelectProductAllResponseDto SelectProductAll() {
        SelectProductAllResponseDto retDto = investSearchService.SelectProductAll();
        logger.debug("SelectProductAllResponseDto : " + retDto);
        return retDto;
    }

    /* 투자하기 API */
    @PostMapping("/buyPrdt")
    public BuyProductResponseDto BuyProduct(@NotNull @RequestHeader(value = "X-USER-ID") String custId,
                                            @Valid @RequestBody BuyProductRequestDto buyProductRequestDto) {
        // 입력값 체크
        if ( custId.isBlank() ) {
            throw new InvestFailureException(ReturnCode.FORMAT_ERROR);
        }

        BuyProductResponseDto retDto = investPurchaseService.BuyProduct(custId, buyProductRequestDto);
        logger.debug("BuyProductResponseDto : " + retDto);
        return retDto;
    }

    /* 나의투자상품조회 API */
    @PostMapping("/selMyPrdt")
    public SelectMyProductResponseDto SelectProductByUserId(@NotNull @RequestHeader(value = "X-USER-ID") String custId) {

        // 입력값 체크
        if ( custId.isBlank() ) {
            throw new InvestFailureException(ReturnCode.FORMAT_ERROR);
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
}
