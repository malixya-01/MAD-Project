package com.example.supportapp.Fragments.Requests.PublishedRequests

import android.app.Dialog
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.supportapp.Fragments.Donations.supportMsgToReqFragment
import com.example.supportapp.Fragments.Fundraisings.sendSupportMsgToAFrFragment
import com.example.supportapp.Fragments.Fundraisings.viewAFrAllUsersFragmentArgs
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentNewRequestBinding
import com.example.supportapp.databinding.FragmentRequestsBinding
import com.example.supportapp.databinding.FragmentViewASingleReqAllUsersBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class viewARequestAllUsersFragment : Fragment(), supportMsgToReqFragment.dialogSubmitButtonClickedListner {

    private lateinit var popupFragment: supportMsgToReqFragment
    private val args by navArgs<viewARequestAllUsersFragmentArgs>()
    private lateinit var binding:FragmentViewASingleReqAllUsersBinding
    private lateinit var storageReference : StorageReference
    private lateinit var auth: FirebaseAuth
    private lateinit var dialog: Dialog
    private var uid : String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewASingleReqAllUsersBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        registerEvents()

    }

    private fun init() {

        auth = FirebaseAuth.getInstance()   //initialize auth
        uid = auth.currentUser?.uid //initialize current user
        //retrieve user profile pic
        storageReference = FirebaseStorage.getInstance().reference
            .child("Users/${args.currentReq.uid}")
        getUserProfilePicture()

        binding.viewAReqCollapsingToolbar.title = args.currentReq.title
        binding.tvName.text = args.currentReq.username
        binding.tvLoc.text = args.currentReq.location
        binding.tvDate.text = args.currentReq.date
        binding.tvDesc.text = args.currentReq.description
        binding.tvPhone.text = args.currentReq.phoneNo
        binding.tvBankDetails.text = args.currentReq.bankDetails

    }

    private fun registerEvents() {
        binding.btnSup.setOnClickListener {
            popupFragment = supportMsgToReqFragment()   //instantiate pop up fragment
            //popupFragment.setListner(this)  //connect pop up fragment and host fragment
            popupFragment.show(childFragmentManager, "sendSupportMsgToAFrFragment") //display fragment

        }
    }

    private fun getUserProfilePicture() {
        showProgressBar()

        //create temporary local file to store the retrieved image
        val localFile = File.createTempFile("tempImage", ".jpg")

        //retrieve image and store it to created temp file
        storageReference.getFile(localFile).addOnSuccessListener {

            //covert temp file to bitmap
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)

            //bind image
            binding.ivViewFrDp.setImageBitmap(bitmap)

            hideProgressBar()

        }.addOnFailureListener{
            hideProgressBar()
            Toast.makeText(activity, "Failed to retrieve user image", Toast.LENGTH_SHORT).show()
        }
    }




    override fun onSave(phone: String?, email: String?, message: String) {
        TODO("Not yet implemented")
    }

    private fun showProgressBar(){
        dialog = Dialog(this@viewARequestAllUsersFragment.requireContext())
        dialog.requestWindowFeature (Window. FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        dialog.dismiss()
    }
}