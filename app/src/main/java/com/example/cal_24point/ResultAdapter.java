package com.example.cal_24point;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<String> datas;

    public ResultAdapter(Context context, ArrayList<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(context).inflate(R.layout.result_item, null);
        ResultAdapter.MyViewHolder holder= new ResultAdapter.MyViewHolder(itemview);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String s=datas.get(position);
        String res="第"+Integer.toString(position+1)+"种算法为："+s;
        holder.textView.setText(res);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tx_res);
        }
    }
}
