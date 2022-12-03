package com.example.planner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter {

    private Context context;
    private List list;

    class ViewHolder {
        public TextView name;
        public TextView info;
    }

    public CustomAdapter(Context context, ArrayList list){
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.row_item, parent, false);
        }

        viewHolder = new ViewHolder();
        viewHolder.name = (TextView) convertView.findViewById(R.id.textView_name);
        viewHolder.info = (TextView) convertView.findViewById(R.id.textView_info);

        final Category category = (Category) list.get(position);
        viewHolder.name.setText(category.getName());
        viewHolder.info.setText(category.getInfo());
//        Glide
//                .with(context)
//                .load(actor.getThumb_url())
//                .centerCrop()
//                .apply(new RequestOptions().override(250, 350))
//                .into(viewHolder.iv_thumb);
//        viewHolder.tv_name.setTag(actor.getName());


        return convertView;
    }
}