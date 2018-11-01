package com.hisense.hiask.main.fragment.my.multi;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hisense.hiask.constant.SpConstant;
import com.hisense.hiask.hiask.R;
import com.hisense.hibeans.main.MyFuncType;
import com.hisense.himultitype.base.BaseViewHolder;
import com.hisense.himultitype.base.BaseViewProvider;
import com.hisense.hitools.utils.SpUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liudunjian on 2018/6/19.
 */

public class MyFuncTypePhotoItemProvider extends BaseViewProvider<MyFuncType, MyFuncTypePhotoItemProvider.ViewHolder> {

    private IMyFuncTypePhotoItemListener listener;

    public MyFuncTypePhotoItemProvider(IMyFuncTypePhotoItemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.multi_my_funct_photo_item_layout, parent, false), listener);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MyFuncType item) {
        holder.bindData(item);
    }

    static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.multi_my_header_image)
        ImageView multiMyHeaderImage;
        @BindView(R.id.multi_my_user_name_text)
        TextView multiMyUserNameText;

        private IMyFuncTypePhotoItemListener listener;

        public ViewHolder(View itemView, IMyFuncTypePhotoItemListener listener) {
            super(itemView);
            this.listener = listener;
        }

        public void bindData(MyFuncType item) {
            if (item == null)
                return;
            if (SpUtils.getInstance(SpUtils.SP_ACCOUNT_KEY).getBoolean(SpConstant.SP_USER_LOGIN_STATE, false)) {
                multiMyUserNameText.setText(SpUtils.getInstance(SpUtils.SP_ACCOUNT_KEY).getString(SpConstant.SP_USER_NICK_NAME, ""));
                Glide.with(itemView).load(SpUtils.getInstance(SpUtils.SP_ACCOUNT_KEY).getString(SpConstant.SP_USER_PHOTO_URL, "")).into(multiMyHeaderImage);
            } else {
                multiMyUserNameText.setText(itemView.getContext().getString(R.string.my_fragment_user_default_name));
                Glide.with(itemView).clear(multiMyHeaderImage);
                multiMyHeaderImage.setImageResource(R.drawable.my_user_photo_default);
            }
        }

        @OnClick(R.id.multi_my_func_item_root)
        public void onViewClicked() {
            if (listener != null)
                listener.onMyFuncTypePhotoItemClicked();
        }
    }

    public interface IMyFuncTypePhotoItemListener {
        void onMyFuncTypePhotoItemClicked();
    }

}
