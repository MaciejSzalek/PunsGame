package com.puns.punsgame;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Maciej Szalek on 2018-05-15.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter{

    private Context _context;
    private List<String> _listCategory;
    private HashMap<String, List<String>> _listPun;

    public ExpandableListAdapter(Context context , List<String> listCategory,
                                 HashMap<String, List<String>> listPun){
        this._context = context;
        this._listCategory = listCategory;
        this._listPun = listPun;
    }

    @Override
    public int getGroupCount() {
        return this._listCategory.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listPun.get(this._listCategory.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listCategory.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listPun.get(this._listCategory.get(groupPosition))
                .get(childPosition);
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

        String headerTitle = (String) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.category_list, null);
        }
        TextView listHeader = convertView.findViewById(R.id.list_category_txt);
        listHeader.setTypeface(null, Typeface.BOLD);
        listHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.pun_list, null);
        }
        TextView txtListChild = convertView.findViewById(R.id.list_child_txt);
        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
