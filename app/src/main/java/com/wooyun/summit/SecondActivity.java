package com.wooyun.summit;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jerome.jni.JNIProcess;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Date;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.params.ClientPNames;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class SecondActivity extends BaseActivity {

    Button btnContact;
    TextView tvResult;
    private static final int CONNECTION_TIMEOUT = 6000;
    protected AsyncHttpClient client;
    String baseUrl = "http://2935cc63796d47603.jie.sangebaimao.com/genkey.php";

    @Override
    protected void initView() {
        setContentView(R.layout.activity_second);
        btnContact = (Button) findViewById(R.id.btn_contact);
        tvResult = (TextView) findViewById(R.id.tv_result);

    }

    @Override
    protected void initData() {
        client = new AsyncHttpClient();
        client.getHttpClient().getParams()
                .setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        client.setTimeout(CONNECTION_TIMEOUT);
        client.setMaxRetriesAndTimeout(1, 3000);
    }

    public long getUnixTimestamp() {
        return new Date().getTime() / 1000;
    }

    @Override
    protected void setListener() {
        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postRequst(new AESUtil().encry(JNIProcess.getPart1(), "partkey=part0123&sign="));
            }
        });
    }

    public void postRequst(final String postParams) {

        String someData = postParams;
        StringEntity entity = null;
        try {
            entity = new StringEntity(someData);
            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/text"));
        } catch (Exception e) {

        }
        client.patch(mContext, baseUrl, entity, "application/text", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
            }

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (client != null)
            client.cancelRequests(mContext, true);
    }
}
