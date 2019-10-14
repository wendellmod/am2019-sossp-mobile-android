package br.com.sossp.sosspapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;

import br.com.sossp.sosspapp.AddressConverter;
import br.com.sossp.sosspapp.R;
import br.com.sossp.sosspapp.api.OccurrenceService;
import br.com.sossp.sosspapp.models.Occurrence;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewOccurrenceActivity extends AppCompatActivity {

    private TextInputEditText txtTypeOccurence, txtDateOccurrence, txtCurrentDateOccurrence, txtAddressOccurrence;
    private Button btnRegisterOccurrence;

    private Retrofit retrofit;
    private OccurrenceService occurrenceService;
    private Occurrence occurrence;
    private AddressConverter addressConverter;

    public static final String API_BASE_URL = "http://10.0.2.2:8080/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.title_activity_new_occurrence);
        setContentView(R.layout.activity_new_occurrence);

        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        occurrenceService = retrofit.create(OccurrenceService.class);

        txtTypeOccurence = findViewById(R.id.txtTypeOccurence);
        txtDateOccurrence = findViewById(R.id.txtDateOccurrence);
        txtCurrentDateOccurrence = findViewById(R.id.txtCurrentDateOccurrence);
        txtAddressOccurrence = findViewById(R.id.txtAddressOccurrence);

        btnRegisterOccurrence = findViewById(R.id.btnRegisterOccurrence);

        Bundle extras = getIntent().getExtras();
        final Long idUser = extras.getLong("idUser");

        btnRegisterOccurrence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addressConverter = new AddressConverter();

                String type = txtTypeOccurence.getText().toString();
                String dateOccurrence = txtDateOccurrence.getText().toString();
                String currentDate = txtCurrentDateOccurrence.getText().toString();
                String addressOccurrence = txtAddressOccurrence.getText().toString();
                double lat = addressConverter.getLocationFromAddress(getApplicationContext(), addressOccurrence).latitude;
                double lng = addressConverter.getLocationFromAddress(getApplicationContext(), addressOccurrence).longitude;

                occurrence = new Occurrence();
                occurrence.setTypeOccurrence(type);
                occurrence.setDateOccurrence(dateOccurrence);
                occurrence.setCurrentDate(currentDate);
                occurrence.setLatitude(lat);
                occurrence.setLongitude(lng);

                saveOccurrence(idUser, occurrence);

            }
        });

    }

    public void saveOccurrence(Long idUser, Occurrence occurrence) {

        Call<Occurrence> call = occurrenceService.postOccurrence(idUser, occurrence);

        call.enqueue(new Callback<Occurrence>() {
            @Override
            public void onResponse(Call<Occurrence> call, Response<Occurrence> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<Occurrence> call, Throwable t) {

            }
        });

        Toast.makeText(NewOccurrenceActivity.this, "Ocorrência cadastrada com sucesso!", Toast.LENGTH_LONG).show();
        finish();

    }
}
