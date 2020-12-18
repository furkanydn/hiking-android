package com.mobil.my_a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private EditText mail;
    private EditText sifre;
    private Button girisyap;
    private Button kayitagit;

    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        girisyap =(Button) findViewById(R.id.girisButon);
        kayitagit =(Button) findViewById(R.id.girisKayiteGitButon);
        //Internet-kontrolu-check
        if(new InternetActivity(this).getInternetStatus()){
            Toast.makeText(this, "İnternet bağlantısı var", Toast.LENGTH_SHORT).show();
        }

        //Giris-yaparken-kullanılacak-kodları-buraya
        girisyap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginData();
            }
        });

        kayitagit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gecis = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(gecis);
            }
        });

    }

    private void loginData(){
        mail =(EditText) findViewById(R.id.editGirisAdi);
        sifre =(EditText) findViewById(R.id.editGirisSifre);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if(TextUtils.isEmpty(mail.getText().toString())){
            Toast.makeText(this, R.string.bos_mail, Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(sifre.getText().toString())){
            Toast.makeText(this, R.string.bos_sifre, Toast.LENGTH_SHORT).show();
        } else
        {
            String email = mail.getText().toString().trim();
            String pass = sifre.getText().toString().trim();
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        gecisYap();
                        temizleEdit();
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.hatali_mail, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void temizleEdit() {
        mail.setText(null);
        sifre.setText(null);
    }

    private void gecisYap(){
        Intent gecis = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(gecis);
    }
}