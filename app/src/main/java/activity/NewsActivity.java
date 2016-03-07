package activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import argument.Constants;
import module.ModuleList;

/**
 * Created by zhanjiyuan on 15/9/27.
 */
public class NewsActivity extends ModuleList {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
    }

//    private Handler handler = new Handler() {
//        public void handleMessage(Message msg){
//            switch(msg.what){
//                case 0:
//                    fetchItem((String)msg.obj);
//                    addItem(null);
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

//    @Override
//    protected void fetchItem(String args) {
//        new Thread() {
//            @Override
//            public void run() {
//
//                HttpClient httpClient = new DefaultHttpClient();
//                // replacße with your url
//                HttpPost httpPost = new HttpPost(Constants.SERVER_IP + "getNewsList");
//
//
//                //Post Data
//
//                java.util.List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
////                nameValuePair.add(new BasicNameValuePair("username", "test_user"));
////                nameValuePair.add(new BasicNameValuePair("password", "123456789"));
//
//
//                //Encoding POST data
//                try {
//                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
//                } catch (UnsupportedEncodingException e) {
//                    // log exception
//                    e.printStackTrace();
//                }
//
//                //making POST request.
//                try {
//                    HttpResponse response = httpClient.execute(httpPost);
//                    // write response to log
////                    Log.d("Http Post Response:", response.toString());
//                    String json = EntityUtils.toString(response.getEntity());
//                    Message msg = new Message();
//                    msg.what = 0;//receive msg
//                    msg.obj = json;
//                    handler.sendMessage(msg);
//                } catch (IOException e) {
//                    // Log exception
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
}
