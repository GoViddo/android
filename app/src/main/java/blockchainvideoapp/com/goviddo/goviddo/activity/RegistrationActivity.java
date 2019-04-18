package blockchainvideoapp.com.goviddo.goviddo.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.Settings;
import android.service.autofill.RegexValidator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import blockchainvideoapp.com.goviddo.goviddo.R;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.LoginUserDetails;

import static android.app.PendingIntent.getActivity;
import static android.view.View.GONE;
import static com.android.volley.Request.Method.HEAD;

public class RegistrationActivity extends AppCompatActivity {

    android.support.design.widget.TextInputLayout mTextInputLayoutUserName,mTextInputLayoutUserPassword, mTextInputLayoutUserConfirmPassword,mTextInputLayoutUseFirstName,mTextInputLayoutUserLastName,mTextInputLayoutUserWalletName;
    android.support.design.widget.TextInputEditText mEdtUserName,mEdtUserPassword, mEdtUserConfirmPassword,mEdtUserFirstName,mEdtUserLastName,mEdtUserWalletName;
    Button mBtnSignup;
    String mUserName, mPassword, mConfirmPassword,mUserFirstName,mUserLastName,mUserWalletName;
    Vibrator mVibrator;
    LoginUserDetails mLoginUserDetails;

    TextView mTextViewNewUserRegistration;
    ProgressWheel mProgressWheelPreview;
    ProgressDialog progressDialog;


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static boolean verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);


        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );

            return false;

        }
        else{
            return true;
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        mProgressWheelPreview =  findViewById(R.id.progress_wheel);

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);


        if (permission != PackageManager.PERMISSION_GRANTED) {


            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("GoViddo is committed to protecting and respecting your privacy, In order to provide you our services and to access your wallet, your wallet keys should be stored in your device. We never store your keys with us. \n" +
                    "\n" +
                    "\n We need your consent to us storing your keys in your device, please Allow the Permission.");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Open Settings",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            verifyStoragePermissions(RegistrationActivity.this);
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();

            Button positiveButton = alert11.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.parseColor("#FF0000"));



        }







        verifyStoragePermissions(this);

        mLoginUserDetails = new LoginUserDetails(this);

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        if (mLoginUserDetails.getEmail() != "")
        {
            Intent loginIntent = new Intent(RegistrationActivity.this,HomeActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);
            finish();
        }

        mTextInputLayoutUserName = findViewById(R.id.textInputLayoutUserNameRegistration);
        mTextInputLayoutUserPassword = findViewById(R.id.textInputLayoutUserPasswordRegistration);
        mTextInputLayoutUserConfirmPassword = findViewById(R.id.textInputLayoutUserReenterPasswordRegistration);
        mTextInputLayoutUseFirstName = findViewById(R.id.textInputLayoutUserFirstName);
        mTextInputLayoutUserLastName = findViewById(R.id.textInputLayoutUserLastName);
        mTextInputLayoutUserWalletName = findViewById(R.id.textInputLayoutUserWalletName);

        mEdtUserName = findViewById(R.id.edtUserNameRegistration);
        mEdtUserPassword = findViewById(R.id.edtUserPasswordRegistration);
        mEdtUserConfirmPassword = findViewById(R.id.edtUserReenterPasswordRegistration);
        mEdtUserFirstName = findViewById(R.id.edtUserFirstName);
        mEdtUserLastName = findViewById(R.id.edtUserLastName);
        mEdtUserWalletName = findViewById(R.id.edtUserWalletName);

        progressDialog = new ProgressDialog(RegistrationActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);



        mBtnSignup = findViewById(R.id.btnSignUp);

        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mUserName = mEdtUserName.getText().toString();
                mPassword = mEdtUserPassword.getText().toString();
                mConfirmPassword = mEdtUserConfirmPassword.getText().toString();
                mUserFirstName = mEdtUserFirstName.getText().toString();
                mUserLastName = mEdtUserLastName.getText().toString();
                mUserWalletName = mEdtUserWalletName.getText().toString();


                if (verifyStoragePermissions(RegistrationActivity.this)) {
                    submitForm(mUserFirstName, mUserLastName, mUserWalletName, mUserName, mPassword, mConfirmPassword);
                }
                else{
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegistrationActivity.this);
                    alertDialogBuilder.setMessage("Please Allow storage permission we need this permission to store your wallet keys without that you will not recived any rewards...");
                            alertDialogBuilder.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            verifyStoragePermissions(RegistrationActivity.this);
                                        }
                                    });


                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }

            }
        });
        }


    private boolean checkFirstName(String userName) {

        userName = userName.trim();

        if(userName.trim().isEmpty() || (userName.length() <= 1)){
            mTextInputLayoutUseFirstName.setErrorEnabled( true );
            mTextInputLayoutUseFirstName.setError( "Please Enter Valid First Name" );
            return false;
        }
        mTextInputLayoutUseFirstName.setErrorEnabled( false );
        return true;
    }


    private boolean checkLastName(String userName) {

        userName = userName.trim();

        if(userName.trim().isEmpty() || (userName.length() <= 1)){
            mTextInputLayoutUserLastName.setErrorEnabled( true );
            mTextInputLayoutUserLastName.setError( "Please Enter Valid Last Name" );
            return false;
        }
        mTextInputLayoutUserLastName.setErrorEnabled( false );
        return true;
    }


    private boolean checkWalletName(String userName) {

        userName = userName.trim();

        if(userName.trim().isEmpty() || !isValidWalletName(userName)){
            mTextInputLayoutUserWalletName.setErrorEnabled( true );
            mTextInputLayoutUserWalletName.setError( "Please Enter Valid Wallet Name" );
            return false;
        }
        mTextInputLayoutUserWalletName.setErrorEnabled( false );
        return true;
    }

    private boolean isValidWalletName(String userName) {

        String pattern = "(?=.*[0-9])(?=.*[a-z]).{12,12}";
        String pattern1 = "(?=.*[a-z]).{12,12}";

        if(userName.matches( pattern ) || userName.matches( pattern1 ))
        {
            return true;
        }
        else{
            return false;
        }

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

    private boolean chkPass(String pass)
    {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}";

       return pass.matches(pattern);

    }

    private boolean checkPassword(String password) {
        if (password.trim().isEmpty() || (password.length() < 7) ){
                mTextInputLayoutUserPassword.setErrorEnabled(true);
                mTextInputLayoutUserPassword.setError("Please enter password length greater than 6");
                return false;
        }
        mTextInputLayoutUserPassword.setErrorEnabled(false);
        return true;

    }

    private boolean checkConfirmPassword(String confirmPassword, String password) {
        if (confirmPassword.trim().isEmpty() || (confirmPassword.length() < 7) ){
            mTextInputLayoutUserConfirmPassword.setErrorEnabled(true);
            mTextInputLayoutUserConfirmPassword.setError("The password does not match, please enter again.");
            return false;
        }
        else {

            if(!confirmPassword.equals(password))
            {
                mTextInputLayoutUserConfirmPassword.setErrorEnabled(true);
                mTextInputLayoutUserConfirmPassword.setError("The password does not match, please enter again.");
                return false;
            }
            else {
                mTextInputLayoutUserConfirmPassword.setErrorEnabled(false);
                return true;
            }
        }

    }


    public void submitForm(String firstname,String lastname,String walletName,String userName, String password, String confirmPassword)
    {

        if (!checkFirstName(firstname)){
        mVibrator.vibrate(1000);
        return;

    }
    else if (!checkLastName(lastname)){
        mVibrator.vibrate(1000);
        return;

    }
    else if (!checkWalletName(walletName)){
        mVibrator.vibrate(1000);
        return;

    }
     else if (!checkUserName(userName)){
            mVibrator.vibrate(1000);
            return;
        }
        else if (!checkPassword(password)){
            mVibrator.vibrate(1000);
            return;

        }
        else if (!checkConfirmPassword(confirmPassword, password)){
            mVibrator.vibrate(1000);
            return;

        }
        else {
            mTextInputLayoutUserName.setErrorEnabled(false);
            mTextInputLayoutUserPassword.setErrorEnabled(false);
            mTextInputLayoutUserConfirmPassword.setErrorEnabled(false);
            mTextInputLayoutUseFirstName.setErrorEnabled(false);
            mTextInputLayoutUserLastName.setErrorEnabled(false);
            mTextInputLayoutUserWalletName.setErrorEnabled(false);

            walletName = walletName.trim();
            progressDialog.show();
            JSONObject params = new JSONObject();
            try {

                params.put("email",userName);
                params.put("password", password);
                params.put("firstName",firstname);
                params.put("lastName",lastname);
                params.put("walletName", walletName);


            } catch (JSONException e) {
                e.printStackTrace();

            }

            System.out.println(params.toString());



            final RequestQueue requestQueue = Volley.newRequestQueue(RegistrationActivity.this);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://178.128.173.51:3000/register", params, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {




                        try {
                            if (response.getString("message").equals("Registration successful")) {

                                JSONArray jsActiveKeys = response.getJSONArray( "activeKeys" );
                                JSONObject jsActiveKeysObject =  jsActiveKeys.getJSONObject( 0 );

                                String activePrivateKey = jsActiveKeysObject.getString( "activePrivateKey" );
                                String activePublicKey = jsActiveKeysObject.getString( "activePublicKey" );

                                JSONArray jsOwnerKeys = response.getJSONArray( "ownerKeys" );
                                JSONObject jsOwnerKeysObject =  jsOwnerKeys.getJSONObject( 0 );


                                String ownerPrivateKey = jsOwnerKeysObject.getString( "ownerPrivateKey" );
                                String ownerPublicKey = jsOwnerKeysObject.getString( "ownerPublicKey" );

                                System.out.println(ownerPublicKey + "\n"+ ownerPrivateKey+"\n"+activePublicKey+"\n"+activePrivateKey);

                                showCustomDialog(activePrivateKey, activePublicKey, ownerPrivateKey, ownerPublicKey,RegistrationActivity.this);


                            }
                            else {
                                System.out.println(response.toString());
                                Toast.makeText(RegistrationActivity.this,"User with this email already Exist",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(RegistrationActivity.this, "Please Check Credentials", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            requestQueue.add(jsonObjectRequest);

        }


    }



    private void showCustomDialog(String activePrivateKey, String activePublicKey, String ownerPrivateKey, String ownerPublicKey, final Context context) {

        final String displayActivePrivateKey = activePrivateKey;
        final String displayActivePublicKey = activePublicKey;
        final String displayOwnerPrivateKey = ownerPrivateKey;
        final String displayOwnerPublicKey = ownerPublicKey;


        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.alert_dialog_box, viewGroup, false);


        TextView textViewDisplayActivePublicKey = dialogView.findViewById(R.id.txtpublickey);
        textViewDisplayActivePublicKey.setText(displayActivePublicKey);

        TextView textViewDisplayActivePrivateKey = dialogView.findViewById(R.id.txtprivatekey);
        textViewDisplayActivePrivateKey.setText(displayActivePrivateKey);

        TextView textViewDisplayOwnerPublicKey = dialogView.findViewById(R.id.txtownerpublickey);
        textViewDisplayOwnerPublicKey.setText(displayOwnerPublicKey);

        TextView textViewDisplayOwnerPrivateKey = dialogView.findViewById(R.id.txtownerprivatekey);
        textViewDisplayOwnerPrivateKey.setText(displayOwnerPrivateKey);

        Button btnSaveKeys = dialogView.findViewById(R.id.btnSaveKeys);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();

        btnSaveKeys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int permission = ActivityCompat.checkSelfPermission(RegistrationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                long millisStart = Calendar.getInstance().getTimeInMillis();

                String data = "Public Keys - \n Active Public Key : \t" + displayActivePublicKey + "\n Active Private Key : \t" + displayActivePrivateKey + "\n \n \n \nOwner Key - \n Owner Public Key : \t"+displayOwnerPublicKey+"\n Owner Private Key : \t"+displayOwnerPrivateKey ;


                if (permission != PackageManager.PERMISSION_GRANTED) {

                    File path = context.getDir("mydir", Context.MODE_PRIVATE);
                    System.out.println(path);


                    File file = new File(path, "EOSWalletKeys_"+millisStart+".txt");


                    FileOutputStream stream = null;
                    try {
                        stream = new FileOutputStream(file);
                        stream.write(data.getBytes());
                        stream.close();
                        alertDialog.cancel();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                else {
                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                    System.out.println(path);
                    File file = new File(path, "EOSWalletKeys_"+millisStart+".txt");


                    FileOutputStream stream = null;
                    try {
                        stream = new FileOutputStream(file);
                        stream.write(data.getBytes());
                        stream.close();
                        alertDialog.cancel();

                        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }




                }

            }
        });



        alertDialog.show();
        progressDialog.dismiss();


        mProgressWheelPreview.setVisibility(GONE);
    }


}
