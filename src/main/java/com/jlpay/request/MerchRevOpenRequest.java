package com.jlpay.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author lwc
 * @since 2024-05-30
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MerchRevOpenRequest extends BaseRequest {
    private String orgCode;
    private String merchType;
    private String mchId;
    private String licenseNumber;
    private String licenseName;
    private String licenseAddress;
    private String licenseBeginDate;
    private String licenseEndDate;
    private String licenseFrontSourceId;
    private String legalName;
    private String legalNumber;
    private String legalBeginDate;
    private String legalEndDate;
    private String legalFrontSourceId;
    private String legalBackSourceId;
    private String mobile;
    private String bankCardNo;
    private String bankCardName;
    private String bankCode;
    private String bankName;
    private String bankBranchCode;
    private String bankBranchName;
    private String bankFrontSourceId;

}
