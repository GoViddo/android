package blockchainvideoapp.com.goviddo.goviddo.coreclass;

import android.net.Uri;

public class CommentsRecyclerModel {
    int comment_id,userid;
    String comment,Username,profilepic;

    public CommentsRecyclerModel(int comment_id, int userid, String comment ,String profilepic,String Username) {
        this.comment_id = comment_id;
        this.userid = userid;
        this.comment = comment;
        this.profilepic = profilepic;
        this.Username = Username;

    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

