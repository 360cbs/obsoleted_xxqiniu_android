package com.xxworkshop.qiniu.test;

import android.util.Base64;
import com.xxworkshop.common.F;

import java.nio.charset.Charset;

/**
 * Created by brochexu on 6/18/14.
 */
public class Main {
    public static void main(String[] args) {
        String sputPolicy = "{\"scope\":\"my-bucket:sunflower.jpg\",\"deadline\":1451491200,\"returnBody\":\"{\"name\":$(fname),\"size\":$(fsize),\"w\":$(imageInfo.width),\"h\":$(imageInfo.height),\"hash\":$(etag)}\"}";
        byte[] bputPolicy = sputPolicy.getBytes(Charset.forName("utf-8"));
        bputPolicy = F.base64Encode(bputPolicy, Base64.URL_SAFE);

//        String encodedPolicy = F.bytes2String(bputPolicy);
//        System.out.println(encodedPolicy);
//        byte[] bencodedSign = F.hmacSHA1(bputPolicy, secretKey.getBytes(Charset.forName("utf-8")));
//        bencodedSign = F.base64Encode(bencodedSign, Base64.URL_SAFE);
//        String sencodedSign = F.bytes2String(bencodedSign);
//        String token = accessKey + ":" + sencodedSign + ":" + encodedPolicy;
//        return token;
    }
}
