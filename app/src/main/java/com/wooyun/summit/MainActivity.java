package com.wooyun.summit;

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

public class MainActivity extends BaseActivity {

    Button btnContact1, btnContact2, btnContact3;
    TextView tvResult1, tvResult2, tvResult3;
    private static final int CONNECTION_TIMEOUT = 6000;
    protected AsyncHttpClient client;
    String baseUrl = "http://2935cc63796d47603.jie.sangebaimao.com/data.php";


    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);

        btnContact1 = (Button) findViewById(R.id.btn_contact1);
        tvResult1 = (TextView) findViewById(R.id.tv_result1);
        btnContact2 = (Button) findViewById(R.id.btn_contact2);
        tvResult2 = (TextView) findViewById(R.id.tv_result2);
        btnContact3 = (Button) findViewById(R.id.btn_contact3);
        tvResult3 = (TextView) findViewById(R.id.tv_result3);
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
        btnContact1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postRequst(1, new AESUtil().encry(JNIProcess.getPart1(), "id=1&sign=" + JNIProcess.getInfoMD5("1" + JNIProcess.getPart2()) + "&dateline=" + getUnixTimestamp()));
            }
        });
        btnContact2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postRequst(2, new AESUtil().encry(JNIProcess.getPart1(), "id=2&sign=" + JNIProcess.getInfoMD5("2" + JNIProcess.getPart2()) + "&dateline=" + getUnixTimestamp()));
            }
        });
        btnContact3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postRequst(3, new AESUtil().encry(JNIProcess.getPart1(), "id=3&sign=" + JNIProcess.getInfoMD5("3" + JNIProcess.getPart2()) + "&dateline=" + getUnixTimestamp()));
            }
        });
    }

    public void postRequst(final int inteType, final String postParams) {

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
                switch (inteType) {
                    case 1:
                        tvResult1.setText(s);
                        break;
                    case 2:
                        tvResult2.setText(s);
                        break;
                    case 3:
                        tvResult3.setText(s);
                        break;
                }
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
