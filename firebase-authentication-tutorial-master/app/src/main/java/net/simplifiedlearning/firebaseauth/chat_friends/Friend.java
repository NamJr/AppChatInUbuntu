package net.simplifiedlearning.firebaseauth.chat_friends;

public class Friend {
    private String linkAvatar;
    private String nickName;
    private String email;

    public Friend() {
    }

    public Friend(String linkAvatar, String nickName, String email) {
        this.linkAvatar = linkAvatar;
        this.nickName = nickName;
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLinkAvatar() {
        return linkAvatar;
    }

    public void setLinkAvatar(String linkAvatar) {
        this.linkAvatar = linkAvatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
