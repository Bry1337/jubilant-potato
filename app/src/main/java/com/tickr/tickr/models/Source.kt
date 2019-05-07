package com.tickr.tickr.models

import android.os.Parcel
import android.os.Parcelable
import org.apache.commons.lang3.StringUtils

/**
 * Created by bry1337 on 19/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class Source() : Parcelable {
  var id: String? = StringUtils.EMPTY
  var name: String? = StringUtils.EMPTY


  constructor(parcel: Parcel) : this() {
    id = parcel.readString()
    name = parcel.readString()
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(id)
    parcel.writeString(name)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<Source> {
    override fun createFromParcel(parcel: Parcel): Source {
      return Source(parcel)
    }

    override fun newArray(size: Int): Array<Source?> {
      return arrayOfNulls(size)
    }
  }

}