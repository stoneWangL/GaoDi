package com.example.stonewang.gaodi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stonewang.gaodi.R;
import com.example.stonewang.gaodi.db.Comment;

import java.util.List;

/**
 * Created by Lenovo on 2018/2/7.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private Context mContext;
    private List<Comment> mCommentList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        //comment_item.xml
        ImageView commentImage;
        TextView commentAuthor;
        TextView commentContent;

        public ViewHolder(View view){
            super(view);
            commentImage = (ImageView) view.findViewById(R.id.comment_image);
            commentAuthor = (TextView) view.findViewById(R.id.comment_author);
            commentContent = (TextView) view.findViewById(R.id.comment_content);
        }
    }


    public CommentAdapter(List<Comment> commentList) {
        mCommentList = commentList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.comment_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment = mCommentList.get(position);
//        Glide.with(mContext).load(comment.get).into(holder.NewsImage);为头像留的位置
        holder.commentAuthor.setText(comment.getAuthor());
        holder.commentContent.setText(comment.getContent());
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }
}
