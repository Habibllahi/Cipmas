package ng.com.codetrik.cipmas.model

import com.google.gson.annotations.SerializedName
import ng.com.codetrik.cipmas.model.Address


data class Customer (
        @SerializedName("id")
        val id: String?,

        @SerializedName("address")
        val address : Address,

        @SerializedName("name")
        val name : String

        )