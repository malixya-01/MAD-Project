package com.example.supportapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.Adapters.mySentReqsAdapter
import com.example.supportapp.DataClasses.mySentReqsData
import com.example.supportapp.R

class mySentReqsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<mySentReqsData>()
    private lateinit var adapter: mySentReqsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_my_sent_reqs, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(getActivity());

        addDataToList()
        adapter = mySentReqsAdapter(mList)
        recyclerView.adapter = adapter

        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: mySentReqsAdapter.onItemClickListner{
            override fun onItemClick(position: Int) {
                findNavController().navigate(R.id.action_mySentReqsFragment_to_viewAMySentReqFragment)
            }

        })

        return view
    }

    private fun addDataToList() {
        mList.add(mySentReqsData("Can I have...", R.drawable.unselected_requests))
        mList.add(mySentReqsData("Can I have...", R.drawable.unselected_requests))
        mList.add(mySentReqsData("Can I have...", R.drawable.unselected_requests))
    }

}