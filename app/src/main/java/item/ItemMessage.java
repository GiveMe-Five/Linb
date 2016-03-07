package item;

import com.example.zhanjiyuan.linb.R;

import argument.Constants;
import module.ModuleList;

/**
 * Created by zhanjiyuan on 15/9/22.
 */
public class ItemMessage extends Item{

    private ModuleList rev;
    private String content;

    public ItemMessage(ModuleList activity, String title, String content){
        this.rev = activity;
        this.key = title;
        this.content = content;
    }

    @Override
    public void autoRunBegin() {
        rev.speakText(content);
        System.out.println("自动播放");
    }

    @Override
    public void speechStart() {
        rev.setOperationIcon(R.string.play);
        System.out.println("开始播放");
    }

    @Override
    public void speechPause() {
        rev.setOperationIcon(R.string.pause);
        System.out.println("暂停播放");
    }

    @Override
    public void speechResume() {
        rev.setOperationIcon(R.string.play);
        System.out.println("继续播放");
    }

    @Override
    public void speechStop() {
        rev.setOperationIcon(R.string.stop);
        System.out.println("停止播放");
    }

    @Override
    public void speechEnd() {
        slipUp(false);
        System.out.println("播放结束");
    }

    @Override
    public void listenStart() {
        rev.setOperationIcon(R.string.microphone);
        System.out.println("开始说话");
    }

    @Override
    public void listenComplete() {
        rev.setOperationIcon(R.string.wait);
        System.out.println("收录结束");

    }

    @Override
    public void listenCancel() {
        rev.setOperationIcon(R.string.stop);
        System.out.println("取消录音");
    }

    @Override
    public void listenResult(String result) {
        rev.setOperationIcon(R.string.stop);
    }

    @Override
    public boolean singleClick() {
        int status = rev.getSpeechStatus();
        switch (status){
            case Constants.SPEECH_PLAY:
                rev.pauseText();
                break;
            case Constants.SPEECH_PAUSE:
                rev.resumeText();
                break;
            case Constants.SPEECH_NULL:
                rev.speakText(content);
                break;
        }
        return true;
    }

    @Override
    public boolean doubleClick() {
        if (rev.getSpeechStatus() != Constants.SPEECH_NULL)
            rev.stopText();
        return true;
    }

    @Override
    public boolean longPress() {
        rev.startListen();
        return true;
    }

    @Override
    public boolean slipUp(boolean is_longpress) {
        if (!is_longpress) {
            rev.setOperationIcon(R.string.step_forward);
            rev.nextItem();
        }
        else{
            rev.stopListen();
        }
        return true;
    }

    @Override
    public boolean slipDown(boolean is_longpress) {
        if (!is_longpress) {
            rev.setOperationIcon(R.string.step_backward);
            rev.lastItem();
        }
        else{
            rev.cancelListen();
        }
        return true;
    }

    @Override
    public void setNull() {
        rev.setOperationIcon(R.string.stop);
    }

}
