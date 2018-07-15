package net.simplifiedlearning.firebaseauth.chat_messege_of_people;

public class MessageOfFriends {

    private String linkAvatarImageSender;
    private String senderName;
    private String newestMessage;
    private String idSender;
    private String idReceiver;

    public MessageOfFriends() {
    }


    public MessageOfFriends(String linkAvatarImageSender, String senderName, String newestMessage,
                            String idSender, String idReceiver) {
        this.linkAvatarImageSender = linkAvatarImageSender;
        this.senderName = senderName;
        this.newestMessage = newestMessage;
        this.idSender = idSender;
        this.idReceiver = idReceiver;

    }

    public String getLinkAvatarImageSender() {
        return linkAvatarImageSender;
    }

    public void setLinkAvatarImageSender(String linkAvatarImageSender) {
        this.linkAvatarImageSender = linkAvatarImageSender;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getNewestMessage() {
        return newestMessage;
    }

    public void setNewestMessage(String newestMessage) {
        this.newestMessage = newestMessage;
    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(String idReceiver) {
        this.idReceiver = idReceiver;
    }
}
