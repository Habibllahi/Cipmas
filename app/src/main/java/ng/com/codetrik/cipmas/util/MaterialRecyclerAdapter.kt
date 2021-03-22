package ng.com.codetrik.cipmas.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ng.com.codetrik.cipmas.R
import ng.com.codetrik.cipmas.model.Material

class MaterialRecyclerAdapter(private val context: Context, private val data : List<Material>?): RecyclerView.Adapter<MaterialRecyclerAdapter.MaterialViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialViewHolder {
        val inflatedView = inflater.inflate(R.layout.material_object_card,parent,false)
        return MaterialViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: MaterialViewHolder, position: Int) {
        var currentMaterial = data?.get(position)
        holder.genericName.text = currentMaterial?.genericName?:"Inveter"
        holder.productName.text = currentMaterial?.productName?:"Techfine"
        holder.guage.text = currentMaterial?.gauge?:"5KVA"
    }

    override fun getItemCount() = data?.size?:0

    inner class MaterialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var genericName : TextView = itemView.findViewById(R.id.material_generic_name)
        var productName : TextView = itemView.findViewById(R.id.material_product_name)
        var guage : TextView = itemView.findViewById(R.id.material_guage)
        var materialPopupMenu : ImageView = itemView.findViewById(R.id.material_popup_icon)
    }
}

