package br.com.sossp.sosspapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import br.com.sossp.sosspapp.R;
import br.com.sossp.sosspapp.api.UserService;
import br.com.sossp.sosspapp.config.ConfigurationFirebase;
import br.com.sossp.sosspapp.models.Genre;
import br.com.sossp.sosspapp.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private EditText txtEmail, txtPassword, txtConfirmPass, txtDateOfBirth, txtTel;
    private TextInputEditText txtName, txtLastName, txtTag, txtCpf, txtGender;
    private Button btnConfirm;

    private FirebaseAuth firebaseAuth;
    private Retrofit retrofit;
    private UserService userService;
    private User user;

    public static final String API_BASE_URL = "http://10.0.2.2:8080/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_register);

        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);

        txtEmail = findViewById(R.id.inputEmail);
        txtPassword = findViewById(R.id.inputPassword);
        txtConfirmPass = findViewById(R.id.inputPasswordAgain);

        txtName = findViewById(R.id.inputName);
        txtLastName = findViewById(R.id.inputLastName);
        txtTag = findViewById(R.id.inputTag);
        txtCpf = findViewById(R.id.inputCpf);

        txtDateOfBirth = findViewById(R.id.inputDateOfBirth);
        txtGender = findViewById(R.id.inputGender);
        txtTel = findViewById(R.id.inputTel);

        btnConfirm = findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = txtEmail.getText().toString();
                String pass = txtPassword.getText().toString();
                String confirmPass = txtConfirmPass.getText().toString();
                String name = txtName.getText().toString();
                String lastName = txtLastName.getText().toString();
                String tag = txtTag.getText().toString();
                String cpf = txtCpf.getText().toString();
                String dateOfBirth = txtDateOfBirth.getText().toString();
                Integer gender = Integer.parseInt(txtGender.getText().toString());
                String tel = txtTel.getText().toString();


                user = new User();
                user.setEmail(email);
                user.setPassword(pass);
                user.setName(name);
                user.setLastName(lastName);
                user.setTag(tag);
                user.setCpf(cpf);
                user.setDateOfbirth(dateOfBirth);

                if (gender == 0) {
                    user.setGenre(Genre.MALE);
                } else if (gender == 1) {
                    user.setGenre(Genre.FEMININE);
                } else if (gender == 2) {
                    user.setGenre(Genre.OTHER);
                } else {
                    user.setGenre(null);
                }

                user.setTel(tel);

                authEmail();

            }
        });

    }

    private void saveUser(User user) {

        Call<User> call = userService.postUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {
                    User userResponse = response.body();
                    System.out.println(userResponse.getName());
                    System.out.println("Usuário cadastrado!");
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    public void authEmail() {

        firebaseAuth = ConfigurationFirebase.getFirebaseAuth();
        firebaseAuth.createUserWithEmailAndPassword(
                user.getEmail(), user.getPassword()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ){

                    saveUser(user);
                    finish();

                }else {

                    String exception = "";
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthWeakPasswordException e){
                        exception = "Digite uma senha mais forte!";
                    }catch ( FirebaseAuthInvalidCredentialsException e){
                        exception = "Por favor, digite um e-mail válido";
                    }catch ( FirebaseAuthUserCollisionException e){
                        exception = "Este conta já foi cadastrada";
                    }catch (Exception e){
                        exception = "Erro ao cadastrar usuário: "  + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(RegisterActivity.this,
                            exception, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
