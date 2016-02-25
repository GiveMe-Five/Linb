package item;

import argument.Constants;
import module.ModuleList;

/**
 * Created by zhanjiyuan on 15/9/22.
 */
public class ItemMessage extends Item{

    private ModuleList rev;

    private String content;

    public ItemMessage(ModuleList activity, String title, String content){
        this.rev = activity;
        this.key = title;
        this.content = content;
    }

    @Override
    public int getStandardBackgroundColor() {
        return Constants.BACKGROUND_MESSAGE;
    }

    @Override
    public void autoRunBegin() {
        rev.speakText(content);
        System.out.println("自动播放");
    }

    @Override
    public void autoRunEnd() {
        rev.stopText();
        System.out.println("自动销毁");
    }

    @Override
    public boolean clickOnce() {
        System.out.println(rev.speech.mTts.isSpeaking());
        int status = rev.getSpeechStatus();
        switch (status){
            case Constants.SPEECH_PLAY:
                rev.pauseText();

                break;
            case Constants.SPEECH_PAUSE:
                rev.resumeText();
                break;
            case Constants.SPEECH_EMPTY:
                rev.speakText(content);
                break;
        }
        return true;
    }

    @Override
    public boolean clickTwice() {
        if (rev.getSpeechStatus() != Constants.SPEECH_EMPTY)
            rev.stopText();
        return true;
    }

    @Override
    public String getKey() {
        return key;
    }
}
