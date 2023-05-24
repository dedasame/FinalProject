package com.example.finalprojesi;

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

public class MainActivity extends AppCompatActivity {
    Button btnlogin,btnregister;
    EditText txtmail,txtpass;
    String mail,password;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnlogin = findViewById(R.id.btnlogin);
        btnregister = findViewById(R.id.btnregister);
        auth = FirebaseAuth.getInstance();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtmail = findViewById(R.id.txtmail);
                txtpass = findViewById(R.id.txtpass);
                mail = txtmail.getText().toString();
                password = txtpass.getText().toString();

                if(!TextUtils.isEmpty(mail) && !TextUtils.isEmpty(password)){

                    auth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                //Dogru olmasi halinde gecis islemler
                                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                finish(); //finish ile aktiviteyi sonlandiriyoruz ekstradan çalismiyor
                                startActivity(intent);

                            }
                            else{
                                Toast.makeText(MainActivity.this, "Giriş Yapılamadı!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(MainActivity.this, "Kullanıcı Adı ve Şifre Boş Olamaz", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtmail = findViewById(R.id.txtmail);
                txtpass = findViewById(R.id.txtpass);
                mail = txtmail.getText().toString();
                password = txtpass.getText().toString();

                if(!TextUtils.isEmpty(mail) && !TextUtils.isEmpty(password)){

                    //Kayıt Etme İşlemleri
                    auth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Hesap Başarıyla Oluşturuldu", Toast.LENGTH_SHORT).show();
                                txtmail.setText("");
                                txtpass.setText("");

                            }
                            else{
                                Toast.makeText(MainActivity.this, "Hesap Oluşturulamadı!", Toast.LENGTH_SHORT).show();
                                txtpass.setText("");
                            }
                        }
                    });
                }

                else {
                    Toast.makeText(MainActivity.this, "Kullanıcı Adı ve Şifre Boş Olamaz", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}