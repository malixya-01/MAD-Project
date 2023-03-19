package com.example.supportapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.Adapters.MyFundraisingsAdapter
import com.example.supportapp.Adapters.myDonationsAdapter
import com.example.supportapp.DataClasses.MyFundraisingsData
import com.example.supportapp.DataClasses.myDonationsData

class MyFundraisingsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<MyFundraisingsData>()
    private lateinit var adapter: MyFundraisingsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_my_fundraisings, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(getActivity());

        addDataToList()
        adapter = MyFundraisingsAdapter(mList)
        recyclerView.adapter = adapter


        return view
    }

    private fun addDataToList(){
         mList.add(MyFundraisingsData("My Fundraisings...", R.drawable.unselected_requests))
         mList.add(MyFundraisingsData("My Fundraisings...", R.drawable.unselected_requests))
         mList.add(MyFundraisingsData("My Fundraisings...", R.drawable.unselected_requests))

    }


}