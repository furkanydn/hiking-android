package com.mobil.my_a;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mReference;

    private EditText isim;
    private EditText soyisim;
    private EditText mail;
    private EditText sifrebir;
    private EditText sifreiki;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        Button kayitol =(Button) findViewById(R.id.kayitOlButon);
        sifrebir=(EditText) findViewById(R.id.editKayitSifreBir);
        kayitol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Geçiş-için-kullanılacak-if-kullan-içine-koy
                if(!TextUtils.isEmpty(sifrebir.getText().toString().trim())){
                    postUser();
                } else {
                    withResimliHata();
                }
            }
        });

    }
    private void postUser(){
        isim=(EditText) findViewById(R.id.editKayitAdi);
        soyisim=(EditText) findViewById(R.id.editKayitSoyadi);
        mail=(EditText) findViewById(R.id.editKayitMail);
        sifrebir=(EditText) findViewById(R.id.editKayitSifreBir);
        sifreiki=(EditText) findViewById(R.id.editKayitSifreİki);
        //
        String name= isim.getText().toString().trim();
        String surname= soyisim.getText().toString().trim();
        String email= mail.getText().toString().trim();
        String passone= sifrebir.getText().toString().trim();
        String passtwo= sifreiki.getText().toString().trim();

        if(TextUtils.isEmpty(mail.getText().toString())){
            Toast.makeText(this, R.string.register_mail_hata, Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(sifrebir.getText().toString()) && TextUtils.isEmpty(sifreiki.getText().toString())){
            Toast.makeText(this, R.string.register_sifre_hata, Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(isim.getText().toString()) && TextUtils.isEmpty(soyisim.getText().toString())){
            Toast.makeText(this, R.string.register_bos_alan, Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email,passone).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, R.string.register_kayit_oldu, Toast.LENGTH_SHORT).show();
                        String userId = mAuth.getCurrentUser().getUid();
                        mReference = FirebaseDatabase.getInstance().getReference().child("User").child(userId);
                        mReference.child("username").setValue(name);
                        mReference.child("user_surname").setValue(surname);
                        mReference.child("email_address").setValue(email);
                        mReference.child("password").setValue(passone).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Intent gecis = new Intent(RegisterActivity.this, LoginActivity.class);
                                    hideKeyboard(sifreiki);
                                    temizleEdit();
                                    startActivity(gecis);
                                }   else {
                                    Toast.makeText(RegisterActivity.this, R.string.register_kayit_hata, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        }

    }

    private void hideKeyboard(View view){
        InputMethodManager inputMethodManager =(InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void temizleEdit() {
        isim.setText(null);
        soyisim.setText(null);
        mail.setText(null);
        sifrebir.setText(null);
        sifreiki.setText(null);
    }

    private void withResimliHata(){
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater.inflate(R.layout.card_hata,null);
        build.setPositiveButton("Tamam",null);
        build.setView(dialog);
        build.show();
    }
}
