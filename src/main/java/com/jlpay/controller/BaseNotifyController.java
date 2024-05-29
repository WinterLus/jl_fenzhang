package com.jlpay.controller;

import com.jlpay.request.notify.MerchIssueUpdateNotify;
import com.jlpay.request.notify.MerchRevBindNotify;

/**
 * 分账回调接口标准
 *
 * @author lwc
 * @since 2024-05-29
 */
public interface BaseNotifyController {


    /**
     * 分账业务信息变更_异步回调
     */
    void merchIssueUpdateNotify(MerchIssueUpdateNotify merchIssueUpdateNotify);

    /**
     * 绑定入账方_异步回调
     */
    void merchRevBindNotify(MerchRevBindNotify merchRevBindNotify);

}
