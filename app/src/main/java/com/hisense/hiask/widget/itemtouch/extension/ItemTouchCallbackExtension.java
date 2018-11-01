package com.hisense.hiask.widget.itemtouch.extension;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by liudunjian on 2017/8/29.
 */

public class ItemTouchCallbackExtension extends ItemTouchHelperExtension.Callback {

    private ItemTouchHelperMoveListener mListener;

    public ItemTouchCallbackExtension (ItemTouchHelperMoveListener listener) {
        super();
        mListener = listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(ItemTouchHelper.UP|ItemTouchHelper.DOWN, ItemTouchHelper.START);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if(viewHolder.getItemViewType()!=target.getItemViewType())
            return false;
        mListener.onItemTouchItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
            return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }


    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (dY != 0 && dX == 0) super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if(viewHolder instanceof IItemTouchListenerExtension) {
            IItemTouchListenerExtension holder = (IItemTouchListenerExtension)viewHolder;
            holder.onChildDraw(dX);
        }
    }
}
