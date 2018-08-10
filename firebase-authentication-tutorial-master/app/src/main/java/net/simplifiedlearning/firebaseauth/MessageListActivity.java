package net.simplifiedlearning.firebaseauth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.HttpHeaders;
import cz.msebera.android.httpclient.entity.StringEntity;

public class MessageListActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private ArrayList<Message> messageList;
    private DatabaseReference mData;
    private EditText edtContent;
    private Button btnSend;
    private FirebaseAuth mAuth;
    private String idCurrentUser = "";
    private String idChosenUSer = "";
    public final String TAG = "MessageListActivity";

    FirebaseUserModel firebaseUserModel = new FirebaseUserModel();
    FirebaseUserModel firebaseUserModel1 = new FirebaseUserModel();
    JSONArray registration_ids = new JSONArray();

    private SharedPreferences preferences;

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

        if (idCurrentUser==null && idChosenUSer==null){
            // nhan thong tin cua nguoi dc chon tu MessageAdapter
            idCurrentUser = intent.getStringExtra("idSenderFromMessageAdapter");
            idChosenUSer = intent.getStringExtra("idReceiverFromMessageAdapter");
        }


        // khoi tao
        preferences= getSharedPreferences("Account", Context.MODE_PRIVATE);
        if (idCurrentUser==null && idChosenUSer==null){
            idCurrentUser = preferences.getString("idCurrentUser","");
            idChosenUSer = preferences.getString("idChosenUser","");

        }else{
            preferences.edit().putString("idCurrentUser",idCurrentUser).commit();
            preferences.edit().putString("idChosenUser",idChosenUSer).commit();
        }


        // load tin nhan cua 2 user
        messageList = new ArrayList<>();
        loadInfo(idCurrentUser, idChosenUSer);
        loadDataFromFirebase();

        mMessageRecycler = findViewById(R.id.reyclerview_message_list);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageAdapter = new MessageListAdapter(this, messageList);
        mMessageRecycler.setAdapter(mMessageAdapter);



        final DatabaseReference messagesRef = mData.child("User")
                .child(idCurrentUser)
                .child("Chat").child("SoloChat")
                .child("ListUsersAreSentMessages")
                .child(idChosenUSer)
                .child("Conversation");


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                final ProgressDialog Dialog = new ProgressDialog(MessageListActivity.this);
//                Dialog.setMessage("Please wait..");
//                Dialog.setCancelable(false);
//                Dialog.show();
                final String content = edtContent.getText().toString();
                edtContent.setText("");
                Calendar now = Calendar.getInstance();
                String strDateFormat24 = "HH:mm";
                SimpleDateFormat sdf;
                sdf = new SimpleDateFormat(strDateFormat24);
                Message message = new Message(content,idCurrentUser,sdf.format(now.getTime()));

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

                final String wishMessage = content;
                if (wishMessage.isEmpty()) {
                    return;
                } else {

                    final FirebaseMessageModel firebaseMessageModel = new FirebaseMessageModel();
                    firebaseMessageModel.setDeviceId(firebaseMessageModel.deviceId);
                    firebaseMessageModel.setNameSender(firebaseMessageModel.nameSender);

                    final DatabaseReference newRef = messagesRef.push();
                    newRef.setValue(firebaseMessageModel, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

//                            Dialog.dismiss();
                            if (databaseError != null) {
                                Log.i(TAG, databaseError.toString());
                            } else {

                                if (registration_ids.length() > 0) {

                                    String url = "https://fcm.googleapis.com/fcm/send";
                                    AsyncHttpClient client = new AsyncHttpClient();
                                    client.addHeader(HttpHeaders.AUTHORIZATION, "key=AIzaSyC1FtoorbcIIuo4AkmACapF040QtaBH1s0");
                                    client.addHeader(HttpHeaders.CONTENT_TYPE, RequestParams.APPLICATION_JSON);

                                    try {
                                        JSONObject params = new JSONObject();
                                        params.put("registration_ids", registration_ids);
                                        JSONObject notificationObject = new JSONObject();
                                        notificationObject.put("body", content+"");
                                        notificationObject.put("title", firebaseUserModel.username);
                                        params.put("notification", notificationObject);
                                        StringEntity entity = new StringEntity(params.toString());

                                        client.post(getApplicationContext(), url, entity, RequestParams.APPLICATION_JSON, new TextHttpResponseHandler() {
                                            @Override
                                            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
//                                                Dialog.dismiss();
//                                                Log.i(TAG, responseString);
                                            }
                                            @Override
                                            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
//                                                Dialog.dismiss();
 //                                               Log.i(TAG, responseString);
                                            }
                                        });

                                    } catch (Exception e) { }
                                }
                            }
                        }
                    });
                }
            }
        });
    }


    public void updateListView() {
//        Log.i(TAG, "Inside prepareWishList()");
//        Log.i(TAG, "Total Wishes : " + totalWishes);

        int totalWishes = messageList.size();
        mMessageRecycler.smoothScrollToPosition(totalWishes-1);
        mMessageAdapter.notifyDataSetChanged();
    }

    private void loadInfo(final String idCurrent, final String idChosen) {
        final boolean[] check = {false};
        final boolean[] check1 = {false};
        mData.child("users")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot.getKey().toString().equals(idCurrent)) {
                            firebaseUserModel = dataSnapshot.getValue(FirebaseUserModel.class);
                            check[0] = true;
                        }
                        if ( dataSnapshot.getKey().toString().equals(idChosen)) {
                            firebaseUserModel1 = dataSnapshot.getValue(FirebaseUserModel.class);
                            check1[0] = true;
                            registration_ids = new JSONArray();
                            registration_ids.put(firebaseUserModel1.deviceToken);
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

    private void loadDataFromFirebase() {

        mData.child("User")
                .child(idCurrentUser)
                .child("Chat").child("SoloChat")
                .child("ListUsersAreSentMessages")
                .child(idChosenUSer)
                .child("Conversation")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        messageList.clear();

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Message message2 = postSnapshot.getValue(Message.class);
                            messageList.add(message2);
                        }
                        updateListView();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        messageList.clear();
                        updateListView();
                    }
                });
    }
}
