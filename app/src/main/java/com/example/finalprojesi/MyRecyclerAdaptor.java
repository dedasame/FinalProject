package com.example.finalprojesi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerAdaptor extends RecyclerView.Adapter<MyRecyclerAdaptor.MyHolder> {
     ArrayList<Kullanici> chats;
     public MyRecyclerAdaptor(ArrayList<Kullanici> chats){

         this.chats = chats;

     }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messageitem,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.txtmail.setText(chats.get(position).getMail());
        holder.txtmesaj.setText(chats.get(position).getMessage());


    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        TextView txtmail,txtmesaj;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            txtmail = itemView.findViewById(R.id.txtmail);
            txtmesaj = itemView.findViewById(R.id.txtmesaj);

        }
    }

}
