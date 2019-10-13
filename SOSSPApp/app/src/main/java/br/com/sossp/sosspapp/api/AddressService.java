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

/**
 * Created by wendellmod
 */

public interface AddressService {

    @GET("user/{idUser}/address")
    Call<List<Address>> getAddress(@Path("idUser") Long idUser);

    @GET("user/{idUser}/address/{idAddress}")
    Call<Address> getAddressId(@Path("idUser") Long idUser,@Path("idAddress") Long idAddress);

    @POST("user/{idUser}/address")
    Call<Address> postAddress(@Path("idUser") Long idUser, @Body Address address);

    @PUT("user/{idUser}/address/{idAddress}")
    Call<Address> putAddress(@Path("idUser") Long idUser, @Path("idAddress") Long idAddress, @Body Address address);

    @DELETE("user/{idUser}/address/{idAddress}")
    Call<Void> deleteAddress(@Path("idUser") Long idUser, @Path("idAddress") Long idAddress);

}
