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

public class FormSvcViewProvider extends BaseViewProvider<SvcBean, FormSvcViewProvider.ViewHolder> {


    private IFormSvcClickListener listener;

    public FormSvcViewProvider(IFormSvcClickListener fatherListener) {
        this.listener = fatherListener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.multi_form_service_item_layout, parent, false), listener);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SvcBean item) {
        holder.bindData(item);
    }

    static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.form_service_title)
        TextView formServiceTitle;

        private SvcBean item;
        private IFormSvcClickListener fatherListener;

        public ViewHolder(View itemView, IFormSvcClickListener fatherListener) {
            super(itemView);
            this.fatherListener = fatherListener;
        }

        public void bindData(SvcBean item) {
            if (EmptyUtils.isEmpty(item))
                return;
            this.item = item;
            formServiceTitle.setText(item.getName());
        }

        @OnClick(R.id.form_service_layout)
        public void onViewClicked() {

            if (EmptyUtils.isNotEmpty(fatherListener)) {
                fatherListener.onFormSvcClicked(this.item);
            }
        }
    }

    public interface IFormSvcClickListener {
        void onFormSvcClicked(SvcBean item);
    }


}
