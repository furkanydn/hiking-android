package com.mobil.my_a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Internet-kontrolu-check
        if(new InternetActivity(this).getInternetStatus()){
            Toast.makeText(this, "İnternet bağlantısı var", Toast.LENGTH_SHORT).show();
        }
        EditText mail =(EditText) findViewById(R.id.editGirisAdi);
        EditText sifre =(EditText) findViewById(R.id.editGirisSifre);
        Button girisyap =(Button) findViewById(R.id.girisButon);
        Button kayitagit =(Button) findViewById(R.id.girisKayiteGitButon);

        //Giris-yaparken-kullanılacak-kodları-buraya
        girisyap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        kayitagit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gecis = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(gecis);
            }
        });

    }
}