package com.example.lostfound;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostfound.data.AppDatabase;
import com.example.lostfound.data.Item;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameView, descriptionView, typeView, locationView, categoryView, lostOnView, postedView;
        ImageView imageView;
        Button removeBtn;

        public ViewHolder(View itemView){
            super(itemView);

            nameView = itemView.findViewById(R.id.nameView);
            descriptionView = itemView.findViewById(R.id.descriptionView);
            typeView = itemView.findViewById(R.id.typeView);
            locationView = itemView.findViewById(R.id.locationView);
            categoryView = itemView.findViewById(R.id.categoryView);
            lostOnView = itemView.findViewById(R.id.lostOnView);
            postedView = itemView.findViewById(R.id.postedView);
            imageView = itemView.findViewById(R.id.imageView);
            removeBtn = itemView.findViewById(R.id.removeBtn);
        }
    }
    List<Item> itemList;
    Context context;

    public ItemAdapter(Context context, List<Item> itemList){
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        Item item = itemList.get(position);

        holder.nameView.setText(item.name);
        holder.typeView.setText(item.type);
        holder.descriptionView.setText(item.description);
        holder.locationView.setText(item.location);
        holder.lostOnView.setText(item.date);
        holder.postedView.setText(item.timeStamp);
        holder.categoryView.setText(item.category);
        if (item.imagePath !=null && !item.imagePath.isEmpty()) {
            holder.imageView.setImageBitmap(BitmapFactory.decodeFile(item.imagePath));
        }

        holder.removeBtn.setOnClickListener(v -> {
            AppDatabase db = AppDatabase.getInstance(context);
            db.itemDao().delete(item);
            itemList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, itemList.size());
            Toast.makeText(context, "Item has been removed", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updateList(List<Item> newList){
        itemList = newList;
        notifyDataSetChanged();
    }
}
