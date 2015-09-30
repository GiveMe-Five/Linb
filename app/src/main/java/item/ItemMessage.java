package item;

import module.ModuleList;

/**
 * Created by zhanjiyuan on 15/9/22.
 */
public class ItemMessage extends Item {

    private ModuleList rev;
    private static int ON_PLAY = 0,
                       ON_PAUSE = 1,
                       ON_STOP = 2;

    private String content;
    private int status;

    public ItemMessage(ModuleList activity, String title, String content){
        this.rev = activity;
        this.key = title;
        this.content = content;
    }

    @Override
    public void autoRunBegin() {
        status = ON_PLAY;
        System.out.println("I auto play the voice whose key is " + key);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        autoRunEnd();
    }

    @Override
    public void autoRunEnd() {
        if (status == ON_PLAY) System.out.println("Stop sound stream whose key is " + key);
    }

    @Override
    public boolean clickOnce() {
        if (status == ON_PLAY) {
            status = ON_PAUSE;
            System.out.println("I pause the voice whose key is " + key);
        }
        else if (status == ON_PAUSE) {
            status = ON_PLAY;
            System.out.println("I pause the voice whose key is " + key);
        }
        else if (status == ON_STOP) {
            status = ON_PLAY;
            System.out.println("I replay the voice whose key is " + key);
        }
        return true;
    }

    @Override
    public boolean clickTwice() {
        status = ON_STOP;
        System.out.println("I stop by clicking twice whose key is " + key);
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
}