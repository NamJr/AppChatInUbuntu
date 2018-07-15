package net.simplifiedlearning.firebaseauth.chat_people;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.simplifiedlearning.firebaseauth.R;
import net.simplifiedlearning.firebaseauth.User;

import java.util.ArrayList;


public class ChatPeopleAdapter extends RecyclerView.Adapter<PeopleViewHolder> {

    ArrayList<User> people;
    Context context;
    private Context getContext;


    public ChatPeopleAdapter(ArrayList<User> people, Context  context1){
        this.people = people;
        this.getContext = context1;
    }

    @Override
    public PeopleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_people,
                        parent,
                        false);
        context = parent.getContext();
        return new PeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder (PeopleViewHolder holder, int position) {

        final User person = people.get(position);
        holder.bind( context ,
                    person.getIdUser(),
                    person.getEmailUser(),
                    person.getNicknameUser(),
                    person.getLinkAvatarUser(),
                    person.getBirthDayUser(),
                    person.getGenderUser(),
                    person.getPhoneNumberUser(),
                    person.getAddressUser()
                   );
        holder.setItemClickListener(new net.simplifiedlearning.firebaseauth.ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                    Toast.makeText(context, "Long Click: "+person.getNicknameUser(), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "Click: "+person.getNicknameUser(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return people.size();
    }
}
