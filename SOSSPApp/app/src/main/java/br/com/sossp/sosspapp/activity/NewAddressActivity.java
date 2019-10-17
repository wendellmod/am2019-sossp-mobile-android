package br.com.sossp.sosspapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import br.com.sossp.sosspapp.R;
import br.com.sossp.sosspapp.api.AddressService;
import br.com.sossp.sosspapp.config.ConfigurationRetrofit;
import br.com.sossp.sosspapp.models.Address;
import br.com.sossp.sosspapp.models.State;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewAddressActivity extends AppCompatActivity {

    private EditText txtZipcode, txtNumAddress;
    private TextInputEditText txtAddressName, txtNeighborhood, txtCity, txtState, txtComplement;
    private Button btnRegisterAddress;

    private ConfigurationRetrofit retrofit;
    private AddressService addressService;
    private Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.title_activity_new_address);
        setContentView(R.layout.activity_new_address);

        retrofit = new ConfigurationRetrofit();
        retrofit.buildRetrofit();
        addressService = retrofit.getRetrofit().create(AddressService.class);

        txtZipcode = findViewById(R.id.inputZipcode);
        txtAddressName = findViewById(R.id.inputAddressName);
        txtNeighborhood = findViewById(R.id.inputNeighborhood);
        txtCity = findViewById(R.id.inputCity);
        txtState = findViewById(R.id.inputState);
        txtNumAddress = findViewById(R.id.inputNumAddress);
        txtComplement = findViewById(R.id.inputComplement);
        btnRegisterAddress = findViewById(R.id.btnRegisterAddress);

        Bundle extras = getIntent().getExtras();
        final Long idUser = extras.getLong("idUser");

        btnRegisterAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Long zipcode = Long.parseLong(txtZipcode.getText().toString());
                String addressName = txtAddressName.getText().toString();
                String neighborhood = txtNeighborhood.getText().toString();
                String city = txtCity.getText().toString();
                int state = Integer.parseInt(txtState.getText().toString());
                int numberAddress = Integer.parseInt(txtNumAddress.getText().toString());
                String complement = txtComplement.getText().toString();

                address = new Address();
                address.setZipcode(zipcode);
                address.setAddressName(addressName);
                address.setNeighborhood(neighborhood);
                address.setCity(city);

                if (state == 0) {
                    address.setState(State.SP);
                } else {
                    address.setState(State.SP);
                }

                address.setNumber(numberAddress);
                address.setComplement(complement);

                saveAddress(idUser,address);

            }
        });

    }

    public void saveAddress(Long idUser, Address address) {

        Call<Address> call = addressService.postAddress(idUser ,address);

        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {

            }
        });

        Toast.makeText(NewAddressActivity.this, "Endereço cadastrado com sucesso!", Toast.LENGTH_LONG).show();
        finish();

    }
}
