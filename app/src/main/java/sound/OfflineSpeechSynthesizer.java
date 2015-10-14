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

    public void TextToSpeech(String text) {
        speechSynthesizer = SpeechSynthesizer.newInstance(SpeechSynthesizer.SYNTHESIZER_AUTO, getApplicationContext(), "holder", this);
        speechSynthesizer.setApiKey("m2eQaVMpmZdYPk62RMttxwWe", "babd78812af739bb428329aca78cec7c");
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




