package com.example.turkeyplatecodeapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "city_map")
data class CityMap(
    @SerializedName("city_url")
    @ColumnInfo(name = "city_url")
    val city_url: String = "",
    @SerializedName("plate")
    @ColumnInfo(name = "plate")
    val plate: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}