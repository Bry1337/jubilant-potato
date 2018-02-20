package com.tickr.tickr.api.managers

import com.tickr.tickr.BuildConfig
import com.tickr.tickr.api.ApiService
import com.tickr.tickr.api.response.TechCrunchResponse
import com.tickr.tickr.application.AppConstants
import rx.Observable

/**
 * Created by bry1337 on 19/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class ApiManager(var apiService: ApiService) {

  fun getTechCrunshTopHeadlinesNews(): Observable<TechCrunchResponse> {
    return apiService.getTechCrunchTopNews(AppConstants.TECH_CRUNCH_SOURCES, BuildConfig.API_KEY)
  }
}