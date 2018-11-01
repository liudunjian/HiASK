package com.hisense.himultitype.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by liudunjian on 2018/4/20.
 */

public class BaseMultiView extends RecyclerView {

    public BaseMultiView(Context context) {
        this(context,null);
    }

    public BaseMultiView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BaseMultiView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initMultiView();
    }

    @Override
    public MultiTypeAdapter getAdapter() {
        return (MultiTypeAdapter)super.getAdapter();
    }

    private void initMultiView() {
        MultiTypeAdapter multiTypeAdapter = new MultiTypeAdapter(new ArrayList<>());
        this.setAdapter(multiTypeAdapter);
    }

    public void resetItems(List<Object> items) {
        getAdapter().setItems(items);
        getAdapter().notifyDataSetChanged();
    }

    public List<?> getItems() {
        return getAdapter().getItems();
    }

    public void removeItem(int index) {
        getItems().remove(index);
        getAdapter().notifyItemRemoved(index);
    }

    public void removeItem(Object item) {
        List contents = getItems();
        if (contents.contains(item)) {
            int before = contents.indexOf(item);
            contents.remove(item);
            getAdapter().notifyItemRemoved(before);
        }
    }

    public void updateItem(Object item) {
        List contents = getItems();
        if (contents.contains(item)) {
            int before = contents.indexOf(item);
            contents.set(before, item);
            getAdapter().notifyItemChanged(before);
        }
    }

    public void updateOrAddItem(Object item) {
        List contents = getItems();
        if (contents.contains(item)) {
            updateItem(item);
        } else {
            addItem(item);
        }
    }

    public void addItem(Object item) {
        List contents = getItems();
        int before = sizeOf(contents);
        contents.add(item);
        getAdapter().notifyItemInserted(before);
    }

    public Object getItem(int position) {
        List contents = getItems();
        if (null != contents && position >= 0 && position < contents.size()) {
            return contents.get(position);
        }
        return null;
    }

    public int lastIndex() {
        List contents = getItems();
        if (contents==null||contents.size()==0) {
            return -1;
        }
        return contents.size() - 1;
    }

    public int firstIndex() {
        List contents = getItems();
        if (contents==null||contents.size()==0) {
            return -1;
        }
        return 0;
    }

    public void scrollToBottom() {
        int index = this.lastIndex();
        if (index != -1) {
            scrollToPosition(index);
        }
    }

    public void smoothScrollToBottom() {
        stopScroll();
        int index = this.lastIndex();
        if (index != -1) {
            smoothScrollToPosition(index);
        }
    }

    private int sizeOf(List<?> items) {
        return items == null ? 0 : items.size();
    }

}
