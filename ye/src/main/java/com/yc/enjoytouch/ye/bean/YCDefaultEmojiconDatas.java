package com.yc.enjoytouch.ye.bean;


import com.yc.enjoytouch.ye.R;
import com.yc.enjoytouch.ye.utils.YCEmojicon;
import com.yc.enjoytouch.ye.utils.YCSmileUtils;

public class YCDefaultEmojiconDatas {
    
    private static String[] emojis = new String[]{
        YCSmileUtils.ee_1,
        YCSmileUtils.ee_2,
        YCSmileUtils.ee_3,
        YCSmileUtils.ee_4,
        YCSmileUtils.ee_5,
        YCSmileUtils.ee_6,
        YCSmileUtils.ee_7,
        YCSmileUtils.ee_8,
        YCSmileUtils.ee_9,
        YCSmileUtils.ee_10,
        YCSmileUtils.ee_11,
        YCSmileUtils.ee_12,
        YCSmileUtils.ee_13,
        YCSmileUtils.ee_14,
        YCSmileUtils.ee_15,
        YCSmileUtils.ee_16,
        YCSmileUtils.ee_17,
        YCSmileUtils.ee_18,
        YCSmileUtils.ee_19,
        YCSmileUtils.ee_20,
        YCSmileUtils.ee_21,
        YCSmileUtils.ee_22,
        YCSmileUtils.ee_23,
        YCSmileUtils.ee_24,
        YCSmileUtils.ee_25,
        YCSmileUtils.ee_26,
        YCSmileUtils.ee_27,
        YCSmileUtils.ee_28,
        YCSmileUtils.ee_29,
        YCSmileUtils.ee_30,
        YCSmileUtils.ee_31,
        YCSmileUtils.ee_32,
        YCSmileUtils.ee_33,
        YCSmileUtils.ee_34,
        YCSmileUtils.ee_35,
       
    };
    
    private static int[] icons = new int[]{
        R.drawable.ee_1,  
        R.drawable.ee_2,
        R.drawable.ee_3,  
        R.drawable.ee_4,  
        R.drawable.ee_5,  
        R.drawable.ee_6,  
        R.drawable.ee_7,  
        R.drawable.ee_8,  
        R.drawable.ee_9,  
        R.drawable.ee_10,  
        R.drawable.ee_11,  
        R.drawable.ee_12,  
        R.drawable.ee_13,  
        R.drawable.ee_14,  
        R.drawable.ee_15,  
        R.drawable.ee_16,  
        R.drawable.ee_17,  
        R.drawable.ee_18,  
        R.drawable.ee_19,  
        R.drawable.ee_20,  
        R.drawable.ee_21,  
        R.drawable.ee_22,  
        R.drawable.ee_23,  
        R.drawable.ee_24,  
        R.drawable.ee_25,  
        R.drawable.ee_26,  
        R.drawable.ee_27,  
        R.drawable.ee_28,  
        R.drawable.ee_29,  
        R.drawable.ee_30,  
        R.drawable.ee_31,  
        R.drawable.ee_32,  
        R.drawable.ee_33,  
        R.drawable.ee_34,  
        R.drawable.ee_35,  
    };
    
    
    private static final YCEmojicon[] DATA = createData();
    
    private static YCEmojicon[] createData(){
        YCEmojicon[] datas = new YCEmojicon[icons.length];
        for(int i = 0; i < icons.length; i++){
            datas[i] = new YCEmojicon(icons[i], emojis[i], YCEmojicon.Type.NORMAL);
        }
        return datas;
    }
    
    public static YCEmojicon[] getData(){
        return DATA;
    }
}
