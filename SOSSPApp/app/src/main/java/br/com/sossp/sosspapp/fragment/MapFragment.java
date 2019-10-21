package br.com.sossp.sosspapp.fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import br.com.sossp.sosspapp.R;
import br.com.sossp.sosspapp.api.OccurrenceService;
import br.com.sossp.sosspapp.api.UserService;
import br.com.sossp.sosspapp.config.ConfigurationFirebase;
import br.com.sossp.sosspapp.config.ConfigurationRetrofit;
import br.com.sossp.sosspapp.models.Occurrence;
import br.com.sossp.sosspapp.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.core.content.ContextCompat.checkSelfPermission;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private ConfigurationRetrofit retrofit;
    private OccurrenceService occurrenceService;
    private UserService userService;
    private List<Occurrence> occurrenceList = new ArrayList<>();
    private LocationManager locationManager;
    private LocationListener locationListener;

    private GoogleMap mMap;

    public MapFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        retrofit = new ConfigurationRetrofit();
        retrofit.buildRetrofit();
        occurrenceService = retrofit.getRetrofit().create(OccurrenceService.class);
        userService = retrofit.getRetrofit().create(UserService.class);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        LatLng masp = new LatLng(-23.561060, -46.655903);
//        // -23.561060, -46.655903
//
//        mMap.addMarker(new MarkerOptions().position(masp).title("MASP"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(masp, 18));

        FirebaseUser firebaseUser = ConfigurationFirebase.getFirebaseAuth().getCurrentUser();
        String userEmail = firebaseUser.getEmail();

        getUserLocation();

        getUserByEmail(userEmail);

        getOccurrencesMap();

    }

    public void getUserLocation() {

        locationManager = (LocationManager)getActivity().getSystemService(getActivity().LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng myLocation = new LatLng(latitude, longitude);

                mMap.clear();
                mMap.addMarker(
                        new MarkerOptions()
                                .position(myLocation)
                                .title("Meu local")
                                .icon(BitmapDescriptorFactory.fromResource( R.drawable.userlocation ))
                );
                mMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(myLocation, 18)
                );

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    10000, 10, locationListener
            );
        }

    }

    public void getUserByEmail(String email) {

        Call<User> call = userService.getUserByEmail(email);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User userResponse = response.body();
                    Long idUser = userResponse.getUserId();



                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    public void getOccurrencesMap() {

        Call<List<Occurrence>> call = occurrenceService.getAllOccurrences();

        call.enqueue(new Callback<List<Occurrence>>() {
            @Override
            public void onResponse(Call<List<Occurrence>> call, Response<List<Occurrence>> response) {

                if (response.isSuccessful()) {

                    occurrenceList = response.body();

                    for (Occurrence occurrence : occurrenceList) {

                        double latitude = Double.valueOf(occurrence.getLatitude());
                        double longitude = Double.valueOf(occurrence.getLongitude());
                        LatLng occurrencesMapView = new LatLng(latitude, longitude);

                        mMap.addMarker(
                                new MarkerOptions()
                                        .position(occurrencesMapView)
                                        .title(occurrence.getTypeOccurrence())
                                        .icon(BitmapDescriptorFactory.fromResource( R.drawable.bandit ))
                        );

                    }

                }

            }

            @Override
            public void onFailure(Call<List<Occurrence>> call, Throwable t) {

            }
        });

    }

}