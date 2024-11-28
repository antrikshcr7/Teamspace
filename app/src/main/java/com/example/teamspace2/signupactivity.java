package com.example.teamspace2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class signupactivity extends AppCompatActivity {
    FirebaseAuth Auth;
    FirebaseFirestore db;
    EditText Email,Password,fullname;
    Button alr,Signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signupactivity);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        db =FirebaseFirestore.getInstance();
        Auth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        fullname = findViewById(R.id.name);
        Signup = findViewById(R.id.Signup);
        alr = findViewById(R.id.alr);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,pass,name;
                email = String.valueOf(Email.getText());
                pass = String.valueOf(Password.getText());
                name = String.valueOf(fullname.getText());

                Users user = new Users();
                user.setName(name);
                user.setEmail(email);
                user.setPass(pass);

                Auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        db.collection("users")
                                        .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        startActivity(new Intent(signupactivity.this,loginactivity.class));
                                        finish();
                                    }
                                });
                        Toast.makeText(signupactivity.this,"Account Created", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(signupactivity.this,task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                    }
                });

            }
        });
alr.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(signupactivity.this,loginactivity.class));
    }
});
    }
}