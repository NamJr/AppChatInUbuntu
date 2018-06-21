package net.simplifiedlearning.firebaseauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MessageListActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private ArrayList<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        messageList = new ArrayList<>();
        fakeData();

        mMessageRecycler = findViewById(R.id.reyclerview_message_list);
        mMessageAdapter = new MessageListAdapter(this, messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);
        mMessageAdapter.notifyDataSetChanged();
    }

    private void fakeData() {
        Calendar now = Calendar.getInstance();
        String strDateFormat24 = "HH:mm";
        SimpleDateFormat sdf =null;
        //Tạo đối tượng SimpleDateFormat với định dạng 24
        sdf = new SimpleDateFormat(strDateFormat24);
        //2. gọi hàm format để lấy giờ:phút:giây loại 24
//        System.out.println("Giờ định dạng 24 : " + sdf.format(now.getTime()));
        User user = new User("123","nam111","Nam","gs://myappfirebase-1d0c8.appspot.com/image1527784768022jpg","","","","");
        Message message = new Message("Hello! How are you. Do you remember me?",user,sdf.format(now.getTime()));
        messageList.add(message);
        User user1 = new User("321","nam111111@gmail.com","Bac","gs://myappfirebase-1d0c8.appspot.com/image1527784768022jpg","","","","");
        Message message1 = new Message("Oh! Hi. I'm fine. Thanks!!", user1, sdf.format(now.getTime()));
        messageList.add(message1);

    }


}
