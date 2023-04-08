package com.example.supportapp.Fragments.Fundraisings

import android.app.Dialog
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentViewAFrBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class viewAFrFragment : Fragment() {

    private lateinit var binding: FragmentViewAFrBinding

    //safe args
    private val args by navArgs<viewAFrAllUsersFragmentArgs>()

    lateinit var progressBar: ProgressBar
    private lateinit var fab: FloatingActionButton

    private lateinit var auth: FirebaseAuth
    private lateinit var storageReference : StorageReference
    private lateinit var dialog: Dialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewAFrBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        registerEvents()

    }

    private fun init() {

        //retrieve user profile pic
        var frUid = args.currentFr.uid
        storageReference = FirebaseStorage.getInstance().reference.child("Users/$frUid")
        getUserProfilePicture()

        //binding data
        binding.vMyFrCollapsingToolbar.title = args.currentFr.title

        binding.tvName.text = args.currentFr.username
        binding.tvDate.text = args.currentFr.date
        binding.tvTotAmt.text = args.currentFr.expectedAmt
        binding.tvReqAmt.text = args.currentFr.collectedAmt
        binding.tvDes.text = args.currentFr.description
        binding.tvPhone.text = args.currentFr.contactNo
        binding.tvWeb.text = args.currentFr.website
        binding.tvEmail.text = args.currentFr.email
        binding.tvBankDetails.text = args.currentFr.bankDetails

        if(args.currentFr.website.isNullOrBlank()){
            binding.tvWeb.text = "No web site..."
        }

        //handle progressbar
        var max = args.currentFr.expectedAmt!!.toInt()
        var progress = args.currentFr.collectedAmt!!.toInt()
        progressBar = binding.frProgressBar
        progressBar.max = max
        progressBar.progress = progress

        //handle verified status
        binding.ivIsVerified.visibility = View.GONE;
        if (args.currentFr.verStatus == true) {
            binding.ivIsVerified.visibility = View.VISIBLE;

        }





    }

    private fun registerEvents() {
        binding.updateBtn.setOnClickListener {
            var action = viewAFrFragmentDirections.actionViewAFrFragmentToUpdateFrFragment(args.currentFr.frId.toString())
            findNavController().navigate(action)
        }

        binding.viewDonors.setOnClickListener {
            findNavController().navigate(R.id.action_viewAFrFragment_to_viewAllDonorsToAFrFragment)
        }

        binding.btnDlt.setOnClickListener {
            showDeleteConfirmationDialog()
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

    private fun showProgressBar(){
        dialog = Dialog(this@viewAFrFragment.requireContext())
        dialog.requestWindowFeature (Window. FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        dialog.dismiss()
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
            Toast.makeText(requireContext(), "Item deleted", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }



}