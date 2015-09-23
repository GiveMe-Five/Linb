package zhanjiyuan.list;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

import java.util.ArrayList;

import zhanjiyuan.format.Datetime;
import zhanjiyuan.gesture.GestureListener;
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

public class List extends Activity implements GestureListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        init();
    }

    private void init(){

    }

    /*
        列表的操作，例如上下滑动，越界检查以及动画的调用
     */
    private ArrayList<Item> itemArrayList = new ArrayList<Item>();
    private int index;

    private void nextItem(){
        if (index + 1 >= itemArrayList.size()) overBottom();
        else index++;
    }
    private void lastItem() {
        if (index - 1 < 0) overTop();
        else index--;
    }
    private void toBottom(){
        if (itemArrayList.size() != 0) index = itemArrayList.size() - 1;
        else emptyList();
    }
    private void toTop(){
        if (itemArrayList.size() != 0) index = 0;
        else emptyList();
    }
    private void overBottom(){
        if (itemArrayList.size() != 0);
        else emptyList();
    }
    private void overTop(){
        if (itemArrayList.size() != 0);
        else emptyList();
    }
    private void emptyList(){

    }
    /*
        监听器的设置以及实现手势接口的各个函数
        如前面所提及的，onDown属于Item，则调用Item类的函数
        滑动属于List，则调用List类也就是本身定义的函数，因为所有列表上下滑动的意义都相同，所以不必重写
     */

    @Override
    public boolean clickOnce() {
        //由各个继承抽象类Item的独特的Item自己定义实现，例如新闻的Message就代表播放
        itemArrayList.get(index).clickOnce();
        return true;
    }

    @Override
    public boolean clickTwice() {
        return false;
    }

    @Override
    public boolean slipUp() {
        nextItem();
        return true;
    }

    @Override
    public boolean slipDown() {
        lastItem();
        return true;
    }

    /*
        TODO: Service
        服务器的连接，以及自己去抓取新的信息加入List，每个List抓取内容应该是不同的，需要重写，这里只是给个例子
     */

    Item fetchItem(){
        //fetch Item from server
        return new ItemMessage("Title", "Content", new Datetime());
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