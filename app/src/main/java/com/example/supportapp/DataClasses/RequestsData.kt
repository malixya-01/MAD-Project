package com.example.supportapp.DataClasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestsData(
    var title: String? = null,
    var username: String? = null,
    var location: String? = null,
    var description: String? = null,
    var phoneNo: String? = null,
    var uid: String? = null,
    var bankDetails: String? = null,
    var date: String? = null,
    var reqId: String? = null): Parcelable

