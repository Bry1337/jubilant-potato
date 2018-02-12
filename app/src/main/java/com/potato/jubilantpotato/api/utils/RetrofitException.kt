package com.potato.jubilantpotato.api.utils

import java.io.IOException
import retrofit2.Response
import retrofit2.Retrofit

/**
 * Created by bry1337 on 25/01/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */

class RetrofitException(builder: Builder) : RuntimeException(builder.message, builder.exception) {

  /** The request URL which produced the error.  */
  val url: String?
  /** Response object containing status code, headers, body, etc.  */
  val response: Response<*>?
  /** The event kind which triggered this error.  */
  val kind: Kind?
  /** The Retrofit this request was executed on  */
  val retrofit: Retrofit?

  init {
    this.url = builder.url
    this.response = builder.response
    this.kind = builder.kind
    this.retrofit = builder.retrofit
  }

  /**
   * HTTP response body converted to specified `type`. `null` if there is no
   * response.
   *
   * @throws IOException if unable to convert the body to the specified `type`.
   */
  @Throws(IOException::class)
  fun <T> getErrorBodyAs(type: Class<T>): T? {
    if (response == null || response.errorBody() == null) {
      return null
    }
    val converter = retrofit!!.responseBodyConverter<T>(type, arrayOfNulls(0))
    return converter.convert(response.errorBody())
  }

  /** Identifies the event kind which triggered a [RetrofitException].  */
  enum class Kind {
    /** An [IOException] occurred while communicating to the server.  */
    NETWORK,
    /** A non-200 HTTP status code was received from the server.  */
    HTTP,
    /**
     * An internal error occurred while attempting to execute a request. It is best practice to
     * re-throw this exception so your application crashes.
     */
    UNEXPECTED
  }

  class Builder(internal val message: String?, internal val exception: Throwable?) {
    internal var url: String? = null
    internal var response: Response<*>? = null
    internal var kind: Kind? = null
    internal var retrofit: Retrofit? = null

    init {
      this.url = null
      this.response = null
      this.kind = null
      this.retrofit = null
    }

    fun setUrl(url: String?): Builder {
      this.url = url
      return this
    }

    fun setResponse(response: Response<*>?): Builder {
      this.response = response
      return this
    }

    fun setKind(kind: Kind): Builder {
      this.kind = kind
      return this
    }

    fun setRetrofit(retrofit: Retrofit?): Builder {
      this.retrofit = retrofit
      return this
    }

    fun build(): RetrofitException {
      return RetrofitException(this)
    }
  }

  companion object {

    fun httpError(url: String, response: Response<*>, retrofit: Retrofit): RetrofitException {
      val message = response.code().toString() + " " + response.message()
      return Builder(message, null).setUrl(url)
          .setResponse(response)
          .setKind(Kind.HTTP)
          .setRetrofit(retrofit)
          .build()
    }

    fun networkError(exception: IOException): RetrofitException {
      return Builder(exception.message, exception).setUrl(null)
          .setResponse(null)
          .setKind(Kind.NETWORK)
          .setRetrofit(null)
          .build()
    }

    fun unexpectedError(exception: Throwable): RetrofitException {
      return Builder(exception.message, exception).setUrl(null)
          .setResponse(null)
          .setKind(Kind.UNEXPECTED)
          .setRetrofit(null)
          .build()
    }
  }
}
