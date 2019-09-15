package com.example.kingominho;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ProjectListViewHolder> {

    private ArrayList<ProjectListItem> mProjectList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener
    {
        void onGitButtonPressed(int position);
    }

    public void setOnItemCLickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    public static class ProjectListViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView duration;
        public ImageButton gitButton;

        public ProjectListViewHolder(View itemView, final OnItemClickListener listener)
        {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            duration = itemView.findViewById(R.id.duration);
            gitButton = itemView.findViewById(R.id.git_button);

            gitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.onGitButtonPressed(position);
                        }
                    }
                }
            });
        }

    }

    public ProjectListAdapter(ArrayList<ProjectListItem> mProjectList) {
        this.mProjectList = mProjectList;
    }

    @NonNull
    @Override
    public ProjectListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_list_card, parent, false);
        ProjectListViewHolder evh = new ProjectListViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectListViewHolder holder, final int position) {
        ProjectListItem currentItem = mProjectList.get(position);

        holder.title.setText(currentItem.getTitle());
        holder.duration.setText(currentItem.getDuration());
        holder.gitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onGitButtonPressed(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProjectList.size();
    }
}
