package module;

import android.graphics.drawable.Drawable;

/**
 * Created by zhanjiyuan on 15/9/30.
 */

public class LayoutList {

    ModuleList moduleList;

    LayoutList(ModuleList moduleList){
        /*
            把activity当作参数传进来
            通过调用activity.setContentView, activity.findViewById等使UI分离
            init部分完成layout以及view的初始化
            后续的set函数  主体的list会再需要的时候调用layoutlist.setxxx()
         */
        this.moduleList = moduleList;
        init();
    }

    private void init(){
        moduleList.setContentView(null);
    }

    public void setIcon(int draw){

    }

    public void setBackground(int draw){

    }

    public void switchIcon(int draw){

    }

    public void switchBackground(int draw){

    }

    public void lastItem(){

    }

    public void nextItem(){

    }

    public void exceedHead(){

    }

    public void exceedTail(){

    }
}
