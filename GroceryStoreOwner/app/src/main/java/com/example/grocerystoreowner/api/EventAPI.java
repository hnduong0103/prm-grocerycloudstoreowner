package com.example.grocerystoreowner.api;

import com.example.grocerystoreowner.model.bill.BillResponse;
import com.example.grocerystoreowner.model.event.EventResponse;
import com.example.grocerystoreowner.model.event.EventResponseList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EventAPI {
    @GET("api/v1.0/events")
    Call<EventResponseList> getEventList(@Query("brand-id") int brandId,
                                         @Query("search-term") String searchTerm,
                                         @Query("page-index") int pageIndex,
                                         @Query("page-size") int pageSize
                                         );
    @GET("api/v1.0/events")
    Call<EventResponseList> getEventBasedOnStatus(@Query("brand-id") int brandId,
                                                    @Query("search-term") String searchTerm,
                                                    @Query("status") int status,
                                                  @Query("page-index") int pageIndex,
                                                  @Query("page-size") int pageSize);
}
