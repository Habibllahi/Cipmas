package ng.com.codetrik.cipmas.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import ng.com.codetrik.cipmas.R
import ng.com.codetrik.cipmas.model.Quotation
import ng.com.codetrik.cipmas.network.ADD_CUSTOMER_FROM_QUOTATION_ACTIVITY_INTENT_FLAG
import ng.com.codetrik.cipmas.network.RETROFIT_CIPMAS_API_HANDLER
import ng.com.codetrik.cipmas.network.SELECTED_CUSTOMER_ID
import ng.com.codetrik.cipmas.network.SELECTED_CUSTOMER_NAME
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuotationAdd : AppCompatActivity() {
    private val description : EditText by lazy { findViewById(R.id.quotation_description_input) }
    private val isFulfilled : CheckBox by lazy { findViewById(R.id.is_quotation_fufilled) }
    private val customerAdd : Button by lazy { findViewById(R.id.customer_add_button)}
    private val customerName : TextView by lazy { findViewById(R.id.customer_name_label_for_quotation) }
    private val postQuotation : Button by lazy { findViewById(R.id.post_quotation_button) }
    private val quoteRecyclerView : RecyclerView by lazy { findViewById(R.id.quotes_recycler_view) }
    private val fab : FloatingActionButton by lazy { findViewById(R.id.add_quote_fab) }
    private val quotationViewGroup : Group by lazy { findViewById(R.id.quotationViewGroup) }
    private val quoteViewGroup : Group by lazy { findViewById(R.id.quoteViewGroup) }
    private var customerId : String? = null
    private var quotation : Quotation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quotation_add)
        makeOnlyQuotationViewGroupVisible()
        getCustomerIdAndDisplayCustomerName()
        customerAdd.setOnClickListener {
            val quotationAddIntent = Intent(this,QuotationActivity::class.java)
            quotationAddIntent.putExtra(ADD_CUSTOMER_FROM_QUOTATION_ACTIVITY_INTENT_FLAG,true)
            startActivity(quotationAddIntent)
        }
        postQuotation.setOnClickListener {
            postQuotationToBackendAndMakeQuoteViewGroupVisibleUponSuccess(it)
        }
    }

    private fun postQuotationToBackendAndMakeQuoteViewGroupVisibleUponSuccess(view: View) {
        if(customerId!=null && validateInputs()){
            postQuotation.isEnabled=false
            var call = RETROFIT_CIPMAS_API_HANDLER.postQuotation(prepareQuotationModel())
            call.enqueue(object : Callback<Quotation> {
                override fun onResponse(call: Call<Quotation>, response: Response<Quotation>) {
                    if (response.isSuccessful){
                        clearInputs()
                        postQuotation.isEnabled=true
                        quotation = response.body()
                        makeOnlyQuoteViewGroupVisible()
                        Snackbar.make(quoteViewGroup,"Upload successful",2000).show()
                    }
                    else{
                        postQuotation.isEnabled=true
                        Snackbar.make(view,"Upload Error,Retry please",2000).show()
                    }
                }

                override fun onFailure(call: Call<Quotation>, t: Throwable) {
                    postQuotation.isEnabled=true
                    Snackbar.make(view,"Upload Failed, Will retry offline",2000).show()
                }

            })
        }
    }

    private fun prepareQuotationModel(): Quotation {
        return Quotation(
                null,
                null,
                isFulfilled.isChecked,
                description.text.toString(),
                null,
                customerId
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,QuotationActivity::class.java))
    }

    private fun makeOnlyQuotationViewGroupVisible(){
        quoteViewGroup.visibility= View.GONE
        quotationViewGroup.visibility= View.VISIBLE
    }

    private fun makeOnlyQuoteViewGroupVisible(){
        quoteViewGroup.visibility= View.VISIBLE
        quotationViewGroup.visibility= View.GONE

    }

    private fun getCustomerIdAndDisplayCustomerName(){
        if(intent.hasExtra(SELECTED_CUSTOMER_ID) && intent.hasExtra(SELECTED_CUSTOMER_NAME)){
            customerId = intent.getStringExtra(SELECTED_CUSTOMER_ID)
            customerName.text = intent.getStringExtra(SELECTED_CUSTOMER_NAME)
        }
    }
    private fun validateInputs():Boolean{
        return description.text.toString().isNotEmpty()
    }

    private fun clearInputs(){
        description.setText("")
        isFulfilled.isChecked=false
        customerName.text=""
        customerId=null
    }
}