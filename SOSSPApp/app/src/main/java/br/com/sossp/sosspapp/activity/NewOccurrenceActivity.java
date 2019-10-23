package br.com.sossp.sosspapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import br.com.sossp.sosspapp.AddressConverter;
import br.com.sossp.sosspapp.R;
import br.com.sossp.sosspapp.api.OccurrenceService;
import br.com.sossp.sosspapp.config.ConfigurationRetrofit;
import br.com.sossp.sosspapp.models.Occurrence;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewOccurrenceActivity extends AppCompatActivity {

    private TextInputEditText txtTypeOccurence, txtDateOccurrence, txtCurrentDateOccurrence, txtAddressOccurrence;
    private Button btnRegisterOccurrence;

    private ConfigurationRetrofit retrofit;
    private OccurrenceService occurrenceService;
    private Occurrence occurrence;
    private AddressConverter addressConverter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_occurrence);

        retrofit = new ConfigurationRetrofit();
        retrofit.buildRetrofit();
        occurrenceService = retrofit.getRetrofit().create(OccurrenceService.class);

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
                double lat = addressConverter.getLocationFromAddress(NewOccurrenceActivity.this, addressOccurrence).latitude;
                double lng = addressConverter.getLocationFromAddress(NewOccurrenceActivity.this, addressOccurrence).longitude;

                occurrence = new Occurrence();
                occurrence.setTypeOccurrence(type);
                occurrence.setDateOccurrence(dateOccurrence);
                occurrence.setCurrentDate(currentDate);
                occurrence.setLatitude(String.valueOf(lat));
                occurrence.setLongitude(String.valueOf(lng));

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