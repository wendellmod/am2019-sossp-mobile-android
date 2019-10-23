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

import java.util.ArrayList;
import java.util.List;

import br.com.sossp.sosspapp.R;
import br.com.sossp.sosspapp.RecyclerItemClickListener;
import br.com.sossp.sosspapp.adapter.OccurrencesAdapter;
import br.com.sossp.sosspapp.api.OccurrenceService;
import br.com.sossp.sosspapp.config.ConfigurationRetrofit;
import br.com.sossp.sosspapp.models.Occurrence;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListOccurrencesActivity extends AppCompatActivity {

    private RecyclerView recyclerOccurrence;
    private List<Occurrence> occurrenceList = new ArrayList<>();

    private ConfigurationRetrofit retrofit;
    private OccurrenceService occurrenceService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_occurrences);

        retrofit = new ConfigurationRetrofit();
        retrofit.buildRetrofit();
        occurrenceService = retrofit.getRetrofit().create(OccurrenceService.class);

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
                                long occurrenceId = occurrence.getOccurrenceId();
                                startActivity(new Intent(getApplicationContext(), UserOccurrenceActivity.class)
                                        .putExtra("idUser", idUser)
                                        .putExtra("occurrenceId", occurrenceId));
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

        Call<List<Occurrence>> call = occurrenceService.getOccurrencesUser(idUser);

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