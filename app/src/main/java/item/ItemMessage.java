package item;

import com.baidu.speechsynthesizer.SpeechSynthesizer;
import com.baidu.speechsynthesizer.SpeechSynthesizerListener;
import com.baidu.speechsynthesizer.publicutility.SpeechError;
import com.example.zhanjiyuan.linb.R;

import module.ModuleList;
import sound.OfflineSpeechSynthesizer;

/**
 * Created by zhanjiyuan on 15/9/22.
 */
public class ItemMessage extends Item implements SpeechSynthesizerListener {

    private ModuleList rev;
    private static int ON_PLAY = R.drawable.play,
                       ON_PAUSE = R.drawable.pause,
                       ON_STOP = R.drawable.stop;

    private String content;
    private int status = ON_STOP;

    public ItemMessage(ModuleList activity, String title, String content){
        this.rev = activity;
        this.key = title;
        this.content = content;
    }

    @Override
    public void autoRunBegin() {
        System.out.println("I auto play the voice whose key is " + key);
//        itemPlay();
    }

    @Override
    public void autoRunEnd() {
        if (status == ON_PLAY){
            System.out.println("Stop sound stream whose key is " + key);
            itemEnd();
        }
    }

    @Override
    public boolean clickOnce() {
        if (status == ON_PLAY) itemPause();
        else if (status == ON_PAUSE) itemPlay();
        else if (status == ON_STOP) itemPlay();
        return true;
    }

    @Override
    public boolean clickTwice() {
        itemStop();
        return true;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getIcon(String status) {
        return null;
    }

    @Override
    public String getBackground(String status) {
        return null;
    }

    public void itemPlay(){
        if (status == ON_PAUSE){
            System.out.println("I play the voice whose key is " + key);
            rev.resumeText();
        }
        else if (status == ON_STOP){
            System.out.println("I replay the voice whose key is " + key);
            rev.speechText(content);
        }
        status = ON_PLAY;
        rev.setCurrentImage(ON_PLAY);
    }

    public void itemPause(){
        status = ON_PAUSE;
        System.out.println("I pause the voice whose key is " + key);
        rev.setCurrentImage(ON_PAUSE);
        rev.pauseText();
    }

    public void itemStop(){
        status = ON_STOP;
        System.out.println("I stop by clicking twice whose key is " + key);
        rev.setCurrentImage(ON_STOP);
        rev.stopText();
    }

    public void itemEnd(){
        status = ON_STOP;
        System.out.println("itemMessage is forcelly end");
    }

    public int getStatus(){
        return status;
    }

    @Override
    public void onStartWorking(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onSpeechStart(SpeechSynthesizer speechSynthesizer) {

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

    }

    @Override
    public void onSpeechResume(SpeechSynthesizer speechSynthesizer) {

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
