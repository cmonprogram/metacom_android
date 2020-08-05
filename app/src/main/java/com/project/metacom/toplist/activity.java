package com.project.metacom.toplist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.metacom.R;
import com.project.metacom.data.TopListItem;

import java.io.IOException;
import java.util.ArrayList;

public class activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Top chart");


        view cv = new view(this);
        setContentView(R.layout.list_layout);
        // RelativeLayout topList_layout= (RelativeLayout) findViewById(R.id.rl);
        // topList_layout.addView(cv);


        final DataAdapter data_adapter = new DataAdapter(this,new ArrayList<TopListItem>());
        ListView data_target = (ListView) findViewById(R.id.list_view);

        data_target.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TopListItem item = (TopListItem)data_adapter.getItem(position);
                //Toast.makeText(getBaseContext(),item.chat_room,Toast.LENGTH_SHORT).show();

                Intent startIntent = new Intent(activity.this, com.project.metacom.comments.activity.class);
                startIntent.putExtra("go_to", item.chat_room);
                startIntent.putExtra("page_title", item.page_title);
                startActivity(startIntent);

            }
        });

        DataReceiver_toplistitem data_receiver = new DataReceiver_toplistitem(data_adapter);
        try { data_receiver.execute();  } catch (IOException e) {   e.printStackTrace();   }

        data_target.setAdapter(data_adapter);

    }



    class view extends View {
        public view(Context context) {
            super(context);
        }
    }

}
