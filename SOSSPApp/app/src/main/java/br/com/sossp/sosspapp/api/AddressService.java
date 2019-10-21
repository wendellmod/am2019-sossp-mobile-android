package br.com.sossp.sosspapp.api;

import java.util.List;

import br.com.sossp.sosspapp.models.Address;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by wendellmod
 */

public interface AddressService {

    @GET("user/{idUser}/addresses")
    Call<List<Address>> getAddressesUser(@Path("idUser") Long idUser);

    @GET("address")
    Call<Address> getAddressId(@Query(value = "idUser", encoded = true) Long idUser,
                               @Query(value = "zipcode", encoded = true) Long zipcode,
                               @Query(value = "numberAddress", encoded = true) Integer numberAddress);

    @POST("user/{idUser}/address")
    Call<Address> postAddress(@Path("idUser") Long idUser, @Body Address address);

}
