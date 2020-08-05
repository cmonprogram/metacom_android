package com.project.metacom.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.project.metacom.R;
import com.project.metacom.toplist.DataAdapter;
import com.project.metacom.toplist.DataRceveiver;
import com.project.metacom.toplist.DataStructure;

import java.io.IOException;
import java.util.ArrayList;

public class activity extends android.app.Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Login");


        view cv = new view(this);
        setContentView(R.layout.list_layout);
        // RelativeLayout topList_layout= (RelativeLayout) findViewById(R.id.rl);
        // topList_layout.addView(cv);


        final DataAdapter data_adapter = new DataAdapter(this,new ArrayList<DataStructure>());
        ListView data_target = (ListView) findViewById(R.id.list_view);

        data_target.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataStructure item = (DataStructure)data_adapter.getItem(position);
                //Toast.makeText(getBaseContext(),item.chat_room,Toast.LENGTH_SHORT).show();

                Intent startIntent = new Intent(activity.this, com.project.metacom.comments.activity.class);
                startIntent.putExtra("chat_room", item.chat_room);
                startIntent.putExtra("page_title", item.page_title);
                startActivity(startIntent);

            }
        });

        DataRceveiver data_receiver = new DataRceveiver(data_adapter);
        try { data_receiver.execute();  } catch (IOException e) {   e.printStackTrace();   }

        data_target.setAdapter(data_adapter);

    }



    class view extends View {
        public view(Context context) {
            super(context);
        }
    }

}
