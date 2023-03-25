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
        val reqAmt: TextView = itemView.findViewById(R.id.tvReqAmt)
        val des: TextView = itemView.findViewById(R.id.tvDes)
        var ic_verified: ImageView = itemView.findViewById(R.id.ivVerified)

        init{
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FundraisingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item_fundraisings, parent, false)
        return FundraisingViewHolder(view, mListner)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: FundraisingViewHolder, position: Int) {
        holder.title.text = mList[position].title
        holder.username.text = mList[position].username
        holder.location.text = mList[position].location
        holder.reqAmt.text = mList[position].reqAmt
        holder.des.text = mList[position].description

        //verification status logic
        holder.ic_verified.setVisibility(View.GONE);
        if (mList[position].verStatus == true) {
            holder.ic_verified.setVisibility(View.VISIBLE);
        }

    }


}