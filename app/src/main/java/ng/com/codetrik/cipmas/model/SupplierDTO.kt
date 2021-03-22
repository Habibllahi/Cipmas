package ng.com.codetrik.cipmas.model

import com.google.gson.annotations.SerializedName

class SupplierDTO(
        @SerializedName("id")
        val id: String?,

        @SerializedName("address")
        val address : Address,

        @SerializedName("name")
        val name : String,
)