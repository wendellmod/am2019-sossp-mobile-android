package br.com.sossp.sosspapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import br.com.sossp.sosspapp.R;

public class SignUpOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_sign_up_options);
    }

    public void signUpEmail(View view){
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void  signUpGoogle(View view){

    }

    public void  signUpFacebook(View view){

    }
}
