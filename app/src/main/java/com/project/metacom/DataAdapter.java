package com.project.metacom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.neovisionaries.ws.client.*;

public class DataAdapter extends BaseExpandableListAdapter {
    private Context context;
    public List<String> listGroup = new ArrayList<String>();
    public HashMap<String, List<String>> listChild  = new HashMap<String, List<String>>();

    public DataAdapter(Context context) {
        this.context = context;
        //this.initData();
    }

    @Override
    public int getGroupCount() {
        return listGroup.size();
    }

    @Override
    public int getChildrenCount(int childPosition) {
        return listChild.get(listGroup.get(childPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listChild.get(listGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_item,
                    null);
        }

        String textGroup = (String) getGroup(groupPosition);

        // get application resource/drawable not in Activity class, using
        // context
        /*
        Resources contextResources = context.getResources();
        Drawable groupDrawable = contextResources
                .getDrawable(R.drawable.ic_android_cat);
        // Set ImageView
        ImageView groupImage = (ImageView) convertView
                .findViewById(R.id.groupimage);
        groupImage.setImageDrawable(groupDrawable);
        */

        TextView textViewGroup = (TextView) convertView.findViewById(R.id.group);
        textViewGroup.setText(textGroup);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,  boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_child_item,
                    null);
        }
        TextView textViewItem = (TextView) convertView.findViewById(R.id.item);

        String text = (String) getChild(groupPosition, childPosition);

        textViewItem.setText(text);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void initData() {
        //listGroup = new ArrayList<String>();
        //listChild = new HashMap<String, List<String>>();

        listGroup.add("Васька");
        listGroup.add("Барсик");
        listGroup.add("Мурзик");
        listGroup.add("Рыжик");

        List<String> listGroupA = new ArrayList<String>();
        listGroupA.add("Умный");
        listGroupA.add("Ловкий");
        listGroupA.add("Голодный");

        List<String> listGroupB = new ArrayList<String>();
        listGroupB.add("Хитрый");
        listGroupB.add("Красивый");
        listGroupB.add("Пушистый");

        List<String> listGroupC = new ArrayList<String>();
        listGroupC.add("Ласковый");
        listGroupC.add("Добрый");
        listGroupC.add("Прыгучий");

        List<String> listGroupD = new ArrayList<String>();
        listGroupD.add("Талантливый");
        listGroupD.add("Великолепный");
        listGroupD.add("Спортивный");

        listChild.put(listGroup.get(0), listGroupA);
        listChild.put(listGroup.get(1), listGroupB);
        listChild.put(listGroup.get(2), listGroupC);
        listChild.put(listGroup.get(3), listGroupD);
    }
}
