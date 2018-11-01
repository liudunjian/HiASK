package com.hisense.hiask.main.fragment.bank.multi;

import android.support.v7.widget.GridLayoutManager;

import com.hisense.hibeans.main.BankBannerBean;
import com.hisense.hibeans.main.BankHeaderBean;
import com.hisense.hibeans.main.SpaceBean;
import com.hisense.hibeans.question.QuestSvcBean;
import com.hisense.himultitype.view.GridMultiView;

/**
 * Created by liudunjian on 2018/4/24.
 */

public class BankSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    private GridMultiView multiView;

    public BankSpanSizeLookup(GridMultiView gridMultiView) {
        this.multiView = gridMultiView;
    }

    @Override
    public int getSpanSize(int position) {
        Object object = this.multiView.getAdapter().getItems().get(position);
        if (object instanceof BankBannerBean) {
            return multiView.getSpanSize();
        } else if (object instanceof BankHeaderBean) {
            return multiView.getSpanSize();
        } else if (object instanceof SpaceBean) {
            return multiView.getSpanSize();
        } else if (object instanceof QuestSvcBean) {
            return multiView.getSpanSize();
        }
        return 1;
    }
}
