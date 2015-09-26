package zhanjiyuan.list;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.TextView;

import com.example.zhanjiyuan.linb.R;

import java.util.ArrayList;

import livhong.gesture.MyGestureDetector;
import zhanjiyuan.item.Item;
import zhanjiyuan.item.ItemMessage;

/**
 * Created by zhanjiyuan on 15/9/22.
 * 暂时作为模版使用的一个Activity，各种列表应当会在这个App中反复使用：
 *      例如：新闻列表，消息列表，好友列表etc
 * 因此可以将列表的操作完全放在这个类里面实现
 *      例如：归属于列表操作的上下滑动切换Item
 * 更多的操作例如点击，则由列表调用列表中Item的函数实现
 * private protected还没写。。。
 */

public class List extends Activity {

    //用于记录当前页面的item的列表
    private ArrayList<Item> itemArrayList = new ArrayList<Item>();
    private int index;
    private double ruler;

    //手势与速度侦测
    private VelocityTracker vTracker = null;
    private MyGestureDetector detector;

    //显示当前所处的item内容
    private TextView display, display_ruler;
    private int slipRate = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        display = (TextView)findViewById(R.id.list_layout_text);
        display_ruler = (TextView)findViewById(R.id.list_layout_ruler);
        detector = new MyGestureDetector(getApplicationContext(), adapter);
        init();
    }

    private void init(){
        //test
        itemArrayList.add(new ItemMessage("firstpage", "content"));
        itemArrayList.add(new ItemMessage("title1", "content"));
        itemArrayList.add(new ItemMessage("title2", "content"));
        itemArrayList.add(new ItemMessage("title3", "content"));
        itemArrayList.add(new ItemMessage("title4", "content"));
        itemArrayList.add(new ItemMessage("title5", "content"));
        itemArrayList.add(new ItemMessage("title5", "content"));
        itemArrayList.add(new ItemMessage("title6", "content"));
        itemArrayList.add(new ItemMessage("title7", "content"));
        itemArrayList.add(new ItemMessage("title8", "content"));
        itemArrayList.add(new ItemMessage("title9", "content"));
        itemArrayList.add(new ItemMessage("lastpage", "content"));
        index = 0;
        updateDisplay();
    }
    /*
        列表的操作，例如上下滑动，越界检查以及动画的调用
     */

    private void updateDisplay(){
        if (index >= itemArrayList.size()) overBottom();
        else display.setText("" + itemArrayList.get(index).getkeyInfo());
    }

    private void toBottom(){
        System.out.println("toBottom");
        if (itemArrayList.size() != 0) index = itemArrayList.size() - 1;
        else emptyList();
    }
    private void toTop(){
        System.out.println("toTop");
        if (itemArrayList.size() != 0) index = 0;
        else emptyList();
    }
    private void overBottom(){
        System.out.println("overBottom");
        if (itemArrayList.size() != 0){
            index = itemArrayList.size() - 1;
            ruler = index * slipRate;
        }
        else emptyList();
    }

    private void emptyList(){
        display.setText("empty list");
    }

    /*
        监听器的设置以及实现手势接口的各个函数
        如前面所提及的，onDown属于Item，则调用Item类的函数
        滑动属于List，则调用List类也就是本身定义的函数，因为所有列表上下滑动的意义都相同，所以不必重写
     */

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                if(vTracker == null)
                    vTracker = VelocityTracker.obtain();
                else
                    vTracker.clear();
                vTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                vTracker.addMovement(event);
                vTracker.computeCurrentVelocity(100);
                ruler -= vTracker.getYVelocity();
                ruler = Math.max(ruler, 0);
                display_ruler.setText("index: " + index + "\truler:" + ruler);
                index = (int)(ruler / slipRate);
                updateDisplay();
                break;
        }
        return detector.onTouchEvent(event);
    }

    ListGestureAdapter adapter = new ListGestureAdapter(){
        @Override
        public boolean onDown(MotionEvent ev){
            System.out.println("Override");
            return true;
        }
    };

    /*
        TODO: Service
        服务器的连接，以及自己去抓取新的信息加入List，每个List抓取内容应该是不同的，需要重写，这里只是给个例子
     */

    Item fetchItem(){
        //fetch Item from server
        return new ItemMessage("Title", "Content");
    }

    void addItem(){
        itemArrayList.add(fetchItem());
    }

    /*
        各种翻页的动画之类的
        一个想法，每个页面显示一个Item，包含一个纯色块背景＋白色巨大符号
        用来表现当前所处的位置以及状态，来提醒用户需要对应怎样的手势
        例如：蓝色背景表示处于List，对应上下滑动手势；再比如，红色背景对应Input，对应输入法
        也可以用来表示当前状态：例如红色代表Warning，绿色代表程序正常工作
        符号可以用来表示例如当前是播放状态的▶️，或是暂停状态的||
     */
}