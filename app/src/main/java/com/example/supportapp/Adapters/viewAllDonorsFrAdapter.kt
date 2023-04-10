package com.example.supportapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.DataClasses.supportFundraiserData
import com.example.supportapp.R

class viewAllDonorsFrAdapter(var mList: List<supportFundraiserData>) :
    RecyclerView.Adapter<viewAllDonorsFrAdapter.ViewAllDonorsFrViewHolder>() {

    private lateinit var mListner : onItemClickListner

    //Setting up onClick listner interface
    interface onItemClickListner{
        fun onItemClick( position: Int)
    }

    fun setOnItemClickListner(listner: onItemClickListner){
        mListner = listner
    }

    inner class ViewAllDonorsFrViewHolder(itemView: View, listner: onItemClickListner) :RecyclerView.ViewHolder(itemView) {
        val tvUserName : TextView = itemView.findViewById(R.id.tvName)
        val tvDate : TextView = itemView.findViewById(R.id.tvDate)
        val tvPhone : TextView = itemView.findViewById(R.id.tvContactNumber)
        val tvEmail : TextView = itemView.findViewById(R.id.tvEmail)
        val tvDescription : TextView = itemView.findViewById(R.id.tvDescription)
        val userDp : ImageView = itemView.findViewById(R.id.ivViewDp)

        init{
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAllDonorsFrViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item_view_sup_msgs, parent, false)
        return ViewAllDonorsFrViewHolder(view, mListner)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewAllDonorsFrViewHolder, position: Int) {
        holder.tvUserName.text = mList[position].supporterId
        holder.tvDate.text = mList[position].date
        holder.tvPhone.text = mList[position].phoneNumber
        holder.tvEmail.text = mList[position].email
        holder.tvDescription.text = mList[position].message



    }

}