package net.simplifiedlearning.firebaseauth.chat_people;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import net.simplifiedlearning.firebaseauth.Profile2Activity;
import net.simplifiedlearning.firebaseauth.R;
import net.simplifiedlearning.firebaseauth.User;

import java.util.ArrayList;

//import static com.google.android.gms.internal.zzs.TAG;

public class PeopleFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<User> people;
    private FirebaseAuth mAuth;
    private DatabaseReference mData;
    ChatPeopleAdapter adapter;
    User userReceiver = null;

    // test
    String currentUserId;
    private Bundle bundle;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_people, container, false);

        // nhan id cua user hien tai ( sender)
//        bundle = this.getArguments();
//        currentUserId = bundle.getString("CurrentUserID");
//        Log.e("CURRENT USER ID", "" + currentUserId);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();

        people = new ArrayList<>();
        loadListUser();

        recyclerView = view.findViewById(R.id.rvListPeople);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ChatPeopleAdapter(people, getContext());
        recyclerView.setAdapter(adapter);


        adapter.notifyDataSetChanged();
    }

    private void loadListUser() {

        mData.child("UserProfile").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                User getUser = dataSnapshot.getValue(User.class);
                people.add(getUser);

                adapter.notifyDataSetChanged();
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


