package com.yc.enjoytouch.ye.widgit.emojicon;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yc.enjoytouch.ye.utils.DensityUtil;
import com.yc.enjoytouch.ye.utils.IdUtil;

import java.util.ArrayList;
import java.util.List;

public class YCEmojiconScrollTabBar extends RelativeLayout{

    private Context context;
    private HorizontalScrollView scrollView;
    private LinearLayout tabContainer;
    
    private List<ImageView> tabList = new ArrayList<ImageView>();
    private YCScrollTabBarItemClickListener itemClickListener;
    
    private int tabWidth = 60;

    public YCEmojiconScrollTabBar(Context context) {
        this(context, null);
    }

    public YCEmojiconScrollTabBar(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public YCEmojiconScrollTabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    
    private void init(Context context, AttributeSet attrs){
        this.context = context;
        LayoutInflater.from(context).inflate(IdUtil.getIdByName(context,"layout","widget_emojicon_tab_bar"), this);
        
        scrollView = (HorizontalScrollView) findViewById(IdUtil.getIdByName(context,"id","scroll_view"));
        tabContainer = (LinearLayout) findViewById(IdUtil.getIdByName(context,"id","tab_container"));
    }
    
    /**
     * 添加tab
     * @param icon
     */
    public void addTab(int icon){
        View tabView = View.inflate(context, IdUtil.getIdByName(context,"layout","scroll_tab_item"), null);
        ImageView imageView = (ImageView) tabView.findViewById(IdUtil.getIdByName(context,"id","iv_icon"));
        imageView.setImageResource(icon);
        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(DensityUtil.dip2px(context, tabWidth), LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(imgParams);
        tabContainer.addView(tabView);
        tabList.add(imageView);
        final int position = tabList.size() -1;
        imageView.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    itemClickListener.onItemClick(position);
                }
            }
        });
    }
    
    /**
     * 移除tab
     * @param position
     */
    public void removeTab(int position){
        tabContainer.removeViewAt(position);
        tabList.remove(position);
    }
    
    public void selectedTo(int position){
        scrollTo(position);
        for (int i = 0; i < tabList.size(); i++) {
            if (position == i) {
                tabList.get(i).setBackgroundColor(getResources().getColor(IdUtil.getIdByName(context,"color","emojicon_tab_selected")));
            } else {
                tabList.get(i).setBackgroundColor(getResources().getColor(IdUtil.getIdByName(context,"color","emojicon_tab_nomal")));
            }
        }
    }
    
    private void scrollTo(final int position){
        int childCount = tabContainer.getChildCount();
        if(position < childCount){
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    int mScrollX = tabContainer.getScrollX();
                    int childX = (int) ViewCompat.getX(tabContainer.getChildAt(position));

                    if(childX < mScrollX){
                        scrollView.scrollTo(childX,0);
                        return;
                    }

                    int childWidth = (int)tabContainer.getChildAt(position).getWidth();
                    int hsvWidth = scrollView.getWidth();
                    int childRight = childX + childWidth;
                    int scrollRight = mScrollX + hsvWidth;

                    if(childRight > scrollRight){
                        scrollView.scrollTo(childRight - scrollRight,0);
                        return;
                    }
                }
            });
        }
    }
    
    
    public void setTabBarItemClickListener(YCScrollTabBarItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
    
    
    public interface YCScrollTabBarItemClickListener{
        void onItemClick(int position);
    }
    

}
