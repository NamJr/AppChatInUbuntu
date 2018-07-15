package net.simplifiedlearning.firebaseauth.chat_friends;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.simplifiedlearning.firebaseauth.ItemClickListener;
import net.simplifiedlearning.firebaseauth.R;
import net.simplifiedlearning.firebaseauth.User;
import net.simplifiedlearning.firebaseauth.chat_people.ProfileUserInPeopleActivity;

public class FriendViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
    private TextView textViewNickName;
    private TextView textViewEmail;
    private ImageView imageViewAvatar;
    private Context context;
    User user = null;
    private ItemClickListener itemClickListener;


    public FriendViewHolder(View itemView) {
        super(itemView);
        imageViewAvatar = itemView.findViewById(
                R.id.imageAvatarInListMessages);
        textViewNickName = itemView.findViewById(R.id.tvSenderInListMessages);
        textViewEmail = itemView.findViewById(R.id.tvNewestInListMessages);
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
//        Intent intent = new Intent(context,ProfileUserInPeopleActivity.class);
//        intent.putExtra("profilePersonFromPeopleFragment",user);
//        context.startActivity(intent);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
    }

    public void bind(Context context, String avatar , String senderName, String newest) {
        Picasso.with(context).load(avatar).into(imageViewAvatar);
//        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        textViewNickName.setText(senderName);
        textViewEmail.setText(newest);
    }

}
