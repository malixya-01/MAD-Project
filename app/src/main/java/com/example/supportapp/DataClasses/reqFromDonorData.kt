package com.example.supportapp.DataClasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class reqFromDonorData(
    var msgId: String? = null,
    var supporterId: String? = null,
    var email: String? = null,
    var phoneNumber: String? = null,
    var message: String? = null,
    var date: String? = null

): Parcelable
