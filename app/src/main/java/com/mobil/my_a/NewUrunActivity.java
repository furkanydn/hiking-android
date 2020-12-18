package com.mobil.my_a;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class NewUrunActivity extends AppCompatActivity {

    private TextView kaydetbuton, temizlebuton;
    private EditText editUrunIsim, editUrunAciklama, editUrunEskiFiyat, editUrunYeniFiyat;
    private ImageView editUrunResimEkle;
    private DatabaseReference mUrunReference;
    private FirebaseAuth auth;
    private StorageReference mUrunResimDepo;
    private final static int Gallery_Pick = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeniurun);
        auth = FirebaseAuth.getInstance();

        butonkontrol();
        mUrunResimDepo = FirebaseStorage.getInstance().getReference().child("Images");
        mUrunReference = FirebaseDatabase.getInstance().getReference().child("Products");


        editUrunResimEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Galeriden profil resmi seçmeme yarayacak. */
                String ID_Gens = auth.getCurrentUser().getUid();
                editUrunResimEkle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Resim seç butonuna tıklandığında Urun_Resmi verisi oluştursun bu sayede resim yüklerken veriler tabloda karışmasın.Bunu kaydet butonundan önce oluşturuyorum ki hata vermesin.Ellemeni tavsiye etmem!
                        mUrunReference.child(ID_Gens).child("Urun_Resmi").setValue("default_product_image");
                        Intent galleryIntent = new Intent();
                        galleryIntent.setAction(Intent.ACTION_PICK);
                        galleryIntent.setType("image/*");
                        startActivityForResult(galleryIntent, Gallery_Pick);

                    }
                });
            }
        });

        kaydetbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urunIsmi= editUrunIsim.getText().toString().trim();
                String urun_aciklama= editUrunAciklama.getText().toString().trim();
                String urun_yeni_fiyat= editUrunYeniFiyat.getText().toString().trim();
                String urun_eski_fiyat= editUrunEskiFiyat.getText().toString().trim();
                kaydetUrun(urunIsmi,urun_aciklama,urun_yeni_fiyat,urun_eski_fiyat);

            }
        });

        temizlebuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTemizlebuton();
            }
        });



    }

    private void butonkontrol() {
        kaydetbuton = (TextView) findViewById(R.id.baslik_kaydet_buton);
        temizlebuton = (TextView) findViewById(R.id.baslik_temizle);
        editUrunIsim = (EditText) findViewById(R.id.editUrunIsim);
        editUrunAciklama = (EditText) findViewById(R.id.editUrunAciklama);
        editUrunEskiFiyat = (EditText) findViewById(R.id.editUrunEskiFiyat);
        editUrunYeniFiyat = (EditText) findViewById(R.id.editUrunYeniFiyat);
        editUrunResimEkle = (ImageView) findViewById(R.id.editUrunResim);
    }

    //Bunu referans alarak diğer kaydetmeler bunla yapabilabilir ! ! ! ! !
    private void kaydetUrun(String urunIsmi,String urun_aciklama, String urun_yeni_fiyat, String urun_eski_fiyat) {
        String Urun_ID_Generator = auth.getCurrentUser().getUid(); //Urun ID Generator kullanmazsan her seferinde tablodaki mevcut verileri değiştirir kaydet butonuna tıklandığında//
        mUrunReference = FirebaseDatabase.getInstance().getReference().child("Products").child(Urun_ID_Generator);

        mUrunReference.child("urunIsım").setValue(urunIsmi);
        mUrunReference.child("urunAciklama").setValue(urun_aciklama);
        mUrunReference.child("urunYeniFiyat").setValue(urun_yeni_fiyat);
        mUrunReference.child("urunEskiFiyat").setValue(urun_eski_fiyat).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(NewUrunActivity.this,"Ürün başarıyla eklendi",Toast.LENGTH_LONG).show();
                    gecisYap();
                }
                else {
                    Toast.makeText(NewUrunActivity.this, "Ürün eklenirken hata oluştu", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    private void gecisYap(){
        Intent gecis = new Intent(NewUrunActivity.this, MainActivity.class);
        startActivity(gecis);
    }

    private void setTemizlebuton(){
        butonkontrol();
        editUrunIsim.setText(null);
        editUrunAciklama.setText(null);
        editUrunEskiFiyat.setText(null);
        editUrunYeniFiyat.setText(null);
        editUrunResimEkle.setImageResource(R.drawable.ic_add);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null) {
            super.onActivityResult(requestCode, resultCode, data);
            String ID_Generator = auth.getCurrentUser().getUid();
            Uri ImageUri = data.getData();
            StorageReference filePath = mUrunResimDepo.child(ID_Generator+".jpg");
            filePath.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    final Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                    firebaseUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri)
                        {

                            final String downloadUrl = uri.toString();
                            mUrunReference.child(ID_Generator).child("urunResim").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Picasso.get().load(downloadUrl).into(editUrunResimEkle);
                                    }
                                }
                            });


                        }
                    });
                }
            });

        }

    }
}
