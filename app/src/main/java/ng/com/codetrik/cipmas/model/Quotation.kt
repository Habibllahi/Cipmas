package ng.com.codetrik.cipmas.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class Quotation (
        @SerializedName("id")
        val id: String?,

        @SerializedName("customer")
        val customer : Customer?,

        @SerializedName("fulfil")
        val fulfil: Boolean,

        @SerializedName("description")
        val description : String,

        @SerializedName("quotes")
        val quotes : List<Quote>?,

        @SerializedName("customerId")
        val customerId : String?

        //@SerializedName("updateTimestamp")
        //val lastUpdatedTime : LocalDate?,

        //@SerializedName("creationTimestamp")
        //val creationTime : LocalDate
        )