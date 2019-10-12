package br.com.sossp.sosspapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import br.com.sossp.sosspapp.R;
import br.com.sossp.sosspapp.api.UserService;
import br.com.sossp.sosspapp.models.Genre;
import br.com.sossp.sosspapp.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserProfileActivity extends AppCompatActivity {

    private TextView tvTag;
    private TextView tvName;
    private TextView tvLastName;
    private TextView tvEmail;
    private TextView tvPassword;
    private TextView tvCpf;
    private TextView tvTelContact;
    private TextView tvGenre;
    private TextView tvDateBirth;

    private Retrofit retrofit;
    private UserService userService;

    public static final String API_BASE_URL = "http://10.0.2.2:8080/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.title_activity_profile_user);
        setContentView(R.layout.activity_user_profile);

        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);

        tvTag = findViewById(R.id.tvUserTag);
        tvName = findViewById(R.id.tvUserName);
        tvLastName = findViewById(R.id.tvUserLastName);
        tvEmail = findViewById(R.id.tvUserEmail);
        tvPassword = findViewById(R.id.tvUserPassword);
        tvCpf = findViewById(R.id.tvUserCpf);
        tvTelContact = findViewById(R.id.tvUserTel);
        tvGenre = findViewById(R.id.tvUserGenre);
        tvDateBirth = findViewById(R.id.tvUserDateBirth);

        Bundle extras = getIntent().getExtras();
        Long idUser = extras.getLong("idUser");

        getUser(idUser);

    }

    public void getUser(Long idUser) {

        Call<User> call = userService.getUser(idUser);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {
                    User user = response.body();
                    tvTag.setText(user.getTag());
                    tvName.setText(user.getName());
                    tvLastName.setText(user.getLastName());
                    tvEmail.setText(user.getEmail());
                    tvPassword.setText(user.getPassword());
                    tvCpf.setText(user.getCpf());
                    tvTelContact.setText(user.getTel());

                    if (user.getGenre() == Genre.MALE) {
                        tvGenre.setText("Masculino");
                    } else if (user.getGenre() == Genre.FEMININE) {
                        tvGenre.setText("Feminino");
                    } else if (user.getGenre() == Genre.OTHER) {
                        tvGenre.setText("Outro");
                    } else {
                        tvGenre.setText("Não especificado");
                    }

                    tvDateBirth.setText(user.getDateOfbirth());
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }
}
