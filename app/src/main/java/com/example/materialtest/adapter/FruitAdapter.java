package com.example.materialtest.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.materialtest.R;
import com.example.materialtest.activities.FruitActivity;
import com.example.materialtest.activities.ShareShowActivity;
import com.example.materialtest.activities.StoreActivity;
import com.example.materialtest.models.Fruit;
import com.example.materialtest.models.Share;
import com.example.materialtest.models.Store;

import org.w3c.dom.Text;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder>{

    private Context mContext;

    private List<Share> shares;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.fruit_item,
                parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Share share = shares.get(position);
        holder.shareContent.setText(share.getContent());
        holder.shareTitle.setText(share.getTitle());
        holder.shareUser.setText(share.getUserId());
        holder.shareShopname.setText(share.getShopName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), ShareShowActivity.class);
                intent.putExtra("shareContent",holder.shareContent.getText().toString());
                intent.putExtra("shareTitle",holder.shareTitle.getText().toString());
                intent.putExtra("shareUser",holder.shareUser.getText().toString());
                intent.putExtra("shareShopname",holder.shareShopname.getText().toString());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shares.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView shareContent;
        TextView shareTitle;
        TextView shareUser;
        TextView shareShopname;
        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            shareContent = view.findViewById(R.id.sharecontent);
            shareTitle = view.findViewById(R.id.sharetitle);
            shareUser = view.findViewById(R.id.shareuser);
            shareShopname = view.findViewById(R.id.shareshopname);
        }
    }
    public FruitAdapter(List<Share> stores) {
        this.shares = stores;
    }
}
