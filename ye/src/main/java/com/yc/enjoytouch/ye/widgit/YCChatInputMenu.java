package com.yc.enjoytouch.ye.widgit;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yc.enjoytouch.ye.bean.YCDefaultEmojiconDatas;
import com.yc.enjoytouch.ye.utils.IdUtil;
import com.yc.enjoytouch.ye.utils.YCEmojicon;
import com.yc.enjoytouch.ye.utils.YCEmojiconGroupEntity;
import com.yc.enjoytouch.ye.utils.YCSmileUtils;
import com.yc.enjoytouch.ye.widgit.emojicon.YCEmojiconMenu;
import com.yc.enjoytouch.ye.widgit.emojicon.YCEmojiconMenuBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 聊天页面底部的聊天输入菜单栏 <br/>
 * 主要包含3个控件:YCChatPrimaryMenu(主菜单栏，包含文字输入、发送等功能), <br/>
 * YCChatExtendMenu(扩展栏，点击加号按钮出来的小宫格的菜单栏), <br/>
 * 以及YCEmojiconMenu(表情栏)
 */
public class YCChatInputMenu extends LinearLayout {
    FrameLayout primaryMenuContainer, emojiconMenuContainer;
    protected YCChatPrimaryMenuBase chatPrimaryMenu;
    protected YCEmojiconMenuBase emojiconMenu;
    protected YCChatExtendMenu chatExtendMenu;
    protected FrameLayout chatExtendMenuContainer;
    protected LayoutInflater layoutInflater;

    private Handler handler = new Handler();
    private ChatInputMenuListener listener;
    private Context context;
    private boolean inited;

