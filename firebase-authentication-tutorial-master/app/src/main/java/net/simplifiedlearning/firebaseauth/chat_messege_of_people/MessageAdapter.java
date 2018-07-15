package net.simplifiedlearning.firebaseauth.chat_messege_of_people;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.simplifiedlearning.firebaseauth.R;

import java.util.ArrayList;


public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    ArrayList<MessageOfFriends> messages;
    Context context;
    private Context getContext;

    public MessageAdapter(ArrayList<MessageOfFriends> messages, Context  context1) {
        this.messages = messages;
        this.getContext = context1;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.item_chat_messages,
                    parent,
                    false);
        context = parent.getContext();
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {

        final MessageOfFriends message = messages.get(position);
        holder.bind( context ,
                    message.getLinkAvatarImageSender() ,
                    message.getSenderName(),
                    message.getNewestMessage(),
                    message.getIdSender(),
                    message.getIdReceiver()
        );
        holder.setItemClickListener(new net.simplifiedlearning.firebaseauth.ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                    Toast.makeText(context, "Long Click: "+message.getNewestMessage(), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "Click: "+message.getNewestMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
