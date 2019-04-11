package blockchainvideoapp.com.goviddo.goviddo.coreclass;

public class LoginUserInfo {
    String mUserFirstName, mUserLastName, mUserEmailId, mUserEOSAccountName, mGeneder, mPhoneNo, mBirthDate, mAddress, mCountry, mUservideoChoice, mUserProfilePic, mNotificationtoken;
    int mUserId, mRegisterAsAdvisor, mRegisterAdProducer;


    public LoginUserInfo(String userFirstName, String userLastName, String userEmailId, String userEOSAccountName, String geneder, String phoneNo, String birthDate, String address, String country, String uservideoChoice, String userProfilePic, String notificationtoken, int userId, int registerAsAdvisor, int registerAdProducer) {
        this.mUserFirstName = userFirstName;
        this.mUserLastName = userLastName;
        this.mUserEmailId = userEmailId;
        this.mUserEOSAccountName = userEOSAccountName;
        this.mGeneder = geneder;
        this.mPhoneNo = phoneNo;
        this.mBirthDate = birthDate;
        this.mAddress = address;
        this.mCountry = country;
        this.mUservideoChoice = uservideoChoice;
        this.mUserProfilePic = userProfilePic;
        this.mNotificationtoken = notificationtoken;
        this.mUserId = userId;
        this.mRegisterAsAdvisor = registerAsAdvisor;
        this.mRegisterAdProducer = registerAdProducer;
    }


    public String getUserFirstName() {
        return mUserFirstName;
    }

    public String getUserLastName() {
        return mUserLastName;
    }

    public String getUserEmailId() {
        return mUserEmailId;
    }

    public String getUserEOSAccountName() {
        return mUserEOSAccountName;
    }

    public String getGeneder() {
        return mGeneder;
    }

    public String getPhoneNo() {
        return mPhoneNo;
    }

    public String getBirthDate() {
        return mBirthDate;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getCountry() {
        return mCountry;
    }

    public String getUservideoChoice() {
        return mUservideoChoice;
    }

    public String getUserProfilePic() {
        return mUserProfilePic;
    }

    public String getNotificationtoken() {
        return mNotificationtoken;
    }

    public int getUserId() {
        return mUserId;
    }

    public int getRegisterAsAdvisor() {
        return mRegisterAsAdvisor;
    }

    public int getRegisterAdProducer() {
        return mRegisterAdProducer;
    }
}
