package item;

import com.example.zhanjiyuan.linb.R;

import module.ModuleList;

/**
 * Created by zhanjiyuan on 15/9/22.
 */
public class ItemMessage extends Item {

    private ModuleList rev;
    private static int ON_PLAY = R.drawable.play,
                       ON_PAUSE = R.drawable.pause,
                       ON_STOP = R.drawable.stop;

    private String content;
    private int status;

    public ItemMessage(ModuleList activity, String title, String content){
        this.rev = activity;
        this.key = title;
        this.content = content;
    }

    @Override
    public void autoRunBegin() {
        System.out.println("I auto play the voice whose key is " + key);
        itemPlay();
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        autoRunEnd();
    }

    @Override
    public void autoRunEnd() {
        if (status == ON_PLAY){
            System.out.println("Stop sound stream whose key is " + key);
            itemStop();
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
        if (status == ON_PAUSE) System.out.println("I play the voice whose key is " + key);
        else if (status == ON_STOP) System.out.println("I replay the voice whose key is " + key);
        status = ON_PLAY;
        rev.setCurrentImage(ON_PLAY);
    }

    public void itemPause(){
        status = ON_PAUSE;
        System.out.println("I pause the voice whose key is " + key);
        rev.setCurrentImage(ON_PAUSE);
    }

    public void itemStop(){
        status = ON_STOP;
        System.out.println("I stop by clicking twice whose key is " + key);
        rev.setCurrentImage(ON_STOP);
    }
}
