package net.simplifiedlearning.firebaseauth;

import android.content.SharedPreferences;

import java.io.Serializable;

public class User implements Serializable {

    private static final User user = new User();

    public static User getInstance() {
        return user;
    }

//    public String firebaseKey = "firebaseKey";
//    public String name = "Owner";
//    public String deviceId = "";
//    public String deviceToken = "";
//
//    public static final String appPreferences = "ChattingAppPreferences" ;
//
//    public static final String Key  = "keyKey";
//    public static final String Name = "nameKey";
//    public static final String DeviceToken = "deviceTokenKey";
//    public static final String DeviceId = "deviceIdKey";
//    public SharedPreferences sharedpreferences;


//    public Boolean login(FirebaseUserModel firebaseUserModel) {
//        name = firebaseUserModel.getUsername();
//        deviceId = firebaseUserModel.getDeviceId();
//        deviceToken = firebaseUserModel.getDeviceToken();
//
//        SharedPreferences.Editor editor = sharedpreferences.edit();
////        editor.putString(Key, firebaseKey);
//        editor.putString(Name, name);
//        editor.putString(DeviceId, deviceId);
//
//        editor.apply();
//
//        return true;
//    }

//    public void saveFirebaseKey(String key) {
////        this.firebaseKey = key;
//
//        SharedPreferences.Editor editor = sharedpreferences.edit();
////        editor.putString(Key, firebaseKey);
//        editor.apply();
//    }

//    public void logout() {
////        firebaseKey = "";
//        name = "";
//        deviceId = "";
//        deviceToken = "";
//
//        SharedPreferences.Editor editor = sharedpreferences.edit();
////        editor.putString(Key, firebaseKey);
//        editor.putString(Name, name);
//        editor.putString(DeviceId, deviceId);
//
//        editor.apply();
//    }


    private String idUser;
    private String emailUser;
    private String nicknameUser;
    private String linkAvatarUser;
    private String birthDayUser;
    private String genderUser;
    private String phoneNumberUser;
    private String addressUser;

    public User() {

    }

    public User(String idUser, String emailUser, String nicknameUser,
                String linkAvatarUser, String birthDayUser, String genderUser,
                String phoneNumberUser, String addressUser) {

        this.idUser = idUser;
        this.emailUser = emailUser;
        this.nicknameUser = nicknameUser;
        this.linkAvatarUser = linkAvatarUser;
        this.birthDayUser = birthDayUser;
        this.genderUser = genderUser;
        this.phoneNumberUser = phoneNumberUser;
        this.addressUser = addressUser;
    }




    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getNicknameUser() {
        return nicknameUser;
    }

    public void setNicknameUser(String nicknameUser) {
        this.nicknameUser = nicknameUser;
    }

    public String getLinkAvatarUser() {
        return linkAvatarUser;
    }

    public void setLinkAvatarUser(String linkAvatarUser) {
        this.linkAvatarUser = linkAvatarUser;
    }

    public String getBirthDayUser() {
        return birthDayUser;
    }

    public void setBirthDayUser(String birthDayUser) {
        this.birthDayUser = birthDayUser;
    }

    public String getGenderUser() {
        return genderUser;
    }

    public void setGenderUser(String genderUser) {
        this.genderUser = genderUser;
    }

    public String getPhoneNumberUser() {
        return phoneNumberUser;
    }

    public void setPhoneNumberUser(String phoneNumberUser) {
        this.phoneNumberUser = phoneNumberUser;
    }

    public String getAddressUser() {
        return addressUser;
    }

    public void setAddressUser(String addressUser) {
        this.addressUser = addressUser;
    }
}
