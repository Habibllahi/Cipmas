package ng.com.codetrik.cipmas.model

import com.google.gson.annotations.SerializedName

data class Supplier(
        @SerializedName("id")
        val id: String?,

        @SerializedName("address")
        val address : Address,

        @SerializedName("name")
        val name : String,

        @SerializedName("materials")
        val materials : List<Material>?
)