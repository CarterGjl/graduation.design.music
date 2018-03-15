package com.example.demoevent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn =  findViewById(R.id.btn_press);
        btn.setOnClickListener(new MyClickListener());
    }
    class MyClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            EditText txt =  findViewById(R.id.txt);
            txt.setText("btn按钮被点击了");
        }
    }
}
