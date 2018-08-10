package net.simplifiedlearning.firebaseauth.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.txusballesteros.bubbles.BubbleLayout;
import com.txusballesteros.bubbles.BubblesManager;
import com.txusballesteros.bubbles.OnInitializedCallback;

import net.simplifiedlearning.firebaseauth.MessageListActivity;
import net.simplifiedlearning.firebaseauth.R;
import net.simplifiedlearning.firebaseauth.chat_head.ChatHeadActivity;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private BubblesManager bubblesManager;
    private DatabaseReference mData ;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        mData = FirebaseDatabase.getInstance().getReference();

        initializeBubblesManager();
        addNewBubble();




        //Calling method to show notification
        showNotification(remoteMessage.getNotification().getBody(),
                         remoteMessage.getNotification().getTitle()
        );

    }

    private void showNotification(String messageBody, String messageTitle) {
        Intent intent = new Intent(this, MessageListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

    private void initializeBubblesManager() {
        bubblesManager = new BubblesManager.Builder(this)
                .setTrashLayout(R.layout.bubble_trash_layout)
                .setInitializationCallback(new OnInitializedCallback() {
                    @Override
                    public void onInitialized() {
//                        addNewBubble();
                    }})
                .build();
        bubblesManager.initialize();

    }
    private void addNewBubble () {
        //        Picasso.with(ChatHeadActivity.this).load(avatarSender).into(avatar);
        BubbleLayout bubbleView = (BubbleLayout) LayoutInflater.from(MyFirebaseMessagingService.this).inflate(R.layout.bubble_layout, null);
        bubbleView.setOnBubbleRemoveListener(new BubbleLayout.OnBubbleRemoveListener() {
            @Override
            public void onBubbleRemoved(BubbleLayout bubble) {
                Toast.makeText(MyFirebaseMessagingService.this, "TAT", Toast.LENGTH_SHORT).show();
            }


        });
        bubbleView.setOnBubbleClickListener(new BubbleLayout.OnBubbleClickListener() {

            @Override
            public void onBubbleClick(BubbleLayout bubble) {
//                Intent intent = new Intent(MyFirebaseMessagingService.this, MessageListActivity.class);
//                intent.putExtra("idSenderFromMessageViewHolder", idCurrentUser);
//                intent.putExtra("idReceiverFromMessageViewHolder", idChosenUSer);
//                startActivity(intent);
//                finish();
            }
        });
        bubbleView.setShouldStickToWall(true);
        bubblesManager.addBubble(bubbleView, 60, 20);
    }
}
