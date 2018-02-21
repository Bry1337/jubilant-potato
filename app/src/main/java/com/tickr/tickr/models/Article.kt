package com.tickr.tickr.models

import android.os.Parcel
import android.os.Parcelable
import org.apache.commons.lang3.StringUtils

/**
 * Created by bry1337 on 19/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class Article() : Parcelable {

  var source: Source? = null
  var author: String? = StringUtils.EMPTY
  var title: String? = StringUtils.EMPTY
  var description: String? = StringUtils.EMPTY
  var url: String? = StringUtils.EMPTY
  var urlToImage: String? = StringUtils.EMPTY
  var publishedAt: String? = StringUtils.EMPTY

  constructor(parcel: Parcel) : this() {
    author = parcel.readString()
    title = parcel.readString()
    description = parcel.readString()
    url = parcel.readString()
    urlToImage = parcel.readString()
    publishedAt = parcel.readString()
    source = parcel.readParcelable(Source.javaClass.classLoader)
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(author)
    parcel.writeString(title)
    parcel.writeString(description)
    parcel.writeString(url)
    parcel.writeString(urlToImage)
    parcel.writeString(publishedAt)
    parcel.writeParcelable(source, 0)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<Article> {
    override fun createFromParcel(parcel: Parcel): Article {
      return Article(parcel)
    }

    override fun newArray(size: Int): Array<Article?> {
      return arrayOfNulls(size)
    }
  }


}