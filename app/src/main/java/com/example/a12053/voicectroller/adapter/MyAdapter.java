package com.example.a12053.voicectroller.adapter;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a12053.voicectroller.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<String> idList;
    private IadapterClick adapterClick;

    public void setAdapterClick( IadapterClick adapterClick){
        this.adapterClick = adapterClick;
    }

    public MyAdapter(List<String> idList){
        this.idList = idList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.invite_user_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.id.setText(idList.get(i));
        myViewHolder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myViewHolder.addButton.setImageResource(R.drawable.invited);
                if (adapterClick!=null){
                    adapterClick.onClick();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return idList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView id;
        ImageView addButton;
        ImageView itemImg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.item_id);
            addButton = itemView.findViewById(R.id.add_button);
            itemImg = itemView.findViewById(R.id.item_image);

        }
    }

}