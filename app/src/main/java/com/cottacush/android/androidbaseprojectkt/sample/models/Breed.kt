package com.cottacush.android.androidbaseprojectkt.sample.models
import android.os.Parcelable
import com.cottacush.android.androidbaseprojectkt.sample.Weight
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Breed(
    val id: String,
    val description: String,
    val lifeSpan: String,
    val name: String,
    val origin: String,
    val socialNeeds: Int,
    val strangerFriendly: Int,
    val temperament: String,
    val weight: Weight,
    val wikipediaUrl: String?
) : Parcelable
