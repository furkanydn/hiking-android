package com.mobil.my_a;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class UrunActivity extends AppCompatActivity {
    ImageView gerigit=(ImageView) findViewById(R.id.urungerigit);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun);


        gerigit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gecis= new Intent(UrunActivity.this, MainActivity.class);
                startActivity(gecis);
                finish();
            }
        });

    }
}
