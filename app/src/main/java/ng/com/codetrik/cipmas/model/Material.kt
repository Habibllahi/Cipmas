package ng.com.codetrik.cipmas.model

import com.google.gson.annotations.SerializedName

data class Material(
        @SerializedName("id")
        val id : String?,

        @SerializedName("genericName")
        val genericName : String,

        @SerializedName("productName")
        val productName : String,

        @SerializedName("gauge")
        val gauge : String,

        @SerializedName("unit")
        val unit : String,

        @SerializedName("unitPrice")
        val unitPrice : Double,

        @SerializedName("dealerPrice")
        val dealerPrice : Boolean,

        @SerializedName("supplierId")
        val supplierId : String?
)
