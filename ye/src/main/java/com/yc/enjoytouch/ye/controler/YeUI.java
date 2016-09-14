package com.yc.enjoytouch.ye.controler;

import android.content.Context;

import com.yc.enjoytouch.ye.utils.YCEmojicon;

import java.util.Map;

public final class YeUI {

    private static YeUI instance = null;
    

    private Context appContext = null;
    
    /**
     * init flag: test if the sdk has been inited before, we don't need to init again
     */
    private boolean sdkInited = false;
    

    private YeUI(){}
    
    /**
     * 获取EaseUI单实例对象
     * @return
     */
    public synchronized static YeUI getInstance(){
        if(instance == null){
            instance = new YeUI();
        }
        return instance;
    }
    

    

    
    /**
     * 表情信息提供者
     *
     */
    public interface EaseEmojiconInfoProvider {
        /**
         * 根据唯一识别号返回此表情内容
         * @param emojiconIdentityCode
         * @return
         */
        YCEmojicon getEmojiconInfo(String emojiconIdentityCode);
        
        /**
         * 获取文字表情的映射Map,map的key为表情的emoji文本内容，value为对应的图片资源id或者本地路径(不能为网络地址)
         * @return
         */
        Map<String, Object> getTextEmojiconMapping();
    }
    
    private EaseEmojiconInfoProvider emojiconInfoProvider;
    
    /**
     * 获取表情提供者
     * @return
     */
    public EaseEmojiconInfoProvider getEmojiconInfoProvider(){
        return emojiconInfoProvider;
    }
    
    /**
     * 设置表情信息提供者
     * @param emojiconInfoProvider
     */
    public void setEmojiconInfoProvider(EaseEmojiconInfoProvider emojiconInfoProvider){
        this.emojiconInfoProvider = emojiconInfoProvider;
    }
    

}
