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
import travel.com.signIn.SignInResponse;
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

    @FormUrlEncoded
    @POST("login")
    Observable<SignInResponse> login(@Header("Accept") String accept, @Header("Authorization") String authorization,
                                     @Header("From") String from, @Header("Accept-Language") String acceptLanguage,
                                     @Header("User-Agent") String userAgent, @Field("email") String email,
                                     @Field("password") String password);
    @FormUrlEncoded
    @POST("register")
    Observable<SignInResponse> SignUp(@Header("Accept") String accept, @Header("Authorization") String authorization,
                                     @Header("From") String from, @Header("Accept-Language") String acceptLanguage,
                                     @Header("User-Agent") String userAgent, @Field("name") String name,
                                     @Field("email") String email, @Field("mobile") String mobile,
                                      @Field("password") String password, @Field("password_confirmation") String passwordConfirmation);

}
