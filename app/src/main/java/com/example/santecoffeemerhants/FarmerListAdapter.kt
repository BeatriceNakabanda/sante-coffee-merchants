package com.example.santecoffeemerhants

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.santecoffeemerhants.data.Entity.Farmer

class FarmerListAdapter internal constructor( private val context: Context)
    : RecyclerView.Adapter<FarmerListAdapter.FarmerViewHolder>()  {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var farmers = emptyList<Farmer>()

    inner class FarmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val farmerNameView: TextView = itemView.findViewById(R.id.farmerNameTextView)
        val farmerPhoneNo: TextView = itemView.findViewById(R.id.farmerPhoneNoTextView)

        init {
            itemView.setOnClickListener{v: View ->
                val FARMER = "farmer"
                val position: Int = adapterPosition

                Toast.makeText(itemView.context, "You clicked on item # ${position + 1}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FarmerViewHolder {
        val itemView = inflater.inflate(R.layout.list_item, parent, false)
        return FarmerViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return farmers.size
    }

    override fun onBindViewHolder(holder: FarmerViewHolder, position: Int) {
        val current = farmers[position]
        holder.farmerNameView.text = current.name
        holder.farmerPhoneNo.text = current.phone_number
    }
    internal fun setFarmers(farmers: List<Farmer>){
        when(farmers){
            null ->{
                this.farmers =farmers
                notifyItemRangeInserted(0, farmers.size)
            } else -> {
            val result : DiffUtil.DiffResult = DiffUtil.calculateDiff(object: DiffUtil.Callback(){
                override fun getOldListSize(): Int {
                    return this@FarmerListAdapter.farmers.size
                }

                override fun getNewListSize(): Int {
                    return farmers.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@FarmerListAdapter.farmers.get(oldItemPosition) ==farmers.get(newItemPosition)
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    return this@FarmerListAdapter.farmers.get(oldItemPosition).equals(farmers.get(newItemPosition))
                }

            })
            this.farmers = farmers
            result.dispatchUpdatesTo(this)
        }
        }
    }
}