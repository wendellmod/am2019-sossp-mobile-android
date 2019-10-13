package br.com.sossp.sosspapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import br.com.sossp.sosspapp.R;
import br.com.sossp.sosspapp.api.AddressService;
import br.com.sossp.sosspapp.models.Address;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAddressActivity extends AppCompatActivity {

    private TextView tvAddressComplete;

    private Retrofit retrofit;
    private AddressService addressService;

    public static final String API_BASE_URL = "http://10.0.2.2:8080/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.title_activity_user_address);
        setContentView(R.layout.activity_user_address);

        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        addressService = retrofit.create(AddressService.class);

        tvAddressComplete = findViewById(R.id.tvAddressComplete);

        Bundle extras = getIntent().getExtras();
        final Long idUser = extras.getLong("idUser");
        final Long idAddress = extras.getLong("idAddress");

        getAddressById(idUser, idAddress);
    }

    public void getAddressById(Long idUser, Long idAddress) {

        Call<Address> call = addressService.getAddressId(idUser, idAddress);

        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {

                if (response.isSuccessful()) {
                    Address address = response.body();
                    tvAddressComplete.setText(
                            address.getAddressName() + ", " +
                            address.getNumber() + " - " +
                            address.getNeighborhood() + ", " +
                            address.getCity() + " - " +
                            address.getState() + ", " +
                            address.getZipcode() + " - Complemento: " +
                            address.getComplement());
                }

            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {

            }
        });

    }
}
