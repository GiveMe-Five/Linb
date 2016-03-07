package module;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhanjiyuan.linb.R;
import com.iflytek.cloud.SpeechUtility;

import java.util.ArrayList;
import android.os.Handler;

import argument.Constants;
import gesture.MyGestureAdapter;
import gesture.MyGestureDetector;
import item.Item;
import item.ItemMessage;
import sound.IATService;
import sound.TTSService;

/**
 * Created by zhanjiyuan on 15/9/22.
 *
 */

public class ModuleList extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initLayout();
        initData();
        detector = new MyGestureDetector(getApplicationContext(), adapter);

        SpeechUtility.createUtility(this, "appid=54eed7e6");
        bindSpeechService();
        bindRecognitionService();

//        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
    }

    /* INIT LAYOUT
     *
     */
    LinearLayout list_background;
    TextView list_type_icon, list_type_text, list_type_status,
            list_operation_icon, list_operation_status;
    Typeface typeface_awesome;

    private void initLayout(){
        setContentView(R.layout.list);

        list_background = (LinearLayout)findViewById(R.id.list_background);
        list_type_icon = (TextView)findViewById(R.id.list_type_icon);
        list_type_text = (TextView)findViewById(R.id.list_type_text);
        list_type_status = (TextView)findViewById(R.id.list_type_status);
        list_operation_icon = (TextView)findViewById(R.id.list_operation_icon);
        list_operation_status = (TextView)findViewById(R.id.list_operation_status);

        typeface_awesome = Typeface.createFromAsset(getAssets(), Constants.font_awesome);
        list_type_icon.setTypeface(typeface_awesome);
        list_type_icon.setText(R.string.news);

        list_operation_icon.setTypeface(typeface_awesome);
        list_operation_icon.setText(R.string.loading);
//        list_operation_icon.setAnimation(Constants.animation_rotate);
    }

    /* MAIN CONTROL AND GESTURE
     *
     */
    private ArrayList<Item> item_arrayList = new ArrayList<Item>();
    private int index = 1;

    private double ruler;
    private int item_width = 500, item_remain = 250;
    private boolean
            flag_excelindex = false,
            flag_longpress = false;

    private int getIndex(){
        return index;
    }
    private Item getItem(int index){
        return item_arrayList.get(index - 1);
    }
    private int getListLen(){
        return item_arrayList.size();
    }

    private void checkIndex(int threshold){
        if (ruler >= threshold){
            ruler = 0;
            getItem(index).slipUp(flag_longpress);
        }
        else if (ruler <= -threshold){
            ruler = 0;
            getItem(index).slipDown(flag_longpress);
        }
    }

    private void emptyList(){
//        set;
    }

    public void lastItem(){
        destroyText();
        if (index - 1 >= 1){
            index--;
            getItem(index).autoRunBegin();
        }
        else {
            if (!flag_excelindex) {
//            setBackgroundAnimatorRet(backgroundColor, Constants.BACKGROUND_EMPTY);
                flag_excelindex = true;
                System.out.println("overTop");
            }
            if (item_arrayList.size() != 0) {
                index = 1;
            }
            else emptyList();
            getItem(index).setNull();
        }
    }
    public void nextItem(){
        destroyText();
        if (index + 1 <= item_arrayList.size()){
            index++;
            getItem(index).autoRunBegin();
        }
        else {
            if (!flag_excelindex) {
//            setBackgroundAnimatorRet(backgroundColor, Constants.BACKGROUND_EMPTY);
                flag_excelindex = true;
                System.out.println("overBottom");
            }
            if (item_arrayList.size() != 0){
                index = item_arrayList.size();
            }
            else emptyList();
            getItem(index).setNull();
        }
    }
    private void gestureFlagClear(){
        flag_excelindex = false;
        flag_longpress = false;
        ruler = 0;
    }

    private Tracker v_tracker = new Tracker();
    private MyGestureDetector detector;
    @Override
    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                v_tracker.trackAlloc(event);
                break;
            case MotionEvent.ACTION_UP:
                v_tracker.trackDispose();
                gestureFlagClear();
                break;
            case MotionEvent.ACTION_MOVE:
                ruler -= v_tracker.trackUpdate(event);
                checkIndex(item_width);
                break;
        }
        return detector.onTouchEvent(event);
    }

    private MyGestureAdapter adapter = new MyGestureAdapter(){
        @Override
        public boolean onSingleClick(MotionEvent ev){
            return getItem(index).singleClick();
        }

        @Override
        public boolean onDoubleClick(MotionEvent ev){
            return getItem(index).doubleClick();
        }

        @Override
        public boolean onLongPress(MotionEvent ev){
            flag_longpress = true;
            return getItem(index).longPress();
        }

        @Override
        public boolean onSlipUp(){
//            System.out.println("slipup");
//            return getItem(index).slipUp();
            return true;
        }

        @Override
        public boolean onSlipDown(){
//            System.out.println("slipdown");
//            return getItem(index).slipDown();
            return true;
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
            item_arrayList.add(item);
    }

    /* UI CONTROL
     *
     */
    private int swift_dur_lane = 300;
    private int swift_dur_ret = 300;

    public void setBackgroundAnimatorLane(LinearLayout switcher, int colorFrom, int colorTo){
        //COLOR FORMAT: AABBGGRR
        ObjectAnimator translationUp = ObjectAnimator.ofInt(switcher, "backgroundColor", colorFrom, colorTo);
        translationUp.setInterpolator(new DecelerateInterpolator());
        translationUp.setDuration(swift_dur_lane);
//        translationUp.setRepeatCount(-1);
//        translationUp.setRepeatMode(Animation.REVERSE);
        translationUp.setEvaluator(new ArgbEvaluator());
        translationUp.start();
    }
    public void setBackgroundAnimatorRet(LinearLayout layout, int colorSide, int colorMid){
        //COLOR FORMAT: AABBGGRR
        ObjectAnimator translationUp = ObjectAnimator.ofInt(layout, "backgroundColor", colorSide, colorMid, colorSide);
        translationUp.setInterpolator(new DecelerateInterpolator());
        translationUp.setDuration(swift_dur_ret);
        translationUp.setEvaluator(new ArgbEvaluator());
        translationUp.start();
    }

    public void setOperationIcon(int icon){
        list_operation_icon.setText(icon);
    }

    private void initData(){
        item_arrayList.add(new ItemMessage(this, "我是标题一", "近日美国一堆新婚夫妇在婚礼上惊现了一名\"不速之客\"让新娘直接傻眼泪奔了，而这名\"不速之客\"不是别人正是美国总统奥巴马。据悉这对新人的婚礼是在一个高尔夫球场举行，而与此同时奥巴马当天也到了该高尔夫球场打球。"));
        item_arrayList.add(new ItemMessage(this, "我是标题二", "日前，央视新修订版的《播音员主持人管理办法》在台内部发布。按央视官方发布的口径，此次修订版的《办法》打破以往传统的播音员、主持人终身制，实现频道与主持人之间的双向选择，而考核制度方面也奖惩分明，凡考核不合格者，将被调离播音主持岗位。"));
        item_arrayList.add(new ItemMessage(this, "我是标题三", "人社部部长尹蔚民昨天介绍了“十二五”以来就业和社会保障工作成就，称我国是目前世界上退休年龄最早的国家，平均退休年龄不到55岁。经中央批准后，人社部将向社会公开延迟退休改革方案，通过小步慢走，每年推迟几个月，逐步推迟到合理的退休年龄。"));
        item_arrayList.add(new ItemMessage(this, "我是标题四", "男子每天吃5斤辣椒，被称“辣椒王”。该男子是河南新郑市龙湖镇沙窝李村村民，名叫李永志，他竟能把辣椒当饭吃，每天要吃3到5斤的辣椒。他曾参加过不少吃辣椒比赛，打败不少“辣椒王子”，赢得殊荣，被称“辣椒王”。"));
        item_arrayList.add(new ItemMessage(this, "我是标题五", "一年前，在石狮经营海鲜火锅店的王勇海的妻子无意间从一个黄螺体中切出一颗珠子，随后便渐渐忘了这件事。最近，有亲戚告诉王先生，网上也有一颗与他收藏的“龙珠”很像，价值不菲，他这才如梦初醒，将原本随意存放的“宝珠”藏进了保险柜。"));
        item_arrayList.add(new ItemMessage(this, "我是标题六", "104岁老人长黑发换新牙，相信你一定觉得不可思议。湖北随县城关104岁婆婆杨传玉身体健康，不但生活自理，还能帮家人做些家务事。让人惊奇的是，杨传玉100岁以后竟然长了新牙和黑头发，而且皮肤不松弛很有弹性，当地人称这位百岁婆婆返老还童。"));
    }

    /* SPEECH SERVER
     *
     */

    private TTSService speech;
    private IATService recognition;
    private boolean isSpeechServiceBound = false, isRecognitionServiceBound = false;

    private ServiceConnection speechConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            speech = ((TTSService.LocalBinder)service).getService();
            System.out.println("Speech : " + speech);
            speech.initialize(sound_handler);
            sound_handler.sendMessage(Constants.createMessage(Constants.MESSAGE_SPEECH_READY));
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            speech = null;
        }
    };
    private ServiceConnection recognitionConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            recognition = ((IATService.LocalBinder)service).getService();
            System.out.println("Recognition : " + recognition);
            recognition.initialize(sound_handler);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            recognition = null;
        }
    };

    private void bindSpeechService(){
        System.out.println("Begin to bind service");
        final Intent serviceIntent = new Intent(getApplicationContext(), TTSService.class);
        boolean is = getApplicationContext().bindService(serviceIntent, speechConnection, Context.BIND_AUTO_CREATE);
        System.out.println("bind service is " + is);
        isSpeechServiceBound = true;
        System.out.println("End to bind service");
    }
    private void bindRecognitionService(){
        System.out.println("Begin to bind service");
        final Intent serviceIntent = new Intent(getApplicationContext(), IATService.class);
        boolean is = getApplicationContext().bindService(serviceIntent, recognitionConnection, Context.BIND_AUTO_CREATE);
        System.out.println("bind service is "+is);
        isRecognitionServiceBound = true;
        System.out.println("End to bind service");
    }
    @Override
    protected void onDestroy() {
        if(isSpeechServiceBound){
            unbindService(speechConnection);
            speech = null;
        }
        if (isRecognitionServiceBound){
            unbindService(recognitionConnection);
            recognition = null;
        }
        super.onDestroy();
    }

    /* SPEECH CONTROL
     *
     */
    private int speech_status = Constants.SPEECH_NULL;
    public void speakText(String str){
        destroyText();
        speech.speechStart(str);
    }
    public void pauseText(){
        if (speech_status == Constants.SPEECH_PLAY){
            speech.speechPause();
        }
    }
    public void resumeText(){
        if (speech_status == Constants.SPEECH_PAUSE){
            speech.speechResume();
        }
    }
    public void stopText(){
        if (speech_status != Constants.SPEECH_NULL){
            speech.speechStop();
        }
    }
    private void destroyText(){
        if (speech_status != Constants.SPEECH_NULL){
            speech.speechDestroySilence();
        }
    }

    private int listen_status = Constants.LISTEN_NULL;
    public void startListen(){
        if (listen_status != Constants.LISTEN_NULL) recognition.cancelListen();
        if (speech_status != Constants.SPEECH_NULL) speech.speechDestroySilence();
        recognition.startListen();
    }

    public void stopListen(){
        if (listen_status == Constants.LISTEN_START)
            recognition.stopListen();
    }

    public void cancelListen(){
        if (listen_status == Constants.LISTEN_START)
            recognition.cancelListen();
    }

    public int getSpeechStatus(){
        return speech_status;
    }
    public int getListenStatus(){
        return listen_status;
    }

    private Handler sound_handler = new Handler(){
        public void handleMessage(Message msg){
            switch(msg.what){
                case Constants.MESSAGE_SPEECH_READY:
                    getItem(index).autoRunBegin();
                    break;
                case Constants.MESSAGE_SPEECH_START:
                    speech_status = Constants.SPEECH_PLAY;
                    getItem(index).speechStart();
                    break;
                case Constants.MESSAGE_SPEECH_PAUSE:
                    speech_status = Constants.SPEECH_PAUSE;
                    getItem(index).speechPause();
                    break;
                case Constants.MESSAGE_SPEECH_RESUME:
                    speech_status = Constants.SPEECH_PLAY;
                    getItem(index).speechResume();
                    break;
                case Constants.MESSAGE_SPEECH_STOP:
                    speech_status = Constants.SPEECH_NULL;
                    getItem(index).speechStop();
                    break;
                case Constants.MESSAGE_SPEECH_END:
                    speech_status = Constants.SPEECH_NULL;
                    getItem(index).speechEnd();
                    break;

                case Constants.MESSAGE_LISTEN_READY:
                    break;
                case Constants.MESSAGE_LISTEN_START:
                    listen_status = Constants.LISTEN_START;
//                    setBackgroundAnimatorLane(list_background, R.color.light_blue, R.color.light_green);
                    getItem(index).listenStart();
                    break;
                case Constants.MESSAGE_LISTEN_COMPLETE:
                    listen_status = Constants.LISTEN_ANALYSIS;
                    getItem(index).listenComplete();
                    break;
                case Constants.MESSAGE_LISTEN_CANCEL:
                    listen_status = Constants.LISTEN_NULL;
                    getItem(index).listenCancel();
                    break;
                case Constants.MESSAGE_LISTEN_RESULT:
                    String result = ((StringBuffer)(msg.obj)).toString();
                    System.out.println(listen_status + " result = " + result);
                    listen_status = Constants.LISTEN_NULL;
                    list_operation_status.setText(result);
//                    setBackgroundAnimatorLane(list_background, R.color.light_green, R.color.light_blue);
                    getItem(index).listenResult(result);
                    break;
                case Constants.MESSAGE_SPEECH_DESTROY:
                    speech_status = Constants.SPEECH_NULL;
                    break;
            }
        }
    };

//    private Vibrator vibrator;
//    public void startVibrator(long time){
////        vibrator.vibrate(time);
////        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL);
////        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
////            r.play();
//    }
//
//    public void stopVibrator(){
//        vibrator.cancel();
//    }

}