package com.mobil.my_a;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewUrunActivity extends AppCompatActivity {
    TextView kaydetbuton=(TextView) findViewById(R.id.baslik_kaydet_buton);
    TextView temizlebuton=(TextView) findViewById(R.id.baslik_temizle);
    //
    EditText editUrunIsim=(EditText) findViewById(R.id.editUrunIsim);
    EditText editUrunAciklama=(EditText) findViewById(R.id.editUrunAciklama);
    EditText editUrunEskiFiyat=(EditText) findViewById(R.id.editUrunEskiFiyat);
    EditText editUrunYeniFiyat=(EditText) findViewById(R.id.editUrunYeniFiyat);
    //
    ImageView editUrunResim=(ImageView) findViewById(R.id.editUrunResim);
    Bitmap grafik;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeniurun);

        editUrunResim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resim = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(resim,250);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 250){
            if(resultCode == Activity.RESULT_OK){
                Uri resimUri = data.getData();
                editUrunResim.setImageURI(resimUri);
            }
        }
    }
}
