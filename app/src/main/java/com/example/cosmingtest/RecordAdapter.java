package com.example.cosmingtest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.CustomViewHolder> {
    private ArrayList<MainData> arrayList;

    public RecordAdapter(ArrayList<MainData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecordAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecordAdapter.CustomViewHolder holder, int position) {
        holder.iv_profile.setImageResource(arrayList.get(position).getIv_profile());
        holder.tv_date.setText(arrayList.get(position).getTv_date());
        holder.tv_time.setText(arrayList.get(position).getTv_time());

        //클릭이 되었을때
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curName = holder.tv_date.getText().toString();
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


        protected ImageView iv_profile;
        protected TextView tv_date;
        protected TextView tv_time;



        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = (ImageView) itemView.findViewById(R.id.iv_profile);
            this.tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            this.tv_time = (TextView) itemView.findViewById(R.id.tv_time);


        }
    }


}
