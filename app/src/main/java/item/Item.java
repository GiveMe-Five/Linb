package item;

/**
 * Created by zhanjiyuan on 15/9/22.
 */
public abstract class Item {

    String key;

    public abstract void autoRunBegin();
    public abstract void autoRunEnd();

    public abstract boolean clickOnce();
    public abstract boolean clickTwice();

    public abstract String getKey();

    public abstract String getIcon(String status);
    public abstract String getBackground(String status);
}
