package com.example.turkeyplatecodeapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

@Entity(tableName = "city_data")
data class Data(
    @SerializedName("alan_kodu")
    @ColumnInfo(name = "alan_kodu")
    val alan_kodu: String = "",
    @SerializedName("bolge")
    @ColumnInfo(name = "bolge")
    val bolge: String = "",
    @SerializedName("erkek_nufus")
    @ColumnInfo(name = "erkek_nufus")
    val erkek_nufus: String = "",
    @SerializedName("erkek_nufus_yuzde")
    @ColumnInfo(name = "erkek_nufus_yuzde")
    val erkek_nufus_yuzde: String = "",
    @SerializedName("il_adi")
    @ColumnInfo(name = "il_adi")
    val il_adi: String = "",
    @SerializedName("ilceler")
    @ColumnInfo(name = "ilceler")
    val ilceler: List<Ilceler>,
    @SerializedName("kadin_nufus")
    @ColumnInfo(name = "kadin_nufus")
    val kadin_nufus: String = "",
    @SerializedName("kadin_nufus_yuzde")
    @ColumnInfo(name = "kadin_nufus_yuzde")
    val kadin_nufus_yuzde: String = "",
    @SerializedName("kisa_bilgi")
    @ColumnInfo(name = "kisa_bilgi")
    val kisa_bilgi: String = "",
    @SerializedName("nufus")
    @ColumnInfo(name = "nufus")
    val nufus: String = "",
    @SerializedName("nufus_yuzdesi_genel")
    @ColumnInfo(name = "nufus_yuzdesi_genel")
    val nufus_yuzdesi_genel: String = "",
    @SerializedName("plaka_kodu")
    @ColumnInfo(name = "plaka_kodu")
    val plaka_kodu: String = "",
    @SerializedName("yuzolcumu")
    @ColumnInfo(name = "yuzolcumu")
    val yuzolcumu: String = "",
    @SerializedName("gercek_nufus")
    @ColumnInfo(name = "gercek_nufus")
    var gercek_nufus: Int = 0,
    @SerializedName("isFavorite")
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false,
    @SerializedName("cityMapUrl")
    @ColumnInfo(name = "cityMapUrl")
    var cityMapUrl: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

class IlcelerTypeConverter {

    @TypeConverter
    fun stringToListIlceler(data: String?): List<Ilceler?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
            TypeToken<List<Ilceler?>?>() {}.type
        return Gson().fromJson<List<Ilceler?>>(data, listType)
    }

    @TypeConverter
    fun listServerToIlceler(someObjects: List<Ilceler?>?): String? {
        return Gson().toJson(someObjects)
    }

}