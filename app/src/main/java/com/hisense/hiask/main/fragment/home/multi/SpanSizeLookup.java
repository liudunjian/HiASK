package com.hisense.hiask.main.fragment.home.multi;

import android.support.v7.widget.GridLayoutManager;

import com.hisense.hibeans.main.HomeHeaderBean;
import com.hisense.himultitype.view.GridMultiView;

/**
 * Created by liudunjian on 2018/10/31.
 */

public class SpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
    private GridMultiView multiTypeView;

    public SpanSizeLookup(GridMultiView multiTypeView) {
        this.multiTypeView = multiTypeView;
    }

    @Override
    public int getSpanSize(int position) {
        Object object = multiTypeView.getAdapter().getItems().get(position);
        if (object instanceof HomeHeaderBean) {
            return multiTypeView.getSpanSize();
        }
        return 1;
    }
}
