package com.hisense.hiask.form.multi;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hisense.hiask.hiask.R;
import com.hisense.hibeans.main.SvcBean;
import com.hisense.himultitype.base.BaseViewHolder;
import com.hisense.himultitype.base.BaseViewProvider;
import com.hisense.hitools.utils.EmptyUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liudunjian on 2018/4/26.
 */

public class FormChildSvcViewProvider extends BaseViewProvider<SvcBean, FormChildSvcViewProvider.ViewHolder> {

    private IFormChildSvcClickListener listener;

    public FormChildSvcViewProvider(IFormChildSvcClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.multi_form_child_service_item_layout, parent, false),listener);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SvcBean item) {
        holder.bindData(item);
    }


    static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.form_child_service_title)
        TextView formChildServiceTitle;

        private IFormChildSvcClickListener listener;
        private SvcBean item;

        public ViewHolder(View itemView, IFormChildSvcClickListener listener) {
            super(itemView);
            this.listener = listener;
        }

        public void bindData(SvcBean item) {
            if (EmptyUtils.isEmpty(item))
                return;
            this.item = item;
            formChildServiceTitle.setText(item.getName());
        }

        @OnClick(R.id.form_child_service_layout)
        public void onViewClicked() {
            if(EmptyUtils.isNotEmpty(listener)) {
                listener.onFormChildSvcClicked(this.item);
            }
        }
    }

    public interface IFormChildSvcClickListener {
        void onFormChildSvcClicked(SvcBean item);
    }
}
