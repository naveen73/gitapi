package com.example.sbnrinaveen.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sbnrinaveen.R;
import com.example.sbnrinaveen.network.GitHubResponseItem;

import java.util.ArrayList;
import java.util.List;

public class GitRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<GitHubResponseItem> mGitResponseItems = new ArrayList<>();
    private static final int GIT_TYPE = 1;
    private static final int LOADING_TYPE = 2;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        switch (viewType){

            case GIT_TYPE:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_item, parent, false);
                return new GitItemViewHolder(view);
            }

            case LOADING_TYPE:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_list_item, parent, false);
                return new LoadingViewHolder(view);
            }
            default:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_item, parent, false);
                return new GitRecyclerAdapter.GitItemViewHolder(view);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if(itemViewType == GIT_TYPE){
            ((GitItemViewHolder)holder).mName.setText(mGitResponseItems.get(position).getName());
            ((GitItemViewHolder)holder).mDescription.setText(mGitResponseItems.get(position).getDescription());
            ((GitItemViewHolder)holder).mIssue_count.setText("Issue Count: " + String.valueOf(mGitResponseItems.get(position).getOpen_issues_count()));
            if (mGitResponseItems.get(position).getLicense() != null) {
                ((GitItemViewHolder)holder).mLicense.setText(mGitResponseItems.get(position).getLicense().getName());
            }
            if (mGitResponseItems.get(position).getPermission() != null) {
                String text = "Permissions - Admin : " +
                        mGitResponseItems.get(position).getPermission().isPull() +
                        " Push : " +
                        mGitResponseItems.get(position).getPermission().isPush() +
                        " Pull : " +
                        mGitResponseItems.get(position).getPermission().isPull();
                ((GitItemViewHolder)holder).mPermission.setText(text);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mGitResponseItems != null) {
            return mGitResponseItems.size();
        }
        return 0;
    }

    public void hideLoading(){
        if(isLoading()){
            if(mGitResponseItems.get(mGitResponseItems.size() - 1).equals("LOADING...")){
                mGitResponseItems.remove(mGitResponseItems.size() - 1);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mGitResponseItems.get(position).getName().equals("LOADING...")){
            return LOADING_TYPE;
        }
        return GIT_TYPE;
    }

    // pagination loading
    public void displayLoading(){
        if(mGitResponseItems == null){
            mGitResponseItems = new ArrayList<>();
        }
        if(!isLoading()){
            GitHubResponseItem recipe = new GitHubResponseItem();
            recipe.setName("LOADING...");
            mGitResponseItems.add(recipe);
            notifyDataSetChanged();
        }
    }


    private boolean isLoading(){
        if(mGitResponseItems != null){
            if(mGitResponseItems.size() > 0){
                return mGitResponseItems.get(mGitResponseItems.size() - 1).getName().equals("LOADING...");
            }
        }
        return false;
    }


    public static class GitItemViewHolder extends RecyclerView.ViewHolder {
        TextView mName;
        TextView mDescription;
        TextView mLicense;
        TextView mIssue_count;
        TextView mPermission;


        public GitItemViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.name);
            mDescription = itemView.findViewById(R.id.description);
            mIssue_count = itemView.findViewById(R.id.issue_count);
            mLicense = itemView.findViewById(R.id.license);
            mPermission = itemView.findViewById(R.id.permission);
        }
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void setItems(List<GitHubResponseItem> locations){
        mGitResponseItems = locations;
        notifyDataSetChanged();
    }
}
