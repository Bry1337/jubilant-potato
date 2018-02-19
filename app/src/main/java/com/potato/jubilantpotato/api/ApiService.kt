package com.potato.jubilantpotato.api

import com.potato.jubilantpotato.api.response.TechCrunchResponse
import com.potato.jubilantpotato.application.AppConstants
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by bry1337 on 19/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
interface ApiService {

  @GET(AppConstants.TECH_CRUNCH_TOP_HEADLINES_API)
  fun getTechCrunchTopNews(@Query(AppConstants.NEWS_API_SOURCES) sources: String,
      @Query(AppConstants.NEWS_API_KEY) apiKey: String): Observable<TechCrunchResponse>
}