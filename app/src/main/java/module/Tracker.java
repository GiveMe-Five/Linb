package module;

import android.view.MotionEvent;
import android.view.VelocityTracker;

/**
 * Created by zhanjiyuan on 16/2/25.
 */
public class Tracker {
    private VelocityTracker v_tracker = null;

    public void trackAlloc(MotionEvent event){
        if(v_tracker == null)
            v_tracker = VelocityTracker.obtain();
        else
            v_tracker.clear();
        v_tracker.addMovement(event);
    }

    public void trackDispose(){
        v_tracker = null;
    }

    private int speed_limit = 150;
    public double trackUpdate(MotionEvent event){
        v_tracker.addMovement(event);
        v_tracker.computeCurrentVelocity(100);
//      System.out.println("speed = " + v_tracker.getYVelocity());
        double delta = v_tracker.getYVelocity();
        if (delta > speed_limit) delta = speed_limit;
        else if (delta < -speed_limit) delta = -speed_limit;
        return delta;
    }
}
