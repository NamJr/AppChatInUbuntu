package net.simplifiedlearning.firebaseauth;

public class FirebaseMessageModel extends Message{

    String id;
    String deviceId;
    String nameSender;

    public String getNameSender() {
        return nameSender;
    }

    public void setNameSender(String nameSender) {
        this.nameSender = nameSender;
    }

    public FirebaseMessageModel() {
    }

    public FirebaseMessageModel(String message, String idSender, String createdAt,
                                String id, String deviceId) {
        super(message, idSender, createdAt);
        this.id = id;
        this.deviceId = deviceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
