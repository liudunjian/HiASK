package com.hisense.hiask.main.fragment.bank.multi;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hisense.hiask.hiask.R;
import com.hisense.hibeans.main.SpaceBean;
import com.hisense.himultitype.base.BaseViewHolder;
import com.hisense.himultitype.base.BaseViewProvider;

import butterknife.BindView;

/**
 * Created by liudunjian on 2018/6/11.
 */

public class SpaceViewProvider extends BaseViewProvider<SpaceBean, SpaceViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.multi_bank_space_line_item_layout, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SpaceBean item) {
        holder.bindData(item);
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.multi_bank_space_bottom)
        RelativeLayout multiBankSpaceBottom;
        @BindView(R.id.multi_bank_space_line)
        View multiBankSpaceLine;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void bindData(SpaceBean item) {
            switch (item) {
                case BOTTOM_SPACE:
                    multiBankSpaceBottom.setVisibility(View.VISIBLE);
                    multiBankSpaceLine.setVisibility(View.GONE);
                    break;
                case LINE_SPACE:
                    multiBankSpaceBottom.setVisibility(View.GONE);
                    multiBankSpaceLine.setVisibility(View.VISIBLE);
                    break;
                default:
                    multiBankSpaceBottom.setVisibility(View.GONE);
                    multiBankSpaceLine.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }
}
