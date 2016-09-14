package com.yc.enjoytouch.ye.widgit.emojicon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.yc.enjoytouch.ye.utils.YCEmojicon;


public class YCEmojiconMenuBase extends LinearLayout{
    protected YCEmojiconMenuListener listener;
    
    public YCEmojiconMenuBase(Context context) {
        super(context);
    }
    
    @SuppressLint("NewApi")
    public YCEmojiconMenuBase(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public YCEmojiconMenuBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    
    /**
     * 设置回调监听
     * @param listener
     */
    public void setEmojiconMenuListener(YCEmojiconMenuListener listener){
        this.listener = listener;
    }
    
    public interface YCEmojiconMenuListener{
        /**
         * 表情被点击
         * @param emojicon
         */
        void onExpressionClicked(YCEmojicon emojicon);
        /**
         * 删除按钮被点击
         */
        void onDeleteImageClicked();
    }
}
