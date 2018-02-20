package com.tickr.tickr.dagger.modules.api

import com.tickr.tickr.api.utils.RxErrorHandlingCallAdapterFactory
import com.tickr.tickr.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by bry1337 on 25/01/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */

@Singleton
@Module
class ApiModule {

  @Provides
  @Singleton internal fun provideOkHttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()
    if (BuildConfig.DEBUG) {
      val loggingInterceptor = HttpLoggingInterceptor()
      loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
      builder.addInterceptor(loggingInterceptor)
    }

    builder.connectTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS).readTimeout(TIMEOUT_IN_SECONDS.toLong(),
        TimeUnit.SECONDS)
    return builder.build()
  }

  @Provides
  @Singleton internal fun provideRestAdapter(okHttpClient: OkHttpClient): Retrofit {
    val builder = Retrofit.Builder()
    builder.client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
    return builder.build()
  }

  companion object {

    private val TIMEOUT_IN_SECONDS = 60
  }
}
