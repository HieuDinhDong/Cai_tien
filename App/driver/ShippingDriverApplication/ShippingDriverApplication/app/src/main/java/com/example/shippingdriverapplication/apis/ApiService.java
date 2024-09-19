package com.example.shippingdriverapplication.apis;


import com.example.shippingdriverapplication.models.responses.DriverResponse;
import com.example.shippingdriverapplication.models.responses.ListOrderResponse;
import com.example.shippingdriverapplication.models.responses.ListTypeOrderResponse;
import com.example.shippingdriverapplication.models.responses.ObjectResponse;
import com.example.shippingdriverapplication.models.responses.OrderResponse;
import com.example.shippingdriverapplication.models.responses.UserResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    ApiService apiService = ApiConfig.getClient("http:/192.168.98.193:8000")
            .create(ApiService.class);


    @GET("/api/service/login_user")
    Call<UserResponse> loginUser(@Query("phone") String phone,
                                 @Query("password") String password);

    @GET("/api/service/login_driver")
    Call<DriverResponse> loginDriver(@Query("phone") String phone,
                                     @Query("password") String password);


    @GET("/api/service/driver_detail/{id}")
    Call<UserResponse> getUserDetail(@Path("id") int id);

    @GET("/api/service/driver_detail/{id}")
    Call<DriverResponse> getDriverDetail(@Path("id") int id);

    @PUT("/api/service/update_driver/{id}")
    Call<UserResponse> updateUser(@Path("id") int id,
                                  @Query("full_name") String fullName,
                                  @Query("email") String email,
                                  @Query("phone") String phone);

    @PUT("/api/service/update_driver/{id}")
    Call<DriverResponse> updateDriver(@Path("id") int id);

    @POST("/api/service/register_user")
    Call<ObjectResponse> registrationUser(@Query("full_name") String fullName,
                                          @Query("email") String email,
                                          @Query("phone") String phone,
                                          @Query("password") String password);

    @POST("/api/service/register_driver")
    Call<ObjectResponse> registrationDriver(@Query("full_name") String fullName,
                                            @Query("email") String email,
                                            @Query("phone") String phone,
                                            @Query("longitude") double longitude,
                                            @Query("latitude") double latitude,
                                            @Query("password") String password);

    @POST("/api/service/refuse_order/{idd}/{ido}")
    Call<ObjectResponse> refuseOrder(@Path("idd") int idDriver,
                                     @Path("ido") int idOrder);
    @POST("/api/service/add_order/{idu}/{idt}")
    Call<ObjectResponse> addOrder(@Path("idu") int idUser,
                                  @Path("idt") int idType,
                                  @Query("address_start") String address_start,
                                  @Query("address_end") String address_end,
                                  @Query("phone_receiver") String phone_receiver,
                                  @Query("price_ship") double price_ship,
                                  @Query("note") String note,
                                  @Query("time") String time,
                                  @Query("status") int status,
                                  @Query("name_receiver") String name_receiver);


    @DELETE("/api/service/delete_order/{id}")
    Call<ObjectResponse> deleteOrder(@Path("id") int id);

    @GET("/api/service/get_list_order_of_user/{id}")
    Call<ListOrderResponse> getListOrderOfUser(@Path("id") int id,
                                               @Query("status") int status);
    @GET("/api/service/static_by_day/{id}")
    Call<ListOrderResponse> staticOrderDay(@Path("id") int id,
                                           @Query("day") String day);

    @GET("/api/service/static_by_month/{id}")
    Call<ListOrderResponse> staticOrderMonth(@Path("id") int id,
                                             @Query("day") String day);

    @GET("/api/service/get_list_type_order")
    Call<ListTypeOrderResponse> getListTypeOrder();

    @GET("/api/service/order_detail/{id}")
    Call<OrderResponse> getOrderDetail(@Path("id") int id);

    @GET("/api/service/get_list_order_of_driver/{id}")
    Call<ListOrderResponse> getListOrderOfDriver(@Path("id") int id,
                                                 @Query("longitude") double longitude,
                                                 @Query("latitude") double latitude,
                                                 @Query("status") int status);

    @PUT("/api/service/receive_order_by_driver/{idd}/{ido}")
    Call<ObjectResponse> receiveOrderByDriver(@Path("idd") int idDriver,
                                              @Path("ido") int idOrder);

    @PUT("/api/service/success_order_by_driver/{idd}/{ido}")
    Call<ObjectResponse> successOrderByDriver(@Path("idd") int idDriver,
                                              @Path("ido") int idOrder);

    @POST("/api/service/add_commodities/{ido}")
    Call<ObjectResponse> addCommodities(@Path("ido") int idOrder,
                                        @Query("name_com") String name_com,
                                        @Query("describe_com") String describe_com,
                                        @Query("weight") double weight,
                                        @Query("price") double price);

    @PUT("/api/service/change_password_user/{id}")
    Call<ObjectResponse> changePasswordUser(@Path("id") int idUser,
                                            @Query("password") String password);

    @PUT("/api/service/change_password_driver/{id}")
    Call<ObjectResponse> changePasswordDriver(@Path("id") int idDriver,
                                              @Query("password") String password);

    @DELETE("/api/service/delete_order_success_driver/{id}")
    Call<ObjectResponse> deleteOrderSuccessDriver(@Path("id") int idOrder);


}