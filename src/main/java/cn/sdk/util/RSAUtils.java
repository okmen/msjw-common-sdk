package cn.sdk.util;

import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.lang3.StringUtils;

public final class RSAUtils {

    public static final String ALGORITHMS_SHA1WITHRSA = "SHA1WithRSA";
    public static final String ALGORITHMS_MD5WITHRSA = "MD5WithRSA";

    public static String sign(final String text, final String privateKey) {

        return sign(text, "UTF-8", ALGORITHMS_SHA1WITHRSA, privateKey);
    }

    public static String sign(final String text, final String encoding, final String algorithms, final String privateKey) {

        try {

            final KeyFactory factory = KeyFactory.getInstance("RSA");
            final Signature signature = Signature.getInstance(algorithms);

            final PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            signature.initSign(factory.generatePrivate(keySpecPKCS8));
            signature.update(text.getBytes(encoding));

            return Base64Encoder.encode(signature.sign());
        } catch (Exception e) {
            System.err.println(System.err);
            return StringUtils.EMPTY;
        }
    }
    
    public static boolean verify(final String text, String sign, final String publicKey) {

        return verify(text, "UTF-8", ALGORITHMS_SHA1WITHRSA, sign, publicKey);
    }

    public static boolean verify(String text, final String encoding, final String algorithms, String sign, String publicKey) {
        
        try {

            final KeyFactory factory = KeyFactory.getInstance("RSA");
            final Signature signature = Signature.getInstance(algorithms);

            final X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.decode(publicKey));
            signature.initVerify(factory.generatePublic(keySpecX509));
            signature.update(text.getBytes(encoding));

            return signature.verify(Base64.decode(sign));

        } catch (Exception e) {
            System.err.println(System.err);
            return false;
        }
    }
}
