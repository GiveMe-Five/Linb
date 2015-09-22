package zhanjiyuan.gesture;

import android.view.MotionEvent;

/**
 * Created by zhanjiyuan on 15/9/22.
 */
public interface GestureListener {

    public boolean onDown(MotionEvent ev);

    public boolean slipUp();
}
