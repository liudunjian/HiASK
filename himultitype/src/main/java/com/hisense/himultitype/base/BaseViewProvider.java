package com.hisense.himultitype.base;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by liudunjian on 2018/4/20.
 */

public abstract class BaseViewProvider<T, VH extends BaseViewHolder> extends ItemViewBinder<T, VH> {

    @NonNull
    @Override
    protected abstract VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent);

    @Override
    protected abstract void onBindViewHolder(@NonNull VH holder, @NonNull T item);

}
