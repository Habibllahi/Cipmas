package ng.com.codetrik.cipmas.util

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ng.com.codetrik.cipmas.network.SELECTED_CUSTOMER_ID
import ng.com.codetrik.cipmas.network.ADD_CUSTOMER_FROM_QUOTATION_ACTIVITY_INTENT_FLAG
import ng.com.codetrik.cipmas.R
import ng.com.codetrik.cipmas.model.Customer
import ng.com.codetrik.cipmas.network.SELECTED_CUSTOMER_NAME
import ng.com.codetrik.cipmas.view.QuotationAdd

class CustomerRecyclerAdapter(private val context: Context, private val data : List<Customer>?,private val receivedIntent : Intent) : RecyclerView.Adapter<CustomerRecyclerAdapter.CustomerViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val customerView = inflater.inflate(R.layout.customer_object_card,parent,false)
        return CustomerViewHolder(customerView)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        var currentData = data?.get(position)
        holder.customerId = currentData?.id
        holder.name.text = currentData?.name?:"Hamzat"
        holder.telephone.text = currentData?.address?.telephone?.telephone?:"+2348100000"
        holder.address.text = "${currentData?.address?.houseNumber?:"No: 23"} ${currentData?.address?.streetName?:"Fatunbi"}, ${currentData?.address?.streetType?:"Street"}" +
                ", ${currentData?.address?.city?:"Ibadan"} ${currentData?.address?.state?:"Oyo state"}"
    }

    override fun getItemCount() = data?.size?:0

    inner class CustomerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                if(receivedIntent.hasExtra(ADD_CUSTOMER_FROM_QUOTATION_ACTIVITY_INTENT_FLAG)){
                    val i = Intent(context,QuotationAdd::class.java)
                    i.putExtra(SELECTED_CUSTOMER_ID,customerId)
                    i.putExtra(SELECTED_CUSTOMER_NAME,name.text.toString())
                    context.startActivity(i)
                }
            }
        }
        val name : TextView = itemView.findViewById(R.id.customer_object_customer_name)
        val address : TextView = itemView.findViewById(R.id.customer_object_customer_address)
        val telephone : TextView = itemView.findViewById(R.id.customer_object_telephone)
        val popupIcon : ImageView = itemView.findViewById(R.id.customer_object_popup_icon)
        var customerId : String? = null
    }


}