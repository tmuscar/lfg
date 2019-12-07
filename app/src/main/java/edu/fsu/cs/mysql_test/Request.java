package edu.fsu.cs.mysql_test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Request extends AppCompatActivity {
    InputStream is = null;
    String result = null;
    String line = null;
    InputStream quest_is = null;
    String quest_result = null;
    String quest_line = null;
    String[] quest_name;

    boolean formerror = false;


    String[] name;
    String game;
    String[] match;
    String quest;
    int count;

    Spinner dropDown;
    Spinner dropQuest;
    EditText  date, time;
    Button button;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //dropDown = (Spinner) findViewById(R.id.spinner);
        dropQuest = (Spinner) findViewById(R.id.spinnerQuest);

        //quest = (EditText) findViewById(R.id.quest);
        date = (EditText) findViewById(R.id.date);
        time = (EditText) findViewById(R.id.time);
        button = (Button) findViewById(R.id.button);

        final List<String> list1 = new ArrayList<String>();
        final List<String> list2 = new ArrayList<>();



        try
        {
            URL url = new URL("http://10.0.2.2/quest.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            MyApplication k = (MyApplication)getApplication();
            String game = k.getGame();
            String post_data = URLEncoder.encode("game", "UTF-8") + "=" + URLEncoder.encode(game, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            quest_is = new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(quest_is, "iso-8859-1"));
            StringBuffer sb = new StringBuffer();
            while ((quest_line = reader.readLine()) != null)
            {
                sb.append(quest_line + "\n");

            }
            quest_is.close();
            quest_result = sb.toString();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        try
        {
            JSONArray JA = new JSONArray(quest_result);
            JSONObject json = null;

            quest_name = new String[JA.length()];

            for (int i = 0; i < JA.length(); i++)
            {
                json = JA.getJSONObject(i);

                quest_name[i]  = json.getString("quest");
            }
            //Toast.makeText(getApplicationContext(), "sss", Toast.LENGTH_LONG).show();

            for(int i = 0; i < quest_name.length; i++)
            {
                list2.add(quest_name[i]);
            }
            //Toast.makeText(getApplicationContext(), "len", Toast.LENGTH_LONG).show();
            quest_spinner();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void OnReq(View view) {
        String str_time = time.getText().toString();
        String str_date = date.getText().toString();

        formerror = false;
        if(time.getText().toString().length() != 7){
            formerror = true;
            time.setError("Incorrect Formatting");
        }
        if(date.getText().toString().length() != 10){
            formerror = true;
            date.setError("Incorrect Formatting");
        }
        if(time.getText().toString().matches("")){
            formerror = true;
            time.setError("Missing Time");
        }
        if(date.getText().toString().matches("")){
            formerror = true;
            date.setError("Missing Date");
        }
        if(formerror)
        {

        }
        else {
            MyApplication g = (MyApplication) getApplication();
            String str_game = g.getGame();
            MyApplication f = (MyApplication) getApplication();
            String str_quest = f.getQuest();
            String type = "looking";
            Schedule schedule = new Schedule(this);
            schedule.execute(type, str_time, str_date, str_game, str_quest);
        }
    }

    public void setMatch(String[] temp) {
        int i = 0;
        match = new String[count+1];
        //Log.d("Count", temp[count]);
        for(i = 0; i < count; i++ )
            this.match[i] = temp[i];
        //this.match = Arrays.stream(this.match).filter(s -> (s != null && s.length() > 0)).toArray(String[]::new);


    }

    public String getData(int i) {
        return this.match[i];
    }

    private class Schedule extends AsyncTask<String, Void, String[]> {

        Context context;
        AlertDialog.Builder alertDialog;

        Schedule (Context ctx){
            context = ctx;
        }
        @Override
        protected String[] doInBackground(String... params) {
            String type = params[0];
            MyApplication g = (MyApplication)getApplication();
            String user_id = g.getUserid();
            //String[] result = new String[3];
            //result[1] = params[0];
            String[] result = new String[50];
            String login_url = "http://10.0.2.2/looking.php";
            try {
                String time = params[1];
                String date = params[2];
                String game = params[3];
                String quest = params[4];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8") + "&"
                        + URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8") + "&"
                        + URLEncoder.encode("game", "UTF-8") + "=" + URLEncoder.encode(game, "UTF-8") + "&"
                        + URLEncoder.encode("quest", "UTF-8") + "=" + URLEncoder.encode(quest, "UTF-8") + "&"
                        + URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8");
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

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String[] result3 = new String[100];

            MyApplication r = MyApplication.getInstance();
            String userid = r.getUserid();
            String register_url = "http://10.0.2.2/get_events.php";
            try {
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_id", "UTF-8")+"="+URLEncoder.encode(userid, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                //result[] = "";
                String line = "";
                int i =0;
                while ((line = bufferedReader.readLine()) != null) {
                    result3[i] = "";
                    result3[i] = line;
                    i++;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                MyApplication t = MyApplication.getInstance();
                t.setEvents(result3, i);
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
            intent.putExtra("array", match);
            context.startActivity(intent);
        }
    }

    private void quest_spinner(){
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(Request.this, android.R.layout.simple_spinner_item, quest_name);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropQuest.setAdapter(dataAdapter2);

        dropQuest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id){
                ((TextView) dropQuest.getChildAt(0)).setTextColor(0xFFFFFFFF);
                dropQuest.setSelection(position);
                TextView tv = (TextView)arg1;
                quest = tv.getText().toString();
                MyApplication g = (MyApplication)getApplication();
                g.setQuest(quest);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });
    }




}
