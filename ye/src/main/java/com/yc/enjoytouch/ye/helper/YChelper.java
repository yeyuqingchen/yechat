package com.yc.enjoytouch.ye.helper;

import android.content.Context;

/**
 * Created by Administrator on 2016/9/12.
 */
public class YChelper {

    private static YChelper yChelper;
    private static Context contexts;
    public  String currentUsername="398515876695";

    public YChelper(Context context) {
        this.contexts = context;
    }

    public static synchronized YChelper getyChelper(Context context){

        if (yChelper==null){
            if (contexts==null){
                yChelper=new YChelper(context);
            }else {
                yChelper=new YChelper(contexts);
            }
        }
        return yChelper;
    }

    /*
     *填充
     */


}
