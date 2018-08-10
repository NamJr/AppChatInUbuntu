package net.simplifiedlearning.firebaseauth;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import net.simplifiedlearning.firebaseauth.chat_messege_of_people.TabLayoutChatActivity;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class Profile2Activity extends AppCompatActivity {

    private TextView tvName;
    private TextView tvEmail;
    private TextView tvGender;
    private TextView tvBirthday;
    private TextView tvPhone;
    private TextView tvAddress;
    private ImageView imgAvatar;
    private Button btnSaveAvatar;
    private Button btnCancelSaveAvatar;
//    private FirebaseAuth mAuth;
    private DatabaseReference mData;
    boolean check  = false;
    private User currentUser = null;
    private Button btnGoToUpdate;
    private Button btnGoToChat;
    int REQUEST_CODE_IMAGE = 1;
    int RESULT_LOAD_IMAGE = 2;
    private Button btnUploadImageFromDevice;
    FirebaseStorage storage = FirebaseStorage.getInstance();

//    private String idUser = "";
    FirebaseUser firebaseUserUser = FirebaseAuth.getInstance().getCurrentUser();



    String currentDeviceId;
    FirebaseDatabase database;
    DatabaseReference usersRef;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);
//        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();
//        idUser = firebaseUserUser.getUid().toString();


        currentDeviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.i("kt",FirebaseInstanceId.getInstance().getToken());
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        final StorageReference storageRef = storage.getReference();

        usersRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                for (com.google.firebase.database.DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    //Getting the data from snapshot
                    FirebaseUserModel firebaseUserModel = userSnapshot.getValue(FirebaseUserModel.class);

                    if (firebaseUserModel.getDeviceId().equals(currentDeviceId)) {
                        firebaseUserModel.setDeviceToken(FirebaseInstanceId.getInstance().getToken());
//                        user.login(firebaseUserModel);
//                        user.saveFirebaseKey(userSnapshot.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        AnhXa();

        final String email = firebaseUserUser.getEmail();
        tvEmail.setText(email);
        loadData(email);

        final Intent intent = getIntent();
        final User user = (User) intent.getSerializableExtra("userUpdate");

        if (user!=null) {
            String name = user.getNicknameUser();
            String mail = user.getEmailUser();
            String gender = user.getGenderUser();
            String birthday = user.getBirthDayUser();
            String phone = user.getPhoneNumberUser();
            String address = user.getAddressUser();
            String link = user.getLinkAvatarUser();
            tvName.setText(name);
            tvGender.setText(gender);
            tvBirthday.setText(birthday);
            tvPhone.setText(phone);
            tvAddress.setText(address);
            if (!link.equals(""))
            Picasso.with(Profile2Activity.this).load(link).into(imgAvatar);
        }

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent1,REQUEST_CODE_IMAGE);
            }
        });


        btnUploadImageFromDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, RESULT_LOAD_IMAGE);
            }
        });

        btnSaveAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                StorageReference mountainsRef = storageRef.child("image"+calendar.getTimeInMillis()+"jpg");

                imgAvatar.setDrawingCacheEnabled(true);
                imgAvatar.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imgAvatar.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(Profile2Activity.this, "Failed to Upload Image Avatar!!!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(Profile2Activity.this, "Uploaded Image Avatar Successfully!!!", Toast.LENGTH_SHORT).show();
//                        Log.d("AAAA",downloadUrl+"");
                        String link = String.valueOf(downloadUrl).toString();

                        if (user!=null) {
                            mData.child("UserProfile").child(user.getIdUser()).child("linkAvatarUser").setValue(link);
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(user.getNicknameUser())
                                    .setPhotoUri(Uri.parse(link))
                                    .build();

                            firebaseUserUser.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("TAG", "User profile updated.");
                                            }
                                        }
                                    });
                        }else {
                            mData.child("UserProfile").child(currentUser.getIdUser()).child("linkAvatarUser").setValue(link);
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(currentUser.getNicknameUser())
                                    .setPhotoUri(Uri.parse(link))
                                    .build();

                            firebaseUserUser.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("TAG", "User profile updated.");
                                            }
                                        }
                                    });
                        }

                        currentUser.setLinkAvatarUser(link);
                        btnSaveAvatar.setVisibility(View.GONE);
                        btnCancelSaveAvatar.setVisibility(View.GONE);
                    }
                });
            }
        });

        btnCancelSaveAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = currentUser.getLinkAvatarUser();
                if (!link.equals(""))
                    Picasso.with(Profile2Activity.this).load(link).into(imgAvatar);
            }
        });



        btnGoToUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Profile2Activity.this, UpdateProfileActivity.class);
                if (currentUser==null){
                    currentUser = user;
                }
                intent1.putExtra("userProfile", currentUser);

                startActivity(intent1);
            }
        });

        btnGoToChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Profile2Activity.this, TabLayoutChatActivity.class);
                if (currentUser==null){
                    currentUser = user;
                }
