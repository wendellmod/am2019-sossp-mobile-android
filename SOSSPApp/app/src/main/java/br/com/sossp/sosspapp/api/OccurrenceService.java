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

    @GET("user/{idUser}/occurrence")
    Call<List<Occurrence>> getOccurrence();

    @GET("user/{idUser}/occurrence/{idOccurrence}")
    Call<Occurrence> getOccurrenceId(@Path("idOccurrence") Long idOccurrence);

    @POST("user/{idUser}/occurrence")
    Call<Occurrence> postOccurrence(@Body Occurrence occurrence);

}
