package com.example.finalprojesi;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.ktx.Firebase;
import com.google.type.Date;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    EditText txtmessage;
    Button btnsend;
    RecyclerView chatRecycler;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    HashMap <String,Object> myData;
    MyRecyclerAdaptor adaptor;
    ArrayList<Kullanici> chats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txtmessage = findViewById(R.id.txtmessage);
        btnsend = findViewById(R.id.btnsend);
        chatRecycler = findViewById(R.id.chatRecycler);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        chats = new ArrayList<>();
        adaptor = new MyRecyclerAdaptor(chats);
        chatRecycler.setAdapter(adaptor);
        chatRecycler.setLayoutManager(new LinearLayoutManager(this));

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gondere basildiktan sonra yapilacaklar
                String message = txtmessage.getText().toString();
                String user = auth.getCurrentUser().getEmail();
                FieldValue date = FieldValue.serverTimestamp();

                myData = new HashMap<>();
                myData.put("Message : ",message);
                myData.put("Mail : ",user);
                myData.put("Date : ",date);

                firestore.collection("Mesajlar").add(myData).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()){
                            txtmessage.setText("");
                            Toast.makeText(MainActivity2.this, "Mesaj Gönderildi.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            txtmessage.setText("");
                            Toast.makeText(MainActivity2.this, "Mesaj Gönderilemedi!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //Mesajlarin Gosterilmesi
        firestore.collection("Mesajlar")
                .orderBy("Date : ", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.e("SnapshotListener", "Hata: ", error);
                        return;
                    }
                    else{
                        if(value != null){
                            if(value.isEmpty()){

                            }
                            else{
                                List<DocumentSnapshot> mesajlar = value.getDocuments();
                                chats.clear(); //mesajlari tekrar tekrar yazmasin diye siliyoruz
                                for (DocumentSnapshot document : mesajlar) {
                                    //mesajlari ve mailleri çekmek için
                                    String text = document.getString("Message : ");
                                    String user = document.getString("Mail : ");
                                    Kullanici mesaj = new Kullanici(user,text);
                                    chats.add(mesaj);
                                }
                                adaptor.chats = chats;
                                adaptor.notifyDataSetChanged();
                            }
                        }
                    }
                });

    }
}