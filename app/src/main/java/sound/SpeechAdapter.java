package sound;

import com.baidu.speechsynthesizer.SpeechSynthesizer;
import com.baidu.speechsynthesizer.SpeechSynthesizerListener;
import com.baidu.speechsynthesizer.publicutility.SpeechError;

/**
 * Created by zhanjiyuan on 15/10/17.
 */
public class SpeechAdapter implements SpeechSynthesizerListener {
    @Override
    public void onStartWorking(SpeechSynthesizer speechSynthesizer) {
        System.out.println("onStartingWorking");
    }

    @Override
    public void onSpeechStart(SpeechSynthesizer speechSynthesizer) {
        System.out.println("onSpeechStart");
    }

    @Override
    public void onNewDataArrive(SpeechSynthesizer speechSynthesizer, byte[] bytes, boolean b) {
    }

    @Override
    public void onBufferProgressChanged(SpeechSynthesizer speechSynthesizer, int i) {
    }

    @Override
    public void onSpeechProgressChanged(SpeechSynthesizer speechSynthesizer, int i) {
    }

    @Override
    public void onSpeechPause(SpeechSynthesizer speechSynthesizer) {
        System.out.println("Pause");
    }

    @Override
    public void onSpeechResume(SpeechSynthesizer speechSynthesizer) {
        System.out.println("Resume");
    }

    @Override
    public void onCancel(SpeechSynthesizer speechSynthesizer) {
    }

    @Override
    public void onSynthesizeFinish(SpeechSynthesizer speechSynthesizer) {
    }

    @Override
    public void onSpeechFinish(SpeechSynthesizer speechSynthesizer) {
    }

    @Override
    public void onError(SpeechSynthesizer speechSynthesizer, SpeechError speechError) {
    }
}
