package item;

/**
 * Created by zhanjiyuan on 15/9/22.
 */
public abstract class Item {

    String key;

    public abstract int getStandardBackgroundColor();

    public abstract void autoRunBegin();
    public abstract void autoRunEnd();

    public abstract boolean clickOnce();
    public abstract boolean clickTwice();

    public abstract String getKey();

//    public abstract void speechStart();
//    public abstract void speechPause();
//    public abstract void speechResume();
//    public abstract void speechStop();
//    public abstract void speechFinish();
}

//    @Override
//    public void onSynthesizeFinish(SpeechSynthesizer speechSynthesizer) {
//
//    }
