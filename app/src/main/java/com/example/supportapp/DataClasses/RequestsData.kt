package com.example.supportapp.DataClasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestsData(val title: String? = null,
                        val username: String? = null,
                        val location: String? = null,
                        val description: String? = null,
                        val phoneNo: String? = null,
                        val uid: String? = null,
                        val bankDetails: String? = null,
                        val date: String? = null,
                        val reqId: String? = null): Parcelable

