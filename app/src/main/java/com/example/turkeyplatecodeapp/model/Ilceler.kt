package com.example.turkeyplatecodeapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Ilceler(
    @SerializedName("erkek_nufus")
    @ColumnInfo(name = "erkek_nufus")
    val erkek_nufus: String,

    @SerializedName("ilce_adi")
    @ColumnInfo(name = "ilce_adi")
    val ilce_adi: String,

    @SerializedName("kadin_nufus")
    @ColumnInfo(name = "kadin_nufus")
    val kadin_nufus: String,

    @SerializedName("nufus")
    @ColumnInfo(name = "nufus")
    val nufus: String,

    @SerializedName("yuzolcumu")
    @ColumnInfo(name = "yuzolcumu")
    val yuzolcumu: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}