package ng.com.codetrik.cipmas.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ng.com.codetrik.cipmas.R
import ng.com.codetrik.cipmas.model.Supplier
import ng.com.codetrik.cipmas.model.Telephone
import ng.com.codetrik.cipmas.network.SELECTED_SUPPLIER_ID
import ng.com.codetrik.cipmas.network.SELECTED_SUPPLIER_NAME
import ng.com.codetrik.cipmas.network.SUPPLIER_RECYCLER_BUNDLE_EXTRA
import ng.com.codetrik.cipmas.view.MaterialAdd

class SupplierRecyclerAdapter(private val context: Context, private val data : List<Supplier>?, private val bundleExtra: Bundle?) : RecyclerView.Adapter<SupplierRecyclerAdapter.SupplierViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupplierViewHolder {
        return SupplierViewHolder(inflater.inflate(R.layout.supply_object_card,parent,false))
    }

    override fun onBindViewHolder(holder: SupplierViewHolder, position: Int) {
        var currentData = data?.get(position)
        holder.name.text = currentData?.name?:"John Doe"
        holder.address.text = "${currentData?.address?.houseNumber?:"No: 23"} ${currentData?.address?.streetName?:"Fatunbi"}, ${currentData?.address?.streetType?:"Street"}" +
                ", ${currentData?.address?.city?:"Ibadan"} ${currentData?.address?.state?:"Oyo state"}"
        holder.telephone.text = currentData?.address?.telephone?.telephone?:"+2348100000"
        holder.id=currentData?.id
    }

    override fun getItemCount() = data?.size?:0


    inner class SupplierViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init{
            itemView.setOnClickListener {
                val intent = Intent(context, MaterialAdd::class.java)
                intent.putExtra(SELECTED_SUPPLIER_ID,id)
                intent.putExtra(SELECTED_SUPPLIER_NAME,name.text.toString())
                if(bundleExtra!=null)
                    intent.putExtra(SUPPLIER_RECYCLER_BUNDLE_EXTRA,bundleExtra)
                context.startActivity(intent)
            }
        }
        val name : TextView = itemView.findViewById(R.id.supplier_object_supplier_name)
        val address : TextView = itemView.findViewById(R.id.supplier_object_supplier_address)
        val telephone : TextView = itemView.findViewById(R.id.supplier_object_telephone)
        val popupMenu : ImageView = itemView.findViewById(R.id.supplier_object_popup_icon)
        var id : String? = null
    }
}