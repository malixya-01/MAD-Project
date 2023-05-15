package com.example.supportapp.Fragments.Requests.PublishedRequests

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.Adapters.viewAllDonorsReqAdapter
import com.example.supportapp.DataClasses.reqFromDonorData
import com.example.supportapp.DataClasses.supportFundraiserData
import com.example.supportapp.Fragments.Fundraisings.editRecyclerItem
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentViewAllDonorsBinding
import com.google.firebase.database.*

class sentReqsFragment : Fragment(),
    viewAllDonorsReqAdapter.popupMenuOnItemClickInterface, editRecyclerItem.onUpdateBtnClickeListner{

    private lateinit var binding : FragmentViewAllDonorsBinding
    private var popupFragment: editRecyclerItem? = null
    //private val args: viewAllDonorsToAFrFragmentArgs by navArgs()
    private var currFrId : String? = null
    private lateinit var databaseRef: DatabaseReference
    private lateinit var dialog: Dialog
    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<reqFromDonorData>()
    private lateinit var adapter: viewAllDonorsReqAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewAllDonorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        retrieveData()
        registerEvents()
    }

    private fun init() {

       // currFrId = args.currFrId
        databaseRef = FirebaseDatabase.getInstance().reference.child("requestFromDonor")
        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context);
        //addData()
        retrieveData()
        adapter = viewAllDonorsReqAdapter(requireContext(), mList)
        recyclerView.adapter = adapter
        adapter.setPopMenulistner(this)
    }

    private fun retrieveData() {
        if (mList.isEmpty()){
            showProgressBar()
        }

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for ( myFrSnapshot in snapshot.children){
                    val data = myFrSnapshot.getValue(reqFromDonorData::class.java)!!
                    mList.add(data)
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
        adapter.setOnItemClickListner(object: viewAllDonorsReqAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
            }
        })
    }

    private fun showProgressBar(){
        dialog = Dialog(this@sentReqsFragment.requireContext())
        dialog.requestWindowFeature (Window. FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        dialog.dismiss()
    }

/*    private fun addData() {
        mList.clear()
        mList.add(supportFundraiserData("", "Nuwan Thushara", "kk@kk.com", "0712345678", "Yoooo yooo what's up", "2023/04/11"))
    }*/

    override fun onEditBtnClicked(supFrData: reqFromDonorData) {
        //pass arguments to the popup fragment
        popupFragment = editRecyclerItem.newInstance(
            supFrData.msgId!!,
            supFrData.phoneNumber!!,
            supFrData.email!!,
            supFrData.message!!,
            supFrData.supporterId!!,
            supFrData.date!!
        )

        popupFragment!!.setListner(this)
        popupFragment!!.show(childFragmentManager, editRecyclerItem.TAG)

    }

    override fun onDeleteBtnClicked(supFrData: reqFromDonorData) {
        var currentMsgId = supFrData.msgId
        databaseRef.child(currentMsgId!!).removeValue().addOnCompleteListener {
            if( it.isSuccessful){
                Toast.makeText(requireContext(), "Item deleted", Toast.LENGTH_SHORT).show()
                //findNavController().navigate(R.id.action_viewAFrFragment_to_myFundraisingsFragment)
            }
        }
        dialog.dismiss()
    }

    override fun onUpdate(updatedSupMsgData: supportFundraiserData) {

        val map = HashMap<String, Any>() // create a hash map

        //add elements to the hashmap
        map["email"] = updatedSupMsgData.email!!
        map["phoneNumber"] = updatedSupMsgData.phoneNumber!!
        map["message"] = updatedSupMsgData.message!!

        //update database
        var currentMsgId = updatedSupMsgData.msgId
        databaseRef.child(currentMsgId!!)
            .updateChildren(map).addOnCompleteListener {
                if( it.isSuccessful){
                    Toast.makeText(context, "Message updated", Toast.LENGTH_SHORT).show()
                    popupFragment!!.dismiss()
                } else {
                    Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }

        /*private fun addData() {
            mList.clear()
            mList.add(supportFundraiserData("", "Nuwan Thushara", "kk@kk.com", "0712345678", "Yoooo yooo what's up", "2023/04/11"))
        }*/
    }
}