package travel.com.rest;

import java.util.List;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import travel.com.touristesTripsFilter.CitiesResponse;
import travel.com.touristesTripsFilter.CountriesResponse;

/**
 * Created by mostafa_anter on 1/1/17.
 */

public interface ApiInterface {
    @GET("lists/country")
    Observable<CountriesResponse> getCountries(@Header("Accept") String accept, @Header("Authorization") String authorization,
                                               @Header("From") String from, @Header("Accept-Language") String acceptLanguage,
                                               @Header("User-Agent") String userAgent);

    @GET("lists/city/{country_id}")
    Observable<CitiesResponse> getCities(@Header("Accept") String accept, @Header("Authorization") String authorization,
                                         @Header("From") String from, @Header("Accept-Language") String acceptLanguage,
                                         @Header("User-Agent") String userAgent,
                                         @Path("country_id") int country_id);

    @GET("lists/city/reg/eg")
    Observable<CitiesResponse> getCitiesOfEgy(@Header("Accept") String accept, @Header("Authorization") String authorization,
                                         @Header("From") String from, @Header("Accept-Language") String acceptLanguage,
                                         @Header("User-Agent") String userAgent);




//
//    @GET("api/version")
//    Observable<NewApkResponse> getApkVersion(@Header("Authorization") String authorization, @Header("Accept") String accept,
//                                             @Query("current") String current);
//
//
//    @FormUrlEncoded
//    @POST("oauth/token")
//    Observable<TokenResponse> getToken(@Field("grant_type") String grant_type,
//                                       @Field("client_id") String client_id,
//                                       @Field("client_secret") String client_secret,
//                                       @Field("scope") String scope);
//
//

}
