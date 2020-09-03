package com.example.santecoffeemerhants

import android.content.ActivityNotFoundException
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.santecoffeemerhants.data.Entity.CooperativeManager


class ManagerListAdapter internal constructor( private val context: Context)
    : RecyclerView.Adapter<ManagerListAdapter.ManagerViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var cooperativeManagers = emptyList<CooperativeManager>()

    inner class ManagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val managerNameView: TextView = itemView.findViewById(R.id.listManagerName)
        val managerContactView: TextView = itemView.findViewById(R.id.listManagerContact)
        val managerEmailView: TextView = itemView.findViewById(R.id.listManagerEmail)
        val sendEmailButton: Button = itemView.findViewById(R.id.sendEmail)
        val editContact: Button = itemView.findViewById(R.id.editContact)

//        init {
//            itemView.setOnClickListener{
//                val COOPERATIVEMANAGER = "cooperativeManager"
//                val position: Int = adapterPosition
//
//                val intent = Intent(context, ManagersActivity::class.java)
//                val bundle = Bundle()
//                bundle.putSerializable(COOPERATIVEMANAGER, cooperativeManagers[position])
//                intent.putExtras(bundle)
//                context.startActivity(intent)
//            }
//        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ManagerListAdapter.ManagerViewHolder {
        val itemView = inflater.inflate(R.layout.item_manager_list, parent, false)
        return ManagerViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return cooperativeManagers.size
    }

    override fun onBindViewHolder(holder: ManagerListAdapter.ManagerViewHolder, position: Int) {
        val current = cooperativeManagers[position]

        holder.managerNameView.text = current.name
        holder.managerContactView.text = current.phone_number
        holder.managerEmailView.text = current.email

        holder.sendEmailButton.setOnClickListener {

            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.data = Uri.parse("mailto:")
            emailIntent.type = ClipDescription.MIMETYPE_TEXT_PLAIN
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(current.email))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Welcome")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi ${current.name}, \n We are pleased to have " +
                    "you as a cooperative manager in our region")
            //check if there is an activity to handle this intent
            try {
                context.startActivity(Intent.createChooser(emailIntent, "Send mail..."))
            } catch (ex: ActivityNotFoundException) {
                Log.i("TAG", "There is no email client installed.")
            }

        }
        holder.editContact.setOnClickListener {
            Log.i("TAG", "YOU CLICKED EDIT CONTACT BUTTON ")
            val COOPERATIVEMANAGER = "cooperativeManager"

            val intent = Intent(context, ManagersActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable(COOPERATIVEMANAGER, cooperativeManagers[position])
            intent.putExtras(bundle)
            context.startActivity(intent)
        }


    }

    internal fun setManagers(cooperativeManagers: List<CooperativeManager>){
        when(cooperativeManagers){
            null ->{
                this.cooperativeManagers = cooperativeManagers
                notifyItemRangeInserted(0, cooperativeManagers.size)
            } else -> {
            val result : DiffUtil.DiffResult = DiffUtil.calculateDiff(object: DiffUtil.Callback(){
                override fun getOldListSize(): Int {
                    return this@ManagerListAdapter.cooperativeManagers.size
                }

                override fun getNewListSize(): Int {
                    return cooperativeManagers.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@ManagerListAdapter.cooperativeManagers[oldItemPosition] == cooperativeManagers[newItemPosition]
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    return this@ManagerListAdapter.cooperativeManagers[oldItemPosition] == cooperativeManagers[newItemPosition]
                }
            })
            this.cooperativeManagers = cooperativeManagers
            result.dispatchUpdatesTo(this)
        }
        }
    }
}