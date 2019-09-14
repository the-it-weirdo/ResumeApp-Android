package com.example.kingominho;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class ImageAdapterGridView extends BaseAdapter {
    private Context context;
    private int[] images;

    ImageAdapterGridView(Context c, int[] imageIDs)
    {
        context = c;images = imageIDs;
    }

    @Override
    public int getCount()
    {
        return images.length;
    }

    @Override
    public Object getItem(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if(convertView == null)
        {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(10,10,10,10);
        }
        else
        {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(images[position]);

        return imageView;
    }
}
