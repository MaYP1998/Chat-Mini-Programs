import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller implements Initializable {

    @FXML
    private TextField UserIdText;

    @FXML
    private TextField ToUserIdText;

    @FXML
    private AnchorPane ContentText;

    @FXML
    private TextArea MessageContentText;

    @FXML
    private ScrollPane pane;

    private static boolean isSending = false;

    private static double height = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

    public void SendMessage(){
        if (!isSending & UserIdText.getText().length() == 10 && ToUserIdText.getText().length() == 10) {
            isSending = true;
            UpdateContent(true);
        }
    }

    private void UpdateContent(boolean isSendMessage){
        Runnable updaterunnable = () -> {
            if (!isSendMessage || (!"".equals(MessageContentText.getText()) && !(UserIdText.getText()).equals(ToUserIdText.getText()) ) ) {
                boolean noException = true;
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    String uri = "https://www.mylifeview.cn:8081/message/transfermessage";
                    HttpPost httppost = new HttpPost(uri);
                    JSONObject obj = new JSONObject();
                    //添加http头信息
                    httppost.addHeader("Content-Type", "application/json;charset=utf-8");
                    httppost.addHeader("Accept", "application/json;charset=utf-8");
                    obj.put("id", UserIdText.getText());
                    if (isSendMessage) {
                        obj.put("toid", ToUserIdText.getText());
                        obj.put("content", MessageContentText.getText());
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
                            //ContentText.setText(ContentText.getText() + "\n" + "给ID（" + ToUserIdText.getText() + "）用户的消息已发送成功！" + "\n");
                            Date date = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String dateNowStr = sdf.format(date);
                            Label temp = new Label("\n" + "接收者ID：" + ToUserIdText.getText() + "\n" + "发送时间：" + dateNowStr + "\n" + MessageContentText.getText() + "\n\n");
                            temp.setLayoutY(height);
                            temp.setPrefWidth(250);
                            temp.setWrapText(true);
                            temp.setStyle("-fx-background-color: #99FF99; -fx-border-color: yellow;");
                            Runnable t1 = () -> {
                                ContentText.getChildren().addAll(temp);
                                ContentText.setPrefHeight(ContentText.getPrefHeight() + 100 + 23 * numOfN());
                                height = height + temp.getHeight() + 100 + 23 * numOfN();
                                ContentText.setRightAnchor(temp, Double.valueOf(temp.getLayoutX() + 40));
                                pane.setVvalue(ContentText.getHeight() - height);
                            };
                            Platform.runLater(t1);
                        }
                        //
                        String rev = EntityUtils.toString(response.getEntity());//返回json格式： {"id": "","name": ""}
                        JSONArray json = JSONArray.fromObject(rev);
                        List<Message> message = (List) JSONArray.toCollection(json, Message.class);
                        Runnable t1 = () -> {
                            for (int i = 0; i < message.size(); i++) {
                                Label temp = new Label("\n" + "发送者ID：" + message.get(i).getUserid() + "\n" + "发送时间：" + message.get(i).getMessagetime() + "\n" + message.get(i).getMessagecontent() + "\n\n");
                                temp.setLayoutY(height);
                                temp.setPrefWidth(240);
                                temp.setWrapText(true);
                                temp.setStyle("-fx-background-color: #FFFF99; -fx-border-color: red;");
                                ContentText.getChildren().addAll(temp);
                                ContentText.setPrefHeight(ContentText.getPrefHeight() + 100 + 22 * numOfN2(message.get(i).getMessagecontent()));
                                ContentText.setLeftAnchor(temp, Double.valueOf(20));
                                height = height + temp.getHeight() + 100 + 22 * numOfN2(message.get(i).getMessagecontent());
                                pane.setVvalue(ContentText.getHeight() - height);
                            }
                        };
                        Platform.runLater(t1);
                    } else {
                        if (isSendMessage) {
                            //ContentText.setText(ContentText.getText() + "\n" + "给ID（" + ToUserIdText.getText() + "）用户的消息发送失败，错误码为：" + code+"code" + "\n");
                            noException = false;
                            Label temp=new Label("\n" + "给ID（" + ToUserIdText.getText() + "）用户的消息发送失败，错误码为：" + code+"code" + "\n");
                            temp.setLayoutY(height);
                            Runnable t1 = () -> {
                                ContentText.getChildren().addAll(temp);
                                ContentText.setPrefHeight(ContentText.getPrefHeight() + 22);
                                height = height + temp.getHeight() + 22;
                                ContentText.setLeftAnchor(temp, Double.valueOf(20));
                                pane.setVvalue(ContentText.getHeight() - 197.0 + 22);
                            };
                            Platform.runLater(t1);
                        }
                    }
                } catch (Exception e) {
                    if (isSendMessage) {
                        noException = false;
                        //ContentText.setText(ContentText.getText() + "\n" + "给ID（" + ToUserIdText.getText() + "）用户的消息发送失败，出现网络连接故障。");
                        Label temp=new Label("\n" + "给ID（" + ToUserIdText.getText() + "）用户的消息发送失败，出现网络连接故障。");
                        temp.setLayoutY(height);
                        Runnable t1 = () -> {
                            ContentText.getChildren().addAll(temp);
                            ContentText.setPrefHeight(ContentText.getPrefHeight() + 22);
                            height = height + temp.getHeight() + 22;
                            ContentText.setLeftAnchor(temp, Double.valueOf(20));
                            pane.setVvalue(ContentText.getHeight() - 197.0 + 22);
                        };
                        Platform.runLater(t1);
                    }
                }
                if (isSendMessage && noException) {
                    MessageContentText.setText("");
                }
            }
            if (isSendMessage) {
                isSending = false;
            }
        };
        Thread thread = new Thread(updaterunnable);
        thread.start();
    }

    private int numOfN() {
        String regEx="\n"; //要匹配的子串，可以用正则表达式
        Pattern p=Pattern.compile(regEx);
        Matcher m=p.matcher(MessageContentText.getText());
        int i = 0;
        while(m.find()) {
            i++;
        }
        //
        String[] split = MessageContentText.getText().split("\n");


        for(String each : split) {
            //System.out.println("'" + each + "'");
            int newi = String_length(each) / 28;
            //System.out.println("newi="+newi);
            i += newi;
        }

        return i;
    }

    private int numOfN2(String str) {
        String regEx="\n"; //要匹配的子串，可以用正则表达式
        Pattern p=Pattern.compile(regEx);
        Matcher m=p.matcher(str);
        int i = 0;
        while(m.find()) {
            i++;
        }
        //
        String[] split = str.split("\n");

        for(String each : split) {
            //System.out.println("'" + each + "'");
            int newi = String_length(each) / 28;
            //System.out.println("newi="+newi);
            i += newi;
        }

        return i;
    }

    private static int String_length(String s)
    {
        int length = 0;
        for(int i = 0; i < s.length(); i++)
        {
            int ascii = Character.codePointAt(s, i);
            if(ascii >= 0 && ascii <=255)
                length++;
            else
                length += 2;

        }
        //System.out.println(length);
        return length;
    }
}
