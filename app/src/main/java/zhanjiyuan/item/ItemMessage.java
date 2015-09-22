package zhanjiyuan.item;

/**
 * Created by zhanjiyuan on 15/9/22.
 */
public class ItemMessage extends Item {

    private String title, content;
    private String timestamp;
    @Override
    public boolean onDown() {
        //invoke stringTovoice(content)
        return false;
    }

}
