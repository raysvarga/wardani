package com.example.wardani.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wardani.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }

        email = findViewById(R.id.textEmail);
        password = findViewById(R.id.textPassword);
    }

    public void login(View view) {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Masukkan username anda!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "Masukkan password anda!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(userPassword.length() < 6){
            Toast.makeText(this, "Password anda terlalu pendek, masukkan minimal 6 karakter!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(userEmail,userPassword)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "Berhasil Masuk", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                }else {
                                    Toast.makeText(LoginActivity.this, "Gagal Masuk"+task.getException(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
    }

    public void signup(View view) {
        startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
    }
    public void togglePasswordVisibility(View view) {
        EditText passwordEditText = findViewById(R.id.textPassword);

        // Periksa apakah klik terjadi pada ikon mata
        if (view.getTag() != null && view.getTag().equals("password_toggle")) {
            boolean isPasswordVisible = passwordEditText.getInputType() ==
                    (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

            // Mengubah ikon mata terbuka atau mata tertutup
            int drawableResId = isPasswordVisible ? R.drawable.hide_password24 : R.drawable.unhide_password24;
            passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableResId, 0);

            // Mengubah tipe input EditText berdasarkan keadaan
            passwordEditText.setInputType(
                    isPasswordVisible ?
                            (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD) :
                            (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
            );
            passwordEditText.setTypeface(Typeface.DEFAULT);

            // Memindahkan kursor ke akhir teks
            passwordEditText.setSelection(passwordEditText.getText().length());
        }
    }


}