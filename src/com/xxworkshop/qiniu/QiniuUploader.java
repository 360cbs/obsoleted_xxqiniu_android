package com.xxworkshop.qiniu;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import com.qiniu.io.IO;
import com.qiniu.io.PutExtra;
import com.xxworkshop.common.F;
import com.xxworkshop.common.S;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Created by brochexu on 6/16/14.
 */
public final class QiniuUploader {
    private Context context;
    private String accessKey;
    private String secretKey;
    private Uri tempImagePath;

    public QiniuUploader(Context context, String accessKey, String secretKey) {
        this.context = context;
        this.accessKey = accessKey;
        this.secretKey = secretKey;

        ContentValues values = new ContentValues(2);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "qiniu.tmp");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        tempImagePath = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    public void upload(Bitmap bitmap, String key, String token, QiniuUploaderListener listener) {
        if (token == null || token.equals("")) {
            listener.onFailure(new NullPointerException());
            return;
        }

        if (bitmap != null) {
            try {
                OutputStream os = context.getContentResolver().openOutputStream(tempImagePath);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                IO.putFile(context, token, key, tempImagePath, new PutExtra(), listener);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                listener.onFailure(e);
            }
        } else {
            listener.onFailure(new NullPointerException());
        }
    }

    public String getToken(String bucket, String callbackUrl, String callbackBody) {
        JSONObject jputPolicy = new JSONObject();
        try {
            jputPolicy.put("scope", bucket);
            jputPolicy.put("deadline", (long) (S.getTimeStamp() + 60 * 60));
            jputPolicy.put("callbackUrl", callbackUrl);
            jputPolicy.put("callbackBody", callbackBody);

            String sputPolicy = jputPolicy.toString();
            byte[] bputPolicy = sputPolicy.getBytes(Charset.forName("utf-8"));
            bputPolicy = F.base64Encode(bputPolicy, Base64.URL_SAFE);
            String encodedPolicy = new String(bputPolicy, Charset.forName("utf-8"));
            encodedPolicy = encodedPolicy.replace("\n", "");

            byte[] bencodedSign = F.hmacSHA1(encodedPolicy.getBytes(Charset.forName("utf-8")), secretKey.getBytes(Charset.forName("utf-8")));
            bencodedSign = F.base64Encode(bencodedSign, Base64.URL_SAFE);
            String encodedSign = new String(bencodedSign, Charset.forName("utf-8"));
            encodedSign = encodedSign.replace("\n", "");

            String token = accessKey + ":" + encodedSign + ":" + encodedPolicy;
            return token;
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
