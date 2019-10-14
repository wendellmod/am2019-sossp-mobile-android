package br.com.sossp.sosspapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.sossp.sosspapp.R;
import br.com.sossp.sosspapp.RecyclerItemClickListener;
import br.com.sossp.sosspapp.adapter.OccurrencesAdapter;
import br.com.sossp.sosspapp.api.OccurrenceService;
import br.com.sossp.sosspapp.models.Address;
import br.com.sossp.sosspapp.models.Occurrence;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserListOccurrencesActivity extends AppCompatActivity {

    private RecyclerView recyclerOccurrence;
    private List<Occurrence> occurrenceList = new ArrayList<>();

    private Retrofit retrofit;
    private OccurrenceService occurrenceService;

    public static final String API_BASE_URL = "http://10.0.2.2:8080/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.title_activity_list_occurrences);
        setContentView(R.layout.activity_user_list_occurrences);

        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        occurrenceService = retrofit.create(OccurrenceService.class);

        Bundle extras = getIntent().getExtras();
        final Long idUser = extras.getLong("idUser");

        recyclerOccurrence = findViewById(R.id.recyclerOccurrences);

        // List Occurrences
        getListOccurrences(idUser);

        // Config RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        // Config RecyclerOccurrences
        recyclerOccurrence.setLayoutManager(layoutManager);
        recyclerOccurrence.setHasFixedSize(true);
        recyclerOccurrence.addItemDecoration( new DividerItemDecoration(this, LinearLayout.VERTICAL));

        // Click event for recyclerOccurrence
        recyclerOccurrence.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerOccurrence,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Occurrence occurrence = occurrenceList.get(position);
                                Long idOccurrence = occurrence.getOccurrenceId();
                                startActivity(new Intent(getApplicationContext(), UserOccurrenceActivity.class)
                                        .putExtra("idUser", idUser)
                                        .putExtra("idOccurrence", idOccurrence));
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

    public void getListOccurrences(Long idUser) {

        Call<List<Occurrence>> call = occurrenceService.getOccurrence(idUser);

        call.enqueue(new Callback<List<Occurrence>>() {
            @Override
            public void onResponse(Call<List<Occurrence>> call, Response<List<Occurrence>> response) {

                if (response.isSuccessful()) {

                    List<Occurrence> occurrences = response.body();

                    OccurrencesAdapter occurrencesAdapter = new OccurrencesAdapter(occurrenceList);

                    for (Occurrence occurrence : occurrences) {
                        occurrenceList.add(occurrence);
                    }

                    recyclerOccurrence.setAdapter(occurrencesAdapter);

                }

            }

            @Override
            public void onFailure(Call<List<Occurrence>> call, Throwable t) {

            }
        });
    }
}