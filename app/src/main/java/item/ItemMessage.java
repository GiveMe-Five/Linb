package item;

import argument.Constants;
import module.ModuleList;

/**
 * Created by zhanjiyuan on 15/9/22.
 */
public class ItemMessage extends Item{

    private ModuleList rev;

    private int ON_STOP = 0,
                ON_PLAY = 1,
                ON_PAUSE = 2;

    private String content;
    private int status = ON_STOP;

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
        if (rev.speechText(content)) {
            status = ON_PLAY;
            rev.setIcon(Constants.ICON_PLAY);
            System.out.println("autobegin");
        }
    }

    @Override
    public void autoRunEnd() {
        while (status != ON_STOP){
            if (rev.stopText())
                status = ON_STOP;
        }
        rev.setIcon(Constants.ICON_EMPTY);
    }

    @Override
    public boolean clickOnce() {
        if (status == ON_PLAY){
            System.out.println("status = onplay");
            if (rev.pauseText()) {
                status = ON_PAUSE;
                rev.setIcon(Constants.ICON_PAUSE);
            }
        }
        else if (status == ON_PAUSE){
            if (rev.resumeText()) {
                status = ON_PLAY;
                rev.setIcon(Constants.ICON_PLAY);
            }
        }
        else if (status == ON_STOP){
            if (rev.speechText(content)) {
                status = ON_PLAY;
                rev.setIcon(Constants.ICON_PLAY);
            }
        }
        return true;
    }

    @Override
    public boolean clickTwice() {
        if (status != ON_STOP){
            if (rev.stopText()) {
                status = ON_STOP;
                rev.setIcon(Constants.ICON_STOP);
            }
        }
        return true;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void speechFinish() {
        if (rev.stopText()) {
            status = ON_STOP;
            rev.setIcon(Constants.ICON_STOP);
        }
    }
}
