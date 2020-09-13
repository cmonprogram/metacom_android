package com.project.metacom;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityMain extends AppCompatActivity {
private Context context = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setContentView(R.layout.activitymain_layout);

        //init fragment

        String action = getIntent().getStringExtra("go_to");
        if(action == null){
            init_fragment(new com.project.metacom.toplist.fragment());
        }else {
            switch (action) {
                case "action_news":
                    init_fragment(new com.project.metacom.toplist.fragment());
                    break;
                case "action_activity":
                    init_fragment(new com.project.metacom.activitylist.fragment());
                    break;
                case "action_settings":
                    init_fragment(new com.project.metacom.settings.fragment());
                    break;
                default:
                    init_fragment(new com.project.metacom.toplist.fragment());
                    break;
            }
        }


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            /*
               Bundle bundle = new Bundle();
               bundle.putString("user_id", comment.user_id);

               user_profile_fragment fragInfo = new user_profile_fragment();
               fragInfo.setArguments(bundle);
               FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
               transaction.replace(R.id.user_profile_conteiner, fragInfo);
               transaction.addToBackStack(null);
               transaction.commit();
               break;

             */


            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_news:
                        init_fragment(new com.project.metacom.toplist.fragment());
                        break;
                    case R.id.action_activity:
                        init_fragment(new com.project.metacom.activitylist.fragment());
                        break;
                    case R.id.action_settings:
                        init_fragment(new com.project.metacom.settings.fragment());
                        break;
                }
                return true;
            }
        });

    }



    public void init_fragment(Fragment fragment){
        //init fragment
        Fragment fragInfo = fragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragInfo);
        transaction.addToBackStack(null);
        transaction.commit();
    };

}
