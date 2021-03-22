package ng.com.codetrik.cipmas.model

import com.google.gson.annotations.SerializedName

data class Telephone (
        @SerializedName("countryCode")
        var countryCode : String,

        @SerializedName("areaCode")
        var areaCode : String,

        @SerializedName("exchangeCode")
        var exchangeCode : String,

        @SerializedName("lineNumber")
        var lineNumber : String,

        @SerializedName("telephone")
        var telephone : String
        )
