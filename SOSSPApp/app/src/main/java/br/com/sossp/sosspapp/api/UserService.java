package br.com.sossp.sosspapp.api;

import br.com.sossp.sosspapp.models.User;
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

public interface UserService {

    @GET("user")
    Call<User> getUserByEmail(@Query(value = "userEmail", encoded = true) String userEmail);

    @GET("user/{idUser}")
    Call<User> getUser(@Path("idUser") Long idUser);

    @POST("user")
    Call<User> postUser(@Body User user);

    @PUT("user/{idUser}")
    Call<User> putUser(@Path("idUser") Long idUser, @Body User user);

    @DELETE("user/{idUser}")
    Call<Void> deleteUser(@Path("idUser") Long idUser);

}
