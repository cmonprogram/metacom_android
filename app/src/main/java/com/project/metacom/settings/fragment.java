package com.project.metacom.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.project.metacom.R;


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
