package com.project.metacom.comments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.metacom.R;

import java.io.IOException;

public class activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra("page_title"));

        // enable toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setContentView(R.layout.comment_layout);
        RecyclerView data_target = (RecyclerView) findViewById(R.id.comments);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        data_target.setHasFixedSize(false);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        data_target.setLayoutManager(layoutManager);

        final DataAdapter data_adapter = new DataAdapter(this);
        data_target.setAdapter(data_adapter);

        DataRceveiver data_receiver = new DataRceveiver(data_adapter);
        data_receiver.execute(getIntent().getStringExtra("chat_room"));
        //getActionBar().setDisplayHomeAsUpEnabled(true);



        /*
        data_target.setOnClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataStructure item = (DataStructure)data_adapter.getItem(position);
                Toast.makeText(getBaseContext(),item.url,Toast.LENGTH_SHORT).show();
            }
        });
        */
    }

    class view extends View {
        public view(Context context) {
            super(context);
        }
    }

    // toolbar actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent startIntent = new Intent(com.project.metacom.comments.activity.this, com.project.metacom.toplist.activity.class);
            startActivity(startIntent);
            //finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}
