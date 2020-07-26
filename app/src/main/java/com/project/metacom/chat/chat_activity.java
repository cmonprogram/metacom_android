package com.project.metacom.chat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.project.metacom.R;

import java.io.IOException;

import data.expList.expListDataAdapter;
import data.expList.expListDataRceveiver;

import static com.project.metacom.config.server;

public class chat_activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("ExpandableListView");

        chat_view cv = new chat_view(this);
        setContentView(R.layout.explist_layout);
       // RelativeLayout topList_layout= (RelativeLayout) findViewById(R.id.rl);
       // topList_layout.addView(cv);

        expListDataAdapter data_adapter = new expListDataAdapter(this);
        ExpandableListView data_target = (ExpandableListView) findViewById(R.id.explist_view);

        expListDataRceveiver data_receiver = new expListDataRceveiver(server,data_adapter, data_target);
        try { data_receiver.Top_chart_execute();  } catch (IOException e) {   e.printStackTrace();   }

    }

    class chat_view extends View {
        public chat_view(Context context) {
            super(context);
        }
    }
}

