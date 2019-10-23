package br.com.sossp.sosspapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import br.com.sossp.sosspapp.R;
import br.com.sossp.sosspapp.api.AddressService;
import br.com.sossp.sosspapp.config.ConfigurationRetrofit;
import br.com.sossp.sosspapp.models.Address;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAddressActivity extends AppCompatActivity {

    private TextView tvAddressComplete;

    private ConfigurationRetrofit retrofit;
    private AddressService addressService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address);

        retrofit = new ConfigurationRetrofit();
        retrofit.buildRetrofit();
        addressService = retrofit.getRetrofit().create(AddressService.class);

        tvAddressComplete = findViewById(R.id.tvAddressComplete);

        Bundle extras = getIntent().getExtras();
        final Long idUser = extras.getLong("idUser");
        final Long idAddress = extras.getLong("idAddress");
        final Integer numberAddress = extras.getInt("numberAddress");

        getAddressById(idUser, idAddress, numberAddress);
    }

    public void getAddressById(Long idUser, Long idAddress, Integer numberAddress) {

        Call<Address> call = addressService.getAddressId(idUser, idAddress, numberAddress);

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
