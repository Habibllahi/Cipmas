package ng.com.codetrik.cipmas.model

import com.google.gson.annotations.SerializedName

data class Address(
        @SerializedName("houseNumber")
        val houseNumber : String,

        @SerializedName("streetName")
        val streetName : String,

        @SerializedName("streetType")
        val streetType : String,

        @SerializedName("city")
        val city : String,

        @SerializedName("state")
        val state : String,

        @SerializedName("postalCode")
        val postalCode : String,

        @SerializedName("telephone")
        val telephone : Telephone

    )