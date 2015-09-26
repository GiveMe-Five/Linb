package zhanjiyuan.list;

import android.view.MotionEvent;

import livhong.gesture.OnGestureListener;

/**
 * Created by zhanjiyuan on 15/9/26.
 */
public class ListGestureAdapter implements OnGestureListener {

    @Override
    public boolean onDown(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onDoubleClick(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onLongPress(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onSlipUp() {
        return false;
    }

    @Override
    public boolean onSlipDown() {
        return false;
    }

    @Override
    public boolean onSlipLeft() {
        return false;
    }

    @Override
    public boolean onSlipRight() {
        return false;
    }

    @Override
    public boolean onSlipDoubleUp() {
        return false;
    }

    @Override
    public boolean onSlipDoubleDown() {
        return false;
    }

    @Override
    public boolean onSlipDoubleLeft() {
        return false;
    }

    @Override
    public boolean onSlipDoubleRight() {
        return false;
    }

    @Override
    public boolean onSlipFastUp() {
        return false;
    }

    @Override
    public boolean onSlipFastDown() {
        return false;
    }
}
