package com.example.supportapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.Adapters.RequestsAdapter
import com.example.supportapp.DataClasses.RequestsData
import com.example.supportapp.R

class RequestsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<RequestsData>()
    private lateinit var adapter: RequestsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_requests, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        searchView = view.findViewById(R.id.searchView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(getActivity());

        //add data to data class
        addDataToList()

        //Passing data to adapter
        adapter = RequestsAdapter(mList)
        recyclerView.adapter = adapter

        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: RequestsAdapter.onItemClickListner{
            override fun onItemClick(position: Int) {
                findNavController().navigate(R.id.action_requestsFragment_to_viewASingleReqAllUsersFragment2)
            }

        })

        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_requestsFragment_to_newRequestFragment)
        }

        return view
    }

    private fun addDataToList(){
        mList.add(RequestsData("Need ...", R.drawable.unselected_requests))
        mList.add(RequestsData("Need ...", R.drawable.unselected_requests))
        mList.add(RequestsData("Need ...", R.drawable.unselected_requests))
        mList.add(RequestsData("Need ...", R.drawable.unselected_requests))
        mList.add(RequestsData("Need ...", R.drawable.unselected_requests))
        mList.add(RequestsData("Need ...", R.drawable.unselected_requests))
        mList.add(RequestsData("Need ...", R.drawable.unselected_requests))
        mList.add(RequestsData("Need ...", R.drawable.unselected_requests))
        mList.add(RequestsData("Need ...", R.drawable.unselected_requests))
        mList.add(RequestsData("Need ...", R.drawable.unselected_requests))
        mList.add(RequestsData("Need ...", R.drawable.unselected_requests))
    }


}