    public YCChatInputMenu(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public YCChatInputMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public YCChatInputMenu(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
//        layoutInflater.inflate(R.layout.widget_chat_input_menu, this);
        layoutInflater.inflate(IdUtil.getIdByName(context,"layout","widget_chat_input_menu"), this);


//        primaryMenuContainer = (FrameLayout) findViewById(R.id.primary_menu_container);
        primaryMenuContainer = (FrameLayout) findViewById(IdUtil.getIdByName(context,"id","primary_menu_container"));

//        emojiconMenuContainer = (FrameLayout) findViewById(R.id.emojicon_menu_container);
        emojiconMenuContainer = (FrameLayout) findViewById(IdUtil.getIdByName(context,"id","emojicon_menu_container"));

//        chatExtendMenuContainer = (FrameLayout) findViewById(R.id.extend_menu_container);
        chatExtendMenuContainer = (FrameLayout) findViewById(IdUtil.getIdByName(context,"id","extend_menu_container"));



         // 扩展按钮栏
//         chatExtendMenu = (YCChatExtendMenu) findViewById(R.id.extend_menu);
        chatExtendMenu = (YCChatExtendMenu) findViewById(IdUtil.getIdByName(context,"id","extend_menu"));
        

    }

    /**
     * init view 此方法需放在registerExtendMenuItem后面及setCustomEmojiconMenu，
     * setCustomPrimaryMenu(如果需要自定义这两个menu)后面
     * @param emojiconGroupList 表情组类别，传null使用easeui默认的表情
     */
    public void init(List<YCEmojiconGroupEntity> emojiconGroupList) {
        if(inited){
            return;
        }
        // 主按钮菜单栏,没有自定义的用默认的
        if(chatPrimaryMenu == null){
//            chatPrimaryMenu = (YCChatPrimaryMenuBase) layoutInflater.inflate(R.layout.layout_chat_primary_menu, null);
            chatPrimaryMenu = (YCChatPrimaryMenuBase) layoutInflater.inflate(IdUtil.getIdByName(context,"layout","layout_chat_primary_menu"), null);
        }
        primaryMenuContainer.addView(chatPrimaryMenu);

        // 表情栏，没有自定义的用默认的
        if(emojiconMenu == null){
//            emojiconMenu = (YCEmojiconMenu) layoutInflater.inflate(R.layout.layout_emojicon_menu, null);
            emojiconMenu = (YCEmojiconMenu) layoutInflater.inflate(IdUtil.getIdByName(context,"layout","layout_emojicon_menu"), null);
            if(emojiconGroupList == null){
                emojiconGroupList = new ArrayList<YCEmojiconGroupEntity>();
//                emojiconGroupList.add(new YCEmojiconGroupEntity(R.drawable.ee_1,  Arrays.asList(YCDefaultEmojiconDatas.getData())));
                emojiconGroupList.add(new YCEmojiconGroupEntity(IdUtil.getIdByName(context,"drawable","ee_1"),  Arrays.asList(YCDefaultEmojiconDatas.getData())));
            }
            ((YCEmojiconMenu)emojiconMenu).init(emojiconGroupList);
        }
        emojiconMenuContainer.addView(emojiconMenu);

        processChatMenu();
        // 初始化extendmenu
        chatExtendMenu.init();
        
        inited = true;
    }
    
    public void init(){
        init(null);
    }
    
    /**
     * 设置自定义的表情栏，该控件需要继承自EaseEmojiconMenuBase，
     * 以及回调你想要回调出去的事件给设置的EaseEmojiconMenuListener
     * @param customEmojiconMenu
     */
    public void setCustomEmojiconMenu(YCEmojiconMenuBase customEmojiconMenu){
        this.emojiconMenu = customEmojiconMenu;
    }
    
    /**
     * 设置自定义的主菜单栏，该控件需要继承自EaseChatPrimaryMenuBase，
     * 以及回调你想要回调出去的事件给设置的EaseEmojiconMenuListener
     * @param customPrimaryMenu
     */
    public void setCustomPrimaryMenu(YCChatPrimaryMenuBase customPrimaryMenu){
        this.chatPrimaryMenu = customPrimaryMenu;
    }
    
    public YCChatPrimaryMenuBase getPrimaryMenu(){
        return chatPrimaryMenu;
    }
    
    public YCChatExtendMenu getExtendMenu(){
        return chatExtendMenu;
    }
    
    public YCEmojiconMenuBase getEmojiconMenu(){
        return emojiconMenu;
    }
    

    /**
     * 注册扩展菜单的item
     * 
     * @param name
     *            item名字
     * @param drawableRes
     *            item背景
     * @param itemId
     *            id
     * @param listener
     *            item点击事件
     */
    public void registerExtendMenuItem(String name, int drawableRes, int itemId,
                                       YCChatExtendMenu.YCChatExtendMenuItemClickListener listener) {
        chatExtendMenu.registerMenuItem(name, drawableRes, itemId, listener);
    }

    /**
     * 注册扩展菜单的item
     * 
     * @param nameRes
     *            item名字
     * @param drawableRes
     *            item背景
     * @param itemId
     *            id
     * @param listener
     *            item点击事件
     */
    public void registerExtendMenuItem(int nameRes, int drawableRes, int itemId,
            YCChatExtendMenu.YCChatExtendMenuItemClickListener listener) {
        chatExtendMenu.registerMenuItem(nameRes, drawableRes, itemId, listener);
    }


    protected void processChatMenu() {
        // 发送消息栏
        chatPrimaryMenu.setChatPrimaryMenuListener(new YCChatPrimaryMenuBase.YCChatPrimaryMenuListener() {

            @Override
            public void onSendBtnClicked(String content) {
                if (listener != null)
                    listener.onSendMessage(content);
            }

            @Override
            public void onToggleVoiceBtnClicked() {
                hideExtendMenuContainer();
            }

            @Override
            public void onToggleExtendClicked() {
                toggleMore();
            }

            @Override
            public void onToggleEmojiconClicked() {
                toggleEmojicon();
            }

            @Override
            public void onEditTextClicked() {
                hideExtendMenuContainer();
            }


            @Override
            public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
                if(listener != null){
                    return listener.onPressToSpeakBtnTouch(v, event);
                }
                return false;
            }
        });

        // emojicon menu
        emojiconMenu.setEmojiconMenuListener(new YCEmojiconMenuBase.YCEmojiconMenuListener() {

            @Override
            public void onExpressionClicked(YCEmojicon emojicon) {
                if(emojicon.getType() != YCEmojicon.Type.BIG_EXPRESSION){
                    if(emojicon.getEmojiText() != null){
                        chatPrimaryMenu.onEmojiconInputEvent(YCSmileUtils.getSmiledText(context,emojicon.getEmojiText()));
                    }
                }else{
                    if(listener != null){
                        listener.onBigExpressionClicked(emojicon);
                    }
                }
            }

            @Override
            public void onDeleteImageClicked() {
                chatPrimaryMenu.onEmojiconDeleteEvent();
            }
        });

    }

