package com.suntech.software.adapter;

/**
 * Created by Ramazan on 15/8/2016.
 */

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.suntech.software.R;
import com.suntech.software.model.StatusImages;

import java.util.ArrayList;
import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.MyViewHolder> {

    private List<Uri> moviesList;
    ArrayList<StatusImages> imagesArrayList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }


    public ImageListAdapter(List<Uri> moviesList) {
        this.moviesList = moviesList;
    }

    public ImageListAdapter(Context context, List<Uri> moviesList) {
        this.moviesList = moviesList;
        this.context = context;
    }

    public ImageListAdapter(Context context, ArrayList<StatusImages> imagesArrayList) {
        this.imagesArrayList = imagesArrayList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        Uri uri = moviesList.get(position);
//
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 4;
//        Bitmap bitmap = BitmapFactory.decodeFile(uri.getPath());
//        int height = bitmap.getHeight(), width = bitmap.getWidth();
//
//        if (height > 1280 && width > 960) {
//            Bitmap imgbitmap = BitmapFactory.decodeFile(uri.getPath(), options);
//            holder.imageView.setImageBitmap(imgbitmap);
//
//            System.out.println("Need to resize");
//
//        } else {
//            holder.imageView.setImageBitmap(bitmap);
//        }

        holder.imageView.setImageBitmap(imagesArrayList.get(position).getPhoto());

    }

    @Override
    public int getItemCount() {
        //return moviesList.size();
        return imagesArrayList.size();
    }
}