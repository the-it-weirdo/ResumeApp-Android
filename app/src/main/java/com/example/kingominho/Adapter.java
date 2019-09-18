package com.example.kingominho;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;


import java.util.List;

public class Adapter extends PagerAdapter {

    private List<Model> models;
    private LayoutInflater layoutInflater;
    private Context context;

    private AdapterOnCardClickListener mAdapterOnCardClickListener;

    public Adapter(List<Model> models, Context context) {
        this.models = models;
        this.context = context;
        if (context instanceof AdapterOnCardClickListener) {
            mAdapterOnCardClickListener = (AdapterOnCardClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AdapterOnCardClickListener");
        }
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item, container, false);

        ImageView imageView, icon;
        TextView title, desc;
        CardView cardView;


        cardView = view.findViewById(R.id.cardView);
        icon = view.findViewById(R.id.nav_icon);
        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.desc);


        icon.setImageResource(models.get(position).getIcon());
        imageView.setImageResource(models.get(position).getImage());
        title.setText(models.get(position).getTitle());
        desc.setText(models.get(position).getDesc());


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAdapterOnCardClickListener!=null)
                {
                    mAdapterOnCardClickListener.onCardClick(models.get(position).getTitle());
                }
                /*Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("param", models.get(position).getTitle());
                context.startActivity(intent);*/

                // finish();
            }
        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    public interface AdapterOnCardClickListener
    {
        void onCardClick(String param);
    }
}
