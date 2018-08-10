package net.simplifiedlearning.firebaseauth;

import android.content.SharedPreferences;

public class FirebaseUserModel {

    String deviceId = "";
    String deviceToken = "";
    String username = "";

    public FirebaseUserModel() {
    }

    public FirebaseUserModel(String deviceId, String deviceToken, String username) {
        this.deviceId = deviceId;
        this.deviceToken = deviceToken;
        this.username = username;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
