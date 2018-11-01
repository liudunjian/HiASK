package com.hisense.hiask.main.fragment.my.multi;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hisense.hiautolayout.autolayout.config.AutoLayoutConifg;
import com.hisense.hibeans.main.MyFuncType;
import com.hisense.himultitype.base.BaseMultiView;

/**
 * Created by liudunjian on 2018/6/19.
 */

public class MultiMyItemDecoration extends RecyclerView.ItemDecoration {

    private int space = 0;
    private BaseMultiView multiTypeView;

    public MultiMyItemDecoration(BaseMultiView multiTypeView, int space) {
        this.space = space;
        this.multiTypeView = multiTypeView;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int lastIndex = this.multiTypeView.lastIndex();
        int position = parent.getChildAdapterPosition(view);
        int space = AutoLayoutConifg.getInstance().getPercentSize(this.space);

        Object object = this.multiTypeView.getItem(position);
        Object prevObject = (position == 0) ? null : this.multiTypeView.getItem(position - 1);

        if (object instanceof MyFuncType) {
            MyFuncType item = (MyFuncType) object;

            switch (item) {
                case MY_FUNC_TYPE_PHOTO:
                    outRect.bottom = space;
                    break;
                case MY_FUNC_TYPE_COLLECT:
                    outRect.bottom = space;
                    break;
                case MY_FUNC_TYPE_ABOUT_US:
                    outRect.bottom = space;
                    break;
                case MY_FUNC_TYPE_EDIT_CYPHER:
                    outRect.bottom = space;
                    break;
                default:
                    outRect.bottom = 0;
                    break;
            }
        }
    }
}
