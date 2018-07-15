package net.simplifiedlearning.firebaseauth.chat_people;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import net.simplifiedlearning.firebaseauth.Message;
import net.simplifiedlearning.firebaseauth.MessageListActivity;
import net.simplifiedlearning.firebaseauth.Profile2Activity;
import net.simplifiedlearning.firebaseauth.R;
import net.simplifiedlearning.firebaseauth.UpdateProfileActivity;
import net.simplifiedlearning.firebaseauth.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProfileUserInPeopleActivity extends AppCompatActivity {

    private TextView tvName;
    private TextView tvEmail;
    private TextView tvGender;
    private TextView tvBirthday;
    private TextView tvPhone;
    private TextView tvAddress;
    private ImageView imgAvatar;
    private Button btnSendMessage;
    private Button btnAddFriend;
    private String idReceiver = "";
    private DatabaseReference mData;
    private FirebaseAuth mAuth;
    private User currentUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_profile);
        AnhXa();

        final String email = mAuth.getCurrentUser().getEmail();
        // lay thong tin nguoi gui yeu cau nhan tin dua vao email cua nguoi do
        loadSenderInfo(email);

        Intent intent = getIntent();
        final User choseUser = (User) intent.getSerializableExtra("profilePersonFromPeopleFragment");  // nguoi dc click
        idReceiver = choseUser.getIdUser();


        if (choseUser!=null) {

            String email1 = choseUser.getEmailUser();
            if (!email1.equals("")){
                tvEmail.setText(email);
            }
            String name = choseUser.getNicknameUser();
            if (!name.equals("")){
                tvName.setText(name);
            }
            String link = choseUser.getLinkAvatarUser();
            if (!link.equals("")){
                Picasso.with(ProfileUserInPeopleActivity.this).load(link).into(imgAvatar);
            }
            String birthday = choseUser.getBirthDayUser();
            if (!birthday.equals("")){
                tvBirthday.setText(birthday);
            }
            String gender = choseUser.getGenderUser();
            if (!gender.equals("")){
                tvGender.setText(gender);
            }
            String phone = choseUser.getPhoneNumberUser();
            if (!phone.equals("")){
                tvPhone.setText(phone);
            }
            String address = choseUser.getAddressUser();
            if (!address.equals("")){
                tvAddress.setText(address);
            }
        }

        btnSendMessage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProfileUserInPeopleActivity.this, MessageListActivity.class);
                intent.putExtra("Chose User From ProfileUserInPeopleFragment",choseUser.getIdUser());
                intent.putExtra("Current User From ProfileUserInPeopleFragment", currentUser.getIdUser());
                startActivity(intent);


//                if (mData.child("User")
//                        .child(currentUser.getIdUser())
//                        .child("Chat").child("SoloChat")
//                        .child("ListUsersAreSentMessages")
//                        .child(choseUser.getIdUser()).getClass()!=null)
//                {   // nếu 2 người này đã nhắn tin với nhau rồi
//                    Toast.makeText(ProfileUserInPeopleActivity.this, "Chưa tạo", Toast.LENGTH_SHORT).show();
//                }else {  // nếu chưa thì tạo node mới
//
//                    Calendar now = Calendar.getInstance();
//                    String strDateFormat24 = "HH:mm";
//                    SimpleDateFormat sdf;
//                    sdf = new SimpleDateFormat(strDateFormat24);
//                    User user = new User("9999","chat4fun.email.test",
//                            "Chat4Fun","https://hinhanhdephd.com/wp-content/uploads/2015/12/hinh-anh-dep-girl-xinh-hinh-nen-dep-gai-xinh.jpg",
//                            "","","","");
//                    Message message = new Message("Start",user,sdf.format(now.getTime()));
//
//                    // tao node moi
//                    mData.child("User").child(currentUser.getIdUser())
//                         .child("Chat").child("SoloChat")
//                         .child("ListUsersAreSentMessages")
//                         .child(choseUser.getIdUser())
//                         .push().setValue(message);
//                    Toast.makeText(ProfileUserInPeopleActivity.this, "Đã tạo rồi", Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }

    private void AnhXa() {
        tvName = findViewById(R.id.tvNicknamePeople);
        tvEmail = findViewById(R.id.tvEmailPeople);
        tvGender = findViewById(R.id.tvGenderPeople);
        tvBirthday = findViewById(R.id.tvBirthdayPeople);
        tvPhone = findViewById(R.id.tvPhonePeople);
        tvAddress = findViewById(R.id.tvAddressPeople);
        imgAvatar = findViewById(R.id.imageAvatarPeople);
        btnSendMessage = findViewById(R.id.btnSendMessagePeople);
        btnAddFriend = findViewById(R.id.btnAddFriendPeople);
        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public void loadSenderInfo(final String email) {
        mData.child("UserProfile").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User getUser = dataSnapshot.getValue(User.class);
                if (getUser.getEmailUser().equals(email)) {
                    currentUser = getUser;
                    return;
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
