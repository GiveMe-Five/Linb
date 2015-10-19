package module;

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
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;

import com.baidu.speechsynthesizer.SpeechSynthesizer;
import com.baidu.speechsynthesizer.publicutility.SpeechError;
import com.example.zhanjiyuan.linb.R;

import java.util.ArrayList;

import argument.Constants;
import gesture.MyGestureAdapter;
import gesture.MyGestureDetector;
import item.Item;
import item.ItemMessage;
import sound.OfflineSpeechSynthesizer;
import sound.SpeechAdapter;

/**
 * Created by zhanjiyuan on 15/9/22.
 *
 */

public class ModuleList extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        listLayout = (RelativeLayout)findViewById(R.id.layout_list);

        detector = new MyGestureDetector(getApplicationContext(), adapter);
        initImageSwitcher();
        bindSpeechService();
        setBackground(Constants.BACKGROUND_EMPTY);

    }

    /* MAIN CONTROL
     *
     */
    private ArrayList<Item> itemArrayList = new ArrayList<Item>();
    private int index;
    private double ruler;
    private int itemWidth = 1000;
    private void updateIndex(){
        int preIndex = index;
        index = (int)(ruler / itemWidth);

        if (ruler < 0) overTop();
        else if (index >= itemArrayList.size()) overBottom();
        else if (preIndex != index){
            itemArrayList.get(preIndex).autoRunEnd();
            overFlag = false;
            setBackgroundAnimatorLane(backgroundColor, itemArrayList.get(index).getStandardBackgroundColor());
            itemArrayList.get(index).autoRunBegin();
        }
    }
    private void overTop(){
        if (!overFlag) {
            setBackgroundAnimatorRet(backgroundColor, Constants.BACKGROUND_EMPTY);
            overFlag = true;
            System.out.println("overTop");
        }
        if (itemArrayList.size() != 0) {
            index = 0;
            indexSyn();
        }
        else emptyList();
    }
    private void overBottom(){
        if (!overFlag) {
            setBackgroundAnimatorRet(backgroundColor, Constants.BACKGROUND_EMPTY);
            overFlag = true;
            System.out.println("overBottom");
        }
        if (itemArrayList.size() != 0){
            index = itemArrayList.size() - 1;
            indexSyn();
        }
        else emptyList();
    }
    private void emptyList(){
//        set;
    }
    private void indexSyn(){
        ruler = index * itemWidth + itemWidth / 2;
    }
    public void lastItem(){
        if (index - 1 >= 0) index--;
        indexSyn();
    }
    public void nextItem(){
        if (index + 1 <= itemArrayList.size() - 1) index++;
        indexSyn();
    }

    /* GESTURE
     *
     */
    private VelocityTracker vTracker = null;
    private MyGestureDetector detector;
    private int speedLimit = 150;
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
                overFlag = false;
                indexSyn();
                break;
            case MotionEvent.ACTION_MOVE:
                vTracker.addMovement(event);
                vTracker.computeCurrentVelocity(100);
