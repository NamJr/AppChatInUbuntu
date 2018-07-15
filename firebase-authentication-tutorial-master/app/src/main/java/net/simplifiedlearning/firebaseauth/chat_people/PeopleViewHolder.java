package net.simplifiedlearning.firebaseauth.chat_people;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import net.simplifiedlearning.firebaseauth.R;
import net.simplifiedlearning.firebaseauth.User;
import net.simplifiedlearning.firebaseauth.ItemClickListener;

public class PeopleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
    private TextView tvNickName;
    private TextView tvEmail;
    private ImageView imageViewAvatar;
    private Context context;
    User user = null;

    private ItemClickListener itemClickListener;


    public PeopleViewHolder(View itemView) {
        super(itemView);
        imageViewAvatar = itemView.findViewById(R.id.imageAvatarInPeople);
        tvNickName = itemView.findViewById(R.id.tvNickName);
        tvEmail = itemView.findViewById(R.id.tvEmail);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        context = itemView.getContext();
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
        Intent intent = new Intent(context,ProfileUserInPeopleActivity.class);
        intent.putExtra("profilePersonFromPeopleFragment",user);
        context.startActivity(intent);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
    }

    public void bind(Context context,
                     String id,
                     String email,
                     String nickName ,
                     String avatar,
                     String birthday,
                     String gender,
                     String phone,
                     String address
    ) {
        Picasso.with(context).load(avatar).into(imageViewAvatar);
        tvNickName.setText(nickName);
        tvEmail.setText(email);
        user = new User(id,email,nickName,avatar,birthday,gender,phone,address);
    }

}
