package net.simplifiedlearning.firebaseauth.chat_friends;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.simplifiedlearning.firebaseauth.ItemClickListener;
import net.simplifiedlearning.firebaseauth.R;

import java.util.ArrayList;

public class FriendAdapter extends RecyclerView.Adapter<FriendViewHolder> {
    ArrayList<Friend> friends;
    Context context;
    private Context getContext;

    public FriendAdapter(ArrayList<Friend> friends, Context  context1){
        this.friends = friends;
        this.getContext = context1;
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_chat_messages,
                        parent,
                        false);
        context = parent.getContext();
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {
        final Friend friend = friends.get(position);
        holder.bind( context , friend.getLinkAvatar() ,friend.getNickName(),friend.getEmail());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                    Toast.makeText(context, "Long Click: "+friend.getNickName(), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "Click: "+friend.getNickName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
}
