package module;

import android.animation.AnimatorInflater;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;

import com.example.zhanjiyuan.linb.R;

import java.util.ArrayList;

import activity.NewsActivity;
import gesture.MyGestureAdapter;
import gesture.MyGestureDetector;
import item.Item;
import item.ItemMessage;
import sound.OfflineSpeechSynthesizer;

/**
 * Created by zhanjiyuan on 15/9/22.
 * 暂时作为模版使用的一个Activity，各种列表应当会在这个App中反复使用：
 *      例如：新闻列表，消息列表，好友列表etc
 * 因此可以将列表的操作完全放在这个类里面实现
 *      例如：归属于列表操作的上下滑动切换Item
 * 更多的操作例如点击，则由列表调用列表中Item的函数实现
 * private protected还没写。。。
 */

public class ModuleList extends Activity {

    //用于记录当前页面的item的列表
    private ArrayList<Item> itemArrayList = new ArrayList<Item>();
    private int index;
    private double ruler;

    //手势与速度侦测
    private VelocityTracker vTracker = null;
    private MyGestureDetector detector;

    //显示当前所处的item内容
    private int slipRate = 1000;

    private RelativeLayout listLayout;
    private ImageSwitcher imageswitcher;
    private ObjectAnimator objectAnimator_over_1, objectAnimator_over_2,
                           objectAnimator_swift_1, objectAnimator_swift_2;
    private boolean over_flag = false;

    //Service
    private OfflineSpeechSynthesizer speech;
    private boolean isServiceBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        listLayout = (RelativeLayout)findViewById(R.id.layout_list);

        detector = new MyGestureDetector(getApplicationContext(), adapter);
        initColorTran();
        initImageSwitcher();

