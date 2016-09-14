package com.yc.enjoytouch.yechat;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yc.enjoytouch.ye.ui.ChatBaseActivity;
import com.yc.enjoytouch.ye.utils.YCEmojicon;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ChatBaseActivity {


    @Override
    protected void wantToDo() {
        titleBar.setTitle("YC");//设置标题
        titleBar.setLeftImageResource(R.mipmap.ic_launcher);//设置左边图标
        titleBar.setRightImageResource(R.mipmap.ic_launcher);//设置右边图标
        titleBar.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.colorAccent));//设置背景
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {//左边点击事件
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"点击了标题栏左边",Toast.LENGTH_SHORT).show();
            }
        });
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {//右边点击事件
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"点击了标题栏右边",Toast.LENGTH_SHORT).show();
            }
        });

        //设置功能集合(根据id 标识)
        for (int i=0;i<6;i++)
           inputMenu.registerExtendMenuItem("测试"+(i+1),R.mipmap.ic_launcher,(i+1),extendMenuItemClickListener);
        //增加自定义表情
        List<YCEmojicon> emojiconList=new ArrayList<>();
        for (int i=0;i<18;i++) {
            emojiconList.add(new YCEmojicon(R.mipmap.ic_launcher, "[" + i + "*]"));
        }
        addEmojiconList(emojiconList);
    }



    @Override
    protected void sendVoiceMessage(String voiceFilePath, int voiceTimeLength) {
        Log.i("yc","发送语音了 语音路径："+voiceFilePath+" 时长："+voiceTimeLength+"秒");
    }

    @Override
    protected void sendTextMessage(String content) {
        Log.i("yc","发送文字了 内容："+content);
    }

    @Override
    protected void onYeActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void sendLocationMessage(double latitude, double longitude, String locationAddress) {
        Log.i("yc","发送位置了");
    }

    @Override
    protected void sendImageMessage(String absolutePath) {//绝对路径
        Log.i("yc","发送图片了 图片路径："+absolutePath);
    }

    @Override
    protected void itemClick(int itemId, View view) {
        Log.i("yc","点击了"+itemId+"功能键");
    }

    @Override
    protected void sendFileMessage(String filePath) {
        Log.i("yc","发送文件了 文件路径："+filePath);
    }

    @Override
    protected void sendBigExpressionMessage(String name, String identityCode) {
        Log.i("yc","发送大表情了 名称:"+name+"  id:"+identityCode);
    }
}
