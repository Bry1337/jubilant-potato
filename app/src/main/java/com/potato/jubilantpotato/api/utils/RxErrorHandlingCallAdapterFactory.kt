package com.potato.jubilantpotato.api.utils

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.HttpException
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import rx.Observable
import java.io.IOException
import java.lang.reflect.Type

/**
 * Created by bry1337 on 25/01/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */

class RxErrorHandlingCallAdapterFactory private constructor() : CallAdapter.Factory() {

  private val original: RxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create()

  override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*> {
    return RxCallAdapterWrapper(retrofit, original.get(returnType, annotations, retrofit))
  }

  private class RxCallAdapterWrapper(private val retrofit: Retrofit,
      private val wrapped: CallAdapter<*>) : CallAdapter<Observable<*>> {

    override fun responseType(): Type {
      return wrapped.responseType()
    }

    override fun <R> adapt(call: Call<R>): Observable<*> {
      return (wrapped.adapt(call) as Observable<*>).onErrorResumeNext(
          { throwable -> Observable.error(asRetrofitException(throwable)) })
    }

    private fun asRetrofitException(throwable: Throwable): RetrofitException {
      // We had non-200 http error
      if (throwable is HttpException) {
        val response = throwable.response()
        return RetrofitException.httpError(response.raw().request().url().toString(), response, retrofit)
      }
      // A network error happened
      return if (throwable is IOException) {
        RetrofitException.networkError(throwable)
      } else RetrofitException.unexpectedError(throwable)
      // We don't know what happened. We need to simply convert to an unknown error
    }
  }

  companion object {

    fun create(): CallAdapter.Factory {
      return RxErrorHandlingCallAdapterFactory()
    }
  }
}
