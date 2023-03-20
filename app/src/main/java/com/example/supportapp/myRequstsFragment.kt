package com.example.supportapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.Adapters.RequestsAdapter
import com.example.supportapp.Adapters.myRequstsAdapter
import com.example.supportapp.DataClasses.myRequstsData

class myRequstsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<myRequstsData>()
    private lateinit var adapter: myRequstsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_my_requsts, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(getActivity());

        addDataToList()
        adapter = myRequstsAdapter(mList)
        recyclerView.adapter = adapter

        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: myRequstsAdapter.onItemClickListner{
            override fun onItemClick(position: Int) {
                findNavController().navigate(R.id.action_myRequstsFragment_to_viewSingleRequestFragment)
            }

        })





        return view
    }

    private fun addDataToList() {
        mList.add(myRequstsData("My requests...", R.drawable.unselected_requests))
        mList.add(myRequstsData("My requests...", R.drawable.unselected_requests))
        mList.add(myRequstsData("My requests...", R.drawable.unselected_requests))
    }

}