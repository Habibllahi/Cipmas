package ng.com.codetrik.cipmas.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.google.android.material.snackbar.Snackbar
import ng.com.codetrik.cipmas.R
import ng.com.codetrik.cipmas.network.RETROFIT_CIPMAS_API_HANDLER
import ng.com.codetrik.cipmas.model.Address
import ng.com.codetrik.cipmas.model.Supplier
import ng.com.codetrik.cipmas.model.SupplierDTO
import ng.com.codetrik.cipmas.model.Telephone
import ng.com.codetrik.cipmas.network.SUPPLIER_ADD_ACTIVITY_INTENT_FLAG
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception


class SupplierAdd : AppCompatActivity() {
    private val addSupplierBtn : Button by lazy{findViewById(R.id.add_supplier)}
    private val name : EditText by lazy { findViewById(R.id.supplier_name_label_input) }
    private val officeAddressNumber : EditText by lazy { findViewById(R.id.supplier_office_number_input) }
    private val streetName : EditText by lazy { findViewById(R.id.supplier_street_name_input) }
    private val streetType : EditText by lazy { findViewById(R.id.supplier_street_type_input) }
    private val city : EditText by lazy { findViewById(R.id.supplier_city_input) }
    private val state : EditText by lazy { findViewById(R.id.supplier_province_input) }
    private val telephone : EditText by lazy { findViewById(R.id.supplier_telephone_input) }
    private val postalCode : EditText by lazy { findViewById(R.id.supplier_postal_code_input) }
    private lateinit var supplier : SupplierDTO
    private lateinit var phone : Telephone
    private var telephoneValidationFailed: Boolean = false
    private var otherValidationFailed: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supplier_add)
        addSupplierBtn.setOnClickListener {
            uploadSupplier(it)

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this,QuotationActivity::class.java)
        i.putExtra(SUPPLIER_ADD_ACTIVITY_INTENT_FLAG,true)
        startActivity(i)
    }

    private fun uploadSupplier(view : View){
        setupDTO(view)
        if(!telephoneValidationFailed && !otherValidationFailed){
            addSupplierBtn.isEnabled=false
            var call = RETROFIT_CIPMAS_API_HANDLER.postSupplier(supplier)
            call.enqueue(
                    object : retrofit2.Callback<Supplier> {
                        override fun onResponse(call: Call<Supplier>, response: Response<Supplier>) {
                            if (response.isSuccessful){
                                clearInput()
                                addSupplierBtn.isEnabled=true
                                Snackbar.make(view,"Upload successful",2000).show()
                            }else{
                                addSupplierBtn.isEnabled=true
                                Snackbar.make(view,"Upload Error,Retry please",2000).show()
                            }
                        }

                        override fun onFailure(call: Call<Supplier>, t: Throwable) {
                            addSupplierBtn.isEnabled=true
                            Snackbar.make(view,"Upload Failed, Will retry offline",2000).show()
                        }

                    }
            )
        }else{
            Snackbar.make(view,"Upload denied, check phone number or other field ",2000).show()
        }
    }
    private fun setupDTO(view:View) {
        analyseTelephoneAndValidateField(view)
        supplier = SupplierDTO(
                null,
                Address(
                        officeAddressNumber.text.toString(),
                        streetName.text.toString(),
                        streetType.text.toString(),
                        city.text.toString(),
                        state.text.toString(),
                        postalCode.text.toString(),
                        phone
                ),
                name.text.toString()
        )
    }

    private fun analyseTelephoneAndValidateField(view:View){
        phone = Telephone("","","","","")
        var tel = telephone.text.toString()
        validateField()
        telephoneValidationFailed=false
        if(tel.isNotEmpty()){
            if(tel.length>10 && tel.substring(1).isDigitsOnly()){
                try {
                    when (tel[0]) {
                        ('0') -> {
                            phone.countryCode = "0"
                            phone.areaCode = tel.substring(1, 4)
                            phone.exchangeCode = tel.substring(4, 7)
                            phone.lineNumber = tel.substring(7)
                            phone.telephone = tel
                        }
                        ('+') -> {
                            phone.countryCode = tel.substring(0, 4)
                            phone.areaCode = tel.substring(4, 7)
                            phone.exchangeCode = tel.substring(7, 10)
                            phone.lineNumber = tel.substring(10)
                            phone.telephone = tel
                        }
                        else -> {
                            telephoneValidationFailed = true
                            Snackbar.make(view, "Telephone number should start with 0 or +", 2000).show()
                        }
                    }
                }catch (e:Exception){
                    telephoneValidationFailed=true
                    Snackbar.make(view,"Telephone number seems incomplete",2000).show()
                }
            }else{
                telephoneValidationFailed=true
            }
        }else{
            telephoneValidationFailed=true
        }
    }

    private fun validateField(){
        otherValidationFailed = false

        if(
                officeAddressNumber.text.toString().isNullOrEmpty() ||
                streetName.text.toString().isNullOrEmpty() ||
                streetType.text.toString().isNullOrEmpty() ||
                city.text.toString().isNullOrEmpty() ||
                state.text.toString().isNullOrEmpty() ||
                postalCode.text.toString().isNullOrEmpty() ||
                name.text.toString().isNullOrEmpty()
        )
            otherValidationFailed = true
    }

    private fun clearInput(){
        officeAddressNumber.setText("")
        streetName.setText("")
        streetType.setText("")
        city.setText("")
        state.setText("")
        postalCode.setText("")
        name.setText("")
        telephone.setText("")
    }
}