package com.example.santecoffeemerhants

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.nav_row.view.*

class NavigationListAdapter(private var items: ArrayList<NavigationItemModel>,
                            private var currentPosition: Int): RecyclerView.Adapter<NavigationListAdapter.NavigationItemViewHolder>() {

    private lateinit var context: Context

    class NavigationItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NavigationItemViewHolder {
        context = parent.context
        val navItem = LayoutInflater.from(parent.context).inflate(R.layout.nav_row, parent, false)
        return NavigationItemViewHolder(navItem)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: NavigationItemViewHolder, position: Int) {
        // To highlight the selected Item, show different background color
        if (position == currentPosition){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorLighter))
        }else{
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))

        }
//        holder.itemView.navigation_title.setTextColor(Color.BLACK)
        holder.itemView.navigation_title.text = items[position].title
    }
}