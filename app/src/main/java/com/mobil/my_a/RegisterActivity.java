package com.mobil.my_a;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText isim=(EditText) findViewById(R.id.editKayitAdi);
        EditText soyisim=(EditText) findViewById(R.id.editKayitSoyadi);
        EditText mail=(EditText) findViewById(R.id.editKayitMail);
        EditText sifrebir=(EditText) findViewById(R.id.editKayitSifreBir);
        EditText sifreiki=(EditText) findViewById(R.id.editKayitSifreİki);
        Button kayitol =(Button) findViewById(R.id.kayitOlButon);
        //Kullanımlar-icin-icerikleri
        String sf = sifrebir.getText().toString();
        String si = sifreiki.getText().toString();
        kayitol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Geçiş-için-kullanılacak-if-kullan-içine-koy
                if(sf == si){
                    Intent gecis = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(gecis);
                } else {
                    withResimliHata();
                }

            }
        });

    }
    public void withResimliHata(){
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater.inflate(R.layout.card_hata,null);
        build.setPositiveButton("Tamam",null);
        build.setView(dialog);
        build.show();
    }
}
