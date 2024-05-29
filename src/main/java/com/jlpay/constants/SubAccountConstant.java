package com.jlpay.constants;

/**
 * @author lwc
 * @since 2024-05-29
 */
public class SubAccountConstant {
    //机构私钥
    public static final String ORG_PRI_KEY = "5c1e714fb3828ceb5fec2af8475e254b3bbdda542a660f0238293228f13612f3";
    //嘉联公钥
    public static final String JL_PUB_KEY = "8f191de3d3f7acd064fb896f0c231654813f995d94a69421aed693920b63abadcb21152049adfb4ef35c43e9216f4ad402e2b429b0e42cf959bf66271c18e629";
    //机构号
    public static final String ORG_CODE = "50720711";
    //服务地址
    public static final String SERVICE = "https://api-uat.jlpay.com/";
    //服务地址
    //private static final String SERVICE = "https://api.jlpay.com/";

    /**
     * 分账配置接口
     */
    //分账业务开通
    public static final String MERCH_ISSUE_OPEN = "fund/ledger/api/merch/issue/open";
    //分账业务信息变更
    public static final String MERCH_ISSUE_UPDATE = "fund/ledger/api/merch/issue/open";
    //分账业务信息查询
    public static final String MERCH_ISSUE_QUERY = "fund/ledger/api/merch/issue/query";
    //入账方创建
    public static final String MERCH_REV_OPEN = "fund/ledger/api/merch/rev/open";
    //入账方信息查询
    public static final String MERCH_REV_QUERY = "fund/ledger/api/merch/rev/query";
    //绑定入账方
    public static final String MERCH_REV_BIND = "fund/ledger/api/merch/rev/bind";
    //解绑入账方
    public static final String MERCH_REV_UNBIND = "fund/ledger/api/merch/rev/unbind";
    //绑定关系查询
    public static final String MERCH_REV_BIND_QUERY = "fund/ledger/api/merch/rev/bind/query";
    // 附件上传
    public static final String MERCH_FILE_UPLOAD = "fund/ledger/api/merch/file/upload";

    /**
     * 交易订单查询
     */
    public static final String ORDER_QUERY = "fund/ledger/api/order/query";

    /**
     * 余额分账接口
     */
    //余额分账请求
    public static final String BALANCE_SPLIT = "fund/ledger/api/balance/split";
    //余额分账结果查询
    public static final String BALANCE_SPLIT_QUERY = "fund/ledger/api/balance/split/query";
    //余额分账回退请求
    public static final String BALANCE_REFUND = "fund/ledger/api/balance/refund";
    //余额分账回退结果查询
    public static final String BALANCE_REFUND_QUERY = "fund/ledger/api/balance/refund/query";
    //账户余额查询
    public static final String BALANCE_QUERY = "fund/ledger/api/balance/query";

    /**
     * 分账账单接口
     */
    //申请分账账单
    public static final String BILL_APPLY_URL = "fund/ledger/api/bill/apply/url";

}

