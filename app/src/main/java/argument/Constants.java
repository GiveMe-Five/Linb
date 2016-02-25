package argument;

import com.example.zhanjiyuan.linb.R;

/**
 * Created by zhanjiyuan on 15/9/27.
 */
public class Constants {

    //Network
    public final static String SERVER_IP = "http://dev.weixin.tunnel.mobi/NewsCrawler/";

    //SPEECH
    public final static int SPEECH_EMPTY = 0,
                            SPEECH_PLAY = 1,
                            SPEECH_PAUSE = 2;

    //SYN
    public final static int MESSAGE_START = 0,
                            MESSAGE_PAUSE = 1,
                            MESSAGE_RESUME = 2,
                            MESSAGE_STOP = 3,
                            MESSAGE_END = 4;
    //ICON
    public final static int ICON_PLAY = R.drawable.play,
                            ICON_PAUSE = R.drawable.pause,
                            ICON_STOP = R.drawable.stop,
                            ICON_EMPTY = 0,
                            ICON_STAR = R.drawable.star;


    //BACKGROUND
    public final static int BACKGROUND_EMPTY = 0xFF666666,
                            BACKGROUND_MESSAGE = 0xFF6666FF;
}
