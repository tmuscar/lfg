package edu.fsu.cs.mysql_test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

public class Setting extends Fragment{

    Button notifications, log_out, add_friend, event_notif;
    Context context;

    String[] match;
    int count;

    public Setting(){
        this.context = context;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings, container, false);

        notifications = (Button) rootView.findViewById(R.id.btnNotification);
        log_out = (Button) rootView.findViewById(R.id.btnLogin);
        add_friend = (Button) rootView.findViewById(R.id.btnAdd);
        event_notif = (Button) rootView.findViewById(R.id.btnEvent);

        add_friend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getContext(),FriendRequest.class);
                startActivity(intent);
                //startActivity(new Intent(context, FriendRequest.class));
            }
        });

        log_out.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getContext(),MainActivity.class);
                startActivity(intent);
                //startActivity(new Intent(context, FriendRequest.class));
            }
        });

        notifications.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MyApplication k = MyApplication.getInstance();
                String user_id = k.getUserid();
                ListRequests schedule = new ListRequests(context);
                schedule.execute(user_id);

                //startActivity(new Intent(context, FriendRequest.class));
            }
        });

        event_notif.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MyApplication k = MyApplication.getInstance();
                String user_id = k.getUserid();
                ListEventRequests schedule = new ListEventRequests(context);
                schedule.execute(user_id);

                //startActivity(new Intent(context, FriendRequest.class));
            }
        });


        return rootView;
    }

    public void setMatch(String[] temp) {
        int i = 0;
        match = new String[count+1];
        //Log.d("Count", temp[count]);
        for(i = 0; i < count; i++ )
            this.match[i] = temp[i];
        //this.match = Arrays.stream(this.match).filter(s -> (s != null && s.length() > 0)).toArray(String[]::new);


    }

    private class ListRequests extends AsyncTask<String, Void, String[]> {

        Context context;
        AlertDialog.Builder alertDialog;

        ListRequests (Context ctx){
            context = ctx;
        }
        @Override
        protected String[] doInBackground(String... params) {
            String[] result = new String[100];
            String login_url = "http://10.0.2.2/view_requests.php";
            try {
                String user_id = params[0];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                //result = "";
                String line = "";
                int i =0;
                while ((line = bufferedReader.readLine()) != null) {
                    result[i] = "";
                    result[i] = line;
                    i++;
                }
                count = i;
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                setMatch(result);
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result[]) {
            Intent intent = new Intent(getContext(), FriendNotifications.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("array", match);
            startActivity(intent);
        }
    }

    private class ListEventRequests extends AsyncTask<String, Void, String[]> {

        Context context;
        AlertDialog.Builder alertDialog;

        ListEventRequests (Context ctx){
            context = ctx;
        }
        @Override
        protected String[] doInBackground(String... params) {
            String[] result = new String[100];
            String login_url = "http://10.0.2.2/get_invite_requests.php";
            try {
                String user_id = params[0];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                //result = "";
                String line = "";
                int i =0;
                while ((line = bufferedReader.readLine()) != null) {
                    result[i] = "";
                    result[i] = line;
                    i++;
                }
                count = i;
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                MyApplication t = MyApplication.getInstance();
                t.setInvite(result, i);
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result[]) {
            Intent intent = new Intent(getContext(), EventNotifList.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }


}
