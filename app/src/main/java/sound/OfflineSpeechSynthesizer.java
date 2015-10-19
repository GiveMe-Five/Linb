package sound;

/**
 * Created by luyichao on 10/14/15.
 */

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.baidu.speechsynthesizer.*;
import com.baidu.speechsynthesizer.publicutility.*;

public class OfflineSpeechSynthesizer extends Service{
    private LocalBinder localBinder = new LocalBinder();
    SpeechSynthesizer speechSynthesizer = null;

    public void init(SpeechSynthesizerListener listener){
        System.loadLibrary("gnustl_shared");
        // 部分版本不需要BDSpeechDecoder_V1
        try {
            System.loadLibrary("BDSpeechDecoder_V1");
        } catch (UnsatisfiedLinkError e) {
            SpeechLogger.logD("load BDSpeechDecoder_V1 failed, ignore");
        }
        System.loadLibrary("bd_etts");
        System.loadLibrary("bds");

        speechSynthesizer = SpeechSynthesizer.newInstance(SpeechSynthesizer.SYNTHESIZER_AUTO, getApplicationContext(), "holder", listener);
        speechSynthesizer.setApiKey("m2eQaVMpmZdYPk62RMttxwWe", "babd78812af739bb428329aca78cec7c");
        speechSynthesizer.setAppId("7027882");
        //speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, "libs/temp_license_2015-10-15");
    }
    public void TextToSpeech(String text) {
        speechSynthesizer.speak(text);
    }

    public class LocalBinder extends Binder {
        public OfflineSpeechSynthesizer getService(){
            return OfflineSpeechSynthesizer.this;
        }
    }

    public void pause() {
        speechSynthesizer.pause();
    }

    public void resume() {
        speechSynthesizer.resume();
    }

    public void cancel() {
        speechSynthesizer.cancel();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return localBinder;
    }
}