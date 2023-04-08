package com.example.supportapp.DataClasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class FundraisingData(
    val title: String? = null,
    val description: String? = null,
    val expectedAmt: String? = null,
    val collectedAmt: String? = null,
    val contactNo : String? = null,
    val email : String? = null,
    val website : String? = null,
    val bankDetails: String? = null,
    val username: String? = null,
    val verStatus: Boolean? = null,
    val uid: String? = null,
    val date: String? = null,
    val pushkey: String? = null
): Parcelable