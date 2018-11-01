package com.hisense.hiask.main.fragment.bank.multi;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hisense.hiask.hiask.R;
import com.hisense.hibeans.main.BankHeaderBean;
import com.hisense.himultitype.base.BaseViewHolder;
import com.hisense.himultitype.base.BaseViewProvider;

/**
 * Created by liudunjian on 2018/6/11.
 */

public class BankHeaderViewProvider extends BaseViewProvider<BankHeaderBean,BankHeaderViewProvider.ViewHolder> {


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.multi_bank_header_item_layout,parent,false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull BankHeaderBean item) {

    }

    static class ViewHolder extends BaseViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
