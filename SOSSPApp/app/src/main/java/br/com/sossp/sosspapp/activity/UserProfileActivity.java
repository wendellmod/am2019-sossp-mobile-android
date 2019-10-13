package br.com.sossp.sosspapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.sossp.sosspapp.R;
import br.com.sossp.sosspapp.RecyclerItemClickListener;
import br.com.sossp.sosspapp.adapter.AddressesAdapter;
import br.com.sossp.sosspapp.api.AddressService;
import br.com.sossp.sosspapp.api.UserService;
import br.com.sossp.sosspapp.models.Address;
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

    private TextView tvBtnNewAddress;

    private RecyclerView recyclerAddresses;
    private List<Address> addressList = new ArrayList<>();

    private Retrofit retrofit;
    private UserService userService;
    private AddressService addressService;

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
        addressService = retrofit.create(AddressService.class);

        tvTag = findViewById(R.id.tvUserTag);
        tvName = findViewById(R.id.tvUserName);
        tvLastName = findViewById(R.id.tvUserLastName);
        tvEmail = findViewById(R.id.tvUserEmail);
        tvPassword = findViewById(R.id.tvUserPassword);
        tvCpf = findViewById(R.id.tvUserCpf);
        tvTelContact = findViewById(R.id.tvUserTel);
        tvGenre = findViewById(R.id.tvUserGenre);
        tvDateBirth = findViewById(R.id.tvUserDateBirth);

        tvBtnNewAddress = findViewById(R.id.txtBtnNewAddress);

        Bundle extras = getIntent().getExtras();
        final Long idUser = extras.getLong("idUser");

        getUser(idUser);

        tvBtnNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewAddressActivity.class).putExtra("idUser", idUser));
            }
        });

        recyclerAddresses = findViewById(R.id.recyclerAddresses);

        // List Addresses
        getListAddress(idUser);

        // Config RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        // Config recyclerAddresses
        recyclerAddresses.setLayoutManager(layoutManager);
        recyclerAddresses.setHasFixedSize(true);
        recyclerAddresses.addItemDecoration( new DividerItemDecoration(this, LinearLayout.VERTICAL));

        // click event for recyclerAddres
        recyclerAddresses.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerAddresses,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Address address = addressList.get(position);
                                Long zipcode = address.getZipcode();
                                startActivity(new Intent(getApplicationContext(), UserAddressActivity.class)
                                        .putExtra("idUser", idUser)
                                        .putExtra("idAddress", zipcode));
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );

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

    public void getListAddress(Long idUser) {

        Call<List<Address>> call = addressService.getAddress(idUser);

        call.enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {

                if (response.isSuccessful()) {

                    List<Address> addresses = response.body();

                    AddressesAdapter adapterAddress = new AddressesAdapter(addressList);

                    for (Address address : addresses) {
                        addressList.add(address);
                    }

                    recyclerAddresses.setAdapter(adapterAddress);

                }

            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {

            }
        });

    }

}
