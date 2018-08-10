package net.simplifiedlearning.firebaseauth.chat_messege_of_people;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.simplifiedlearning.firebaseauth.R;

import java.util.ArrayList;

public class MessagesOfFriendsFragment extends Fragment {


    private RecyclerView recyclerView;
    private ArrayList<MessageOfFriends> messageOfFriends;
    private ArrayList<String> idChoseUsers;
    private DatabaseReference mData;
    MessageAdapter adapter;

    String currentUserId;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_message_of_friends, container, false);
        // nhan id cua user hien tai ( sender)
        bundle = this.getArguments();
        currentUserId = bundle.getString("CurrentUserID");
//        Log.e("CURRENT USER ID", "" + currentUserId);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        idChoseUsers = new ArrayList<>();
        messageOfFriends = new ArrayList<>();
        mData = FirebaseDatabase.getInstance().getReference();

        loadIdUChoseUsers();


        adapter = new MessageAdapter(messageOfFriends, getContext());
        recyclerView = view.findViewById(R.id.rvListMessages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void loadIdUChoseUsers() {

        // load ds users da gui tin nhan
        mData.child("User")
                .child(currentUserId)
                .child("Chat").child("SoloChat")
                .child("ListUsersAreSentMessages")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String idUser = dataSnapshot.getKey();
                        idChoseUsers.add(idUser);
                        loadListMessages(idUser);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void loadListMessages(final String idChosenUser) {
        final ArrayList<String> test = new ArrayList<>();
        final String[] link = {""};
        final String[] name = {""};

        // get link anh avatar va nick name tu UserProfile
        mData.child("UserProfile")
                .child(idChosenUser)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        String info = dataSnapshot.getValue().toString();
                        test.add(info);
                        if (test.size() == 8) {
                            link[0] = test.get(5);
                            name[0] = test.get(6);

//                            Toast.makeText(getContext(), "Link " + link[0] + "\nNickname" + name[0], Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        // get newest message
        mData.child("User")
                .child(currentUserId)
                .child("Chat").child("SoloChat")
                .child("ListUsersAreSentMessages")
                .child(idChosenUser)
                .child("LastestMessage")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String newestMessage = (String) dataSnapshot.getValue();
  //                      Toast.makeText(getContext(), "LM: " + newestMessage, Toast.LENGTH_SHORT).show();
                        MessageOfFriends messageOF = new MessageOfFriends(link[0], name[0], newestMessage,currentUserId, idChosenUser);
                        messageOfFriends.add(messageOF);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

}
