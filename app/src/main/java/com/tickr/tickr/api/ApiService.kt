package com.tickr.tickr.api

import com.tickr.tickr.api.response.NewsResponse
import com.tickr.tickr.application.AppConstants
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
      @Query(AppConstants.NEWS_API_KEY) apiKey: String): Observable<NewsResponse>

  @GET(AppConstants.NEWS_TOP_HEADLINES_API)
  fun getCategoryTopHeadlines(@Query(AppConstants.NEWS_API_SOURCES) sources: String,
      @Query(AppConstants.NEWS_API_KEY) apiKey: String): Observable<NewsResponse>

  @GET(AppConstants.NEWS_EVERYTHING_API)
  fun getEverything(@Query(AppConstants.NEWS_API_QUERY) sources: String,
      @Query(AppConstants.NEWS_API_KEY) apiKey: String): Observable<NewsResponse>
}