package com.hisense.hiask.widget.itemtouch;

/**
 * Created by liudunjian on 2018/5/2.
 */

public interface IItemMoveListener {
    void onItemSwiped(int position);
    void onItemMoved(int fromPosition, int toPosition);
    boolean canItemMove(int fromPosition, int toPosition);
}
