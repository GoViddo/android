package blockchainvideoapp.com.goviddo.goviddo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import blockchainvideoapp.com.goviddo.goviddo.R;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.LoginUserDetails;

public class MainActivity extends AppCompatActivity {



    ImageView mImageViewLoginLogo;
    android.support.design.widget.TextInputLayout mTextInputLayoutUserName,mTextInputLayoutUserPassword;
    android.support.design.widget.TextInputEditText mEdtUserName,mEdtUserPassword;
    Button mBtnLogin;
    String mUserName, mPassword;
    Vibrator mVibrator;
    LoginUserDetails mLoginUserDetails;

    TextView mTextViewNewUserRegistration;


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyStoragePermissions(this);


        mLoginUserDetails = new LoginUserDetails(this);


        if (mLoginUserDetails.getEmail() != "")
        {
            Intent loginIntent = new Intent(MainActivity.this,HomeActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);
            finish();
        }





        mImageViewLoginLogo = findViewById(R.id.imageViewLoginLogo);

        mTextInputLayoutUserName = findViewById(R.id.textInputLayoutUserName);
        mTextInputLayoutUserPassword = findViewById(R.id.textInputLayoutUserPassword);

        mEdtUserName = findViewById(R.id.edtUserName);
        mEdtUserPassword = findViewById(R.id.edtUserPassword);

        mBtnLogin = findViewById(R.id.btnLogin);

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        mTextViewNewUserRegistration = findViewById(R.id.textRegisterNewUser);



        mTextViewNewUserRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registrationIntent = new Intent(MainActivity.this,RegistrationActivity.class);
                startActivity(registrationIntent);

            }
        });




        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mUserName = mEdtUserName.getText().toString();
                mPassword = mEdtUserPassword.getText().toString();

                submitForm(mUserName,mPassword);


            }
        });




    }



    private boolean checkUserName(String userName) {

        userName = userName.trim();

        if (userName.trim().isEmpty() || !isValidEmail(userName)){
            mTextInputLayoutUserName.setErrorEnabled(true);
            mTextInputLayoutUserName.setError("Please Enter Valid Email ID");
            return false;
        }
        mTextInputLayoutUserName.setErrorEnabled(false);
        return true;
    }

    private boolean isValidEmail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private boolean checkPassword(String password) {
        if (password.trim().isEmpty() || (password.length() < 7)){
            mTextInputLayoutUserPassword.setErrorEnabled(true);
            mTextInputLayoutUserPassword.setError("Please Enter Password length greater than 6");
            return false;
        }
        mTextInputLayoutUserPassword.setErrorEnabled(false);
        return true;

    }






    public void submitForm(String userName, String password) {

        if (!checkUserName( userName )) {
            mVibrator.vibrate( 1000 );
            return;
        } else if (!checkPassword( password )) {
            mVibrator.vibrate( 1000 );
            return;

        } else {
            mTextInputLayoutUserName.setErrorEnabled( false );
            mTextInputLayoutUserPassword.setErrorEnabled( false );




            JSONObject params = new JSONObject();
            try {


                params.put( "email", userName );
                params.put( "password", password );


            } catch (JSONException e) {
                e.printStackTrace();

            }


            RequestQueue requestQueue = Volley.newRequestQueue( MainActivity.this );

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.POST, "http://178.128.173.51:3000/login", params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        if (response.getString( "message" ).equalsIgnoreCase( "Login success" )) {

                            mLoginUserDetails.setEmail( response.getString( "email" ) );
                            mLoginUserDetails.setWalletName( response.getString( "walletName" ) );


                            Intent loginIntent = new Intent( MainActivity.this, HomeActivity.class );
                            loginIntent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                            startActivity( loginIntent );
                            finish();

                        } else {
                            Toast.makeText( MainActivity.this, response.getString( "message" ), Toast.LENGTH_SHORT ).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText( MainActivity.this, "Please check login details", Toast.LENGTH_SHORT ).show();

                }
            } ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put( "Content-Type", "application/json" );
                    return headers;
                }
            };

            requestQueue.add( jsonObjectRequest );


        }


    }

}

