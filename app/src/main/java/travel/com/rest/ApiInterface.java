package travel.com.rest;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import travel.com.signIn.SignInResponse;
import travel.com.touristesTripResults.models.SearchResults;
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

    @GET
    Observable<SearchResults> search(@Header("Accept") String accept, @Header("Authorization") String authorization,
                                     @Header("From") String from, @Header("Accept-Language") String acceptLanguage,
                                     @Header("User-Agent") String userAgent,
                                     @Query("date") String date,
                                     @Query("price_from") String price_from,
                                     @Query("price_to") String price_to,
                                     @Query("category_id") String category_id,
                                     @Query("sub_category_id") String sub_category_id,
                                     @Query("region") String region,
                                     @Query("member_id") String member_id,
                                     @Query("country_id") String country_id,
                                     @Query("city_id") String city_id,
                                     @Query("has_internet") String has_internet,
                                     @Query("has_parking") String has_parking,
                                     @Query("allow_pets") String allow_pets);


    @FormUrlEncoded
    @POST("login")
    Observable<SignInResponse> login(@Header("Accept") String accept, @Header("Authorization") String authorization,
                                     @Header("From") String from, @Header("Accept-Language") String acceptLanguage,
                                     @Header("User-Agent") String userAgent, @Field("email") String email,
                                     @Field("password") String password);

    @Multipart
    @POST("register")
    Observable<SignInResponse> signUp(@Header("Accept") String accept,
                                      @Header("Authorization") String authorization,
                                      @Header("From") String from,
                                      @Header("Accept-Language") String acceptLanguage,
                                      @Header("User-Agent") String userAgent,
                                      @Part("name") RequestBody name,
                                      @Part("email") RequestBody email,
                                      @Part("mobile") RequestBody mobile,
                                      @Part("password") RequestBody password,
                                      @Part("password_confirmation") RequestBody passwordConfirmation,
                                      @Part MultipartBody.Part fileToUpload);

}
