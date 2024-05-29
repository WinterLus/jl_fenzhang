package com.jlpay.request.notify;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

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
public class MerchIssueUpdateNotify implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String orgCode;
    private String mchId;
    private String ledgerModule;
    private String maxSplitRate;
    private String checkStatus;
    private String failedReason;
}
