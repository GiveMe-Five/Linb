package zhanjiyuan.item;

/**
 * Created by zhanjiyuan on 15/9/22.
 */
public class ItemMessage extends Item {

    private String title, content;

    public ItemMessage(String title, String content){
        this.title = title;
        this.content = content;
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
    public String getkeyInfo() {
        return title;
    }
}
