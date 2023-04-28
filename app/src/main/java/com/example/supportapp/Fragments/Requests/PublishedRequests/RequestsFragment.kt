package com.example.supportapp.Fragments.Requests.PublishedRequests

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
import com.example.supportapp.databinding.FragmentNewRequestBinding
import com.example.supportapp.databinding.FragmentRequestsBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RequestsFragment : Fragment() {
    private lateinit var binding:FragmentRequestsBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<RequestsData>()
    private lateinit var adapter: RequestsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRequestsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        registerEvents()
    }

    private fun init() {
        val recyclerView = binding.recyclerView
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
    }

    private fun registerEvents() {
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_requestsFragment_to_newRequestFragment)
        }

    }

    private fun addDataToList(){
        mList.add(RequestsData("I need food ...","Nuwan Thushara","Colombo", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
        mList.add(RequestsData("I need stationary itemes ...","Kamal Gunarathne", "Kandy", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
        mList.add(RequestsData("I need cloths ...","Lahiru Kumara", "Galle", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
        mList.add(RequestsData("I need money ...","Ranil Wikramasinghe", "Negombo", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
    }

}