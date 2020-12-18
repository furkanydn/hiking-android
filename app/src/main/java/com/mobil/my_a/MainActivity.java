package com.mobil.my_a;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private ImageView newItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newItem = (ImageView) findViewById(R.id.mainAddItem);

        newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gecis = new Intent(MainActivity.this, NewUrunActivity.class);
                startActivity(gecis);
            }
        });
    }
}
