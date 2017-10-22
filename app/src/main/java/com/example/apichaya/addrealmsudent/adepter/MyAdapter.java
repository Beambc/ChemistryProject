package com.example.apichaya.addrealmsudent.adepter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apichaya.addrealmsudent.Model.RgbColor;
import com.example.apichaya.addrealmsudent.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context mContext;
    private CardView cvItem;

    private ArrayList<RgbColor> rgbColorArrayList = new ArrayList<>();

    public MyAdapter(Context context, ArrayList<RgbColor> rgbColorArrayList) {
        this.mContext = context;
        this.rgbColorArrayList = rgbColorArrayList;
    }


    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, final int position) {
        holder.tvRedvalue.setText(String.valueOf(rgbColorArrayList.get(position).getRedValue()));
        holder.tvGreenvalue.setText(String.valueOf(rgbColorArrayList.get(position).getGreenValue()));
        holder.tvBluevalue.setText(String.valueOf(rgbColorArrayList.get(position).getBlueValue()));

    }

    @Override
    public int getItemCount() {
        return rgbColorArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvRedvalue;
        private TextView tvGreenvalue;
        private TextView tvBluevalue;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvRedvalue = (TextView) itemView.findViewById(R.id.tvRedvalue);
            tvGreenvalue = (TextView) itemView.findViewById(R.id.tvGreenvalue);
            tvBluevalue = (TextView) itemView.findViewById(R.id.tvBluevalue);
        }
    }

    public void setRgbColorArrayList(ArrayList<RgbColor> rgbColorArrayList) {
        this.rgbColorArrayList = rgbColorArrayList;
        notifyDataSetChanged();
    }

    public void addRgbColor(RgbColor rgbColorArrayList) {
        this.rgbColorArrayList.add(rgbColorArrayList);
        notifyDataSetChanged();
    }
}