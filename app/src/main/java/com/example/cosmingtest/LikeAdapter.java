package com.example.cosmingtest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.CustomViewHolder> {
    private ArrayList<LikeData> arrayList;

    public LikeAdapter(ArrayList<LikeData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.like_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final LikeAdapter.CustomViewHolder holder, int position) {
        holder.cos_name.setText(arrayList.get(position).getCos_name());
        holder.cos_type.setText(arrayList.get(position).getCos_type());
        holder.open_date.setText(arrayList.get(position).getOpen_date());
        holder.due_date.setText(arrayList.get(position).getDue_date());
        holder.memo.setText(arrayList.get(position).getMemo());

        //클릭이 되었을때
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curName = holder.cos_name.getText().toString();
                Toast.makeText(v.getContext(), curName, Toast.LENGTH_SHORT).show();
            }
        });

        /*holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                remove(holder.getAdapterPosition());
                return true;
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0); // 리스트가 널이면 0
    }

    public void remove(int position) {
        try {
            arrayList.remove(position);
            notifyItemRemoved(position); //notify는 새로고침

        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView cos_name;
        protected TextView cos_type;
        protected TextView open_date;
        protected TextView due_date;
        protected TextView memo;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cos_name = (TextView) itemView.findViewById(R.id.cos_name);
            this.cos_type = (TextView) itemView.findViewById(R.id.cos_type);
            this.open_date = (TextView) itemView.findViewById(R.id.open_date);
            this.due_date = (TextView) itemView.findViewById(R.id.due_date);
            this.memo = (TextView) itemView.findViewById(R.id.memo);

        }
    }
}
