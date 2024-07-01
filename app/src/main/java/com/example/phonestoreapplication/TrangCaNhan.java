package com.example.phonestoreapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.phonestoreapplication.Model.User;
import java.util.Date;

public class TrangCaNhan extends AppCompatActivity {
    DatabaseReference usersRef;

    SharedPreferences sharedPreferences;
    TextView txtProFileName;

    Button btDonHangDaMua, btnTTCN, btnDangXuat;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_trang_ca_nhan);
        sharedPreferences= getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);
        String myId = sharedPreferences.getString("id", "-1");
        usersRef = FirebaseDatabase.getInstance().getReference("Users").child(myId);

        txtProFileName=findViewById(R.id.profileName);
        btnTTCN = findViewById(R.id.TTCN);
        btnDangXuat = findViewById(R.id.DangXuat);
//        btEditThongTin=findViewById(R.id.btEditThongTin);
        btDonHangDaMua = findViewById(R.id.btDonHangDaMua);

        btnTTCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangCaNhan.this, ChinhSuaThongTinCaNhan.class);
                startActivity(intent);
            }
        });

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Lấy dữ liệu từ dataSnapshot
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    // Gán tên người dùng vào txtProFileName
                    txtProFileName.setText(user.getName());
//
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi khi lấy dữ liệu từ Firebase
            }
        });

        btDonHangDaMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i =new Intent(TrangMyProfile.this,TrangXemDHdamua.class);
//                startActivity(i);
            }
        });

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("id");
                editor.apply();

                Intent intent = new Intent(TrangCaNhan.this, TrangDangNhap.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }


}
