package blockchainvideoapp.com.goviddo.goviddo.coreclass;

import android.content.Context;
import android.content.SharedPreferences;

import blockchainvideoapp.com.goviddo.goviddo.Fragments.TransactionFragment;

public class LoginUserDetails {

    SharedPreferences mSharedPreferences;
    String mUserName,mUserPassword,mUserFirstName,mUserLastName,mUSerWalletName;
    Context mContext;



    public LoginUserDetails(TransactionFragment context) {
        this.mContext = context;
        mSharedPreferences = mContext.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    }



    public void removeUserInfo(){
        mSharedPreferences.edit().clear().commit();
    }


    public String getFirstName() {
        mUserFirstName = mSharedPreferences.getString("First Name","");
        return mUserFirstName;
    }

    public void setFirstName(String Firstname) {
        this.mUserFirstName = Firstname;
        mSharedPreferences.edit().putString("First Name",mUserFirstName).commit();
    }

    public String getLastName() {
        mUserLastName = mSharedPreferences.getString("email","");
        return mUserLastName;
    }

    public void setLastName(String LastName) {
        this.mUserLastName = LastName;
        mSharedPreferences.edit().putString("Last Name",mUserLastName).commit();
    }

    public String getWalletName() {
        mUSerWalletName = mSharedPreferences.getString("Wallet Name","");
        return mUSerWalletName;
    }

    public void setWalletName(String walletName) {
        this.mUSerWalletName = walletName;
        mSharedPreferences.edit().putString("Wallet Name",mUSerWalletName).commit();
    }


    public String getEmail() {
        mUserName = mSharedPreferences.getString("email","");
        return mUserName;
    }

    public void setEmail(String email) {
        this.mUserName = email;
        mSharedPreferences.edit().putString("email",mUserName).commit();
    }

    public String getPassword() {
        mUserPassword = mSharedPreferences.getString("password","");
        return mUserPassword;
    }

    public void setPassword(String password) {
        this.mUserPassword = password;
        mSharedPreferences.edit().putString("password",mUserName).commit();
    }


}
