package argument;

import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * Created by zhanjiyuan on 15/9/27.
 */
public class Constants {

    //Network
    public final static String SERVER_IP = "http://dev.weixin.tunnel.mobi/NewsCrawler/";

    //Font
    public final static String font_awesome = "fonts/fontawesome-webfont.ttf";

    //Speech
    public final static int
            SPEECH_NULL = 0,
            SPEECH_PLAY = 1,
            SPEECH_PAUSE = 2;

    //Listen
    public final static int
            LISTEN_START = 0,
            LISTEN_NULL = 1,
            LISTEN_ANALYSIS = 2;

    //Message between Main and Speech, Listen
    public final static int
            MESSAGE_SPEECH_START = 0,
            MESSAGE_SPEECH_PAUSE = 1,
            MESSAGE_SPEECH_RESUME = 2,
            MESSAGE_SPEECH_STOP = 3,
            MESSAGE_SPEECH_END = 4,
            MESSAGE_SPEECH_READY = 5,
            MESSAGE_LISTEN_START = 6,
            MESSAGE_LISTEN_CANCEL = 7,
            MESSAGE_LISTEN_COMPLETE = 8,
            MESSAGE_LISTEN_RESULT = 11,
            MESSAGE_LISTEN_READY = 9,
            MESSAGE_SPEECH_DESTROY = 10;

    public static RotateAnimation animation_rotate;
    static {
        animation_rotate =new RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation_rotate.setInterpolator(new LinearInterpolator());
        animation_rotate.setDuration(2000);//设置动画持续时间
        animation_rotate.setRepeatCount(-1);
    }

    public static Message createMessage(int what){
        Message msg = new Message();
        msg.what = what;
        return msg;
    }

}