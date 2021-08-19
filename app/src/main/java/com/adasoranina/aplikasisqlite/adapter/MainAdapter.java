package com.adasoranina.aplikasisqlite.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

import com.adasoranina.aplikasisqlite.R;
import com.adasoranina.aplikasisqlite.model.Data;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends BaseAdapter {
    @LayoutRes
    private static final int layoutRes = R.layout.list_row;

    private final List<Data> allData;

    public MainAdapter() {
        this.allData = new ArrayList<>();
    }

    public void updateData(List<Data> allData) {
        this.allData.clear();
        this.allData.addAll(allData);
    }

    @Override
    public int getCount() {
        return allData.size();
    }

    @Override
    public Object getItem(int i) {
        return allData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) view = inflateView(viewGroup);

        Data data = (Data) getItem(i);
        bindView(view, data);

        return view;
    }

    private View inflateView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext())
                .inflate(MainAdapter.layoutRes, parent, false);
    }

    private void bindView(View view, Data data) {
        TextView textId = view.findViewById(R.id.text_id);
        TextView textName = view.findViewById(R.id.text_name);
        TextView textAddress = view.findViewById(R.id.text_address);

        textId.setText(data.getStringId());
        textName.setText(data.getName());
        textAddress.setText(data.getAddress());
    }
}
