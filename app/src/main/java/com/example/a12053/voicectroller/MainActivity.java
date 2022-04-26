package com.example.a12053.voicectroller;

import static com.example.a12053.voicectroller.WelcomePage.USER_ID;
import static com.example.a12053.voicectroller.WelcomePage.USER_PSD;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a12053.voicectroller.adapter.IadapterClick;
import com.example.a12053.voicectroller.adapter.MyAdapter;
import com.example.a12053.voicectroller.data.User;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.IDN;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity extends Activity implements OnClickListener {

    public final static String SP_NAME = "sp_name";
    public final static String JOIN_TIMES = "join_times";
    //存放听写分析结果文本
    private HashMap<String, String> hashMapTexts = new LinkedHashMap<String, String>();
    private ImageView b_btn;  //初始化控件
    private EditText e_text;
    SpeechRecognizer hearer;  //听写对象
    RecognizerDialog dialog;  //讯飞提示框
    private SharedPreferences sp;
    private ImageView userImg;
    private TextView userId;
    private ImageView inviteWidget;
    private ImageView addFriend;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    showNotify();
                    break;
                case 1:
                    showOtherAgree();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getNotification();

        checkIsFirstJoin();
        initview();
    }

    private void initview() {
        sp = getSharedPreferences(SP_NAME,MODE_PRIVATE);
        b_btn = (ImageView) findViewById(R.id.record_widget);
        e_text = (EditText) findViewById(R.id.content_et);
        userImg = findViewById(R.id.userImg);
        userId = findViewById(R.id.userName);
        inviteWidget = findViewById(R.id.invite_widget);
        addFriend = findViewById(R.id.add_friend);
        b_btn.setOnClickListener(this);
        userImg.setOnClickListener(this);
        inviteWidget.setOnClickListener(this);
        addFriend.setOnClickListener(this);

        Intent intent = getIntent();
        String userid = intent.getStringExtra(USER_ID);
        String userpsd= intent.getStringExtra(USER_PSD);
        if (userid!=null&&userpsd!=null){
            userId.setText(userid);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.record_widget:
                recordTransWord();
                break;
            case R.id.userImg:
                getIntoProfilePage();
                break;
            case R.id.invite_widget:
                inviteFriend();
                break;
            case R.id.add_friend:
                addFriend();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearJoinTimes();
    }

    private void addFriend(){
        LayoutInflater inflater = LayoutInflater.from(getApplication());
        View view = inflater.inflate(R.layout.add_dialog_item, null);
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        WindowManager.LayoutParams wlp =dialog.getWindow().getAttributes();
        wlp.gravity = Gravity.TOP | Gravity.LEFT;
        wlp.x=20;
        wlp.y=20;
        dialog.getWindow().setLayout(1040,1200);


        ImageView closeButton=view.findViewById(R.id.close);
        closeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button search = view.findViewById(R.id.search);
        EditText editText = view.findViewById(R.id.dialog_edit);
        RecyclerView recyclerView = view.findViewById(R.id.add_recycler);
        recyclerView.setVisibility(View.INVISIBLE);

        List<String> idList = new ArrayList<>();
        idList.add("小李");
        idList.add("小王");
        idList.add("AAAA");
        idList.add("joe");
        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editText.getText().toString();
                if (idList.contains(id)){
                    recyclerView.setVisibility(View.VISIBLE);
                    List<String> showList = new ArrayList<>();
                    showList.add(id);
                    MyAdapter adapter = new MyAdapter(showList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                }
            }
        });
        dialog.show();

    }

    private void inviteFriend() {
        LayoutInflater inflater = LayoutInflater.from(getApplication());
        View view = inflater.inflate(R.layout.dialog_item, null);
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        WindowManager.LayoutParams wlp =dialog.getWindow().getAttributes();
        wlp.gravity = Gravity.TOP | Gravity.LEFT;
        wlp.x=20;
        wlp.y=20;
        dialog.getWindow().setLayout(1040,1200);


        ImageView closeButton=view.findViewById(R.id.close);
        closeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        List<String> idList = new ArrayList<>();
        idList.add("小李");
        idList.add("小王");
        idList.add("AAAA");
        idList.add("joe");
        MyAdapter adapter = new MyAdapter(idList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAdapterClick(new IadapterClick() {
            @Override
            public void onClick() {
                notifyCooperation(5000l);
            }
        });
        dialog.show();
    }

    private void getIntoProfilePage(){
        Intent intent = new Intent(MainActivity.this,ProfileAcivity.class);
        intent.putExtra(USER_ID,userId.getText().toString());
        startActivity(intent);
    }

    private void recordTransWord(){
        // 语音配置对象初始化
        SpeechUtility.createUtility(MainActivity.this, SpeechConstant.APPID + "=5b932d09");

        // 1.创建SpeechRecognizer对象，第2个参数：本地听写时传InitListener
        hearer = SpeechRecognizer.createRecognizer( MainActivity.this, null);
        // 交互动画
        dialog = new RecognizerDialog(MainActivity.this, null);
        // 2.设置听写参数，详见《科大讯飞MSC API手册(Android)》SpeechConstant类
        hearer.setParameter(SpeechConstant.DOMAIN, "iat"); // domain:域名
        hearer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        hearer.setParameter(SpeechConstant.ACCENT, "mandarin"); // mandarin:普通话

        //3.开始听写
        dialog.setListener(new RecognizerDialogListener() {  //设置对话框

            @Override
            public void onResult(RecognizerResult results, boolean isLast) {
                // TODO 自动生成的方法存根
                Log.d("Result", results.getResultString());
                //(1) 解析 json 数据<< 一个一个分析文本 >>
                StringBuffer strBuffer = new StringBuffer();
                try {
                    JSONTokener tokener = new JSONTokener(results.getResultString());
                    Log.i("TAG", "Test"+results.getResultString());
                    Log.i("TAG", "Test"+results.toString());
                    JSONObject joResult = new JSONObject(tokener);

                    JSONArray words = joResult.getJSONArray("ws");
                    for (int i = 0; i < words.length(); i++) {
                        // 转写结果词，默认使用第一个结果
                        JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                        JSONObject obj = items.getJSONObject(0);
                        strBuffer.append(obj.getString("w"));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//            		String text = strBuffer.toString();
                // (2)读取json结果中的sn字段
                String sn = null;

                try {
                    JSONObject resultJson = new JSONObject(results.getResultString());
                    sn = resultJson.optString("sn");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //(3) 解析语音文本<< 将文本叠加成语音分析结果  >>
                hashMapTexts.put(sn, strBuffer.toString());
                StringBuffer resultBuffer = new StringBuffer();  //最后结果
                for (String key : hashMapTexts.keySet()) {
                    resultBuffer.append(hashMapTexts.get(key));
                }

                e_text.setText(resultBuffer.toString());
                e_text.requestFocus();//获取焦点
                e_text.setSelection(1);//将光标定位到文字最后，以便修改

            }

            @Override
            public void onError(SpeechError error) {
                // TODO 自动生成的方法存根
                error.getPlainDescription(true);
            }
        });

        dialog.show();  //显示对话框

    }

    private void checkIsFirstJoin(){
        if (sp==null){
            sp = getSharedPreferences(SP_NAME,MODE_PRIVATE);
        }
        int joinTimes = sp.getInt(JOIN_TIMES,0);
        if (joinTimes==0){
            Intent intent = new Intent(MainActivity.this,WelcomePage.class);
            startActivity(intent);
            sp.edit().putInt(JOIN_TIMES,joinTimes+1).commit();
        } else {
            sp.edit().putInt(JOIN_TIMES,joinTimes+1).commit();
        }
    }

    private void clearJoinTimes(){
        sp.edit().putInt(JOIN_TIMES,0).commit();
    }

    private void notifyCooperation(Long time){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg1 = handler.obtainMessage();
                msg1.what = 1;
                handler.sendMessage(msg1);
            }


        }).start();
    }

    private void getNotification(Long time){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg = handler.obtainMessage();
                msg.what = 0;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void showNotify(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("通知")
                .setMessage("有人邀请你一起录制")
                .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        recordTransWord();
                    }
                })
                .setNegativeButton("了解", null).create().show();
    }

    private void showOtherAgree(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("通知")
                .setMessage("【小李】邀请你一起参与录制")
                .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        recordTransWord();
                    }
                })
                .setNegativeButton("不同意", null).create().show();

    }
}
