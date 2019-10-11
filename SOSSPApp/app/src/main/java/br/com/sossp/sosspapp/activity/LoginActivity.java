package br.com.sossp.sosspapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import br.com.sossp.sosspapp.R;
import br.com.sossp.sosspapp.config.ConfigurationFirebase;
import br.com.sossp.sosspapp.models.User;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText txtEmail;
    private EditText txtPassword;
    private FirebaseAuth firebaseAuth;
    private Button btnSignInEmail;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();

        txtEmail = findViewById(R.id.inputTextEmail);
        txtPassword = findViewById(R.id.inputTextPassword);
        btnSignInEmail = findViewById(R.id.btnSignInEmail);

        btnSignInEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();

                if (!email.isEmpty()) {
                    if (!password.isEmpty()) {

                        user = new User();
                        user.setEmail(email);
                        user.setPassword(password);
                        validateLogin();

                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Preencha a senha!",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this,
                            "Preencha o email!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void validateLogin() {

        firebaseAuth = ConfigurationFirebase.getFirebaseAuth();
        firebaseAuth.signInWithEmailAndPassword(
                user.getEmail(),
                user.getPassword()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ){

                    goToMainActivity();

                }else {

                    String exception = "";
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthInvalidUserException e ) {
                        exception = "Usuário não está cadastrado.";
                    }catch ( FirebaseAuthInvalidCredentialsException e ){
                        exception = "E-mail e senha não correspondem a um usuário cadastrado";
                    }catch (Exception e){
                        exception = "Erro ao cadastrar usuário: "  + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(LoginActivity.this,
                            exception, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void newUser(View view){
        startActivity(new Intent(this, SignUpOptionsActivity.class));
    }

}
