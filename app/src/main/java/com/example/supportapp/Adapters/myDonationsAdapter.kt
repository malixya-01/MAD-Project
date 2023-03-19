package com.example.supportapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.DataClasses.myDonationsData
import com.example.supportapp.R

class myDonationsAdapter(var mList: List<myDonationsData>) :
    RecyclerView.Adapter<myDonationsAdapter.myDonationsViewHolder>() {

    inner class myDonationsViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val logo: ImageView = itemView.findViewById(R.id.logoIv)
        val titleTv : TextView = itemView.findViewById(R.id.titleTv)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myDonationsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent, false)
        return myDonationsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: myDonationsViewHolder, position: Int) {
        holder.logo.setImageResource(mList[position].logo)
        holder.titleTv.text = mList[position].title
    }


}