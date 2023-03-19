package com.example.supportapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.Adapters.FundraisingAdapter
import com.example.supportapp.DataClasses.FundraisingData

class FundrasingFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<FundraisingData>()
    private lateinit var adapter: FundraisingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fundrasing, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        searchView = view.findViewById(R.id.searchView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(getActivity());

        addDataToList()
        adapter = FundraisingAdapter(mList)
        recyclerView.adapter = adapter


        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_fundrasingFragment_to_newFundraiserFragment)
        }

        return view
    }

    private fun addDataToList(){
        mList.add(FundraisingData("Need funds for...", R.drawable.unselected_requests))
        mList.add(FundraisingData("Need funds for...", R.drawable.unselected_requests))
        mList.add(FundraisingData("Need funds for...", R.drawable.unselected_requests))
        mList.add(FundraisingData("Need funds for...", R.drawable.unselected_requests))
        mList.add(FundraisingData("Need funds for...", R.drawable.unselected_requests))
        mList.add(FundraisingData("Need funds for...", R.drawable.unselected_requests))
        mList.add(FundraisingData("Need funds for...", R.drawable.unselected_requests))
        mList.add(FundraisingData("Need funds for...", R.drawable.unselected_requests))
        mList.add(FundraisingData("Need funds for...", R.drawable.unselected_requests))
        mList.add(FundraisingData("Need funds for...", R.drawable.unselected_requests))
        mList.add(FundraisingData("Need funds for...", R.drawable.unselected_requests))
        mList.add(FundraisingData("Need funds for...", R.drawable.unselected_requests))

    }
}