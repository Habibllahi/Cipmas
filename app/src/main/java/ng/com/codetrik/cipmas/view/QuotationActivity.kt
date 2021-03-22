package ng.com.codetrik.cipmas.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import ng.com.codetrik.cipmas.R
import ng.com.codetrik.cipmas.model.Customer
import ng.com.codetrik.cipmas.model.Material
import ng.com.codetrik.cipmas.model.Quotation
import ng.com.codetrik.cipmas.model.Supplier
import ng.com.codetrik.cipmas.network.*
import ng.com.codetrik.cipmas.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.exitProcess

class QuotationActivity : AppCompatActivity() {

    private val recycler: RecyclerView by lazy { findViewById(R.id.quotation_recycler_view) }
    private val drawer : DrawerLayout by lazy { findViewById(R.id.drawer_layout) }
    private val navigationView : NavigationView by lazy { findViewById(R.id.nav_view)}
    private val layoutManager by lazy { LinearLayoutManager(this) }
    private val fab : FloatingActionButton by lazy { findViewById(R.id.fab) }
    private val quotationItem: MenuItem by lazy {navigationView.menu.findItem(R.id.quotation_item)}
    private val customerItem: MenuItem by lazy { navigationView.menu.findItem(R.id.customer_item)}
    private val materialItem: MenuItem by lazy { navigationView.menu.findItem(R.id.material_item)}
    private val supplierItem: MenuItem by lazy { navigationView.menu.findItem(R.id.supplier_item)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quotation)
        setupView();
        fab.setOnClickListener {
            createNew()
        }
    }

    private fun createNew() {
        if(quotationItem.isChecked){
            startActivity(Intent(this,QuotationAdd::class.java))
        }
        if(materialItem.isChecked){
            startActivity(Intent(this,MaterialAdd::class.java))
        }
        if(supplierItem.isChecked){
            startActivity(Intent(this,SupplierAdd::class.java))
        }
        if(customerItem.isChecked){
            startActivity(Intent(this,CustomerAdd::class.java))
        }
    }

    private fun setUpViewBaseOnReceivedIntent(){
        var intentReceived = intent
        when{
            intentReceived.hasExtra(ADD_CUSTOMER_FROM_QUOTATION_ACTIVITY_INTENT_FLAG) ->{
                customerItem.isChecked=true
                customerItemChecked()
            }
            intentReceived.hasExtra(CUSTOMER_ADD_ACTIVITY_INTENT_FLAG)->{
                customerItem.isChecked=true
                customerItemChecked()
            }
            intentReceived.hasExtra(SUPPLIER_ADD_ACTIVITY_INTENT_FLAG)->{
                supplierItem.isChecked=true
                supplyItemChecked()
            }
            intentReceived.hasExtra(MATERIAL_ADD_ACTIVITY_INTENT_FLAG)->{
                materialItem.isChecked=true
                materialItemChecked()
            }
            intentReceived.hasExtra(QUOTATION_ADD_ACTIVITY_INTENT_FLAG)->{
                quotationItem.isChecked=true
                quotationItemChecked()
            }
            else->{
                quotationItem.isChecked = true
                quotationItemChecked()
            }
        }
    }
    private fun customerItemChecked(){
        quotationItem.isChecked=false
        materialItem.isChecked=false
        supplierItem.isChecked=false
        getAllCustomersByRetrofit()
    }
    private fun quotationItemChecked(){
        customerItem.isChecked=false
        materialItem.isChecked=false
        supplierItem.isChecked=false
        getAllQuotationsByRetrofit()
    }
    private fun materialItemChecked(){
        customerItem.isChecked=false
        quotationItem.isChecked=false
        supplierItem.isChecked=false
        getAllMaterialsByRetrofit()
    }
    private fun supplyItemChecked(){
        quotationItem.isChecked=false
        customerItem.isChecked=false
        materialItem.isChecked=false
        getAllSupplierByRetrofit()
    }
    private fun setupView(){

        setUpViewBaseOnReceivedIntent()

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                (R.id.quotation_item)->{
                    it.isChecked = true
                    quotationItemChecked()
                    drawer.closeDrawer(GravityCompat.START)
                    true
                }

                (R.id.customer_item)->{
                    it.isChecked=true
                    customerItemChecked()
                    drawer.closeDrawer((GravityCompat.START))
                    true
                }

                (R.id.material_item)->{
                    it.isChecked=true
                    materialItemChecked()
                    drawer.closeDrawer(GravityCompat.START)
                    true
                }

                (R.id.supplier_item)->{
                    it.isChecked=true
                    supplyItemChecked()
                    drawer.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }
    }

    private fun setUpViewByIntent(){

    }

    private fun getAllSupplierByRetrofit() {
        val cal = RETROFIT_CIPMAS_API_HANDLER.getAllSupplier()
        cal.enqueue(object : Callback<List<Supplier>>{
            override fun onResponse(call: Call<List<Supplier>>, response: Response<List<Supplier>>) {
                if(response.isSuccessful){
                    populateRecyclerViewWithSupplier(response.body())
                    Snackbar.make(navigationView,"OK: data retrieved",2000).show()
                }
                else
                    Snackbar.make(navigationView,"Error: Connection to Cipmas backend unsucessful",2000).show()
            }

            override fun onFailure(call: Call<List<Supplier>>, t: Throwable) {
                Snackbar.make(navigationView,"Failure: Connection to Cipmas backend unsucessful",2000).show()
            }

        })
    }

    private fun populateRecyclerViewWithSupplier(data: List<Supplier>?) {
        var adapter = if(intent.hasExtra(MATERIAL_ACTIVITY_BUNDLE))
            SupplierRecyclerAdapter(this, data,intent.getBundleExtra(MATERIAL_ACTIVITY_BUNDLE))
        else
            SupplierRecyclerAdapter(this, data,null)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
    }

    private fun getAllMaterialsByRetrofit() {
        val call = RETROFIT_CIPMAS_API_HANDLER.getAllMaterials()
        call.enqueue(object:Callback<List<Material>>{
            override fun onResponse(call: Call<List<Material>>, response: Response<List<Material>>) {
                if (response.isSuccessful){
                    populateRecyclerViewWithMaterials(response.body())
                    Snackbar.make(navigationView,"OK: data retrieved",2000).show()
                }
                else
                    Snackbar.make(navigationView,"Error: Connection to Cipmas backend unsucessful",2000).show()
            }

            override fun onFailure(call: Call<List<Material>>, t: Throwable) {
                Snackbar.make(navigationView,"Failure: Connection to Cipmas backend unsucessful",2000).show()
            }

        })
    }

    private fun populateRecyclerViewWithMaterials(data : List<Material>?){
        val adapter = MaterialRecyclerAdapter(this,data)
        recycler.layoutManager=layoutManager
        recycler.adapter=adapter
    }
    private fun populateRecyclerViewWithCustomers(data:List<Customer>?){
        val adapter = CustomerRecyclerAdapter(this,data,intent)
        recycler.layoutManager=layoutManager
        recycler.adapter=adapter
    }

    private fun getAllCustomersByRetrofit(): Unit {
        var call = RETROFIT_CIPMAS_API_HANDLER.getAllCustomers()
        call.enqueue(object : Callback<List<Customer>>{
            override fun onResponse(call: Call<List<Customer>>, response: Response<List<Customer>>) {
                if(response.isSuccessful){
                    populateRecyclerViewWithCustomers(response.body())
                    Snackbar.make(navigationView,"OK:Data retrieved",2000).show()
                }
                else
                    Snackbar.make(navigationView,"Error: Connection to Cipmas backend unsucessful",2000).show()
            }

            override fun onFailure(call: Call<List<Customer>>, t: Throwable) {
                Snackbar.make(navigationView,"Failure: Connection to Cipmas backend unsucessful",2000).show()
            }

        })
    }



    fun populateRecyclerViewWithQuotations(data : List<Quotation>?) : Unit{
        val adapter  = QuotationRecyclerAdapter(this,data)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter

    }

    private fun getAllQuotationsByRetrofit() : Unit{
        var call = RETROFIT_CIPMAS_API_HANDLER.getAllQuotations() //call : Call<List<Quotation>>
        call.enqueue(object : Callback<List<Quotation>>{
            override fun onResponse(call: Call<List<Quotation>>, response: Response<List<Quotation>>) {
                if (response.isSuccessful){
                    populateRecyclerViewWithQuotations(response.body())
                    Snackbar.make(navigationView,"OK:Data retrieved",2000).show()
                }
                else
                    Snackbar.make(navigationView,"Error: Connection to Cipmas backend unsucessful",2000).show()
            }

            override fun onFailure(call: Call<List<Quotation>>, t: Throwable) {
                Snackbar.make(navigationView,"Failure: Connection to Cipmas backend unsucessful",2000).show()
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(quotationItem.isChecked){
            finishAffinity()
            exitProcess(0)
        }
    }
}