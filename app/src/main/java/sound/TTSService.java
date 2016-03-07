package sound;

/**
 * Created by luyichao on 15/11/20.
 */

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Handler;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import argument.Constants;

public class TTSService extends Service {

    //service
    private LocalBinder localBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public TTSService getService(){
            return TTSService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return localBinder;
    }

    //sound
    public SpeechSynthesizer mTts;
    private AudioManager audiomanager;
    private Handler handler;
    private String defaultVoicer = "xiaoyan";
    private String defaultSpeed = "50";
    private String defaultPitch = "50";
    private String defaultVolume = "50";

    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                System.out.println("init error");
            }
        }
    };

    public void initialize(Handler handler) {
        mTts = SpeechSynthesizer.createSynthesizer(TTSService.this, mTtsInitListener);
        audiomanager = (AudioManager)TTSService.this.getSystemService(Context.AUDIO_SERVICE);
        this.handler = handler;
        setParam();
    }

    private void setParam(){
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置在线合成发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        //设置合成语速
        mTts.setParameter(SpeechConstant.SPEED, "50");
        //设置合成音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME, "50");
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");
    }

    public void speechStart(String text) {
        mTts.startSpeaking(text, mTtsListener);
    }

    public void speechPause() {
        mTts.pauseSpeaking();
        new Thread(){
            @Override
            public void run(){
                while (audiomanager.isMusicActive());
                handler.sendMessage(Constants.createMessage(Constants.MESSAGE_SPEECH_PAUSE));
            }
        }.start();
    }

    public void speechResume() {
        mTts.resumeSpeaking();
        new Thread(){
            @Override
            public void run(){
                while (!audiomanager.isMusicActive());
                handler.sendMessage(Constants.createMessage(Constants.MESSAGE_SPEECH_RESUME));
            }
        }.start();
    }

    public void speechStop(){
        mTts.stopSpeaking();
        new Thread(){
            @Override
            public void run(){
                while (mTts.isSpeaking());
                handler.sendMessage(Constants.createMessage(Constants.MESSAGE_SPEECH_STOP));
            }
        }.start();
    }

    public void speechDestroySilence(){
        mTts.stopSpeaking();
        new Thread(){
            @Override
            public void run(){
                while (mTts.isSpeaking());
                handler.sendMessage(Constants.createMessage(Constants.MESSAGE_SPEECH_DESTROY));
            }
        }.start();
    }

    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            handler.sendMessage(Constants.createMessage(Constants.MESSAGE_SPEECH_START));
        }

        @Override
        public void onSpeakPaused() {
        }

        @Override
        public void onSpeakResumed() {
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            // 合成进度
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
        }

        @Override
        public void onCompleted(SpeechError error) {
            handler.sendMessage(Constants.createMessage(Constants.MESSAGE_SPEECH_END));
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };
}