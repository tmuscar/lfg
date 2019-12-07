package edu.fsu.cs.mysql_test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class FriendRequest extends AppCompatActivity {

    EditText username;
    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_request);
        button = (Button) findViewById(R.id.button);
        username = (EditText) findViewById(R.id.username);
    }

    public void SendRequest(View view){
        String str_username = username.getText().toString();
        FriendRequest.AddFriend friends = new FriendRequest.AddFriend(this);
        friends.execute(str_username);
    }

    private class AddFriend extends AsyncTask<String, Void, String> {

        Context context;
        AlertDialog.Builder alertDialog;

        AddFriend (Context ctx){
            context = ctx;
        }
        @Override
        protected String doInBackground(String... params) {
            MyApplication g = (MyApplication)getApplication();
            String user_id = g.getUserid();
            String friend_id = null;
            String[] result = new String[50];
            String add_request_url = "http://10.0.2.2/add_request.php";
            String get_friend_url = "http://10.0.2.2/get_friend_request.php";
            try {
                String username = params[0];
                URL url = new URL(get_friend_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                //result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    friend_id = "";
                    friend_id = line;
                }
                //count = i;
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                //setMatch(result);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                URL url2 = new URL(add_request_url);
                HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2.openConnection();
                httpURLConnection2.setRequestMethod("POST");
                httpURLConnection2.setDoOutput(true);
                httpURLConnection2.setDoInput(true);
                OutputStream outputStream = httpURLConnection2.getOutputStream();
                BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data2 = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8") + "&" +
                        URLEncoder.encode("friend_id", "UTF-8") + "=" + URLEncoder.encode(friend_id, "UTF-8");
                bufferedWriter2.write(post_data2);
                bufferedWriter2.flush();
                bufferedWriter2.close();
                outputStream.close();
                InputStream inputStream2 = httpURLConnection2.getInputStream();
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream2, "iso-8859-1"));
                //result = "";
                String line = "";
                int i =0;
                while ((line = bufferedReader2.readLine()) != null) {
                    result[i] = "";
                    result[i] = line;
                    i++;
                }
                //count = i;
                bufferedReader2.close();
                inputStream2.close();
                httpURLConnection2.disconnect();
                //setMatch(result);
                return friend_id;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            AlertDialog.Builder alertDialog;
            View rootView = LayoutInflater.from(context).inflate(R.layout.confirm_dialog, null);
            if (result.equals("Insert failure")) {
                alertDialog = new AlertDialog.Builder(context, R.style.MyDialogTheme);
                alertDialog.setTitle("User Not Found").setView(rootView);
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
            else{
                Intent intent = new Intent(context, Tabs.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        }
    }
}
