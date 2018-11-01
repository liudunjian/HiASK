package com.hisense.hiask.main.fragment.my.multi;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hisense.hiask.hiask.R;
import com.hisense.hibeans.main.MyFuncType;
import com.hisense.himultitype.base.BaseViewHolder;
import com.hisense.himultitype.base.BaseViewProvider;
import com.hisense.hitools.utils.CacheUtils;
import com.hisense.hitools.utils.EmptyUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liudunjian on 2018/6/19.
 */

public class MyFuncTypeItemViewProvider extends BaseViewProvider<MyFuncType, MyFuncTypeItemViewProvider.ViewHolder> {

    private IMyFuncTypeItemListener listener;

    public MyFuncTypeItemViewProvider(IMyFuncTypeItemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.multi_my_func_item_layout, parent, false), listener);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MyFuncType item) {
        holder.bindData(item);
    }


    static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.multi_my_func_left_img)
        ImageView multiMyFuncLeftImg;
        @BindView(R.id.multi_my_func_title)
        TextView multiMyFuncTitle;
        @BindView(R.id.multi_my_func_right_img)
        ImageView multiMyFuncRightImg;
        @BindView(R.id.multi_my_func_middle_title)
        TextView multiMyFuncMiddleTitle;
        @BindView(R.id.multi_my_func_little_title)
        TextView multiMyFuncLittleTitle;
        @BindView(R.id.multi_my_func_space_line)
        View multiMyFuncSpaceLine;

        private IMyFuncTypeItemListener listener;
        private MyFuncType item;

        public ViewHolder(View itemView, IMyFuncTypeItemListener listener) {
            super(itemView);
            this.listener = listener;
        }

        public void bindData(MyFuncType item) {
            if (item == null)
                return;
            this.item = item;
            switch (item) {
                case MY_FUNC_TYPE_COLLECT:

                    multiMyFuncLeftImg.setVisibility(View.VISIBLE);
                    multiMyFuncTitle.setVisibility(View.VISIBLE);
                    multiMyFuncRightImg.setVisibility(View.VISIBLE);
                    multiMyFuncMiddleTitle.setVisibility(View.GONE);
                    multiMyFuncLittleTitle.setVisibility(View.GONE);
                    multiMyFuncSpaceLine.setVisibility(View.GONE);

                    multiMyFuncLeftImg.setImageResource(R.drawable.ic_my_collect);
                    multiMyFuncTitle.setText(R.string.my_func_collect);
                    break;
                case MY_FUNC_TYPE_CLEAR_CACHE:

                    multiMyFuncLeftImg.setVisibility(View.VISIBLE);
                    multiMyFuncTitle.setVisibility(View.VISIBLE);
                    multiMyFuncRightImg.setVisibility(View.GONE);
                    multiMyFuncMiddleTitle.setVisibility(View.GONE);
                    multiMyFuncLittleTitle.setVisibility(View.VISIBLE);
                    multiMyFuncSpaceLine.setVisibility(View.VISIBLE);

                    multiMyFuncLeftImg.setImageResource(R.drawable.ic_my_clear_cache);
                    multiMyFuncTitle.setText(R.string.my_func_clear_cache);
                    multiMyFuncLittleTitle.setText(CacheUtils.
                            getFormatSize(CacheUtils.calculateCachedSize(itemView.getContext().getApplicationContext())));
                    break;
                case MY_FUNC_TYPE_CHECK_VERSION:

                    multiMyFuncLeftImg.setVisibility(View.VISIBLE);
                    multiMyFuncTitle.setVisibility(View.VISIBLE);
                    multiMyFuncRightImg.setVisibility(View.VISIBLE);
                    multiMyFuncMiddleTitle.setVisibility(View.GONE);
                    multiMyFuncLittleTitle.setVisibility(View.GONE);
                    multiMyFuncSpaceLine.setVisibility(View.VISIBLE);

                    multiMyFuncLeftImg.setImageResource(R.drawable.ic_my_check_version);
                    multiMyFuncTitle.setText(R.string.my_func_check_version);
                    break;
                case MY_FUNC_TYPE_ABOUT_US:

                    multiMyFuncLeftImg.setVisibility(View.VISIBLE);
                    multiMyFuncTitle.setVisibility(View.VISIBLE);
                    multiMyFuncRightImg.setVisibility(View.VISIBLE);
                    multiMyFuncMiddleTitle.setVisibility(View.GONE);
                    multiMyFuncLittleTitle.setVisibility(View.GONE);
                    multiMyFuncSpaceLine.setVisibility(View.GONE);

                    multiMyFuncLeftImg.setImageResource(R.drawable.ic_my_about_us);
                    multiMyFuncTitle.setText(R.string.my_func_about_us);
                    break;
                case MY_FUNC_TYPE_EDIT_CYPHER:

                    multiMyFuncLeftImg.setVisibility(View.VISIBLE);
                    multiMyFuncTitle.setVisibility(View.VISIBLE);
                    multiMyFuncRightImg.setVisibility(View.VISIBLE);
                    multiMyFuncMiddleTitle.setVisibility(View.GONE);
                    multiMyFuncLittleTitle.setVisibility(View.GONE);
                    multiMyFuncSpaceLine.setVisibility(View.GONE);

                    multiMyFuncLeftImg.setImageResource(R.drawable.ic_my_edit_cypher);
                    multiMyFuncTitle.setText(R.string.my_func_edit_cypher);
                    break;
                case MY_FUNC_TYPE_EXIT_LOGIN:

                    multiMyFuncLeftImg.setVisibility(View.GONE);
                    multiMyFuncTitle.setVisibility(View.GONE);
                    multiMyFuncRightImg.setVisibility(View.GONE);
                    multiMyFuncMiddleTitle.setVisibility(View.VISIBLE);
                    multiMyFuncLittleTitle.setVisibility(View.GONE);
                    multiMyFuncSpaceLine.setVisibility(View.GONE);

                    multiMyFuncMiddleTitle.setText(R.string.my_func_exit_login);

                    break;
                case MY_FUNC_TYPE_LOGIN:
                    multiMyFuncLeftImg.setVisibility(View.GONE);
                    multiMyFuncTitle.setVisibility(View.GONE);
                    multiMyFuncRightImg.setVisibility(View.GONE);
                    multiMyFuncMiddleTitle.setVisibility(View.VISIBLE);
                    multiMyFuncLittleTitle.setVisibility(View.GONE);
                    multiMyFuncSpaceLine.setVisibility(View.GONE);

                    multiMyFuncMiddleTitle.setText(R.string.my_func_login);
                    multiMyFuncMiddleTitle.setTextColor(Color.rgb(70, 159, 239));
                    break;
                default:
                    break;
            }
        }

        @OnClick(R.id.multi_my_func_item_root)
        public void onViewClicked() {
            if (EmptyUtils.isNotEmpty(this.listener))
                listener.onMyFuncTypeItemClicked(this.item);
        }
    }

    public interface IMyFuncTypeItemListener {
        void onMyFuncTypeItemClicked(MyFuncType item);
    }
}
