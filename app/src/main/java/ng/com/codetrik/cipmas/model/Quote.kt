package ng.com.codetrik.cipmas.model

import com.google.gson.annotations.SerializedName
import ng.com.codetrik.cipmas.model.Material

data class Quote(
        @SerializedName("id")
        val id : String?,

        @SerializedName("price")
        val price : Double,

        @SerializedName("quantity")
        val quantity : Double,

        @SerializedName("material")
        val material : Material,

        @SerializedName("quotation")
        val quotation : Quotation?
)
