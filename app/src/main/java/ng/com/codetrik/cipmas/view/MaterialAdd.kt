package ng.com.codetrik.cipmas.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ng.com.codetrik.cipmas.*
import ng.com.codetrik.cipmas.model.Material
import ng.com.codetrik.cipmas.network.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MaterialAdd : AppCompatActivity() {
    private val genericName : EditText by lazy {findViewById(R.id.generic_material_name_input) }
    private val productName : EditText by lazy { findViewById(R.id.material_product_name_input) }
    private val productUnit : EditText by lazy { findViewById(R.id.material_unit_input) }
    private val gauge : EditText by lazy { findViewById(R.id.material_guage_input) }
    private val unitPrice : EditText by lazy { findViewById(R.id.material_unit_price_input) }
    private val isDealerPrice : CheckBox by lazy { findViewById(R.id.material_dealership_input) }
    private val addSupplierBtn : Button by lazy { findViewById(R.id.select_supplier_button)}
    private val addMaterialBtn : Button by lazy {findViewById(R.id.add_material_button)}
    private val supplierPlate : TextView by lazy { findViewById(R.id.selected_supplier) }
    private var supplierId : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_add)

        addMaterialBtn.setOnClickListener {
            postMaterialViaRetrofit(it)
        }

        addSupplierBtn.setOnClickListener {
            val i = Intent(this,QuotationActivity::class.java)
            //providing a workaround to re-instate all filled state before the user go ahead to select supplier to associate to the material to be posted
            val bundle = Bundle()
            bundle.run {
                putString("genericName",genericName.text.toString())
                putString("productName",productName.text.toString())
                putString("gauge",gauge.text.toString())
                putString("productUnit",productUnit.text.toString())
                putDouble("unitPrice", getUnitPrice())
                putBoolean("isDealerPrice",isDealerPrice.isChecked)
            }
            i.putExtra(MATERIAL_ACTIVITY_BUNDLE,bundle)
            i.putExtra(SUPPLIER_ADD_ACTIVITY_INTENT_FLAG,true)
            startActivity(i)
        }

        getSupplierIdAndDisplaySupplierName()

    }

    private fun getSupplierIdAndDisplaySupplierName() {
        if(intent.hasExtra(SELECTED_SUPPLIER_ID) && intent.hasExtra(SELECTED_SUPPLIER_NAME)){
            supplierId = intent.getStringExtra(SELECTED_SUPPLIER_ID)!!
            supplierPlate.text = intent.getStringExtra(SELECTED_SUPPLIER_NAME)
        }
    }

    private fun getUnitPrice():Double{
        return if(unitPrice.text.toString().isEmpty())
            0.0
        else
            unitPrice.text.toString().toDouble()
    }
    override fun onResume() {
        super.onResume()
        if(intent.hasExtra(SUPPLIER_RECYCLER_BUNDLE_EXTRA)){
            var bundle = intent.getBundleExtra(SUPPLIER_RECYCLER_BUNDLE_EXTRA)
            genericName.setText(bundle?.getString("genericName"))
            productName.setText(bundle?.getString("productName"))
            productUnit.setText(bundle?.getString("productUnit"))
            gauge.setText(bundle?.getString("gauge"))
            unitPrice.setText(bundle?.getDouble("unitPrice").toString())
            isDealerPrice.isChecked = bundle?.getBoolean("isDealerPrice")!!
        }

    }

    private fun postMaterialViaRetrofit(view: View){
        if(supplierId!=null && validateInputs()){
            addMaterialBtn.isEnabled=false
            var call = RETROFIT_CIPMAS_API_HANDLER.postMaterial(prepareMaterialModel())
            call.enqueue(
                    object : Callback<Material>{
                        override fun onResponse(call: Call<Material>, response: Response<Material>) {
                            if (response.isSuccessful){
                                clearInputs()
                                addMaterialBtn.isEnabled=true
                                Snackbar.make(view,"Upload successful",2000).show()
                            } else{
                                addMaterialBtn.isEnabled=true
                                Snackbar.make(view,"Upload Error,Retry please",2000).show()
                            }
                        }

                        override fun onFailure(call: Call<Material>, t: Throwable) {
                            addMaterialBtn.isEnabled=true
                            Snackbar.make(view,"Upload Failed, Will retry offline",2000).show()
                        }

                    }
            )
        }else{
            Snackbar.make(view,"Upload Impossible, Select a supplier first",2000).show()
        }
    }

    private fun validateInputs(): Boolean {
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putString("genericName",genericName.text.toString())
            putString("productName",productName.text.toString())
            putString("gauge",gauge.text.toString())
            putString("productUnit",productUnit.text.toString())
            putDouble("unitPrice",getUnitPrice())
            putBoolean("isDealerPrice",isDealerPrice.isChecked)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState!=null){
            genericName.setText(savedInstanceState.getString("genericName"))
            productName.setText(savedInstanceState.getString("productName"))
            productUnit.setText(savedInstanceState.getString("productUnit"))
            gauge.setText(savedInstanceState.getString("gauge"))
            unitPrice.setText(savedInstanceState.getDouble("unitPrice").toString())
            isDealerPrice.isChecked = savedInstanceState.getBoolean("isDealerPrice")
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this,QuotationActivity::class.java)
        i.putExtra(MATERIAL_ADD_ACTIVITY_INTENT_FLAG,true)
        startActivity(i)
    }

    private fun prepareMaterialModel():Material{
        return Material(
                null,
                genericName.text.toString(),
                productName.text.toString(),
                gauge.text.toString(),
                productUnit.text.toString(),
                getUnitPrice(),
                isDealerPrice.isChecked,
                supplierId
        )
    }

    private fun clearInputs(){
        genericName.setText("")
        productName.setText("")
        gauge.setText("")
        productUnit.setText("")
        unitPrice.setText("")
        isDealerPrice.isChecked=false
        supplierPlate.text=""
        supplierId=null
    }
}