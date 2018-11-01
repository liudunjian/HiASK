package com.hisense.hiask.main.fragment.home.multi;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hisense.hiask.hiask.R;
import com.hisense.hibeans.main.HomeHeaderBean;
import com.hisense.himultitype.base.BaseViewHolder;
import com.hisense.himultitype.base.BaseViewProvider;

import butterknife.OnClick;

/**
 * Created by liudunjian on 2018/10/31.
 */

public class HomeHeaderViewProvider extends BaseViewProvider<HomeHeaderBean, HomeHeaderViewProvider.ViewHolder> {

    private IHomeHeaderListener listener;

    public HomeHeaderViewProvider(IHomeHeaderListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.multi_home_header_item_layout, parent, false),this.listener);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull HomeHeaderBean item) {

    }

    static class ViewHolder extends BaseViewHolder {
        private IHomeHeaderListener listener;
        public ViewHolder(View itemView,IHomeHeaderListener listener) {
            super(itemView);
            this.listener = listener;
        }
        @OnClick(R.id.enter)
        public void onViewClicked() {
            if(listener!=null)
                listener.onHomeHeaderEnterClicked();
        }
    }

    public interface IHomeHeaderListener {
        void onHomeHeaderEnterClicked();
    }
}
