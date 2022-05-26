package com.example.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CongViecAdapter extends BaseAdapter  {
    private MainActivity context;
    private int layout;
    private List<CongViec> congViecs;

    public CongViecAdapter(MainActivity context, int layout, List<CongViec> congViecs) {
        this.context = context;
        this.layout = layout;
        this.congViecs = congViecs;
    }

    @Override
    public int getCount() {
        return congViecs.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtTen;
        ImageView imgDelete, imgUpdate;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(layout,viewGroup,false);
            holder.txtTen = (TextView) view.findViewById(R.id.tv_ten);
            holder.imgDelete = (ImageView) view.findViewById(R.id.dele);
            holder.imgUpdate = (ImageView) view.findViewById(R.id.update);

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        CongViec congViec = congViecs.get(i);
        holder.txtTen.setText(congViec.getTenCV());

        //bắt sự kiện
        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.openDiaglogsua(congViec.getTenCV(), congViec.getIdCV());
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.onClickDeleteData(congViec.getTenCV(),congViec.getIdCV());
            }
        });
        return view;
    }
}
