package com.jlpay.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
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
public class MerchIssueUpdateRequest extends BaseRequest {
    private String orgCode;
    private String mchId;
    @Builder.Default
    private String ledgerModule = "02";
    private String maxSplitRate;
    private String[] sourceIds;
    private String notifyUrl;
}
