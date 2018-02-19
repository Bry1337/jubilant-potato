package com.potato.jubilantpotato.api.managers

import com.potato.jubilantpotato.BuildConfig
import com.potato.jubilantpotato.api.ApiService
import com.potato.jubilantpotato.api.response.TechCrunchResponse
import com.potato.jubilantpotato.application.AppConstants
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