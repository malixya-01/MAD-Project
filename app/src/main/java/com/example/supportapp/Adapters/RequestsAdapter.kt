package com.example.supportapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.DataClasses.RequestsData
import com.example.supportapp.R

class RequestsAdapter(var mList: List<RequestsData>) :
    RecyclerView.Adapter<RequestsAdapter.RequestsViewHolder>() {



    private lateinit var mListner : onItemClickListner

    //Setting up onClick listner interface
    interface onItemClickListner{
        fun onItemClick( position: Int)
    }

    fun setOnItemClickListner(listner: onItemClickListner){
        mListner = listner
    }

    inner class RequestsViewHolder(itemView: View, listner: onItemClickListner) :RecyclerView.ViewHolder(itemView) {
        val logo: ImageView = itemView.findViewById(R.id.logoIv)
        val titleTv : TextView = itemView.findViewById(R.id.titleTv)
        init{
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent, false)
        return RequestsViewHolder(view, mListner)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: RequestsViewHolder, position: Int) {
        holder.logo.setImageResource(mList[position].logo)
        holder.titleTv.text = mList[position].title
    }
}