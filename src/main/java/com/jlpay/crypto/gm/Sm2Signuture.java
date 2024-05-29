package com.jlpay.crypto.gm;


import com.jlpay.crypto.ISignuture;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithID;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.custom.gm.SM2P256V1Curve;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECFieldFp;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import java.util.Base64;

/**
 * @author whowe
 * add at 2023/7/19
 */
public class Sm2Signuture implements ISignuture {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static final SM2P256V1Curve CURVE = new SM2P256V1Curve();
    public final static BigInteger SM2_ECC_P = CURVE.getQ();
    public final static BigInteger SM2_ECC_A = CURVE.getA().toBigInteger();
    public final static BigInteger SM2_ECC_B = CURVE.getB().toBigInteger();
    public final static BigInteger SM2_ECC_N = CURVE.getOrder();
    public final static BigInteger SM2_ECC_H = CURVE.getCofactor();
    public final static BigInteger SM2_ECC_GX = new BigInteger(
            "32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7", 16);
    public final static BigInteger SM2_ECC_GY = new BigInteger(
            "BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0", 16);
    public static final ECPoint G_POINT = CURVE.createPoint(SM2_ECC_GX, SM2_ECC_GY);
    public static final ECDomainParameters DOMAIN_PARAMS = new ECDomainParameters(CURVE, G_POINT,
            SM2_ECC_N, SM2_ECC_H);
//    public static final int CURVE_LEN = BcecUtils.getCurveLength(DOMAIN_PARAMS);
    //////////////////////////////////////////////////////////////////////////////////////

    public static final EllipticCurve JDK_CURVE = new EllipticCurve(new ECFieldFp(SM2_ECC_P), SM2_ECC_A, SM2_ECC_B);
    public static final java.security.spec.ECPoint JDK_G_POINT = new java.security.spec.ECPoint(
            G_POINT.getAffineXCoord().toBigInteger(), G_POINT.getAffineYCoord().toBigInteger());
    public static final java.security.spec.ECParameterSpec JDK_EC_SPEC = new java.security.spec.ECParameterSpec(
            JDK_CURVE, JDK_G_POINT, SM2_ECC_N, SM2_ECC_H.intValue());

    //////////////////////////////////////////////////////////////////////////////////////

    public static final String EC_ALGORITHM = "EC";

    @Override
    public boolean verify(String pubKey, String data, String sign) {
        ECPublicKeyParameters pubKeyParameters = getPublicKeyParameters(pubKey);
        return verify(pubKeyParameters, null, data.getBytes(StandardCharsets.UTF_8), Base64.getDecoder().decode(sign));
    }

    /**
     * 验证
     *
     * @param pubKey  公钥对象
     * @param srcData 待验证数据
     * @param sign    签名值
     * @return 验证结果
     */
    public boolean verify(BCECPublicKey pubKey, byte[] srcData, byte[] sign) {
        ECParameterSpec parameterSpec = pubKey.getParameters();
        ECDomainParameters domainParameters = new ECDomainParameters(parameterSpec.getCurve(), parameterSpec.getG(),
                parameterSpec.getN(), parameterSpec.getH());
        ECPublicKeyParameters pubKeyParameters = new ECPublicKeyParameters(pubKey.getQ(), domainParameters);
        return verify(pubKeyParameters, null, srcData, sign);
    }

    /**
     * ECC公钥验签
     *
     * @param pubKeyParameters ECC公钥
     * @param withId           可以为null，若为null，则默认withId为字节数组:"1234567812345678".getBytes()
     * @param srcData          源数据
     * @param sign             签名
     * @return 验签成功返回true，失败返回false
     */
    private boolean verify(ECPublicKeyParameters pubKeyParameters, byte[] withId, byte[] srcData, byte[] sign) {
        SM2Signer signer = new SM2Signer();
        CipherParameters param;
        if (withId != null) {
            param = new ParametersWithID(pubKeyParameters, withId);
        } else {
            param = pubKeyParameters;
        }
        signer.init(false, param);
        signer.update(srcData, 0, srcData.length);
        return signer.verifySignature(sign);
    }

    /**
     * 根据公钥字符串获取公钥对象
     *
     * @param pubKey 公钥字符串
     * @return 公钥对象
     */
    private ECPublicKeyParameters getPublicKeyParameters(String pubKey) {
        BCECPublicKey ecPubKey = getBcecPublicKey(pubKey);
        ECParameterSpec parameterSpec = ecPubKey.getParameters();
        ECDomainParameters domainParameters = new ECDomainParameters(parameterSpec.getCurve(), parameterSpec.getG(),
                parameterSpec.getN(), parameterSpec.getH());
        return new ECPublicKeyParameters(ecPubKey.getQ(), domainParameters);
    }

    /**
     * 16进制公钥串转为公钥
     *
     * @param hexStr 公钥hex值
     * @return 公钥对象
     */
    public BCECPublicKey getBcecPublicKey(String hexStr) {
        java.security.spec.ECPoint point = new java.security.spec.ECPoint(new BigInteger(hexStr.substring(0, 64), 16), new BigInteger(hexStr.substring(64), 16));
        ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(point, JDK_EC_SPEC);
        return new BCECPublicKey(EC_ALGORITHM, ecPublicKeySpec, BouncyCastleProvider.CONFIGURATION);
    }

    @Override
    public String sign(String priKey, String data) {
        ECPrivateKeyParameters priKeyParameters = getECPrivateKeyParameters(priKey);
        try {
            byte[] signs = sign(priKeyParameters, null, data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(signs);
        } catch (CryptoException e) {
            throw new RuntimeException(e);
        }
    }

    private ECPrivateKeyParameters getECPrivateKeyParameters(String hexStr) {
        BCECPrivateKey ecPriKey = getBcecPrivateKey(hexStr);
        ECParameterSpec parameterSpec = ecPriKey.getParameters();
        ECDomainParameters domainParameters = new ECDomainParameters(parameterSpec.getCurve(), parameterSpec.getG(),
                parameterSpec.getN(), parameterSpec.getH());
        return new ECPrivateKeyParameters(ecPriKey.getD(), domainParameters);
    }

    /**
     * 16进制私钥串转为私钥
     *
     * @param hexStr
     * @return
     */
    public BCECPrivateKey getBcecPrivateKey(String hexStr) {
        BigInteger d = new BigInteger(hexStr, 16);
        ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(d, JDK_EC_SPEC);
        return new BCECPrivateKey(EC_ALGORITHM, ecPrivateKeySpec, BouncyCastleProvider.CONFIGURATION);
    }

    /**
     * ECC私钥签名
     *
     * @param priKeyParameters ECC私钥
     * @param withId           可以为null，若为null，则默认withId为字节数组:"1234567812345678".getBytes()
     * @param srcData          源数据
     * @return 签名
     * @throws CryptoException
     */
    public byte[] sign(ECPrivateKeyParameters priKeyParameters, byte[] withId, byte[] srcData)
            throws CryptoException {
        SM2Signer signer = new SM2Signer();
        CipherParameters param = null;
        ParametersWithRandom pwr = new ParametersWithRandom(priKeyParameters, new SecureRandom());
        if (withId != null) {
            param = new ParametersWithID(pwr, withId);
        } else {
            param = pwr;
        }
        signer.init(true, param);
        signer.update(srcData, 0, srcData.length);
        return signer.generateSignature();
    }

}
