package com.example.cal_24point;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Card> datas;
    public interface OnItemClickListener{
        void onClikitem(View view);
    }

    private OnItemClickListener listener;
    public void SetOnItemClickListener(OnItemClickListener listener)
    {
        this.listener=listener;
    }

    public MyRecyclerViewAdapter(Context context, ArrayList<Card> datas) {
        this.context = context;
        this.datas=datas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview =LayoutInflater.from(context).inflate(R.layout.recyclerview_item, null);
        MyViewHolder holder= new MyViewHolder(itemview);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Card card=datas.get(position);
        holder.imageView.setImageResource(card.getImage());
        holder.imageView.setTag(card.getInfo());
        holder.textView.setText(card.getInfo());
//        Log.e("information","我被调用了！");
       /* Log.e("ifo", (String) holder.imageView.getTag());*/

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_item);
            textView=itemView.findViewById(R.id.tv_item_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                    {
                        listener.onClikitem(view);
                    }
                }
            });
        }
    }
}
