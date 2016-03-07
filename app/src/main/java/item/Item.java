package item;

/**
 * Created by zhanjiyuan on 15/9/22.
 */
public abstract class Item {

    String key;

//    public abstract int getStandardBackgroundColor();

    public abstract void autoRunBegin();

//    public abstract String getKey();

    public abstract void speechStart();
    public abstract void speechPause();
    public abstract void speechResume();
    public abstract void speechStop();
    public abstract void speechEnd();

    public abstract void listenStart();
    public abstract void listenComplete();
    public abstract void listenCancel();
    public abstract void listenResult(String result);

    public abstract boolean singleClick();
    public abstract boolean doubleClick();
    public abstract boolean longPress();
    public abstract boolean slipUp(boolean is_longpress);
    public abstract boolean slipDown(boolean is_longpress);

    public abstract void setNull();
}
