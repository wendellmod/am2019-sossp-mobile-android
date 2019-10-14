package br.com.sossp.sosspapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import br.com.sossp.sosspapp.AddressConverter;
import br.com.sossp.sosspapp.R;
import br.com.sossp.sosspapp.api.OccurrenceService;
import br.com.sossp.sosspapp.models.Occurrence;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserOccurrenceActivity extends AppCompatActivity {

    private TextView tvTypeOuser, tvDateOuser, tvCurrentOuser, tvStatusOuser, tvAddressOuser;

    private Retrofit retrofit;
    private OccurrenceService occurrenceService;
    private AddressConverter addressConverter;

    public static final String API_BASE_URL = "http://10.0.2.2:8080/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.title_activity_user_occurrence);
        setContentView(R.layout.activity_user_occurrence);

        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        occurrenceService = retrofit.create(OccurrenceService.class);

        tvTypeOuser = findViewById(R.id.tvTypeOuser);
        tvDateOuser = findViewById(R.id.tvDateOuser);
        tvCurrentOuser = findViewById(R.id.tvCurrentOuser);
        tvStatusOuser = findViewById(R.id.tvStatusOuser);
        tvAddressOuser = findViewById(R.id.tvAddressOuser);

        Bundle extras = getIntent().getExtras();
        final Long idUser = extras.getLong("idUser");
        final Long idOccurrence = extras.getLong("idOccurrence");

        getOccurrence(idUser, idOccurrence);

    }

    public void getOccurrence(Long idUser, Long idOccurrence) {

        Call<Occurrence> call = occurrenceService.getOccurrenceId(idUser, idOccurrence);

        call.enqueue(new Callback<Occurrence>() {
            @Override
            public void onResponse(Call<Occurrence> call, Response<Occurrence> response) {
                if (response.isSuccessful()) {
                    Occurrence occurrence = response.body();
                    addressConverter = new AddressConverter();
                    double latitude = occurrence.getLatitude();
                    double longitude = occurrence.getLongitude();
                    String address = addressConverter.getCompleteAddressString(getApplicationContext(), latitude, longitude);

                    tvTypeOuser.setText(occurrence.getTypeOccurrence());
                    tvDateOuser.setText(occurrence.getDateOccurrence());
                    tvCurrentOuser.setText(occurrence.getCurrentDate());
                    if (occurrence.isStatus() == true) {
                        tvStatusOuser.setText("Solucionado");
                    } else {
                        tvStatusOuser.setText("Não solucionado");
                    }
                    tvAddressOuser.setText(address);

                }
            }

            @Override
            public void onFailure(Call<Occurrence> call, Throwable t) {

            }
        });

    }
}
