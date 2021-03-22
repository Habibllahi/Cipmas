package ng.com.codetrik.cipmas.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ng.com.codetrik.cipmas.R
import ng.com.codetrik.cipmas.model.Quotation

class QuotationRecyclerAdapter(private val context: Context, private val quotations : List<Quotation>?) : RecyclerView.Adapter<QuotationRecyclerAdapter.QuotationViewHolder>(){

    private val inflater = LayoutInflater.from(context);

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotationViewHolder {
        val inflatedView = inflater.inflate(R.layout.quotation_object_card,parent,false)
        return QuotationViewHolder(inflatedView)
    }


    override fun onBindViewHolder(holder: QuotationViewHolder, position: Int) {
        var currentQuotation = quotations?.get(position)
        holder.customerName.text = currentQuotation?.customer?.name?:"Imran"
        holder.description.text = currentQuotation?.description?:"3KVA inveter installation"
        holder.telephone.text= currentQuotation?.customer?.address?.telephone?.telephone?:"081XXXXXXXXX"

    }

    override fun getItemCount() = quotations?.size?:0


    inner class QuotationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val customerName: TextView = itemView.findViewById(R.id.customer_name)
        val description: TextView = itemView.findViewById(R.id.description)
        val telephone: TextView = itemView.findViewById(R.id.telephone)
        val popupIcon: ImageView = itemView.findViewById(R.id.popup_icon)
    }
}