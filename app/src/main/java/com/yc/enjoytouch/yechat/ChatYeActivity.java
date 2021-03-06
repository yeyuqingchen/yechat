package com.yc.enjoytouch.yechat;

import android.content.Intent;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yc.enjoytouch.ye.ui.ChatBaseActivity;
import com.yc.enjoytouch.ye.utils.YCEmojicon;
import com.yc.enjoytouch.ye.utils.YCEmojiconGroupEntity;
import com.yc.enjoytouch.ye.utils.YCSmileUtils;

import java.util.ArrayList;
import java.util.List;


public class ChatYeActivity extends ChatBaseActivity {
    //测试

    @Override
    protected void wantToDo() {
        titleBar.setTitle("YC");//设置标题
        titleBar.setLeftImageResource(R.mipmap.ic_launcher);//设置左边图标
        titleBar.setRightImageResource(R.mipmap.ic_launcher);//设置右边图标
        titleBar.setBackgroundColor(ChatYeActivity.this.getResources().getColor(R.color.colorAccent));//设置背景
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {//左边点击事件
            @Override
            public void onClick(View view) {
                Toast.makeText(ChatYeActivity.this,"点击了标题栏左边",Toast.LENGTH_SHORT).show();
            }
        });
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {//右边点击事件
            @Override
            public void onClick(View view) {
                Toast.makeText(ChatYeActivity.this,"点击了标题栏右边",Toast.LENGTH_SHORT).show();
            }
        });

        //设置功能集合(根据第三个参数 id 标识)
        for (int i=0;i<6;i++)
           inputMenu.registerExtendMenuItem("测试"+(i+1),R.mipmap.ic_launcher,(i+1),extendMenuItemClickListener);
        //增加自定义表情--------------------------------
        List<YCEmojiconGroupEntity> emojiconGroupList=new ArrayList<>();//一级表情集合
        //第一套表情
        List<YCEmojicon> emojiconList_1=new ArrayList<>();//二级表情集合 no.1
        for (int i=0;i<18;i++) {
            emojiconList_1.add(new YCEmojicon(R.mipmap.ic_launcher, "[" + i + "*]"));
            YCSmileUtils.addPattern(emojiconList_1.get(i).getEmojiText(),emojiconList_1.get(i).getIcon());
        }
        YCEmojiconGroupEntity ycEmojiconGroupEntity_1=new YCEmojiconGroupEntity();
        ycEmojiconGroupEntity_1.setIcon(emojiconList_1.get(0).getIcon());
        ycEmojiconGroupEntity_1.setName(emojiconList_1.get(0).getEmojiText());
        ycEmojiconGroupEntity_1.setEmojiconList(emojiconList_1);
        emojiconGroupList.add(ycEmojiconGroupEntity_1);
        //第二套表情
        List<YCEmojicon> emojiconList_2=new ArrayList<>();//二级表情集合 no.2
        for (int i=0;i<18;i++) {
            emojiconList_2.add(new YCEmojicon(R.mipmap.ic_launcher, "[" + i + "*]"));
            YCSmileUtils.addPattern(emojiconList_2.get(i).getEmojiText(),emojiconList_2.get(i).getIcon());
        }
        YCEmojiconGroupEntity ycEmojiconGroupEntity_2=new YCEmojiconGroupEntity();
        ycEmojiconGroupEntity_2.setIcon(emojiconList_2.get(0).getIcon());
        ycEmojiconGroupEntity_2.setName(emojiconList_2.get(0).getEmojiText());
        ycEmojiconGroupEntity_2.setEmojiconList(emojiconList_2);
        emojiconGroupList.add(ycEmojiconGroupEntity_2);
        inputMenu.init(emojiconGroupList);
        //----------------------------------------------
        //填充消息列表 完全自定义 通过往容器里加东西形成视图
        initMessageList();
    }

    //填充消息列表 一个容器想加哪种列表自己加
    List<String> mydata;
    BaseAdapter myadapter;
    private void initMessageList(){
        //填充消息列表 一个容器想加哪种列表自己加
        mydata=new ArrayList<>();
        ListView listView=new ListView(this);
        myadapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return mydata.size();
            }

            @Override
            public Object getItem(int i) {
                return mydata.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                view=View.inflate(ChatYeActivity.this,R.layout.message_item,null);
                Spannable span = YCSmileUtils.getSmiledText(ChatYeActivity.this, mydata.get(i));
                ((TextView)view.findViewById(R.id.message_text)).setText(span, TextView.BufferType.SPANNABLE);
                return view;
            }
        };
        listView.setAdapter(myadapter);
        list_container.addView(listView);
    }



    @Override
    protected void sendVoiceMessage(String voiceFilePath, int voiceTimeLength) {
        Log.i("yc","发送语音了 语音路径："+voiceFilePath+" 时长："+voiceTimeLength+"秒");
        mydata.add("发送语音了 语音路径："+voiceFilePath+" 时长："+voiceTimeLength+"秒");
        myadapter.notifyDataSetChanged();
    }

    @Override
    protected void sendTextMessage(String content) {
        Log.i("yc","发送文字了 内容："+content);
        mydata.add("发送文字了 内容："+content);
        myadapter.notifyDataSetChanged();
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
        mydata.add("发送图片了 图片路径："+absolutePath);
        myadapter.notifyDataSetChanged();
    }

    @Override
    protected void itemClick(int itemId, View view) {
        Log.i("yc","点击了"+itemId+"功能键");
        Toast.makeText(this,"点击了"+itemId+"功能键",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void sendFileMessage(String filePath) {
        Log.i("yc","发送文件了 文件路径："+filePath);
    }

    @Override
    protected void sendBigExpressionMessage(String name, String identityCode) {
        Log.i("yc","发送大表情了 名称:"+name+"  id:"+identityCode);
        mydata.add("发送大表情了 名称:"+name+"  id:"+identityCode);
        myadapter.notifyDataSetChanged();
    }
}
