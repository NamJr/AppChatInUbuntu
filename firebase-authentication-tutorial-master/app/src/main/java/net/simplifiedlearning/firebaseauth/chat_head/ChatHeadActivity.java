package net.simplifiedlearning.firebaseauth.chat_head;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.txusballesteros.bubbles.BubbleLayout;
import com.txusballesteros.bubbles.BubblesManager;
import com.txusballesteros.bubbles.OnInitializedCallback;

import net.simplifiedlearning.firebaseauth.MessageListActivity;
import net.simplifiedlearning.firebaseauth.R;

public class ChatHeadActivity extends AppCompatActivity {

    private BubblesManager bubblesManager;
    private String idCurrentUser = "";
    private String idChosenUSer = "";
    private String avatarSender = "https://firebasestorage.googleapis.com/v0/b/myappfirebase-1d0c8.appspot.com/o/image1531849122810jpg?alt=media&token=11b38969-0fed-4958-8056-fe954f2f1a50";
    private ImageView avatar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_head);

        avatar = findViewById(R.id.avatar);
        Intent intent = getIntent();

        // nhan thong tin 2 users tu Message Fragment
        idCurrentUser = intent.getStringExtra("idSenderFromMessageViewHolder");
        idChosenUSer = intent.getStringExtra("idReceiverFromMessageViewHolder");
//        avatarSender = intent.getStringExtra("AvatarSender").toString();
//        for (int i=0;i<2;i++){
//            Toast.makeText(this, ""+idCurrentUser, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, ""+idChosenUSer, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, ""+avatarSender, Toast.LENGTH_SHORT).show();
//        }

        Log.i("link",avatarSender);
//
//        if (avatarSender.equals("https://firebasestorage.googleapis.com/v0/b/myappfirebase-1d0c8.appspot.com/o/image1531849122810jpg?alt=media&token=11b38969-0fed-4958-8056-fe954f2f1a50")){
//            Toast.makeText(this, "Avatar", Toast.LENGTH_SHORT).show();
//
//        }




        initializeBubblesManager();

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewBubble();
            }
        });
    }

    private void addNewBubble() {
//        Picasso.with(ChatHeadActivity.this).load(avatarSender).into(avatar);
        BubbleLayout bubbleView = (BubbleLayout) LayoutInflater.from(ChatHeadActivity.this).inflate(R.layout.bubble_layout, null);
        bubbleView.setOnBubbleRemoveListener(new BubbleLayout.OnBubbleRemoveListener() {
            @Override
            public void onBubbleRemoved(BubbleLayout bubble) { }
        });
        bubbleView.setOnBubbleClickListener(new BubbleLayout.OnBubbleClickListener() {

            @Override
            public void onBubbleClick(BubbleLayout bubble) {
                Intent intent = new Intent(ChatHeadActivity.this, MessageListActivity.class);
                intent.putExtra("idSenderFromMessageViewHolder",idCurrentUser);
                intent.putExtra("idReceiverFromMessageViewHolder",idChosenUSer);
                startActivity(intent);
                finish();
            }
        });
        bubbleView.setShouldStickToWall(true);
        bubblesManager.addBubble(bubbleView, 60, 20);
    }

    private void initializeBubblesManager() {
        bubblesManager = new BubblesManager.Builder(this)
                .setTrashLayout(R.layout.bubble_trash_layout)
                .setInitializationCallback(new OnInitializedCallback() {
                    @Override
                    public void onInitialized() {
//                        addNewBubble();
                    }
                })
                .build();
        bubblesManager.initialize();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bubblesManager.recycle();
    }
}
