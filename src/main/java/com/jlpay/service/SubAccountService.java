package com.jlpay.service;

import com.jlpay.constants.SubAccountConstant;
import com.jlpay.crypto.Signuture;
import com.jlpay.request.*;
import com.jlpay.utils.JacksonUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author lwc
 * @since 2024-05-30 13:56:44
 */
public class SubAccountService {
    private static final Logger LOGGER = Logger.getLogger(SubAccountService.class.getName());

    public static void main(String[] args) {
    }

    /**
     * 分账业务开通
     */
    public static Map<String, Object> merchIssueOpen(MerchIssueOpenRequest merchIssueOpenRequest) {
        return post(merchIssueOpenRequest, SubAccountConstant.SERVICE + SubAccountConstant.MERCH_ISSUE_OPEN);
    }

    /**
     * 分账业务信息变更
     */
    public static Map<String, Object> merchIssueUpdate(MerchIssueUpdateRequest merchIssueUpdateRequest) {
        return post(merchIssueUpdateRequest, SubAccountConstant.SERVICE + SubAccountConstant.MERCH_ISSUE_UPDATE);
    }

    /**
     * 分账业务信息查询
     */
    public static Map<String, Object> merchIssueQuery(MerchIssueQueryRequest merchIssueQueryRequest) {
        return post(merchIssueQueryRequest, SubAccountConstant.SERVICE + SubAccountConstant.MERCH_ISSUE_QUERY);
    }

    /**
     * 入账方创建
     */
    public static Map<String, Object> merchRevOpen(MerchRevOpenRequest merchRevOpenRequest) {
        return post(merchRevOpenRequest, SubAccountConstant.SERVICE + SubAccountConstant.MERCH_REV_OPEN);
    }

    /**
     * 入账方信息查询
     */
    public static Map<String, Object> merchRevQuery(MerchRevQueryRequest merchRevQueryRequest) {
        return post(merchRevQueryRequest, SubAccountConstant.SERVICE + SubAccountConstant.MERCH_REV_QUERY);
    }

    /**
     * 绑定入账方
     */
    public static Map<String, Object> merchRevBind(MerchRevBindRequest merchRevBindRequest) {
        return post(merchRevBindRequest, SubAccountConstant.SERVICE + SubAccountConstant.MERCH_REV_BIND);
    }

    /**
     * 绑定入账方
     */
    public static Map<String, Object> merchRevUnbind(MerchRevUnbindRequest merchRevUnbindRequest) {
        return post(merchRevUnbindRequest, SubAccountConstant.SERVICE + SubAccountConstant.MERCH_REV_UNBIND);
    }

    /**
     * 绑定关系查询
     */
    public static Map<String, Object> merchRevBindQuery(MerchRevBindQueryRequest merchRevBindQueryRequest) {
        return post(merchRevBindQueryRequest, SubAccountConstant.SERVICE + SubAccountConstant.MERCH_REV_BIND_QUERY);
    }

    /**
     * 附件上传
     */
    public static Map<String, Object> merchRevBindQuery(MerchFileUploadRequest merchFileUploadRequest) {
        return post(merchFileUploadRequest, SubAccountConstant.SERVICE + SubAccountConstant.MERCH_FILE_UPLOAD);
    }

    /**
     * 交易订单查询
     */
    public static Map<String, Object> orderQuery(OrderQueryRequest orderQueryRequest) {
        return post(orderQueryRequest, SubAccountConstant.SERVICE + SubAccountConstant.ORDER_QUERY);
    }

    /**
     * 余额分账请求
     */
    public static Map<String, Object> balanceSplit(BalanceSplitRequest balanceSplitRequest) {
        return post(balanceSplitRequest, SubAccountConstant.SERVICE + SubAccountConstant.BALANCE_SPLIT);
    }

    /**
     * 余额分账查询
     */
    public static Map<String, Object> balanceSplitQuery(BalanceSplitQueryRequest balanceSplitQueryRequest) {
        return post(balanceSplitQueryRequest, SubAccountConstant.SERVICE + SubAccountConstant.BALANCE_SPLIT_QUERY);
    }

    /**
     * 余额分账回退请求
     */
    public static Map<String, Object> balanceRefund(BalanceRefundRequest balanceRefundRequest) {
        return post(balanceRefundRequest, SubAccountConstant.SERVICE + SubAccountConstant.BALANCE_REFUND);
    }

    /**
     * 余额分账回退请求查询
     */
    public static Map<String, Object> balanceRefundQuery(BalanceRefundQueryRequest balanceRefundQueryRequest) {
        return post(balanceRefundQueryRequest, SubAccountConstant.SERVICE + SubAccountConstant.BALANCE_REFUND_QUERY);
    }

    /**
     * 账户余额查询
     */
    public static Map<String, Object> balanceQuery(BalanceQueryRequest balanceQueryRequest) {
        return post(balanceQueryRequest, SubAccountConstant.SERVICE + SubAccountConstant.BALANCE_QUERY);
    }

    /**
     * 分账账单接口
     */
    public static Map<String, Object> billApplyUrl(BillApplyUrlRequest billApplyUrlRequest) {
        return post(billApplyUrlRequest, SubAccountConstant.SERVICE + SubAccountConstant.BILL_APPLY_URL);
    }

    /**
     * @param request
     * @param url
     * @param <T>
     * @return
     */
    private static <T extends BaseRequest> Map<String, Object> post(T request, String url) {
        String context = Signuture.SM2.acsiiContext(request, "sign");
        System.out.println("待签名内容:" + context);
        String sign = Signuture.SM2.sign(SubAccountConstant.ORG_PRI_KEY, context);
        request.setSign(sign);
        URI uri = URI.create(url);
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(JacksonUtils.toJsonString(request)))
                .uri(uri)
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = null;
        Map<String, Object> map = null;
        try {
            response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            map = JacksonUtils.toObject(response.body(), Map.class);
            String responseSign = (String) map.get("sign");
            String responseContext = Signuture.SM2.acsiiContext(response.body(), "sign");
//            //验证签名
            if (Signuture.SM2.verify(SubAccountConstant.JL_PUB_KEY, responseContext, responseSign)) {
                LOGGER.log(Level.WARNING, (String) map.get("ret_msg"));
            }
            return map;
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.toString());
            return map;
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, e.toString());
            return map;
        }
    }
}
