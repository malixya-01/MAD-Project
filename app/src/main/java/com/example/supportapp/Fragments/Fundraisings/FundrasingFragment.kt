package com.example.supportapp.Fragments.Fundraisings

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
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentFundrasingBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FundrasingFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var dataaseRef: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<FundraisingData>()
    private lateinit var adapter: FundraisingAdapter
    private lateinit var binding: FragmentFundrasingBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFundrasingBinding.inflate(inflater, container, false)


        recyclerView = binding.recyclerView
        searchView = binding.searchView

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(getActivity());

        addDataToList()


        adapter = FundraisingAdapter(mList)
        recyclerView.adapter = adapter


        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_fundrasingFragment_to_newFundraiserFragment)
        }

        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: FundraisingAdapter.onItemClickListner{
            override fun onItemClick(position: Int) {
                findNavController().navigate(R.id.action_fundrasingFragment_to_viewAFrAllUsersFragment)
            }

        })



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // init(view)



    }

    private fun init(view: View) {
        auth = FirebaseAuth.getInstance()
        dataaseRef = FirebaseDatabase.getInstance().reference.child("fundraising")


    }

    private fun addDataToList(){
        mList.add(FundraisingData("Little Hearts","Manusath Derana","Island wide","250000", "150000", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", true))
        mList.add(FundraisingData("Fundraiser 2","Gammadda", "Kandy","300000", "170000", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", false))
        mList.add(FundraisingData("Fundraiser 3","Dialog", "Galle","700000", "10000", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", true))
        mList.add(FundraisingData("Fundraiser 4","Mobitel", "Negombo","300000","275000",  "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", false))
    }
}