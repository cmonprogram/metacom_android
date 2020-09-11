package com.project.metacom.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.project.metacom.R;
import com.project.metacom.Receiver;
import com.project.metacom.data.User;
import com.project.metacom.data.User_last;
import com.project.metacom.data.User_stats;
import com.project.metacom.fragment.user_profile_fragment;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class fragment extends Fragment {
    private AppCompatActivity context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.settings_layout, container, false);
        context = (AppCompatActivity)getActivity();
        context.setTitle("Settings");
        setHasOptionsMenu(true);


        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    // Data init place

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        // View change place

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();


        return view;
    }

}
