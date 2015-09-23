package zhanjiyuan.item;

import zhanjiyuan.format.Datetime;

/**
 * Created by zhanjiyuan on 15/9/22.
 */
public class ItemMessage extends Item {

    private String title, content;
    private Datetime timestamp;

    public ItemMessage(String title, String content, Datetime timestamp){
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }
    @Override
    public boolean clickOnce() {
        //invoke stringTovoice(content)
        return false;
    }

    @Override
    public boolean clickTwice() {
        return false;
    }

    @Override
    public boolean longPress() {
        return false;
    }
}
