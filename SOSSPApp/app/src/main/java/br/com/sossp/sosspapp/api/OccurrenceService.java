package br.com.sossp.sosspapp.api;

import java.util.List;

import br.com.sossp.sosspapp.models.Occurrence;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by wendellmod
 */

public interface OccurrenceService {

    @GET("user/{idUser}/occurrences")
    Call<List<Occurrence>> getOccurrencesUser(@Path("idUser") Long idUser);

    @GET("occurrences")
    Call<List<Occurrence>> getAllOccurrences();

    @GET("user/{idUser}/occurrence/{idOccurrence}")
    Call<Occurrence> getOccurrenceId(@Path("idUser") Long idUser,@Path("idOccurrence") Long idOccurrence);

    @POST("user/{idUser}/occurrence")
    Call<Occurrence> postOccurrence(@Path("idUser") Long idUser,@Body Occurrence occurrence);

}
