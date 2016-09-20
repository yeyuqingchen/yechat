# yechat 
  enjoytouch-android-yechen(叶晨)
   
#  1.使用请先添加gradle依赖:
   
      (1) 根目录gradle加入：
      
          allprojects {
		       repositories {
		         	...
	         		maven { url "https://jitpack.io" }
        		}
        	}     
                  
      (2) 自己app gradle加入：
      
          compile 'com.github.yeyuqingchen:yechat:-SNAPSHOT'
       
 # 2.加入权限：
   
    <uses-permission android:name="android.permission.VIBRATE" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.GET_TASKS" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
    <uses-permission android:name="android.permission.GET_ACCOUNTS" />
    <uses-permission android:name="android.permission.BROADCAST_STICKY" />
    <uses-permission android:name="android.permission.WRITE_SETTINGS" />
      
       
       
  #3.写一个Activity继承抽象类ChatBaseActivity
   
     (ps:已填充布局,不可以再执行setContentView方法,不用再重写onCreate方法,
        在wantToDo方法里写你想做的事,否则无效果……………………………………)
   
     给出例子源码：（有疑问请联系QQ：398518700）
      
    //这个方法里你可以做你想做的事
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


    //----这个方法是一个简单的加消息列表的例子,可以参考 做自己的消息列表---------------------- 
    /**list_container是一个容器，
    通过add方法加入列表之类的控件 如下： */
    List<String> mydata;
    BaseAdapter myadapter;
    private void initMessageList(){
        //填充消息列表 一个容器想加哪种列表自己加上
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
    //------------------------------------------------------------------------



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

   
     
