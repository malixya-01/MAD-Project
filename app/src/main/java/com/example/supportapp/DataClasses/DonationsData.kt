package com.example.supportapp.DataClasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DonationsData(
    var title: String? = null,
    var username: String? = null,
    var location: String? = null,
    var description: String? = null,
    var phoneNo: String? = null,
    var uid: String? = null,
    var date: String? = null,
    var donId: String? = null): Parcelable