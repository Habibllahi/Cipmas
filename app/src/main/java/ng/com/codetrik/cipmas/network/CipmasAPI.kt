package ng.com.codetrik.cipmas.network

import ng.com.codetrik.cipmas.model.*
import retrofit2.Call
import retrofit2.http.*

interface CipmasAPI {

    @GET("quotations")
    fun getAllQuotations() : Call<List<Quotation>>

    @GET("customers")
    fun getAllCustomers() : Call<List<Customer>>

    @GET("materials")
    fun getAllMaterials() : Call<List<Material>>

    @GET("suppliers")
    fun getAllSupplier() : Call<List<Supplier>>

    @GET("suppliers/{id}")
    fun getSupplier(@Path("id") id: String) : Call<Supplier>

    @GET("customers/{id}")
    fun getCustomer(@Path("id") id:String) : Call<Customer>

    @POST("suppliers")
    fun postSupplier(@Body supplier: SupplierDTO) : Call<Supplier>

    @POST("materials")
    fun postMaterial(@Body material:Material) : Call<Material>

    @POST("customers")
    fun postCustomer(@Body customer: Customer) : Call<Customer>

    @POST("quotations")
    fun postQuotation(@Body quotation: Quotation) : Call<Quotation>


}