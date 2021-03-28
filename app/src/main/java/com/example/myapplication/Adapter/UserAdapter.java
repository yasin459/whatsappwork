package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.MessageActivity;
import com.example.myapplication.Model.Users;
import com.example.myapplication.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewaHolder> {
    private Context context;
    private List<Users> usersList;

    public UserAdapter( Context context,List<Users> usersList ) {
        this.context=context;
        this.usersList =usersList;

    }


    @NonNull
    @Override
    public ViewaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return new UserAdapter.ViewaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewaHolder holder, int position) {
        Users users = usersList.get(position);
        holder.username.setText(users.getUsername());
        if(users.getImageURL().equals("default")){
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(context).load(users.getImageURL()).into(holder.imageView);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(context, MessageActivity.class);
                 intent.putExtra("userid",users.getId());
                 context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
    public class ViewaHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView imageView;

        public ViewaHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            imageView =  itemView.findViewById(R.id.userImage);

        }
    }
}
