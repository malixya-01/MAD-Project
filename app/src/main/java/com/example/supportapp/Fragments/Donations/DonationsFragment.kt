package com.example.supportapp.Fragments.Donations

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.Adapters.DonationsAdapter
import com.example.supportapp.DataClasses.DonationsData
import com.example.supportapp.DataClasses.RequestsData
import com.example.supportapp.Fragments.Requests.PublishedRequests.RequestsFragmentDirections
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentDonationsBinding
import com.example.supportapp.databinding.FragmentRequestsBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DonationsFragment : Fragment(R.layout.fragment_donations) {

    private lateinit var binding: FragmentDonationsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<DonationsData>()
    private lateinit var adapter: DonationsAdapter
    private lateinit var uid: String
    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDonationsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        retrieveDonations()
        registerEvents()
    }



    private fun init() {

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("donations")

        //set up recyclerView
        recyclerView = binding.recyclerView

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(getActivity());

        adapter = DonationsAdapter(mList)
        recyclerView.adapter = adapter
    }

    private fun retrieveDonations() {
        if (mList.isEmpty()){
            showProgressBar()
        }
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for ( frSnapshot in snapshot.children){
                    val req = frSnapshot.getValue(DonationsData::class.java)!!
                    if( req != null){
                        mList.add(req)
                    }
                }
                adapter.notifyDataSetChanged()
                hideProgressBar()
            }

            override fun onCancelled(error: DatabaseError) {
                hideProgressBar()
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun registerEvents() {


        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: DonationsAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
                //findNavController().navigate(R.id.action_donationsFragment_to_viewADonationAllUserFragment)
                val data = mList[position]
                val action = DonationsFragmentDirections.actionDonationsFragmentToViewADonationAllUserFragment(data)
                findNavController().navigate(action)


            }

        })

        val btnAdd = binding.btnAdd
        btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_donationsFragment_to_newDonationFragment)
        }


    }

/*    private fun addDataToList(){
        mList.add(DonationsData("I can donate ...","Nuwan Thushara","Colombo", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
        mList.add(DonationsData("I can donate ...","Kamal Gunarathne", "Kandy", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
        mList.add(DonationsData("I can donate ...","Lahiru Kumara", "Galle", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
        mList.add(DonationsData("I can donate ...","Ranil Wikramasinghe", "Negombo", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))

    }*/

    private fun showProgressBar(){
        dialog = Dialog(this@DonationsFragment.requireContext())
        dialog.requestWindowFeature (Window. FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        dialog.dismiss()
    }

}