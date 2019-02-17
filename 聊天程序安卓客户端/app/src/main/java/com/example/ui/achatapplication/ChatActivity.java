package com.example.ui.achatapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class ChatActivity extends AppCompatActivity {
    private EditText UserIdText;
    private EditText ToUserIdText;
    private EditText MessageContentText;
    private ListView listView;
    List<String> listdata = new ArrayList<String>();
    private Handler handler=null;
    private static boolean isSending = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        setTitle("聊天程序");
        UserIdText = findViewById(R.id.UserId);
        ToUserIdText = findViewById(R.id.ToUserId);
        MessageContentText = findViewById(R.id.MessageContent);
        listView = findViewById(R.id.Pane);
        handler = new Handler();
        //
        final long timeInterval = 1000;
        Runnable runnable = () -> {
            while (true) {
                // ------- code for task to run
                try {
                    if (UserIdText.getText().length() == 10) {
                        UpdateContent(false);
                    }
                    Thread.sleep(timeInterval);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
                // ------- ends here
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void Send(View view) {
        if (!isSending & UserIdText.getText().toString().length() == 10 && ToUserIdText.getText().toString().length() == 10) {
            UpdateContent(true);
        }
    }

    private void UpdateContent(boolean isSendMessage){
        Runnable updaterunnable = () -> {
            if (!isSendMessage || (!"".equals(MessageContentText.getText().toString()) && !(UserIdText.getText().toString()).equals(ToUserIdText.getText().toString()) ) ) {
                boolean noException = true;
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    String uri = "https://www.mylifeview.cn:8081/message/transfermessage";
                    HttpPost httppost = new HttpPost(uri);
                    JSONObject obj = new JSONObject();
                    //添加http头信息
                    httppost.addHeader("Content-Type", "application/json;charset=utf-8");
                    httppost.addHeader("Accept", "application/json;charset=utf-8");
                    obj.put("id", UserIdText.getText().toString());
                    if (isSendMessage) {
                        obj.put("toid", ToUserIdText.getText().toString());
                        obj.put("content", MessageContentText.getText().toString());
                    } else {
                        obj.put("toid", null);
                        obj.put("content", null);
                    }
                    httppost.setEntity(new StringEntity(obj.toString(), HTTP.UTF_8));
                    HttpResponse response;
                    SSLSocketFactory.getSocketFactory().setHostnameVerifier(new AllowAllHostnameVerifier());
                    response = httpclient.execute(httppost);
                    //检验状态码，如果成功接收数据
                    int code = response.getStatusLine().getStatusCode();
                    if (code == 200) {
                        if (isSendMessage) {
                            Date date = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String dateNowStr = sdf.format(date);
                            listdata.add("接收者ID：" + ToUserIdText.getText().toString() + "\n" + "发送时间：" + dateNowStr + "\n" + MessageContentText.getText().toString().replace("\\n", "\n") + "\n");
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.list_item,listdata);//listdata和str均可
                            Runnable runnable = () -> {
                                listView.setAdapter(arrayAdapter);
                                listView.setSelection(listView.getBottom());
                            };
                            handler.post(runnable);
                        }
                        //
                        String rev = EntityUtils.toString(response.getEntity());//返回json格式： {"id": "","name": ""}
                        List<Message> message = new ArrayList<Message>();
                        if (rev != null && !"".equals(rev) && rev.length() > 2) {
                            rev = rev.substring(1, rev.length()-1);
                            String[] revlist = rev.split("\\{");
                            for (int i = 1; i < revlist.length; i++) {
                                Message msg = new Message();
                                String[] temp1 = revlist[i].split("\\}");
                                String classstring = temp1[0];
                                String[] listtemp=classstring.split(",");
                                String[] temp = listtemp[0].split(":");
                                msg.setIsview(Integer.valueOf(temp[1]));
                                temp = listtemp[1].split(":\"");
                                String[] temp2 = temp[1].split("\"");
                                msg.setMessagecontent(temp2[0].replace("\\n", "\n"));
                                temp = listtemp[2].split(":\"");
                                temp2 = temp[1].split("\\.");
                                msg.setMessagetime(temp2[0]);
                                temp = listtemp[3].split(":");
                                msg.setMid(Integer.valueOf(temp[1]));
                                temp = listtemp[4].split(":\"");
                                temp2 = temp[1].split("\"");
                                msg.setTouserid(temp2[0]);
                                temp = listtemp[5].split(":\"");
                                temp2 = temp[1].split("\"");
                                msg.setUserid(temp2[0]);
                                message.add(msg);
                            }
                        }
                        for (int i = 0; i < message.size(); i++) {
                            //System.out.println("message:" + message.get(i).getIsview()+","+message.get(i).getMessagecontent());
                            listdata.add("发送者ID：" + message.get(i).getUserid() + "\n" + "发送时间：" + message.get(i).getMessagetime() + "\n" + message.get(i).getMessagecontent() + "\n");
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.list_item,listdata);//listdata和str均可
                            Runnable runnable = () -> {
                                listView.setAdapter(arrayAdapter);
                                listView.setSelection(listView.getBottom());
                            };
                            handler.post(runnable);
                        }
                    } else {
                        if (isSendMessage) {
                            listdata.add("给ID（" + ToUserIdText.getText().toString() + "）用户的消息发送失败，错误码为：" + code+"code");
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.list_item,listdata);//listdata和str均可
                            Runnable runnable = () -> {
                                listView.setAdapter(arrayAdapter);
                                listView.setSelection(listView.getBottom());
                            };
                            handler.post(runnable);
                            noException = false;
                        }
                    }
                } catch (Exception e) {
                    if (isSendMessage) {
                        noException = false;
                        listdata.add("给ID（" + ToUserIdText.getText().toString() + "）用户的消息发送失败，出现网络连接故障。");
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.list_item,listdata);//listdata和str均可
                        Runnable runnable = () -> {
                            listView.setAdapter(arrayAdapter);
                            listView.setSelection(listView.getBottom());
                        };
                        handler.post(runnable);
                    }
                }
                if (isSendMessage && noException) {
                    Runnable runnable = () -> {
                        MessageContentText.setText("");
                    };
                    handler.post(runnable);
                }
            }
        };
        Thread thread = new Thread(updaterunnable);
        thread.start();
    }
}

