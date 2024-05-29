package com.jlpay.crypto;

public interface ISignuture {
    boolean verify(String pubKey, String data, String sign);

    String sign(String priKey, String data);


}
