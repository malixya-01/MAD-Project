package com.example.supportapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.DataClasses.FundraisingData
import com.example.supportapp.R

class MyFundraisingsAdapter(var mList: List<FundraisingData>) :
    RecyclerView.Adapter<MyFundraisingsAdapter.MyFundraisingsViewHolder>() {



    private lateinit var mListner : onItemClickListner

    //Setting up onClick listner interface
    interface onItemClickListner{
        fun onItemClick( position: Int)
    }

    fun setOnItemClickListner(listner: onItemClickListner){
        mListner = listner
    }

    inner class MyFundraisingsViewHolder(itemView: View, listner: onItemClickListner) :RecyclerView.ViewHolder(itemView) {
        val titleTv : TextView = itemView.findViewById(R.id.titleTv)
        val dateTv : TextView = itemView.findViewById(R.id.tvMyFrDate)
        val shortAmtTv : TextView = itemView.findViewById(R.id.tvMyFrShortAmt)

        init{
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFundraisingsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item_my_fundraisings, parent, false)
        return MyFundraisingsViewHolder(view, mListner)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyFundraisingsViewHolder, position: Int) {

        var expectedAmt = mList[position].expectedAmt!!.toInt()
        var collectedAmt = mList[position].collectedAmt!!.toInt()
        var shortAmt = expectedAmt.minus(collectedAmt)

        holder.titleTv.text = mList[position].title
        holder.dateTv.text = mList[position].date
        holder.shortAmtTv.text = shortAmt.toString()

    }
}