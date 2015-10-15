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

public class OfflineSpeechSynthesizer extends Service implements SpeechSynthesizerListener{
    private LocalBinder localBinder = new LocalBinder();
    SpeechSynthesizer speechSynthesizer = null;
    private static int ON_EMPTY = 0,
                ON_PLAYING = 1,
                ON_PASUE = 2;
    private int status = ON_EMPTY;

    private void init(){
        System.loadLibrary("gnustl_shared");
        // 部分版本不需要BDSpeechDecoder_V1
        try {
            System.loadLibrary("BDSpeechDecoder_V1");
        } catch (UnsatisfiedLinkError e) {
            SpeechLogger.logD("load BDSpeechDecoder_V1 failed, ignore");
        }
        System.loadLibrary("bd_etts");
        System.loadLibrary("bds");

        speechSynthesizer = SpeechSynthesizer.newInstance(SpeechSynthesizer.SYNTHESIZER_AUTO, getApplicationContext(), "holder", this);
        speechSynthesizer.setApiKey("m2eQaVMpmZdYPk62RMttxwWe", "babd78812af739bb428329aca78cec7c");
        speechSynthesizer.setAppId("7027882");
        //speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, "libs/temp_license_2015-10-15");
    }
    public void TextToSpeech(String text) {
        init();
        if (status != ON_EMPTY) speechSynthesizer.cancel();
        status = ON_PLAYING;
        speechSynthesizer.speak(text);
    }

    public class LocalBinder extends Binder {
        public OfflineSpeechSynthesizer getService(){
            return OfflineSpeechSynthesizer.this;
        }
    }

    public void pause() {
        if (status == ON_PLAYING){
            status = ON_PASUE;
            speechSynthesizer.pause();
        }
    }

    public void resume() {
        if (status == ON_PASUE) {
            status = ON_PLAYING;
            speechSynthesizer.resume();
        }
    }

    public void cancel() {
        if (status != ON_EMPTY) {
            status = ON_EMPTY;
            speechSynthesizer.cancel();
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return localBinder;
    }

    @Override
    public void onStartWorking(SpeechSynthesizer arg0) {

    }

    @Override
    public void onSpeechStart(SpeechSynthesizer synthesizer) {

    }

    @Override
    public void onSpeechResume(SpeechSynthesizer synthesizer) {

    }

    @Override
    public void onSpeechProgressChanged(SpeechSynthesizer synthesizer, int progress) {

    }

    @Override
    public void onSpeechPause(SpeechSynthesizer synthesizer) {

    }

    @Override
    public void onSpeechFinish(SpeechSynthesizer synthesizer) {

    }

    @Override
    public void onNewDataArrive(SpeechSynthesizer synthesizer, byte[] audioData, boolean isLastData) {

    }

    @Override
    public void onError(SpeechSynthesizer synthesizer, SpeechError error) {

    }

    @Override
    public void onCancel(SpeechSynthesizer synthesizer) {

    }

    @Override
    public void onBufferProgressChanged(SpeechSynthesizer synthesizer, int progress) {

    }

    @Override
    public void onSynthesizeFinish(SpeechSynthesizer arg0) {

    }
}