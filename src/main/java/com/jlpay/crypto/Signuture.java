package com.jlpay.crypto;


import com.jlpay.crypto.gm.Sm2Signuture;
import com.jlpay.utils.JacksonUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author whowe
 * add at 2023/7/19
 */
public enum Signuture {
    SM2(new Sm2Signuture());
    private ISignuture signuture;

    Signuture(ISignuture signuture) {
        this.signuture = signuture;
    }

    /**
     * 验证签名
     *
     * @param pubKey 公钥
     * @param data   待验证数据
     * @param sign   签名值
     * @return 验证结果
     */
    public boolean verify(String pubKey, String data, String sign) {
        return signuture.verify(pubKey, data, sign);
    }

    /**
     * 签名
     *
     * @param priKey 私钥
     * @param data   待签名数据
     * @return 签名值
     */
    public String sign(String priKey, String data) {
        return signuture.sign(priKey, data);
    }


    public String acsiiContext(Object contextObj, String... ignoreKey) {
        String context = contextObj instanceof String ? contextObj.toString() : JacksonUtils.toJsonString(contextObj);
        TreeMap<String, Object> objectTreeMap = JacksonUtils.toObject(context, TreeMap.class);
        List<String> ignoreKeyList = ignoreKey != null ? Arrays.asList(ignoreKey) : new ArrayList<>();
        return objectTreeMap.entrySet().stream()
                .filter(entry -> !ignoreKeyList.contains(entry.getKey()))
                .map(this::getKeyValue)
                .collect(Collectors.joining("&"));
    }

    private String getKeyValue(Map.Entry<String, Object> entry) {
        StringBuilder sb = new StringBuilder();
        sb.append(entry.getKey()).append("=");
        if (entry.getValue() instanceof Map || entry.getValue() instanceof List) {
            sb.append(JacksonUtils.toJsonString(entry.getValue()));
        } else {
            sb.append(entry.getValue());
        }
        return sb.toString();
    }
}
