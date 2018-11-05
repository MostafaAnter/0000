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
import retrofit2.http.Url;
import rx.Observable;
import travel.com.bookTrip.models.ReservationResponse;
import travel.com.myBookings.models.MyReservationsResponse;
import travel.com.signIn.SignInResponse;
import travel.com.touristesCompanies.models.CommentResponse;
import travel.com.touristesCompanies.models.CompaniesResponse;
import travel.com.touristesCompanies.models.RateResponse;
import travel.com.touristesTripDetail.models.TripCommentResponse;
import travel.com.touristesTripResults.models.SearchResults;
import travel.com.touristesTripsFilter.CitiesResponse;
import travel.com.touristesTripsFilter.CountriesResponse;
import travel.com.touristesTripsFilter.models.TripCategoriesResponse;
import travel.com.touristesTripsFilter.models.TripSubCategoryResponse;

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

    @GET("lists/category")
    Observable<TripCategoriesResponse> getTripsCategories(@Header("Accept") String accept, @Header("Authorization") String authorization,
                                                          @Header("From") String from, @Header("Accept-Language") String acceptLanguage,
                                                          @Header("User-Agent") String userAgent);

    @GET("lists/sub_category/{category_id}")
    Observable<TripSubCategoryResponse> getTripsSubCategories(@Header("Accept") String accept, @Header("Authorization") String authorization,
                                                              @Header("From") String from, @Header("Accept-Language") String acceptLanguage,
                                                              @Header("User-Agent") String userAgent,
                                                              @Path("category_id") String category_id);


    @GET(".")
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

    @GET(".")
    Observable<SearchResults> searchNextUrl(@Header("Accept") String accept, @Header("Authorization") String authorization,
                                     @Header("From") String from, @Header("Accept-Language") String acceptLanguage,
                                     @Header("User-Agent") String userAgent);


    @GET("companies")
    Observable<CompaniesResponse> getCompanies(@Header("Accept") String accept, @Header("Authorization") String authorization,
                                               @Header("From") String from, @Header("Accept-Language") String acceptLanguage,
                                               @Header("User-Agent") String userAgent);

    @GET(".")
    Observable<CompaniesResponse> getNextCompanies(@Header("Accept") String accept, @Header("Authorization") String authorization,
                                               @Header("From") String from, @Header("Accept-Language") String acceptLanguage,
                                               @Header("User-Agent") String userAgent);


    @FormUrlEncoded
    @POST("companies/comment")
    Observable<CommentResponse> commentOnCompany(@Header("Accept") String accept, @Header("Authorization") String authorization,
                                                 @Header("From") String from, @Header("Accept-Language") String acceptLanguage,
                                                 @Header("User-Agent") String userAgent, @Field("company_id") String company_id,
                                                 @Field("comment") String comment);


    @FormUrlEncoded
    @POST("companies/rate")
    Observable<RateResponse> rateOnCompany(@Header("Accept") String accept, @Header("Authorization") String authorization,
                                           @Header("From") String from, @Header("Accept-Language") String acceptLanguage,
                                           @Header("User-Agent") String userAgent, @Field("company_id") String company_id,
                                           @Field("rate") String rate);



    /*1 للغرفة الفردية 2 للغرفة المزدوجة 3 للغرفة الثلاثية وخلى بالك يعنى لما يكون عدد الغرف 2 يكون فية 2 ولازم يختار النوع بتاع كل غرفة

child_ages[]2
هيبقى 2 فئات عمرية 1 ( 0 الى 5) سنوات و 2 (5 الى 11) سنوات وبرضوا هيتوقف على عدد الاطفال

payment_method1
هتبقى 2 يا اما هيبقى 1 الدفع عن طريق احد مكاتبنا 2 الدفع كاش عن طريق احد مندوبينا

notefdsf
دى هتبقى يعنى يكتب ملاحظاتة*/

    @FormUrlEncoded
    @POST("trips/reservation")
    Observable<ReservationResponse> reserveTrip(@Header("Accept") String accept, @Header("Authorization") String authorization,
                                                @Header("From") String from, @Header("Accept-Language") String acceptLanguage,
                                                @Header("User-Agent") String userAgent,
                                                @Field("trip_id") String trip_id,
                                                @Field("adult_count") String adult_count,
                                                @Field("child_count") String child_count,
                                                @Field("room_count") String room_count,
                                                @Field("room_types[]") List<String> room_types,
                                                @Field("child_ages[]") List<String> child_ages,
                                                @Field("payment_method") String payment_method,
                                                @Field("note") String note);


    /*دا لعرض الرحلات المحجوزة للعضو طيب لو عايز بقى تعمل لحالة معينة يبقى url/0 'r_status_0' => ' فى انتظار السعر ', 'r_status_1' => ' فى انتظار تاكيد العميل', 'r_status_2' => ' فى انتظار تاكيد الدفع ', 'r_status_3' => ' تم الحجز ',*/

    @GET("member/reservations/{status}")
    Observable<MyReservationsResponse> getReservations(@Header("Accept") String accept, @Header("Authorization") String authorization,
                                                       @Header("From") String from, @Header("Accept-Language") String acceptLanguage,
                                                       @Header("User-Agent") String userAgent,
                                                       @Path("status") String status);
    @GET(".")
    Observable<MyReservationsResponse> getNextReservations(@Header("Accept") String accept, @Header("Authorization") String authorization,
                                                       @Header("From") String from, @Header("Accept-Language") String acceptLanguage,
                                                       @Header("User-Agent") String userAgent);

    @FormUrlEncoded
    @POST("trips/comment")
    Observable<TripCommentResponse> commentOnTrip(@Header("Accept") String accept, @Header("Authorization") String authorization,
                                                  @Header("From") String from, @Header("Accept-Language") String acceptLanguage,
                                                  @Header("User-Agent") String userAgent, @Field("trip_id") String trip_id,
                                                  @Field("comment") String comment);

    @FormUrlEncoded
    @POST("trips/rate")
    Observable<RateResponse> rateOnTrip(@Header("Accept") String accept, @Header("Authorization") String authorization,
                                           @Header("From") String from, @Header("Accept-Language") String acceptLanguage,
                                           @Header("User-Agent") String userAgent, @Field("trip_id") String company_id,
                                           @Field("rate") String rate);




    @FormUrlEncoded
    @POST("login")
    Observable<SignInResponse> login(@Header("Accept") String accept, @Header("Authorization") String authorization,
                                     @Header("From") String from, @Header("Accept-Language") String acceptLanguage,
                                     @Header("User-Agent") String userAgent, @Field("email") String email,
                                     @Field("password") String password);
    @FormUrlEncoded
    @POST("login/google")
    Observable<SignInResponse> loginSocial(@Header("Accept") String accept, @Header("Authorization") String authorization,
                                     @Header("From") String from, @Header("Accept-Language") String acceptLanguage,
                                     @Header("User-Agent") String userAgent, @Field("token") String token);

    @FormUrlEncoded
    @POST("login/facebook")
    Observable<SignInResponse> loginSocialf(@Header("Accept") String accept, @Header("Authorization") String authorization,
                                           @Header("From") String from, @Header("Accept-Language") String acceptLanguage,
                                           @Header("User-Agent") String userAgent, @Field("token") String token);

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
