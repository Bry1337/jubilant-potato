package com.tickr.tickr.api.managers

import com.tickr.tickr.BuildConfig
import com.tickr.tickr.api.ApiService
import com.tickr.tickr.api.response.NewsResponse
import com.tickr.tickr.application.AppConstants
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by bry1337 on 19/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class ApiManager(var apiService: ApiService) {

  fun getTechCrunshTopHeadlinesNews(): Observable<NewsResponse> {
    return apiService.getTechCrunchTopNews(AppConstants.TECH_CRUNCH_SOURCES, BuildConfig.API_KEY).subscribeOn(
        Schedulers.io()).observeOn(
        AndroidSchedulers.mainThread())
  }

  fun getCategoryTopHeadlines(sources: String): Observable<NewsResponse> {
    return apiService.getCategoryTopHeadlines(sources, BuildConfig.API_KEY).subscribeOn(Schedulers.io()).observeOn(
        AndroidSchedulers.mainThread())
  }
}