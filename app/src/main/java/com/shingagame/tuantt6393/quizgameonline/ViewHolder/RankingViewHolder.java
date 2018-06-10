package com.shingagame.tuantt6393.quizgameonline.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shingagame.tuantt6393.quizgameonline.Interface.ItemClickListener;
import com.shingagame.tuantt6393.quizgameonline.R;

public class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtName, txtScore;
    private ItemClickListener itemClickListener;

    public RankingViewHolder(View itemView) {
        super(itemView);
        txtName = (TextView)itemView.findViewById(R.id.txtName);
        txtScore = (TextView)itemView.findViewById(R.id.txtScore);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(), false);

    }
}