//                final FirebaseUserModel firebaseUserModel = new FirebaseUserModel();
//                firebaseUserModel.setUsername(currentUser.getNicknameUser());
//                firebaseUserModel.setDeviceId(currentDeviceId);
//                firebaseUserModel.setDeviceToken(FirebaseInstanceId.getInstance().getToken());
//
//                final ProgressDialog Dialog = new ProgressDialog(Profile2Activity.this);
//                Dialog.setMessage("Please wait..");
//                Dialog.setCancelable(false);
//                Dialog.show();
//
//                final DatabaseReference newRef = usersRef.push();
//                newRef.setValue(firebaseUserModel, new DatabaseReference.CompletionListener() {
//                    @Override
//                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                        Dialog.dismiss();
////                        if (user.login(firebaseUserModel)) {
////                            Toast.makeText(Profile2Activity.this, "moveToChattingScreen()", Toast.LENGTH_SHORT).show();
////                        }
//                    }
//                });
                intent1.putExtra("userProfile", currentUser);
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data!=null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgAvatar.setImageBitmap(bitmap);
        }

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            imgAvatar.setImageURI(imageUri);

        }
        btnSaveAvatar.setVisibility(View.VISIBLE);
        btnCancelSaveAvatar.setVisibility(View.VISIBLE);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadData(final String email) {
        mData.child("UserProfile").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User getUser = dataSnapshot.getValue(User.class);
                if ( getUser.getEmailUser().equals(email)){
                    currentUser = getUser;
                    String name = currentUser.getNicknameUser();
                    String gender = currentUser.getGenderUser();
                    String birthday = currentUser.getBirthDayUser();
                    String phone = currentUser.getPhoneNumberUser();
                    String address = currentUser.getAddressUser();
                    String link = currentUser.getLinkAvatarUser();
                    if (!name.equals(""))
                        tvName.setText(name);
                    else
                        tvName.setText("Unavailable");
                    if (!gender.equals(""))
                        tvGender.setText(gender);
                    else
                        tvGender.setText("Unavailable");
                    if (!birthday.equals(""))
                        tvBirthday.setText(birthday);
                    else
                        tvBirthday.setText("Unavailable");
                    if (!phone.equals(""))
                        tvPhone.setText(phone);
                    else
                        tvPhone.setText("Unavailable");
                    if (!address.equals(""))
                    tvAddress.setText(address);
                    else
                        tvAddress.setText("Unavailable");
                    if (!link.equals(""))
                        Picasso.with(Profile2Activity.this).load(link).into(imgAvatar);
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

    private void AnhXa() {
        tvName = findViewById(R.id.tvNicknameProfile);
        tvEmail = findViewById(R.id.tvEmailProfile);
        tvGender = findViewById(R.id.tvGenderProfile);
        tvBirthday = findViewById(R.id.tvBirthdayProfile);
        tvPhone = findViewById(R.id.tvPhoneProfile);
        tvAddress = findViewById(R.id.tvAddressProfile);
        btnSaveAvatar = findViewById(R.id.buttonSaveAvatarProfile);
        btnCancelSaveAvatar = findViewById(R.id.buttonCancelSaveAvatarProfile);
        btnGoToUpdate = findViewById(R.id.btnGoToUpdate);
        btnGoToChat = findViewById(R.id.btnGoToChat);
        imgAvatar = findViewById(R.id.imageAvatarProfile);
        btnUploadImageFromDevice = findViewById(R.id.buttonUploadFromDevice);
    }

    public void showMessage(String strTitle, String strMessage) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(strTitle)
                .setMessage(strMessage)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }
}
