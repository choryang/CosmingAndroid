package com.example.cosmingtest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.CustomViewHolder> {
    private ArrayList<ResultData> arrayList;

    public ResultAdapter(ArrayList<ResultData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ResultAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ResultAdapter.CustomViewHolder holder, int position) {
        holder.ing_ewg.setImageResource(arrayList.get(position).getIng_ewg());
        holder.ing_data.setText(arrayList.get(position).getIng_data());
        holder.ing_name.setText(arrayList.get(position).getIng_name());
        holder.ing_purpose.setText(arrayList.get(position).getIng_purpose());

        /*//클릭이 되었을때
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curName = holder.tv_date.getText().toString();
                Toast.makeText(v.getContext(), curName, Toast.LENGTH_SHORT).show();
            }
        });*/

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


        protected TextView ing_name;
        protected ImageView ing_ewg;
        protected TextView ing_data;
        protected TextView ing_purpose;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ing_ewg = (ImageView) itemView.findViewById(R.id.ing_ewg);
            this.ing_data = (TextView) itemView.findViewById(R.id.ing_data);
            this.ing_purpose = (TextView) itemView.findViewById(R.id.ing_purpose);
            this.ing_name = (TextView) itemView.findViewById(R.id.ing_name);

        }
    }


}