        initData();
        bindSpeechService();
//        OfflineSpeechSynthesizer offlineSpeechSynthesizer = new OfflineSpeechSynthesizer();
//        offlineSpeechSynthesizer.bindService();
//        speech.TextToSpeech("哈哈哈哈耶");
    }

    private void speech(String str){
        speech.TextToSpeech(str);
    }

    private void bindSpeechService(){
        System.out.println("Begin to bind service");
        final Intent serviceIntent = new Intent(getApplicationContext(), OfflineSpeechSynthesizer.class);
        boolean is = this.bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
        System.out.println("bind service is "+is);
        isServiceBound = true;
        System.out.println("End to bind service");
    }

    /*
        列表的操作，例如上下滑动，越界检查以及动画的调用
     */

    private void updateDisplay(){
        int pre_index = index;
        index = (int)(ruler / slipRate);

        if (ruler < 0) overTop();
        else if (index >= itemArrayList.size()) overBottom();
        else if (pre_index != index){
            itemArrayList.get(index).autoRunEnd();
            over_flag = false;
            objectAnimator_swift_1.start();
            objectAnimator_swift_2.start();
            itemArrayList.get(index).autoRunBegin();
        }
    }

    private void toBottom(){
        System.out.println("toBottom");
        if (itemArrayList.size() != 0) index = itemArrayList.size() - 1;
        else emptyList();
    }
    private void toTop(){
        System.out.println("toTop");
        if (itemArrayList.size() != 0) index = 0;
        else emptyList();
    }
    private void overTop(){
        if (!over_flag) {
            objectAnimator_over_1.start();
            objectAnimator_over_2.start();
            over_flag = true;
        }
        System.out.println("overTop");
        if (itemArrayList.size() != 0) {
            index = 0;
            ruler = 0;
        }
        else emptyList();
    }
    private void overBottom(){
        if (!over_flag) {
            objectAnimator_over_1.start();
            objectAnimator_over_2.start();
            over_flag = true;
        }
        System.out.println("overBottom");
        if (itemArrayList.size() != 0){
            index = itemArrayList.size() - 1;
            ruler = index * slipRate;
        }
        else emptyList();
    }

    private void emptyList(){
//        set;
    }

    /*
        监听器的设置以及实现手势接口的各个函数
        如前面所提及的，onDown属于Item，则调用Item类的函数
        滑动属于List，则调用List类也就是本身定义的函数，因为所有列表上下滑动的意义都相同，所以不必重写
     */

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                if(vTracker == null)
                    vTracker = VelocityTracker.obtain();
                else
                    vTracker.clear();
                vTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_UP:
                over_flag = false;
                break;
            case MotionEvent.ACTION_MOVE:
                vTracker.addMovement(event);
                vTracker.computeCurrentVelocity(100);
                ruler -= vTracker.getYVelocity();
                updateDisplay();
                break;
        }
        return detector.onTouchEvent(event);
    }
    private MyGestureAdapter adapter = new MyGestureAdapter(){

        @Override
        public boolean onDown(MotionEvent ev){

//            speech("会红红火火");
//            return true;
            return itemArrayList.get(index).clickOnce();
        }

        @Override
        public boolean onDoubleClick(MotionEvent ev){
            return itemArrayList.get(index).clickTwice();
        }
    };


    /*
        TODO: Service
        服务器的连接，以及自己去抓取新的信息加入List，每个List抓取内容应该是不同的，需要重写，这里只是给个例子
        服务需要重写fetchItem
     */

    protected void fetchItem(String arg){
        //TODO OVERRIDE
    }

    protected void addItem(Item item){
        if (item != null)
            itemArrayList.add(item);
    }

    /*
        各种翻页的动画之类的
        一个想法，每个页面显示一个Item，包含一个纯色块背景＋白色巨大符号
        用来表现当前所处的位置以及状态，来提醒用户需要对应怎样的手势
        例如：蓝色背景表示处于List，对应上下滑动手势；再比如，红色背景对应Input，对应输入法
        也可以用来表示当前状态：例如红色代表Warning，绿色代表程序正常工作
        符号可以用来表示例如当前是播放状态的▶️，或是暂停状态的||
     */

    private void initImageSwitcher(){
        imageswitcher = (ImageSwitcher)findViewById(R.id.layout_list_image_switcher);
        imageswitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return new ImageView(ModuleList.this);
            }
        });

        imageswitcher.setImageResource(0);
        imageswitcher.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in));
        imageswitcher.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out));
    }

    public void setCurrentImage(int res){
        imageswitcher.setImageResource(res);
    }

    private void initColorTran(){
        listLayout.setBackgroundColor(getResources().getColor(R.color.news_color));

        objectAnimator_over_1 = (ObjectAnimator) AnimatorInflater.loadAnimator(ModuleList.this, R.animator.coloranimation_over_1);
        objectAnimator_over_1.setEvaluator(new ArgbEvaluator());
        objectAnimator_over_1.setTarget(listLayout);

        objectAnimator_over_2 = (ObjectAnimator) AnimatorInflater.loadAnimator(ModuleList.this, R.animator.coloranimation_over_2);
        objectAnimator_over_2.setEvaluator(new ArgbEvaluator());
        objectAnimator_over_2.setTarget(listLayout);
        objectAnimator_over_2.setStartDelay(200);

        objectAnimator_swift_1 = (ObjectAnimator) AnimatorInflater.loadAnimator(ModuleList.this, R.animator.coloranimation_swift_1);
        objectAnimator_swift_1.setEvaluator(new ArgbEvaluator());
        objectAnimator_swift_1.setTarget(listLayout);

        objectAnimator_swift_2 = (ObjectAnimator) AnimatorInflater.loadAnimator(ModuleList.this, R.animator.coloranimation_swift_2);
        objectAnimator_swift_2.setEvaluator(new ArgbEvaluator());
        objectAnimator_swift_2.setTarget(listLayout);
        objectAnimator_swift_2.setStartDelay(200);
    }

    private void initData(){
        itemArrayList.add(new ItemMessage(this, "我是标题一", "美丽的珍珠链历史的脊梁骨贯古今"));
        itemArrayList.add(new ItemMessage(this, "我是标题二", "悄悄的我走了正如我悄悄的来"));
        itemArrayList.add(new ItemMessage(this, "我是标题三", "春江潮水连海平海上明月共潮生"));
        itemArrayList.add(new ItemMessage(this, "我是标题四", "随风奔跑自由是方向"));
        itemArrayList.add(new ItemMessage(this, "我是标题五", "假如生活欺骗了你不要悲伤不要心急"));
        itemArrayList.add(new ItemMessage(this, "我是标题六", "我们在哪里我在在干什么"));
        itemArrayList.get(0).autoRunBegin();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            speech = ((OfflineSpeechSynthesizer.LocalBinder)service).getService();
            System.out.println("Speech : " + speech);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            speech = null;
        }
    };

    @Override
    protected void onDestroy() {
        if(isServiceBound){
            unbindService(mConnection);
            speech = null;
        }
        super.onDestroy();
    }
}