//                System.out.println("speed = " + vTracker.getYVelocity());
                double delta = vTracker.getYVelocity();
                if (delta > speedLimit) delta = speedLimit;
                else if (delta < -speedLimit) delta = -speedLimit;
                ruler -= delta;
                updateIndex();
                break;
        }
        return detector.onTouchEvent(event);
    }
    private MyGestureAdapter adapter = new MyGestureAdapter(){

        @Override
        public boolean onDown(MotionEvent ev){
            return itemArrayList.get(index).clickOnce();
        }

        @Override
        public boolean onDoubleClick(MotionEvent ev){
            return itemArrayList.get(index).clickTwice();
        }
    };

    /* FETCH DATA
     *
     */
    protected void fetchItem(String arg){
        //TODO OVERRIDE
    }
    protected void addItem(Item item){
        if (item != null)
            itemArrayList.add(item);
    }

    /* UI CONTROL
     *
     */
    private RelativeLayout listLayout;
    private ImageSwitcher imageswitcher;
    private boolean overFlag = false;
    private int swiftDurLane = 400;
    private int swiftDurRet = 400;
    private int backgroundColor;
    private int iconStatus;
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
    public void setIcon(int icon){
        imageswitcher.setImageResource(icon);
        iconStatus = icon;
    }
    public void setBackground(int color){
        listLayout.setBackgroundColor(color);
        backgroundColor = color;
    }
    public void setBackgroundAnimatorLane(int colorFrom, int colorTo){
        //COLOR FORMAT: AABBGGRR
        ObjectAnimator translationUp = ObjectAnimator.ofInt(listLayout, "backgroundColor", colorFrom, colorTo);
        translationUp.setInterpolator(new DecelerateInterpolator());
        translationUp.setDuration(swiftDurLane);
//        translationUp.setRepeatCount(-1);
//        translationUp.setRepeatMode(Animation.REVERSE);
        translationUp.setEvaluator(new ArgbEvaluator());
        translationUp.start();
        backgroundColor = colorTo;
    }
    public void setBackgroundAnimatorRet(int colorSide, int colorMid){
        //COLOR FORMAT: AABBGGRR
        ObjectAnimator translationUp = ObjectAnimator.ofInt(listLayout, "backgroundColor", colorSide, colorMid, colorSide);
        translationUp.setInterpolator(new DecelerateInterpolator());
        translationUp.setDuration(swiftDurRet);
        translationUp.setEvaluator(new ArgbEvaluator());
        translationUp.start();
    }
    public int getBackgroundColor(){
        return backgroundColor;
    }
    public int getIconStatus(){
        return iconStatus;
    }

    private void initData(){
        itemArrayList.add(new ItemMessage(this, "我是标题一", "近日美国一堆新婚夫妇在婚礼上惊现了一名\"不速之客\"让新娘直接傻眼泪奔了，而这名\"不速之客\"不是别人正是美国总统奥巴马。据悉这对新人的婚礼是在一个高尔夫球场举行，而与此同时奥巴马当天也到了该高尔夫球场打球。"));
        itemArrayList.add(new ItemMessage(this, "我是标题二", "日前，央视新修订版的《播音员主持人管理办法》在台内部发布。按央视官方发布的口径，此次修订版的《办法》打破以往传统的播音员、主持人终身制，实现频道与主持人之间的双向选择，而考核制度方面也奖惩分明，凡考核不合格者，将被调离播音主持岗位。"));
        itemArrayList.add(new ItemMessage(this, "我是标题三", "人社部部长尹蔚民昨天介绍了“十二五”以来就业和社会保障工作成就，称我国是目前世界上退休年龄最早的国家，平均退休年龄不到55岁。经中央批准后，人社部将向社会公开延迟退休改革方案，通过小步慢走，每年推迟几个月，逐步推迟到合理的退休年龄。"));
        itemArrayList.add(new ItemMessage(this, "我是标题四", "男子每天吃5斤辣椒，被称“辣椒王”。该男子是河南新郑市龙湖镇沙窝李村村民，名叫李永志，他竟能把辣椒当饭吃，每天要吃3到5斤的辣椒。他曾参加过不少吃辣椒比赛，打败不少“辣椒王子”，赢得殊荣，被称“辣椒王”。"));
        itemArrayList.add(new ItemMessage(this, "我是标题五", "一年前，在石狮经营海鲜火锅店的王勇海的妻子无意间从一个黄螺体中切出一颗珠子，随后便渐渐忘了这件事。最近，有亲戚告诉王先生，网上也有一颗与他收藏的“龙珠”很像，价值不菲，他这才如梦初醒，将原本随意存放的“宝珠”藏进了保险柜。"));
        itemArrayList.add(new ItemMessage(this, "我是标题六", "104岁老人长黑发换新牙，相信你一定觉得不可思议。湖北随县城关104岁婆婆杨传玉身体健康，不但生活自理，还能帮家人做些家务事。让人惊奇的是，杨传玉100岁以后竟然长了新牙和黑头发，而且皮肤不松弛很有弹性，当地人称这位百岁婆婆返老还童。"));
        index = 1;
        updateIndex();
    }

    /* SPEECH SERVER
     *
     */
    private OfflineSpeechSynthesizer speech;
    private boolean isServiceBound = false;

    private SpeechAdapter speechAdapter = new SpeechAdapter(){
        @Override
        public void onSpeechFinish(SpeechSynthesizer speechSynthesizer){
            itemArrayList.get(index).speechFinish();
        }
    };
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            speech = ((OfflineSpeechSynthesizer.LocalBinder)service).getService();
            System.out.println("Speech : " + speech);
            speech.init(speechAdapter);
            initData();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            speech = null;
        }
    };
    private void bindSpeechService(){
        System.out.println("Begin to bind service");
        final Intent serviceIntent = new Intent(getApplicationContext(), OfflineSpeechSynthesizer.class);
        boolean is = this.bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
        System.out.println("bind service is "+is);
        isServiceBound = true;
        System.out.println("End to bind service");
    }
    @Override
    protected void onDestroy() {
        if(isServiceBound){
            unbindService(mConnection);
            speech = null;
        }
        super.onDestroy();
    }

    /* SPEECH CONTROL
     *
     */
    private int speechStatus = Constants.SPEECH_EMPTY;
    public int getSpeechStaus(){
        return speechStatus;
    }
    private void setSpeechStatus(int status){
        speechStatus = status;
    }
    public boolean speechText(String str){
        if (speechStatus != Constants.SPEECH_EMPTY) speech.cancel();
        speech.TextToSpeech(str);
        speechStatus = Constants.SPEECH_PLAY;
        return true;
    }
    public boolean pauseText(){
        if (speechStatus == Constants.SPEECH_PLAY){
            speech.pause();
            speechStatus = Constants.SPEECH_PAUSE;
            return true;
        }
        else return false;
    }
    public boolean resumeText(){
        if (speechStatus == Constants.SPEECH_PAUSE){
            speech.resume();
            speechStatus = Constants.SPEECH_PLAY;
            return true;
        }
        else return false;
    }
    public boolean stopText(){
        if (speechStatus != Constants.SPEECH_EMPTY){
            speech.cancel();
            speechStatus = Constants.SPEECH_EMPTY;
            return true;
        }
        else return false;
    }
}