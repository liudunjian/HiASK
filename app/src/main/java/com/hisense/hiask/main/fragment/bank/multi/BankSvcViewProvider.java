package com.hisense.hiask.main.fragment.bank.multi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hisense.hiask.hiask.R;
import com.hisense.hibeans.main.SvcBean;
import com.hisense.himultitype.base.BaseViewHolder;
import com.hisense.himultitype.base.BaseViewProvider;
import com.hisense.hitools.utils.EmptyUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liudunjian on 2018/4/23.
 */

public class BankSvcViewProvider extends BaseViewProvider<SvcBean, BankSvcViewProvider.ViewHolder> {


    private IMainSvcItemClickListener listener;
    private Context context;

    public BankSvcViewProvider(Context context, IMainSvcItemClickListener listener) {
        this.listener = listener;
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.multi_main_service_item_layout, parent, false), listener);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SvcBean item) {
        holder.bindData(context, item);
    }


    static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.main_srv_item_img)
        ImageView mainSrvItemImg;
        @BindView(R.id.main_srv_item_title)
        TextView mainSrvItemTitle;
        @BindView(R.id.main_srv_item_layout)
        LinearLayout mainSrvItemLayout;

        private SvcBean svcBean;
        private IMainSvcItemClickListener listener;

        public ViewHolder(View itemView, IMainSvcItemClickListener listener) {
            super(itemView);
            this.listener = listener;
        }

        public void bindData(Context context, SvcBean item) {
            this.svcBean = item;

            if (item.getStatus() == 0) {
                mainSrvItemTitle.setTextColor(context.getResources().getColor(R.color.color_main_svc_title_no));
                mainSrvItemLayout.setEnabled(false);
            } else {
                mainSrvItemTitle.setTextColor(context.getResources().getColor(R.color.color_main_svc_title_enable));
                mainSrvItemLayout.setEnabled(true);
            }
            mainSrvItemTitle.setText(item.getName());
            Glide.with(context).load(item.getIconLink()).into(mainSrvItemImg);
        }

        @OnClick(R.id.main_srv_item_layout)
        public void onViewClicked() {
            if (EmptyUtils.isNotEmpty(this.listener)) {
                listener.onMainSvcItemClicked(this.svcBean);
            }
        }
    }

    public interface IMainSvcItemClickListener {
        void onMainSvcItemClicked(SvcBean item);
    }
}
