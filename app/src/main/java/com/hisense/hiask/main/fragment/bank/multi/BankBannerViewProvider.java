package com.hisense.hiask.main.fragment.bank.multi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hisense.hiask.hiask.R;
import com.hisense.hibeans.main.BankBannerBean;
import com.hisense.himultitype.base.BaseViewHolder;
import com.hisense.himultitype.base.BaseViewProvider;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import butterknife.BindView;

/**
 * Created by liudunjian on 2018/4/24.
 */

public class BankBannerViewProvider extends BaseViewProvider<BankBannerBean, BankBannerViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.multi_main_banner_item_layout, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull BankBannerBean item) {
        holder.bindData(item);
    }

    static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.main_multi_banner)
        Banner mainMultiBanner;

        public ViewHolder(View itemView) {
            super(itemView);
            initBanner();
        }

        public void bindData(BankBannerBean item) {
            mainMultiBanner.setImages(item.getImages());
            mainMultiBanner.start();
        }

        private void initBanner() {
            mainMultiBanner.setImageLoader(new GlideImageLoader());
            mainMultiBanner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        }
    }

    static class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }
}
