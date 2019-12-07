package edu.fsu.cs.mysql_test;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListMatch extends AppCompatActivity {

    Request mRequest;

    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        mListView = (ListView) findViewById(R.id.listView);
        mRequest = new Request();

        populateListView();
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

        final ListAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listData) {
        };
        mListView.setAdapter(adapter);


    }
}
