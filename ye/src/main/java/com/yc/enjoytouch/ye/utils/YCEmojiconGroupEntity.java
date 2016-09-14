package com.yc.enjoytouch.ye.utils;


import java.util.List;

/**
 * 一组表情所对应的实体类
 *
 */
public class YCEmojiconGroupEntity {
    /**
     * 表情数据
     */
    private List<YCEmojicon> emojiconList;
    /**
     * 图片
     */
    private int icon;
    /**
     * 组名
     */
    private String name;
    /**
     * 表情类型
     */
    private YCEmojicon.Type type;
    
    public YCEmojiconGroupEntity(){}
    
    public YCEmojiconGroupEntity(int icon, List<YCEmojicon> emojiconList){
        this.icon = icon;
        this.emojiconList = emojiconList;
        type = YCEmojicon.Type.NORMAL;
    }
    
    public YCEmojiconGroupEntity(int icon, List<YCEmojicon> emojiconList, YCEmojicon.Type type){
        this.icon = icon;
        this.emojiconList = emojiconList;
        this.type = type;
    }
    
    public List<YCEmojicon> getEmojiconList() {
        return emojiconList;
    }
    public void setEmojiconList(List<YCEmojicon> emojiconList) {
        this.emojiconList = emojiconList;
    }
    public int getIcon() {
        return icon;
    }
    public void setIcon(int icon) {
        this.icon = icon;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public YCEmojicon.Type getType() {
        return type;
    }

    public void setType(YCEmojicon.Type type) {
        this.type = type;
    }
    
    
}
