package com.cottacush.android.androidbaseprojectkt.sample.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.cottacush.android.androidbaseprojectkt.sample.Weight
import com.google.gson.Gson


@Entity
class DatabaseBreedModel(
    @PrimaryKey
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
)

class WeightConverter {
    private val gson: Gson = Gson()

    @TypeConverter
    fun toWeight(weight: String): Weight {
        return gson.fromJson(weight, Weight::class.java)
    }

    @TypeConverter
    fun toString(weight: Weight): String {
        return gson.toJson(weight)
    }
}

fun List<DatabaseBreedModel>.asDomainModel(): List<Breed> {
    return map {
        Breed(
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
    }
}
