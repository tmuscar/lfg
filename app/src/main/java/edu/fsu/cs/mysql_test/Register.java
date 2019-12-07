package edu.fsu.cs.mysql_test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import edu.fsu.cs.mysql_test.utility.CognitoSettings;

public class Register extends AppCompatActivity {
    EditText first_name, last_name, phone, email, password, username;

    String str_first_name, str_last_name, str_phone, str_email, str_password, str_username;
    boolean formerror = false;
    final String TAG = "LFGDB";
    SignUpHandler signupCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        first_name = findViewById(R.id.rFirstName);
        last_name = findViewById(R.id.rLastName);
        phone = findViewById(R.id.rPhone);
        email = findViewById(R.id.rEmail);
        password = findViewById(R.id.rPassword);
        username = findViewById(R.id.rUsername);

        final CognitoUserAttributes userAttributes = new CognitoUserAttributes();

        signupCallback = new SignUpHandler() {
            @Override
            public void onSuccess(CognitoUser user, boolean signUpConfirmationState, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
                Log.i(TAG, "sign up success...is confirmed: " + signUpConfirmationState);

                if(!signUpConfirmationState){
                    Log.i(TAG, "sign up success...not confirmed, verification code sent to: " + cognitoUserCodeDeliveryDetails.getDestination());
                }
                else{
                    Log.i(TAG, "sign up success...confirmed");
                }
            }

            @Override
            public void onFailure(Exception exception) {
                Log.i(TAG, "sign up failure: " + exception.getLocalizedMessage());
            }
        };

    }

    public void OnReg(View view) {
        str_first_name = first_name.getText().toString();
        str_last_name = last_name.getText().toString();
        str_phone = phone.getText().toString();
        str_email = email.getText().toString();
        str_username = username.getText().toString();
        str_password = password.getText().toString();
        String type = "register";
        /*BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,str_first_name,str_last_name,str_phone,str_email,str_username,str_password);
        */

        final CognitoUserAttributes userAttributes = new CognitoUserAttributes();
        formerror = false;

        if(first_name.getText().toString().matches("")){
            formerror = true;
            first_name.setError("Missing First Name");
        }
        if(last_name.getText().toString().matches("")){
            formerror = true;
            last_name.setError("Missing Last Name");
        }
        if(phone.getText().toString().matches("")){
            formerror = true;
            phone.setError("Missing Phone Number");
        }
        if(email.getText().toString().matches("")){
            formerror = true;
            email.setError("Missing Email Address");
        }
        if(username.getText().toString().matches("")){
            formerror = true;
            username.setError("Missing Username");
        }
        if(password.getText().toString().matches("")){
            formerror = true;
            password.setError("Missing Password");
        }
        if(password.getText().toString().length() < 8){
            formerror = true;
            password.setError("Password Must be at least 8 Characters");
        }
        if(username.getText().toString().length() < 4){
            formerror = true;
            username.setError("Username Must be at least 4 Characters");
        }

        userAttributes.addAttribute("name", str_first_name);
        userAttributes.addAttribute("phone_number", str_phone);
        userAttributes.addAttribute("email", str_email);

        CognitoSettings cognitoSettings = new CognitoSettings(Register.this);

        cognitoSettings.getUserPool().signUpInBackground(str_username, str_password, userAttributes, null, signupCallback);

//        if(formerror){
//            Toast.makeText(Register.this, "Error in form", Toast.LENGTH_LONG).show();
//        } else {
//            NewUser newUser = new NewUser(this);
//            newUser.execute(type, str_first_name, str_last_name, str_phone, str_email, str_username, str_password);
//        }
    }

    private class NewUser extends AsyncTask<String, Void, String> {

        Context context;
        AlertDialog.Builder alertDialog;

        NewUser (Context ctx){
            context = ctx;
        }
        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String result;



            String first_name = params[1];
            String last_name = params[2];
            String phone = params[3];
            String email = params[4];
            String user_name = params[5];
            String password = params[6];

            /**
             * Stores address of php file to access database and receive results from the SQL call
             */
//            String register_url = "http://10.0.2.2/register.php";
//            try {
//                String first_name = params[1];
//                String last_name = params[2];
//                String phone = params[3];
//                String email = params[4];
//                String user_name = params[5];
//                String password = params[6];
//                URL url = new URL(register_url);
//                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                System.out.println("Here");
//                OutputStream outputStream = httpURLConnection.getOutputStream();
//                System.out.println("After");
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                String post_data = "&" + URLEncoder.encode("first_name", "UTF-8")+"="+URLEncoder.encode(first_name, "UTF-8")+"&"
//                        +URLEncoder.encode("last_name", "UTF-8")+"="+URLEncoder.encode(last_name, "UTF-8")+"&"
//                        +URLEncoder.encode("phone", "UTF-8")+"="+URLEncoder.encode(phone, "UTF-8")+"&"
//                        +URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(email, "UTF-8")+"&"
//                        +URLEncoder.encode("user_name", "UTF-8")+"="+URLEncoder.encode(user_name, "UTF-8")+"&"
//                        +URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8");
//                bufferedWriter.write(post_data);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                outputStream.close();
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
//                result = "";
//                String line = "";
//                while((line = bufferedReader.readLine()) != null) {
//                    result += line;
//                }
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//                return result;
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            View rootView = LayoutInflater.from(context).inflate(R.layout.confirm_dialog,null);
            alertDialog = new AlertDialog.Builder(context, R.style.MyDialogTheme);
            alertDialog.setTitle("Register Status").setView(rootView);
            alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
            });
            alertDialog.setNegativeButton("Modify", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            alertDialog.show();
        }
    }

    public void OnReset(View view){
        first_name.setText("");
        last_name.setText("");
        phone.setText("");
        email.setText("");
        password.setText("");
        username.setText("");
    }
}
