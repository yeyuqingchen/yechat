package com.yc.enjoytouch.ye.widgit;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yc.enjoytouch.ye.utils.IdUtil;


/**
 * 聊天输入栏主菜单栏
 *
 */
public class YCChatPrimaryMenu extends YCChatPrimaryMenuBase implements OnClickListener {
    private EditText editText;
    private View buttonSetModeKeyboard;
    private RelativeLayout edittext_layout;
    private View buttonSetModeVoice;
    private View buttonSend;
    private View buttonPressToSpeak;
    private ImageView faceNormal;
    private ImageView faceChecked;
    private Button buttonMore;
    private RelativeLayout faceLayout;
    private Context context;
    private YCVoiceRecorderView voiceRecorderView;

    public YCChatPrimaryMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public YCChatPrimaryMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YCChatPrimaryMenu(Context context) {
        super(context);
        init(context, null);
    }

    private void init(final Context context, AttributeSet attrs) {
        this.context = context;
        LayoutInflater.from(context).inflate(IdUtil.getIdByName(context,"layout","widget_chat_primary_menu"), this);
        editText = (EditText) findViewById(IdUtil.getIdByName(context,"id","et_sendmessage"));
        buttonSetModeKeyboard = findViewById(IdUtil.getIdByName(context,"id","btn_set_mode_keyboard"));
        edittext_layout = (RelativeLayout) findViewById(IdUtil.getIdByName(context,"id","edittext_layout"));
        buttonSetModeVoice = findViewById(IdUtil.getIdByName(context,"id","btn_set_mode_voice"));
        buttonSend = findViewById(IdUtil.getIdByName(context,"id","btn_send"));
        buttonPressToSpeak = findViewById(IdUtil.getIdByName(context,"id","btn_press_to_speak"));
        faceNormal = (ImageView) findViewById(IdUtil.getIdByName(context,"id","iv_face_normal"));
        faceChecked = (ImageView) findViewById(IdUtil.getIdByName(context,"id","iv_face_checked"));
        faceLayout = (RelativeLayout) findViewById(IdUtil.getIdByName(context,"id","rl_face"));
        buttonMore = (Button) findViewById(IdUtil.getIdByName(context,"id","btn_more"));
        edittext_layout.setBackgroundResource(IdUtil.getIdByName(context,"drawable","ease_input_bar_bg_normal"));
        
        buttonSend.setOnClickListener(this);
        buttonSetModeKeyboard.setOnClickListener(this);
        buttonSetModeVoice.setOnClickListener(this);
        buttonMore.setOnClickListener(this);
        faceLayout.setOnClickListener(this);
        editText.setOnClickListener(this);
        editText.requestFocus();
        
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edittext_layout.setBackgroundResource(IdUtil.getIdByName(context,"drawable","ease_input_bar_bg_active"));
                } else {
                    edittext_layout.setBackgroundResource(IdUtil.getIdByName(context,"drawable","ease_input_bar_bg_normal"));
                }

            }
        });
        // 监听文字框
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    buttonMore.setVisibility(View.GONE);
                    buttonSend.setVisibility(View.VISIBLE);
                } else {
                    buttonMore.setVisibility(View.VISIBLE);
                    buttonSend.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        
        
        buttonPressToSpeak.setOnTouchListener(new View.OnTouchListener() {
            
            @Override 
            public boolean onTouch(View v, MotionEvent event) {
                if(listener != null){
                    return listener.onPressToSpeakBtnTouch(v, event);
                }
                return false;
            }
        });
    }
    
    /**
     * 设置长按说话录制控件
     * @param voiceRecorderView
     */
    public void setPressToSpeakRecorderView(YCVoiceRecorderView voiceRecorderView){
        this.voiceRecorderView = voiceRecorderView;
    }

    /**
     * 表情输入
     * @param emojiContent
     */
    public void onEmojiconInputEvent(CharSequence emojiContent){
        editText.append(emojiContent);
    }
    
    /**
     * 表情删除
     */
    public void onEmojiconDeleteEvent(){
        if (!TextUtils.isEmpty(editText.getText())) {
            KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
            editText.dispatchKeyEvent(event);
        }
    }
    
    /**
     * 点击事件
     * @param view
     */
    @Override
    public void onClick(View view){
        int id = view.getId();
        if (id == IdUtil.getIdByName(context,"id","btn_send")) {
            if(listener != null){
                String s = editText.getText().toString();
                editText.setText("");
                listener.onSendBtnClicked(s);
            }
        } else if (id == IdUtil.getIdByName(context,"id","btn_set_mode_voice")) {
            setModeVoice();
            showNormalFaceImage();
            if(listener != null)
                listener.onToggleVoiceBtnClicked();
        } else if (id == IdUtil.getIdByName(context,"id","btn_set_mode_keyboard")) {
            setModeKeyboard();
            showNormalFaceImage();
            if(listener != null)
                listener.onToggleVoiceBtnClicked();
        } else if (id == IdUtil.getIdByName(context,"id","btn_more")) {
            buttonSetModeVoice.setVisibility(View.VISIBLE);
            buttonSetModeKeyboard.setVisibility(View.GONE);
            edittext_layout.setVisibility(View.VISIBLE);
            buttonPressToSpeak.setVisibility(View.GONE);
            showNormalFaceImage();
            if(listener != null)
                listener.onToggleExtendClicked();
        } else if (id == IdUtil.getIdByName(context,"id","et_sendmessage")) {
            edittext_layout.setBackgroundResource(IdUtil.getIdByName(context,"drawable","ease_input_bar_bg_active"));
            faceNormal.setVisibility(View.VISIBLE);
            faceChecked.setVisibility(View.INVISIBLE);
            if(listener != null)
                listener.onEditTextClicked();
        } else if (id == IdUtil.getIdByName(context,"id","rl_face")) {
            toggleFaceImage();
            if(listener != null){
                listener.onToggleEmojiconClicked();
            }
        } else {
        }
    }
    
    
    /**
     * 显示语音图标按钮
     * 
     */
    protected void setModeVoice() {
        hideKeyboard();
        edittext_layout.setVisibility(View.GONE);
        buttonSetModeVoice.setVisibility(View.GONE);
        buttonSetModeKeyboard.setVisibility(View.VISIBLE);
        buttonSend.setVisibility(View.GONE);
        buttonMore.setVisibility(View.VISIBLE);
        buttonPressToSpeak.setVisibility(View.VISIBLE);
        faceNormal.setVisibility(View.VISIBLE);
        faceChecked.setVisibility(View.INVISIBLE);

    }

    /**
     * 显示键盘图标
     */
    protected void setModeKeyboard() {
        edittext_layout.setVisibility(View.VISIBLE);
        buttonSetModeKeyboard.setVisibility(View.GONE);
        buttonSetModeVoice.setVisibility(View.VISIBLE);
        // mEditTextContent.setVisibility(View.VISIBLE);
        editText.requestFocus();
        // buttonSend.setVisibility(View.VISIBLE);
        buttonPressToSpeak.setVisibility(View.GONE);
        if (TextUtils.isEmpty(editText.getText())) {
            buttonMore.setVisibility(View.VISIBLE);
            buttonSend.setVisibility(View.GONE);
        } else {
            buttonMore.setVisibility(View.GONE);
            buttonSend.setVisibility(View.VISIBLE);
        }

    }
    
    
    protected void toggleFaceImage(){
        if(faceNormal.getVisibility() == View.VISIBLE){
            showSelectedFaceImage();
        }else{
            showNormalFaceImage();
        }
    }
    
    private void showNormalFaceImage(){
        faceNormal.setVisibility(View.VISIBLE);
        faceChecked.setVisibility(View.INVISIBLE);
    }
    
    private void showSelectedFaceImage(){
        faceNormal.setVisibility(View.INVISIBLE);
        faceChecked.setVisibility(View.VISIBLE);
    }
    

    @Override
    public void onExtendMenuContainerHide() {
        showNormalFaceImage();
    }

}
