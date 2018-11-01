package com.hisense.hiask.widget.itemtouch;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;


import com.hisense.hitools.utils.EmptyUtils;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_SWIPE;

/**
 * Created by liudunjian on 2018/5/2.
 */

public class ItemTouchCallback extends ItemTouchHelper.Callback {

    private int swipeFlags;
    private boolean dragEnable;
    private boolean swipeEnable;
    private IItemMoveListener itemMoveListener;

    public ItemTouchCallback() {

    }


    public int getSwipeFlags() {
        return swipeFlags;
    }

    public void setSwipeFlags(int swipeFlags) {
        this.swipeFlags = swipeFlags;
    }

    public boolean isDragEnable() {
        return dragEnable;
    }

    public void setDragEnable(boolean dragEnable) {
        this.dragEnable = dragEnable;
    }

    public boolean isSwipeEnable() {
        return swipeEnable;
    }

    public void setSwipeEnable(boolean swipeEnable) {
        this.swipeEnable = swipeEnable;
    }

    public IItemMoveListener getItemMoveListener() {
        return itemMoveListener;
    }

    public void setItemMoveListener(IItemMoveListener itemMoveListener) {
        this.itemMoveListener = itemMoveListener;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return this.dragEnable;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return this.swipeEnable;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int dragFlags = 0;
        if (layoutManager instanceof GridLayoutManager) {
                // 如果是Grid布局，能上下左右拖动
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

        } else if (layoutManager instanceof LinearLayoutManager) {
            // 如果是纵向Linear布局，则能上下拖动，不能左右滑动
            if (((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.VERTICAL) {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            } else {
                // 如果是横向Linear布局，则能左右拖动，上下滑动
                dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            }
        }
        return makeMovementFlags(dragFlags, this.swipeFlags); //该方法指定可进行的操作
    }

    @Override
    public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder current, RecyclerView.ViewHolder target) {
        if (EmptyUtils.isNotEmpty(itemMoveListener)) {
            return itemMoveListener.canItemMove(current.getAdapterPosition(), target.getAdapterPosition());
        }
        return super.canDropOver(recyclerView, current, target);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if(viewHolder.getItemViewType()!=target.getItemViewType())
            return false;
        if (EmptyUtils.isNotEmpty(itemMoveListener)) {
            itemMoveListener.onItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (EmptyUtils.isNotEmpty(itemMoveListener)) {
            itemMoveListener.onItemSwiped(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (viewHolder instanceof IViewHolerLietener) {
            IViewHolerLietener itemViewHolder = (IViewHolerLietener) viewHolder;
            itemViewHolder.onItemSelected();
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (viewHolder instanceof IViewHolerLietener) {
            IViewHolerLietener itemViewHolder = (IViewHolerLietener) viewHolder;
            itemViewHolder.onItemFinish();
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if(viewHolder instanceof IViewHolerLietener&&actionState==ACTION_STATE_SWIPE) {
            IViewHolerLietener itemViewHolder = (IViewHolerLietener) viewHolder;
            itemViewHolder.onItemSwipe(dX,dY);
        }else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }
}
