package com.example.supportapp.Fragments.Fundraisings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.Adapters.FundraisingAdapter
import com.example.supportapp.DataClasses.FundraisingData
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentFundrasingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FundrasingFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<FundraisingData>()
    private lateinit var adapter: FundraisingAdapter
    private lateinit var binding: FragmentFundrasingBinding
    private lateinit var uid: String
    private lateinit var frData: FundraisingData



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFundrasingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        //addDataToList()
        retrieveFrs()
        registerEvents()
    }
    private fun init(view: View) {
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("fundraising")

        recyclerView = binding.recyclerView
        searchView = binding.searchView

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context);

        adapter = FundraisingAdapter(mList)
        recyclerView.adapter = adapter

    }
    private fun retrieveFrs() {
        databaseRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for ( frSnapshot in snapshot.children){
                    val fr = frSnapshot.getValue(FundraisingData::class.java)!!
                    if( fr != null){
                        mList.add(fr)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun registerEvents() {

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_fundrasingFragment_to_newFundraiserFragment)
        }

        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: FundraisingAdapter.onItemClickListner{
            override fun onItemClick(position: Int) {
                findNavController().navigate(R.id.action_fundrasingFragment_to_viewAFrAllUsersFragment)
            }
        })
    }



    private fun addDataToList(){
        mList.add(FundraisingData("Donate a CT scanner", "he Leo Club of the SLIIT is organizing a fundraiser to donate a CT scanner to","2000000", "10000", "0732254620", "leo@sliit.lk", "www.leo.sliit.lk", "BOC Malabe branch", "Leo Club Sliit", false, "abcd"))
        mList.add(FundraisingData("Donate a CT scanner", "he Leo Club of the SLIIT is organizing a fundraiser to donate a CT scanner to","2000000", "10000", "0732254620", "leo@sliit.lk", "www.leo.sliit.lk", "BOC Malabe branch", "Leo Club Sliit", false, "abcd"))
        mList.add(FundraisingData("Donate a CT scanner", "he Leo Club of the SLIIT is organizing a fundraiser to donate a CT scanner to","2000000", "10000", "0732254620", "leo@sliit.lk", "www.leo.sliit.lk", "BOC Malabe branch", "Leo Club Sliit", false, "abcd"))
    }
}