    /**
     * 显示或隐藏图标按钮页
     * 
     */
    protected void toggleMore() {
        if (chatExtendMenuContainer.getVisibility() == View.GONE) {
            hideKeyboard();
            handler.postDelayed(new Runnable() {
                public void run() {
                    chatExtendMenuContainer.setVisibility(View.VISIBLE);
                    chatExtendMenu.setVisibility(View.VISIBLE);
                    emojiconMenu.setVisibility(View.GONE);
                }
            }, 50);
        } else {
            if (emojiconMenu.getVisibility() == View.VISIBLE) {
                emojiconMenu.setVisibility(View.GONE);
                chatExtendMenu.setVisibility(View.VISIBLE);
            } else {
                chatExtendMenuContainer.setVisibility(View.GONE);
            }

        }

    }

    /**
     * 显示或隐藏表情页
     */
    protected void toggleEmojicon() {
        if (chatExtendMenuContainer.getVisibility() == View.GONE) {
            hideKeyboard();
            handler.postDelayed(new Runnable() {
                public void run() {
                    chatExtendMenuContainer.setVisibility(View.VISIBLE);
                    chatExtendMenu.setVisibility(View.GONE);
                    emojiconMenu.setVisibility(View.VISIBLE);
                }
            }, 50);
        } else {
            if (emojiconMenu.getVisibility() == View.VISIBLE) {
                chatExtendMenuContainer.setVisibility(View.GONE);
                emojiconMenu.setVisibility(View.GONE);
            } else {
                chatExtendMenu.setVisibility(View.GONE);
                emojiconMenu.setVisibility(View.VISIBLE);
            }

        }
    }

    /**
     * 隐藏软键盘
     */
    private void hideKeyboard() {
        chatPrimaryMenu.hideKeyboard();
    }

    /**
     * 隐藏整个扩展按钮栏(包括表情栏)
     */
    public void hideExtendMenuContainer() {
        chatExtendMenu.setVisibility(View.GONE);
        emojiconMenu.setVisibility(View.GONE);
        chatExtendMenuContainer.setVisibility(View.GONE);
        chatPrimaryMenu.onExtendMenuContainerHide();
    }

    /**
     * 系统返回键被按时调用此方法
     * 
     * @return 返回false表示返回键时扩展菜单栏时打开状态，true则表示按返回键时扩展栏是关闭状态<br/>
     *         如果返回时打开状态状态，会先关闭扩展栏再返回值
     */
    public boolean onBackPressed() {
        if (chatExtendMenuContainer.getVisibility() == View.VISIBLE) {
            hideExtendMenuContainer();
            return false;
        } else {
            return true;
        }

    }
    

    public void setChatInputMenuListener(ChatInputMenuListener listener) {
        this.listener = listener;
    }

    public interface ChatInputMenuListener {
        /**
         * 发送消息按钮点击
         * 
         * @param content
         *            文本内容
         */
        void onSendMessage(String content);
        
        /**
         * 大表情被点击
         * @param emojicon
         */
        void onBigExpressionClicked(YCEmojicon emojicon);

        /**
         * 长按说话按钮touch事件
         * @param v
         * @param event
         * @return
         */
        boolean onPressToSpeakBtnTouch(View v, MotionEvent event);
    }
    
}
