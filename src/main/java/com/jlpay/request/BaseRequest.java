package com.jlpay.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jlpay.constants.SubAccountConstant;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * @author lwc
 * @since 2024-05-30
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BaseRequest {
    @Builder.Default
    private String orgCode = SubAccountConstant.ORG_CODE;
    @Builder.Default
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timestamp = new Date();
    @Builder.Default
    private String nonceStr = "test";
    @Builder.Default
    private String signType = "SM3WithSM2WithDer";
    private String sign;
}
