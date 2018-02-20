package com.tickr.tickr.dagger.modules.api

import com.tickr.tickr.api.ApiService
import com.tickr.tickr.api.managers.ApiManager
import com.tickr.tickr.dagger.scopes.UserScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by bry1337 on 19/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@UserScope
@Module
class UserApiModule {

  @Provides
  @UserScope
  fun provideApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
  }

  @Provides
  @UserScope
  fun provideApiManager(apiService: ApiService): ApiManager {
    return ApiManager(apiService)
  }
}