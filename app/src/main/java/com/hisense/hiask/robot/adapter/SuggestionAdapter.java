package com.hisense.hiask.robot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hisense.hiask.hiask.R;
import com.hisense.hibeans.bot.model.BotMenuItem;

import java.util.ArrayList;

/**
 * Created by liudunjian on 2018/4/27.
 */

public class SuggestionAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<BotMenuItem> suggestionList;

    public SuggestionAdapter(Context context) {
        this.context = context;
    }

    public int getCount() {
        return this.suggestionList == null ? 0 : this.suggestionList.size();
    }

    public Object getItem(int position) {
        return this.suggestionList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) convertView;
        if (view == null) {
            view = (TextView) LayoutInflater.from(this.context).inflate(R.layout.list_robot_suggestion_item_layout, (ViewGroup) null);
        }

        BotMenuItem item = (BotMenuItem) this.suggestionList.get(position);
        view.setText(item.getContent());
        view.setTag(item);
        return view;
    }

    public void setSuggestionList(ArrayList<BotMenuItem> suggestionList) {
        this.suggestionList = suggestionList;
        this.notifyDataSetChanged();
    }
}
