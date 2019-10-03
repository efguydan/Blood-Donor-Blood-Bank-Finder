package com.cottacush.android.androidbaseprojectkt.sample

import android.os.Parcelable
import com.cottacush.android.androidbaseprojectkt.sample.models.Breed
import com.cottacush.android.androidbaseprojectkt.sample.models.DatabaseBreedModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class NetworkBreedModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("life_span")
    val lifeSpan: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin")
    val origin: String,
    @SerializedName("social_needs")
    val socialNeeds: Int,
    @SerializedName("stranger_friendly")
    val strangerFriendly: Int,
    @SerializedName("temperament")
    val temperament: String,
    @SerializedName("weight")
    val weight: Weight,
    @SerializedName("wikipedia_url")
    val wikipediaUrl: String
)
@Parcelize
data class Weight(
    @SerializedName("imperial")
    val imperial: String,
    @SerializedName("metric")
    val metric: String
) : Parcelable



fun NetworkBreedModel.asDomainModel(): Breed {
    //TODO further manipulation before the transformation?
    return Breed(
        id, description, lifeSpan, name, origin, socialNeeds,
        strangerFriendly, temperament, weight, wikipediaUrl
    )
}

fun List<NetworkBreedModel>.asDataBaseModel(): Array<DatabaseBreedModel> {
    return map {
        DatabaseBreedModel(
            id = it.id,

            description = it.description,

            lifeSpan = it.lifeSpan,

            name = it.name,

            origin = it.origin,

            socialNeeds = it.socialNeeds,

            strangerFriendly = it.strangerFriendly,

            temperament = it.temperament,

            weight = it.weight,

            wikipediaUrl = it.wikipediaUrl

        )
    }.toTypedArray()
}


