package com.example.submission_github

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data (
    var photo: String? = null,
    var nama: String? = null,
    var id: String? = null,
    var company: String? = null,
    var location: String? = null,
    var following: String? = null,
    var followers: String? = null
): Parcelable