package com.project.metacom;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import java.io.IOException;

import data.DataAdapter;
import data.DataRceveiver;

public class chat_activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("ExpandableListView");

        chat_view cv = new chat_view(this);
        setContentView(R.layout.chat_layout);
       // RelativeLayout layout= (RelativeLayout) findViewById(R.id.rl);
       // layout.addView(cv);


        DataAdapter data_adapter = new DataAdapter(this);
        ExpandableListView data_target = (ExpandableListView) findViewById(R.id.expListView);

        DataRceveiver data_receiver = new DataRceveiver("http://109.196.164.38",data_adapter, data_target);
        try { data_receiver.Top_chart_execute();  } catch (IOException e) {   e.printStackTrace();   }

    }

    class chat_view extends View {
        public chat_view(Context context) {
            super(context);
        }
    }
}

