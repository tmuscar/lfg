package edu.fsu.cs.mysql_test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;

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
import edu.fsu.cs.mysql_test.utility.DatabaseAccess;


public class    MainActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    boolean formerror = false;
    RelativeLayout rellay1, rellay2;
    final String TAG = "LFGDB";

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication first = MyApplication.getInstance();
        first.setRequestid(null);
        first.setUserid(null);
        first.setGame(null);
        first.setEvents(null,0);
        first.setFriendid(null);
        first.setFriends(null,0);
        first.setFriendUsername(null);
        first.setInvite(null,0);
        first.setQuest(null);


        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);

        handler.postDelayed(runnable,3000);

        etUsername = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
    }

    // Assuming we do login... Go to page tabs!


    public void OnForgot(View view) {
        startActivity(new Intent(this,ForgotPassword.class));
    }

    public void OnLogin(View view){

        String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();
        String type = "login";

        formerror = false;

        if(etPassword.getText().toString().length() < 8){
            formerror = true;
            etPassword.setError("Password Must be at least 8 Characters");
        }
        if(etUsername.getText().toString().length() < 4){
            formerror = true;
            etUsername.setError("Username Must be at least 4 Characters");
        }
//
//        final AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
//            @Override
//            public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
//                Log.i(TAG, "Login successful");
//            }
//
//            @Override
//            public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
//                AuthenticationDetails authenticationDetails = new AuthenticationDetails(userId, password, null);
//
//                authenticationContinuation.setAuthenticationDetails(authenticationDetails);
//
//                authenticationContinuation.continueTask();
//            }
//
//            @Override
//            public void getMFACode(MultiFactorAuthenticationContinuation continuation) {
//
//            }
//
//            @Override
//            public void authenticationChallenge(ChallengeContinuation continuation) {
//
//            }
//
//            @Override
//            public void onFailure(Exception exception) {
//                Log.i(TAG, "Login failed" + exception.getLocalizedMessage());
//            }
//        };
//
//        CognitoSettings cognitoSettings = new CognitoSettings(MainActivity.this);
//
//        CognitoUser thisUser = cognitoSettings.getUserPool().getUser(username);
//
//        thisUser.getSessionInBackground(authenticationHandler);

        /**
         * Starts the background process to access database
         */

        if(formerror){
            Toast.makeText(MainActivity.this, "Error in form", Toast.LENGTH_LONG).show();
        } else {
            Login login = new Login(this);
            login.execute(type,username,password);
        }
    }

    // Page "Register Button"  brings you to.
    public void OpenReg(View view) {
        startActivity(new Intent(this,Register.class));
    }


    private class Login extends AsyncTask<String, Void, String> {


        Context context;
        AlertDialog.Builder alertDialog;

        Login (Context ctx){
            context = ctx;
        }
        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String result = null;
            Boolean isSuccess = false;
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(MainActivity.this);
            String newContact = "{'phone':'7272829973','user':{'email':'sarahmuscarella4@gmail.com','firstname':'Sarah','lastname':'Muscarella'}}";
            Document doc = Document.fromJson(newContact);
            try {
                isSuccess = databaseAccess.createContact(doc);
            }catch(Exception e){

            }

            /**
             * Stores address of php file to access database
             */
//            String login_url = "http://192.168.1.13/login.php";
//            try {
//
//                /**
//                 * Stores user entered inputs for username and passord
//                 */
//                String user_name = params[1];
//                String password = params[2];
//
//                /**
//                 * Accesses database with HttpURLConnection by passes in the two user
//                 * entered arguments and stores the returned values from the database
//                 */
//                URL url = new URL(login_url);
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                OutputStream outputStream = httpURLConnection.getOutputStream();
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
//                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
//                bufferedWriter.write(post_data);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                outputStream.close();
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
//                result = "";
//                String line = "";
//
//
//                 //Stores the results
//
//                while ((line = bufferedReader.readLine()) != null) {
//                    result += line;
//                }
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//
//                 // Sets the global UserID variable for future use
//
//                MyApplication g = (MyApplication)getApplication();
//                g.setUserid(result);
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            MyApplication k = (MyApplication)getApplication();
//            String userid = k.getUserid();
//
//            String[] result3 = new String[100];
//
//            String register_url = "http://127.0.0.1/get_events.php";
//            try {
//                URL url = new URL(register_url);
//                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                OutputStream outputStream = httpURLConnection.getOutputStream();
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                String post_data = URLEncoder.encode("user_id", "UTF-8")+"="+URLEncoder.encode(userid, "UTF-8");
//                bufferedWriter.write(post_data);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                outputStream.close();
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
//                //result[] = "";
//                String line = "";
//                int i =0;
//                while ((line = bufferedReader.readLine()) != null) {
//                    result3[i] = "";
//                    result3[i] = line;
//                    i++;
//                }
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//                MyApplication t = MyApplication.getInstance();
//                t.setEvents(result3, i);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//            String[] result2 = new String[50];
//            String friends_url = "http://10.0.2.2/friends.php";
//            try {
//                URL url = new URL(friends_url);
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                OutputStream outputStream = httpURLConnection.getOutputStream();
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                String post_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8");
//                bufferedWriter.write(post_data);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                outputStream.close();
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
//                //result = "";
//                String line = "";
//                int i =0;
//                int count = 0;
//                while ((line = bufferedReader.readLine()) != null) {
//                    result2[i] = "";
//                    result2[i] = line;
//                    i++;
//                }
//                count = i;
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//                MyApplication f = (MyApplication)getApplication();
//                f.setFriends(result2,count);
//                return result;
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            System.out.println(isSuccess);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {


             //If statement to see if successful login
            View rootView = LayoutInflater.from(context).inflate(R.layout.confirm_dialog,null);
            if(!result.equals("login failure")) {
                alertDialog = new AlertDialog.Builder(context, R.style.MyDialogTheme);
                alertDialog.setTitle("Login Status").setView(rootView);



                Intent intent = new Intent(context, Tabs.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
            if(result.equals("login failure")){
                alertDialog = new AlertDialog.Builder(context, R.style.MyDialogTheme);
                alertDialog.setTitle("Login Failed").setView(rootView);
                alertDialog.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       /* Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);*/
                    }
                });
                alertDialog.show();
            }
        }
    }
}


