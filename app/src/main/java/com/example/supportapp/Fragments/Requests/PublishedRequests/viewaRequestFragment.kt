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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentViewARequestBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class viewaRequestFragment : Fragment() {

    private lateinit var binding: FragmentViewARequestBinding
    private lateinit var storageReference : StorageReference
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var dialog: Dialog
    private var uid : String? = null
    private val args by navArgs<viewaRequestFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewARequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        registerEvents()
    }

    private fun init() {

        databaseReference = FirebaseDatabase.getInstance().getReference("requests")

        auth = FirebaseAuth.getInstance()   //initialize auth
        uid = auth.currentUser?.uid //initialize current user
        //retrieve user profile pic
        storageReference = FirebaseStorage.getInstance().reference
            .child("Users/$uid")
        getUserProfilePicture()

        binding.toolbarTitle.title = args.currentReq.title
        binding.tvName.text = args.currentReq.username
        binding.tvLoc.text = args.currentReq.location
        binding.tvDate.text = args.currentReq.date
        binding.tvDesc.text = args.currentReq.description
        binding.tvPhone.text = args.currentReq.phoneNo
        binding.tvBankDetails.text = args.currentReq.bankDetails
    }

    private fun registerEvents() {
        binding.btnEditReq.setOnClickListener {
            var action = viewaRequestFragmentDirections.actionViewSingleRequestFragmentToEditARequestFragment2(args.currentReq)
            findNavController().navigate(action)
        }

        binding.viewDonors.setOnClickListener {
            findNavController().navigate(R.id.action_viewSingleRequestFragment_to_viewReqAllDonorsFragment)
        }

        binding.btnDlt.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }


    private fun showDeleteConfirmationDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_delete_confirmation)
        dialog.setCancelable(false)

        val cancelButton = dialog.findViewById<Button>(R.id.btn_cancel)
        val deleteButton = dialog.findViewById<Button>(R.id.btn_delete)


        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        deleteButton.setOnClickListener {
            databaseReference.child(args.currentReq.reqId!!).removeValue().addOnCompleteListener {
                if( it.isSuccessful){
                    Toast.makeText(requireContext(), "Item deleted", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_viewSingleRequestFragment_to_myRequestsFragment)
                }
            }
            dialog.dismiss()
        }

        dialog.show()
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

    private fun showProgressBar(){
        dialog = Dialog(this@viewaRequestFragment.requireContext())
        dialog.requestWindowFeature (Window. FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        dialog.dismiss()
    }


}