package com.cookandroid.loarang;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class CalenderFragmentListItemViewHolder extends RecyclerView.ViewHolder {
    ImageView calender_image;
    TextView calender_location;
    TextView calender_name;
    TextView calender_time;

    public CalenderFragmentListItemViewHolder(@NonNull View itemView) {
        super(itemView);

        calender_image = itemView.findViewById(R.id.calender_image);
        calender_location = itemView.findViewById(R.id.calender_location);
        calender_name = itemView.findViewById(R.id.calender_name);
        calender_time = itemView.findViewById(R.id.calender_time);
    }

    public void onBind(CalenderFragmentListItem calenderFragmentListItem) {
        Glide.with(itemView).load(calenderFragmentListItem.getImage())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(calender_image);
        calender_location.setText(calenderFragmentListItem.getLocation());
        calender_name.setText(calenderFragmentListItem.getName());
        calender_time.setText(calenderFragmentListItem.getTime());
    }
}
