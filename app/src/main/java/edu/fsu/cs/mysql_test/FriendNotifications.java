package edu.fsu.cs.mysql_test;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

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
import java.util.ArrayList;
import java.util.List;



public class FriendNotifications extends AppCompatActivity {

    Request mRequest;

    private ListView mListView;


    //public FriendNotifications(Context context){ this.mContext = context;}






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        mListView = (ListView) findViewById(R.id.listView);
        mRequest = new Request();
        final Context mContext = this;

        populateListView();



        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                View rootView = LayoutInflater.from(mContext).inflate(R.layout.confirm_dialog,null);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);

                alertDialog.setMessage("Confirm Friend")
                        .setView(rootView);

                alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       String friendUsername = parent.getAdapter().getItem(position).toString();

                        AddFriend friends = new AddFriend(mContext);
                        friends.execute(friendUsername);
                    }

                });

                alertDialog.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String friendUsername = parent.getAdapter().getItem(position).toString();

                        RemoveInvite event = new RemoveInvite(mContext);
                        event.execute(friendUsername);
                    }
                });

                if (rootView.getParent() != null){
                    ((ViewGroup) rootView.getParent()).removeView(rootView);
                }
                alertDialog.show();
            }
        });

    }

    private class AddFriend extends AsyncTask<String, Void, String[]> {

        Context context;
        AlertDialog.Builder alertDialog;

        AddFriend (Context ctx){
            context = ctx;
        }
        @Override
        protected String[] doInBackground(String... params) {
            MyApplication g = (MyApplication)getApplication();
            String user_id = g.getUserid();
            String friend_id = null;
            String[] result = new String[100];
            String add_request_url = "http://10.0.2.2/confirm_friend.php";
            String get_friend_url = "http://10.0.2.2/get_friend.php";
            try {
                String username = params[0];
                String[] splited = username.split(" ");
                URL url = new URL(get_friend_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("first_name", "UTF-8") + "=" + URLEncoder.encode(splited[0], "UTF-8") + "&" +
                        URLEncoder.encode("last_name", "UTF-8") + "=" + URLEncoder.encode(splited[1], "UTF-8");
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

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String[] result3 = new String[100];
            String friends_url = "http://10.0.2.2/friends.php";
            try {
                URL url3 = new URL(friends_url);
                HttpURLConnection httpURLConnection3 = (HttpURLConnection) url3.openConnection();
                httpURLConnection3.setRequestMethod("POST");
                httpURLConnection3.setDoOutput(true);
                httpURLConnection3.setDoInput(true);
                OutputStream outputStream3 = httpURLConnection3.getOutputStream();
                BufferedWriter bufferedWriter3 = new BufferedWriter(new OutputStreamWriter(outputStream3, "UTF-8"));
                String post_data3 = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8");
                bufferedWriter3.write(post_data3);
                bufferedWriter3.flush();
                bufferedWriter3.close();
                outputStream3.close();
                InputStream inputStream3 = httpURLConnection3.getInputStream();
                BufferedReader bufferedReader3 = new BufferedReader(new InputStreamReader(inputStream3, "iso-8859-1"));
                //result = "";
                String line = "";
                int i =0;
                int count = 0;
                while ((line = bufferedReader3.readLine()) != null) {
                    result3[i] = "";
                    result3[i] = line;
                    i++;
                }
                count = i;
                bufferedReader3.close();
                inputStream3.close();
                httpURLConnection3.disconnect();
                MyApplication f = (MyApplication)getApplication();
                f.setFriends(result3,count);
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
            Intent intent = new Intent(context, Tabs.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }
    }


    private class RemoveInvite extends AsyncTask<String, Void, String[]> {

        Context context;
        AlertDialog.Builder alertDialog;

        RemoveInvite (Context ctx){
            context = ctx;
        }
        @Override
        protected String[] doInBackground(String... params) {
            MyApplication g = (MyApplication)getApplication();
            String user_id = g.getUserid();
            String friend_id = null;
            String[] result = new String[100];
            String add_request_url = "http://10.0.2.2/remove_friend_request.php";
            String get_friend_url = "http://10.0.2.2/get_friend.php";
            try {
                String username = params[0];
                String[] splited = username.split(" ");
                URL url = new URL(get_friend_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("first_name", "UTF-8") + "=" + URLEncoder.encode(splited[0], "UTF-8") + "&" +
                        URLEncoder.encode("last_name", "UTF-8") + "=" + URLEncoder.encode(splited[1], "UTF-8");
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

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String[] result3 = new String[100];
            String friends_url = "http://10.0.2.2/friends.php";
            try {
                URL url3 = new URL(friends_url);
                HttpURLConnection httpURLConnection3 = (HttpURLConnection) url3.openConnection();
                httpURLConnection3.setRequestMethod("POST");
                httpURLConnection3.setDoOutput(true);
                httpURLConnection3.setDoInput(true);
                OutputStream outputStream3 = httpURLConnection3.getOutputStream();
                BufferedWriter bufferedWriter3 = new BufferedWriter(new OutputStreamWriter(outputStream3, "UTF-8"));
                String post_data3 = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8");
                bufferedWriter3.write(post_data3);
                bufferedWriter3.flush();
                bufferedWriter3.close();
                outputStream3.close();
                InputStream inputStream3 = httpURLConnection3.getInputStream();
                BufferedReader bufferedReader3 = new BufferedReader(new InputStreamReader(inputStream3, "iso-8859-1"));
                //result = "";
                String line = "";
                int i =0;
                int count = 0;
                while ((line = bufferedReader3.readLine()) != null) {
                    result3[i] = "";
                    result3[i] = line;
                    i++;
                }
                count = i;
                bufferedReader3.close();
                inputStream3.close();
                httpURLConnection3.disconnect();
                MyApplication f = (MyApplication)getApplication();
                f.setFriends(result3,count);
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
            Intent intent = new Intent(context, Tabs.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }
    }

    private void populateListView() {
        //for(int i = 0; i < mRequest.count)
        //String[] data = mRequest.match;
        Intent intent = getIntent();
        String[] data = intent.getStringArrayExtra("array");
        if(data == null)
            data[0] = "No Data";
        int size = (data.length/3);
        String[] name = new String[size];
        String[] game = new String[size];

        //ArrayAdapter<String[]> adapter = new ArrayAdapter<String[]>(this,android.R.layout.simple_list_item_1,data);
        ArrayList<String> listData = new ArrayList<>();
        int i = 0;
        for (i = 0; i < data.length-1; i++){
            listData.add(data[i]);
        }

        final ListAdapter adapter = new ArrayAdapter(this, R.layout.list_white_text, R.id.list_content, listData) {
        };
        mListView.setAdapter(adapter);


    }
}
