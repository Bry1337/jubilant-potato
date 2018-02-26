package com.tickr.tickr.application

/**
 * Created by bry1337 on 19/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class AppConstants {
  companion object {
    // BASE URL
    const val NEWS_API: String = "https://newsapi.org/v2/"
    const val NEWS_API_SOURCES: String = "sources"
    const val NEWS_API_QUERY: String = "q"
    const val NEWS_API_KEY: String = "apiKey"
    const val NEWS_TOP_HEADLINES_API: String = "top-headlines"
    const val NEWS_EVERYTHING_API: String = "everything"
    const val NEWS_API_STATUS_OK = "ok"


    // TECHCRUNCH
    const val TECH_CRUNCH_TOP_HEADLINES_API: String = "top-headlines"
    const val TECH_CRUNCH_SOURCES: String = "techcrunch"


    // FIREBASE CONSTANTS
    const val FIREBASE_CATEGORY = "category"
    const val FIREBASE_DEFAULT_CATEGORY = "default"
    const val FIREBASE_INFO_CONNECTION = ".info/connected"


    // AppActivityManager Constants
    const val ARTICLE_OBJECT = "articleObject"
    const val CATEGORY_CURRENT_COUNT: Int = 10
    const val SHARING_TYPE: String = "text/plain"
    const val SHARE_TEXT: String = "Share"
  }
}