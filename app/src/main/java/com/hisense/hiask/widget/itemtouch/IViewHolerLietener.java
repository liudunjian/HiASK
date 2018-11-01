package com.hisense.hiask.widget.itemtouch;

/**
 * Created by liudunjian on 2018/5/2.
 */

public interface IViewHolerLietener {
    /**
     * Item滑动时触发
     * @param dx
     * @param dy
     */
    void onItemSwipe(float dx, float dy);
    /**
     * Item被选中时触发
     */
    void onItemSelected();
    /**
     * Item在拖拽结束/滑动结束后触发
     */
    void onItemFinish();
}
