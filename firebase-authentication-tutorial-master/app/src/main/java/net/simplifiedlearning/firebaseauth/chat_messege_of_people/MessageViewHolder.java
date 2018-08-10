package net.simplifiedlearning.firebaseauth.chat_messege_of_people;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.simplifiedlearning.firebaseauth.ItemClickListener;
import net.simplifiedlearning.firebaseauth.MessageListActivity;
import net.simplifiedlearning.firebaseauth.R;
import net.simplifiedlearning.firebaseauth.chat_head.ChatHeadActivity;
import net.simplifiedlearning.firebaseauth.chat_people.ProfileUserInPeopleActivity;

public class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
    private TextView textViewSenderName;
    private TextView textViewNewest;
    private ImageView imageViewAvatar;
    private Context context;
    private String idSender = "";
    private String idReceiver = "";
    private String linkAvatar = "";

    private ItemClickListener itemClickListener;


    public MessageViewHolder(View itemView) {
        super(itemView);
        imageViewAvatar = itemView.findViewById(
                R.id.imageAvatarInListMessages);
        textViewSenderName = itemView.findViewById(R.id.tvSenderInListMessages);
        textViewNewest = itemView.findViewById(R.id.tvNewestInListMessages);
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
        Intent intent = new Intent(context,MessageListActivity.class);
        intent.putExtra("idSenderFromMessageViewHolder",idSender);
        intent.putExtra("idReceiverFromMessageViewHolder",idReceiver);
        context.startActivity(intent);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        Intent intent = new Intent(context,ChatHeadActivity.class);
        intent.putExtra("idSenderFromMessageViewHolder",idSender);
        intent.putExtra("idReceiverFromMessageViewHolder",idReceiver);
        intent.putExtra("AvatarSender",linkAvatar);
        context.startActivity(intent);
        return true;
    }

    public void bind(Context context, String avatar , String senderName, String newest,
                     String idSender1, String idReceiver1) {
        Picasso.with(context).load(avatar).into(imageViewAvatar);
//        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        textViewSenderName.setText(senderName);
        textViewNewest.setText(newest);
        idSender = idSender1;
        idReceiver = idReceiver1;
        linkAvatar = avatar;
    }

}
