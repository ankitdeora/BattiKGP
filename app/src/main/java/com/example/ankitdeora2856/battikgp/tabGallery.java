package com.example.ankitdeora2856.battikgp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by ankitdeora2856 on 17-03-2016.
 */
public class tabGallery extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_gallery,container,false);
        Button bt = (Button) v.findViewById(R.id.id_ok);
        //Bundle bundle = getArguments();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Button pressed in Gallery Tab", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

}
