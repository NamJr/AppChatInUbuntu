package net.simplifiedlearning.firebaseauth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import net.simplifiedlearning.firebaseauth.chat_people.ProfileUserInPeopleActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MessageListActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private ArrayList<Message> messageList;
    private DatabaseReference mData;
//    private User choseUser = null;
//    private User currentUser;
    private EditText edtContent;
    private Button btnSend;
    private FirebaseAuth mAuth;
    private String idCurrentUser = "";
    private String idChosenUSer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        mData = FirebaseDatabase.getInstance().getReference();
        edtContent = findViewById(R.id.edittext_chatbox);
        btnSend = findViewById(R.id.button_chatbox_send);
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();

        // nhan thong tin 2 users tu Message Fragment
        idCurrentUser = intent.getStringExtra("idSenderFromMessageViewHolder");
        idChosenUSer = intent.getStringExtra("idReceiverFromMessageViewHolder");


        if (idCurrentUser==null && idChosenUSer==null){
            // nhan thong tin cua nguoi dc chon tu People Fragment
            // nhung dc chuyen qua o ProfileUserInPeopleFragment
            idCurrentUser = intent.getStringExtra("Current User From ProfileUserInPeopleFragment");
            idChosenUSer = intent.getStringExtra("Chose User From ProfileUserInPeopleFragment");
        }
//        Toast.makeText(this, "CurrentUser: "+idCurrentUser+
//                                            "\nChosenUser: "+idChosenUSer, Toast.LENGTH_SHORT).show();


//        choseUser = (User) intent.getSerializableExtra("Chose User From ProfileUserInPeopleFragment");
//        currentUser = (User) intent.getSerializableExtra("Current User From ProfileUserInPeopleFragment");



        // load tin nhan cua 2 user
        messageList = new ArrayList<>();
        loadDataFromFirebase();

        mMessageRecycler = findViewById(R.id.reyclerview_message_list);
        mMessageAdapter = new MessageListAdapter(this, messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);
        mMessageAdapter.notifyDataSetChanged();


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = edtContent.getText().toString();
                edtContent.setText("");
                Calendar now = Calendar.getInstance();
                String strDateFormat24 = "HH:mm";
                SimpleDateFormat sdf;
                sdf = new SimpleDateFormat(strDateFormat24);
                Message message = new Message(content,idCurrentUser,sdf.format(now.getTime()));
//                messageList.add(message);
//                mMessageAdapter.notifyDataSetChanged();

                mData.child("User")
                        .child(idCurrentUser)
                        .child("Chat").child("SoloChat")
                        .child("ListUsersAreSentMessages")
                        .child(idChosenUSer)
                        .child("Conversation")
                        .push().setValue(message);
                mData.child("User")
                        .child(idCurrentUser)
                        .child("Chat").child("SoloChat")
                        .child("ListUsersAreSentMessages")
                        .child(idChosenUSer)
                        .child("LastestMessage")
                        .setValue(message.getMessage());
                mData.child("User")
                        .child(idCurrentUser)
                        .child("Chat").child("SoloChat")
                        .child("ListUsersAreSentMessages")
                        .child(idChosenUSer)
                        .child("DateLastestMessage")
                        .setValue(message.getCreatedAt());


                mData.child("User")
                        .child(idChosenUSer)
                        .child("Chat").child("SoloChat")
                        .child("ListUsersAreSentMessages")
                        .child(idCurrentUser)
                        .child("Conversation")
                        .push().setValue(message);
                mData.child("User")
                        .child(idChosenUSer)
                        .child("Chat").child("SoloChat")
                        .child("ListUsersAreSentMessages")
                        .child(idCurrentUser)
                        .child("LastestMessage")
                        .setValue(message.getMessage());
                mData.child("User")
                        .child(idChosenUSer)
                        .child("Chat").child("SoloChat")
                        .child("ListUsersAreSentMessages")
                        .child(idCurrentUser)
                        .child("DateLastestMessage")
                        .setValue(message.getCreatedAt());

            }
        });
    }

    private void loadDataFromFirebase() {

        // lay node da tao ben ProfileUserPeopleActivity de day len firebase
        mData.child("User")
                .child(idCurrentUser)
                .child("Chat").child("SoloChat")
                .child("ListUsersAreSentMessages")
                .child(idChosenUSer)
                .child("Conversation")
                .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message2 = dataSnapshot.getValue(Message.class);
                messageList.add(message2);
                mMessageAdapter.notifyDataSetChanged();
//                Toast.makeText(MessageListActivity.this, "load", Toast.LENGTH_SHORT).show();
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
