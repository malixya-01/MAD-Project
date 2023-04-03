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



    private lateinit var mListner : onItemClickListner

    //Setting up onClick listner interface
    interface onItemClickListner{
        fun onItemClick( position: Int)
    }

    fun setOnItemClickListner(listner: onItemClickListner){
        mListner = listner
    }

    inner class myDonationsViewHolder(itemView: View, listner: onItemClickListner) :RecyclerView.ViewHolder(itemView) {
        val count: TextView = itemView.findViewById(R.id.tvCount)
        val titleTv : TextView = itemView.findViewById(R.id.titleTv)
        val dateTv : TextView = itemView.findViewById(R.id.tvDate)
        init{
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myDonationsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item_my_reqs_and_dons_test, parent, false)
        return myDonationsViewHolder(view, mListner)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: myDonationsViewHolder, position: Int) {
        holder.titleTv.text = mList[position].title
        holder.dateTv.text = mList[position].date
        holder.count.text = mList[position].count
    }
}