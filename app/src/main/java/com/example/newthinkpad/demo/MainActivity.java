package com.example.newthinkpad.demo;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;



public class MainActivity extends AppCompatActivity {

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.constraint);
        View root = this.getLayoutInflater().inflate(R.layout.popup_window, null);

        final PopupWindow popupWindow = new PopupWindow(root, 560, 720);
        Button btnPop = (Button) findViewById(R.id.pop_window);
        btnPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.showAsDropDown(view);
                popupWindow.showAtLocation(layout, Gravity.CENTER,20,20);
            }
        });
        root.findViewById(R.id.v_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }
}
