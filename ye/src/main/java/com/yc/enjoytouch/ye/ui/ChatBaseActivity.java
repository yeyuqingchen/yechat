package com.yc.enjoytouch.ye.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.ClipboardManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.yc.enjoytouch.ye.R;
import com.yc.enjoytouch.ye.helper.YChelper;
import com.yc.enjoytouch.ye.utils.PathUtil;
import com.yc.enjoytouch.ye.utils.YCEmojicon;
import com.yc.enjoytouch.ye.utils.YCEmojiconGroupEntity;
import com.yc.enjoytouch.ye.utils.YCSmileUtils;
import com.yc.enjoytouch.ye.widgit.YCChatExtendMenu;
import com.yc.enjoytouch.ye.widgit.YCChatInputMenu;
import com.yc.enjoytouch.ye.widgit.YCTitleBar;
import com.yc.enjoytouch.ye.widgit.YCVoiceRecorderView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class ChatBaseActivity extends FragmentActivity {
    protected static final int REQUEST_CODE_MAP = 1;
    protected static final int REQUEST_CODE_CAMERA = 2;
    protected static final int REQUEST_CODE_LOCAL = 3;

    protected YCChatInputMenu inputMenu;


    protected InputMethodManager inputManager;
    protected ClipboardManager clipboard;

    protected File cameraFile;
    protected YCVoiceRecorderView voiceRecorderView;
    protected YCTitleBar titleBar;

    protected MyItemClickListener extendMenuItemClickListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_base);
        PathUtil.getInstance().initDirs(null, YChelper.getyChelper(this).currentUsername,this);
        initView();
        wantToDo();
    }

    protected abstract void wantToDo();



    //img_Res 资源   name 标识
    protected void addEmojiconList(List<YCEmojicon> emojiconList){
        if (emojiconList==null) {
            inputMenu.init();
            return;
        }
        List<YCEmojiconGroupEntity> emojiconGroupList=new ArrayList<>();
        for (int i=0;i<emojiconList.size();i++) {
            YCSmileUtils.addPattern(emojiconList.get(i).getEmojiText(),emojiconList.get(i).getIcon());
        }
        YCEmojiconGroupEntity ycEmojiconGroupEntity=new YCEmojiconGroupEntity();
        ycEmojiconGroupEntity.setIcon(emojiconList.get(0).getIcon());
        ycEmojiconGroupEntity.setName(emojiconList.get(0).getEmojiText());
        ycEmojiconGroupEntity.setEmojiconList(emojiconList);
        emojiconGroupList.add(ycEmojiconGroupEntity);
        inputMenu.init(emojiconGroupList);
    }

    /**
     * init view
     */
    protected void initView() {
        voiceRecorderView = (YCVoiceRecorderView)findViewById(R.id.voice_recorder);
        titleBar= (YCTitleBar) findViewById(R.id.title_bar);
        extendMenuItemClickListener = new MyItemClickListener();
        inputMenu = (YCChatInputMenu)findViewById(R.id.input_menu);
        inputMenu.setChatInputMenuListener(new YCChatInputMenu.ChatInputMenuListener() {
            @Override
            public void onSendMessage(String content) {
                // 发送文本消息
                sendTextMessage(content);
            }

            @Override
            public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
                return voiceRecorderView.onPressToSpeakBtnTouch(v, event, new YCVoiceRecorderView.YCVoiceRecorderCallback() {

                    @Override
                    public void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength) {
                        // 发送语音消息
                        sendVoiceMessage(voiceFilePath, voiceTimeLength);
                    }
                });
            }
            @Override
            public void onBigExpressionClicked(YCEmojicon emojicon) {
                //发送大表情(动态表情)
                sendBigExpressionMessage(emojicon.getName(), emojicon.getIdentityCode());
            }
        });
        inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    //发送大表情消息
    protected abstract void sendBigExpressionMessage(String name, String identityCode);

    //发送语音消息
    protected abstract void sendVoiceMessage(String voiceFilePath, int voiceTimeLength);

    //发送文本消息
    protected abstract void sendTextMessage(String content);







    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onYeActivityResult(requestCode, resultCode, data);

    }

    protected abstract void onYeActivityResult(int requestCode, int resultCode, Intent data);

    //发送位置消息
    protected abstract void sendLocationMessage(double latitude, double longitude, String locationAddress);

    //发送图片消息
    protected abstract void sendImageMessage(String absolutePath);


    /**
     * 扩展菜单栏item点击事件
     *
     */
    class MyItemClickListener implements YCChatExtendMenu.YCChatExtendMenuItemClickListener{

        @Override
        public void onClick(int itemId, View view) {
            itemClick(itemId,view);
        }

    }

    protected abstract void itemClick(int itemId, View view);


    //发送消息方法
    //==========================================================================





    //===================================================================================


//    /**
//     * 根据图库图片uri发送图片
//     *
//     * @param selectedImage
//     */
//    protected void sendPicByUri(Uri selectedImage) {
//        String[] filePathColumn = { MediaStore.Images.Media.DATA };
//        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//            cursor = null;
//
//            if (picturePath == null || picturePath.equals("null")) {
//                Toast toast = Toast.makeText(this, "照片不存在", Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
//                return;
//            }
//            sendImageMessage(picturePath);
//        } else {
//            File file = new File(selectedImage.getPath());
//            if (!file.exists()) {
//                Toast toast = Toast.makeText(this, "照片不存在", Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
//                return;
//
//            }
//            sendImageMessage(file.getAbsolutePath());
//        }
//
//    }
//
//    /**
//     * 根据uri发送文件
//     * @param uri
//     */
//    protected void sendFileByUri(Uri uri){
//        String filePath = null;
//        if ("content".equalsIgnoreCase(uri.getScheme())) {
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//            Cursor cursor = null;
//
//            try {
//                cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
//                int column_index = cursor.getColumnIndexOrThrow("_data");
//                if (cursor.moveToFirst()) {
//                    filePath = cursor.getString(column_index);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            filePath = uri.getPath();
//        }
//        File file = new File(filePath);
//        if (file == null || !file.exists()) {
//            Toast.makeText(this, "不存在", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        //大于10M不让发送
//        if (file.length() > 10 * 1024 * 1024) {
//            Toast.makeText(this, "文件过大", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        sendFileMessage(filePath);
//    }
    //发送文件消息
    protected abstract void sendFileMessage(String filePath);

//    /**
//     * 照相获取图片
//     */
//    protected void selectPicFromCamera() {
//
//    }

//    /**
//     * 从图库获取图片
//     */
//    protected void selectPicFromLocal() {
//        Intent intent;
//        if (Build.VERSION.SDK_INT < 19) {
//            intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("image/*");
//
//        } else {
//            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        }
//        startActivityForResult(intent, REQUEST_CODE_LOCAL);
//    }



//    /**
//     * 点击清空聊天记录
//     *
//     */
//    protected void emptyHistory() {
//
//    }



    /**
     * 隐藏软键盘
     */
    protected void hideKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

   





}
