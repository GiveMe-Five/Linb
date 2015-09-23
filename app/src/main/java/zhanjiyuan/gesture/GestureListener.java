package zhanjiyuan.gesture;

import android.view.MotionEvent;

/**
 * Created by zhanjiyuan on 15/9/22.
 */
public interface GestureListener {

    public boolean clickOnce();

    public boolean clickTwice();

    public boolean slipUp();

    public boolean slipDown();
}
