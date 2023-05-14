package com.example.supportapp.Fragments.Donations.MyDonationsTabs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.Adapters.myDonationsAdapter
import com.example.supportapp.DataClasses.DonationsData
import com.example.supportapp.DataClasses.RequestsData
import com.example.supportapp.DataClasses.myDonationsData
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentMyDonationsBinding
import com.example.supportapp.databinding.FragmentMyPublishedReqsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class myDonationsFragment : Fragment() {
    private lateinit var binding: FragmentMyDonationsBinding
    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<DonationsData>()
    private lateinit var adapter: myDonationsAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyDonationsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        retrieveDons()
        registerEvents()
    }

    private fun registerEvents() {
        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: myDonationsAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
                findNavController().navigate(R.id.action_myDonationsFragment_to_viewADonationFragment)
            }
        })
    }

    private fun retrieveDons() {
        if (mList.isEmpty()){
            showProgressBar()
        }
        val query = databaseRef.orderByChild("uid").equalTo(uid)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for ( frSnapshot in snapshot.children){
                    val data = frSnapshot.getValue(DonationsData::class.java)!!
                    if( data != null){
                        mList.add(data)
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

    private fun init() {
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("donations")

        recyclerView = binding.recyclerView

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(getActivity());

       /* addDataToList()*/
        adapter = myDonationsAdapter(mList)
        recyclerView.adapter = adapter
    }




  /*  private fun addDataToList(){
        mList.add(myDonationsData("My donation 1...", "2022/10/30", "10"))
        mList.add(myDonationsData("My donation 2...", "2022/10/30", "15"))
        mList.add(myDonationsData("My donation 3...", "2022/10/30", "5"))
        mList.add(myDonationsData("My donation 4...", "2022/10/30", "20"))


    }*/
  private fun showProgressBar(){
      dialog = Dialog(this@myDonationsFragment.requireContext())
      dialog.requestWindowFeature (Window. FEATURE_NO_TITLE)
      dialog.setContentView(R.layout.dialog_wait)
      dialog.setCanceledOnTouchOutside(false)
      dialog.show()
  }
    private fun hideProgressBar(){
        dialog.dismiss()
    }

}