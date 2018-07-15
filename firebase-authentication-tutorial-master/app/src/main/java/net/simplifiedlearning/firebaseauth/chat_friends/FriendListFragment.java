package net.simplifiedlearning.firebaseauth.chat_friends;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.simplifiedlearning.firebaseauth.R;
import net.simplifiedlearning.firebaseauth.User;
import net.simplifiedlearning.firebaseauth.chat_people.ChatPeopleAdapter;

import java.util.ArrayList;

public class FriendListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<User> people;
    private ArrayList<String> idChoseUsers;
    private ArrayList<Friend> friends;
    private FirebaseAuth mAuth;
    private DatabaseReference mData;
    FriendAdapter adapter;
    User userReceiver = null;

    // test
    String currentUserId;
    private Bundle bundle;

    public FriendListFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_friend_list, container, false);

//         nhan id cua user hien tai ( sender)
        bundle = this.getArguments();
        currentUserId = bundle.getString("CurrentUserID");
        Log.e("CURRENT USER ID", "" + currentUserId);
        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();

        people = new ArrayList<>();
        idChoseUsers = new ArrayList<>();
        friends = new ArrayList<>();
//        loadInfoUser();
        loadListFriend();

        recyclerView = view.findViewById(R.id.rvListFriend);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FriendAdapter(friends, getContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    private void loadListFriend() {

        mData.child("User")
                .child(currentUserId)
                .child("FriendList")
                .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String id = dataSnapshot.getKey();
                idChoseUsers.add(id);
                loadInfoUser(id);
//                adapter.notifyDataSetChanged();
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

    public void loadInfoUser(String id){
        final ArrayList<String> test = new ArrayList<>();
        final String[] link = {""};
        final String[] name = {""};
        final String[] email = {""};

        mData.child("UserProfile")
                .child(id)
                .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String info = dataSnapshot.getValue().toString();
                test.add(info);
                if (test.size() == 8) {
                    link[0] = test.get(5);
                    name[0] = test.get(6);
                    email[0] = test.get(2);
                    Friend friend = new Friend(link[0],name[0],email[0]);
                    friends.add(friend);
                    adapter.notifyDataSetChanged();
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
    }
}
