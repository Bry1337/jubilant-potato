package com.tickr.tickr.models

import android.os.Parcel
import android.os.Parcelable
import org.apache.commons.lang3.StringUtils

/**
 * Created by bry1337 on 27/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class User() : Parcelable {

  var uid: String = StringUtils.EMPTY
  var fullName: String = StringUtils.EMPTY
  var email: String = StringUtils.EMPTY

  constructor(uid: String, fullName: String, email: String) : this() {
    this.uid = uid
    this.fullName = fullName
    this.email = email
  }

  constructor(parcel: Parcel) : this() {
    uid = parcel.readString()
    fullName = parcel.readString()
    email = parcel.readString()
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(uid)
    parcel.writeString(fullName)
    parcel.writeString(email)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<User> {
    override fun createFromParcel(parcel: Parcel): User {
      return User(parcel)
    }

    override fun newArray(size: Int): Array<User?> {
      return arrayOfNulls(size)
    }
  }
}