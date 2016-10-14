package com.example.android.layoutinflater;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout parent = (LinearLayout) findViewById(R.id.parent_main);
        Button btnShowAlert = (Button) findViewById(R.id.btnShowAlert);
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.view_button,parent,true);

        Button btnAttachToRootFalse = (Button) inflater.inflate(R.layout.view_button,parent,false);
        btnAttachToRootFalse.setText(R.string.action_attach_to_root_false);
        parent.addView(btnAttachToRootFalse);
        btnShowAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelloDialogFragment.newInstance().show(getSupportFragmentManager(),"Dialog");
            }
        });
    }
}
