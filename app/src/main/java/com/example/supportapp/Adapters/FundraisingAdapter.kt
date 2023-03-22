package com.example.supportapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.DataClasses.FundraisingData
import com.example.supportapp.R

class FundraisingAdapter(var mList: List<FundraisingData>) :
    RecyclerView.Adapter<FundraisingAdapter.FundraisingViewHolder>() {

    private lateinit var mListner : onItemClickListner

    //Setting up onClick listner interface
    interface onItemClickListner{
        fun onItemClick( position: Int)
    }

    fun setOnItemClickListner(listner: onItemClickListner){
        mListner = listner
    }


    inner class FundraisingViewHolder(itemView: View, listner: FundraisingAdapter.onItemClickListner) :RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val username: TextView = itemView.findViewById(R.id.tvUserName)
        val location: TextView = itemView.findViewById(R.id.tvLocation)
        val des: TextView = itemView.findViewById(R.id.tvDes)

        init{
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FundraisingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item_req_and_don, parent, false)
        return FundraisingViewHolder(view, mListner)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: FundraisingViewHolder, position: Int) {
        holder.title.text = mList[position].title
        holder.username.text = mList[position].username
        holder.location.text = mList[position].location
        holder.des.text = mList[position].description
    }


}