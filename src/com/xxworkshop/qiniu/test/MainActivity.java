package com.xxworkshop.qiniu.test;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import com.xxworkshop.common.L;
import com.xxworkshop.qiniu.QiniuUploader;
import com.xxworkshop.qiniu.QiniuUploaderListener;
import com.xxworkshop.qiniu.R;
import org.json.JSONObject;

/**
 * Created by brochexu on 6/18/14.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        QiniuUploader uploader = new QiniuUploader(this, "Odn7wy9IdvXp7NTGPhfQqk2_7t5MsdqnxWYaWdvH", "OEQ4OZvB2LxG9GMHcZnGmOK8giFWXEAMWs4_OjIf");
        uploader.upload(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher), uploader.getToken("cbsavatar", "", ""), "test", new QiniuUploaderListener() {
            @Override
            public void onSuccess(JSONObject obj) {
                L.logSplitter();
                L.log("success: " + obj.toString());
            }

            @Override
            public void onFailure(Exception ex) {
                L.logSplitter();
                L.log("fail: " + ex.toString());
            }
        });
//        byte[] bencodedSign = F.hmacSHA1(bputPolicy, secretKey.getBytes(Charset.forName("utf-8")));
//        bencodedSign = F.base64Encode(bencodedSign, Base64.URL_SAFE);
//        String sencodedSign = F.bytes2String(bencodedSign);
//        String token = accessKey + ":" + sencodedSign + ":" + encodedPolicy;
//        return token;

    }
}
