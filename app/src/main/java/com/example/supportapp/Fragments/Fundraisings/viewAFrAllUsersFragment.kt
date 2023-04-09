package com.example.supportapp.Fragments.Fundraisings

import android.app.Dialog
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentViewAFrAllUsersBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class viewAFrAllUsersFragment : Fragment() {

    private lateinit var binding: FragmentViewAFrAllUsersBinding

    //safe args
    private val args by navArgs<viewAFrAllUsersFragmentArgs>()

    lateinit var progressBar: ProgressBar
    private lateinit var fab: FloatingActionButton
    private var unselectedIcon = true

    private lateinit var auth: FirebaseAuth
    private lateinit var storageReference : StorageReference
    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewAFrAllUsersBinding.inflate(inflater, container, false)
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
        binding.vFrACollapsingToolbar.title = args.currentFr.title
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
            binding.viewAFrWebContainerLayout.visibility = View.GONE
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

        //toggle saved items
        fab = binding.btnSave
        fab.setOnClickListener {
            //varibles to hold toast msgs
            val itemAdded = resources.getString(R.string.itemAdded)
            val itemRemoved = resources.getString(R.string.itemRemoved)

            // unselectedIcon true if unselected_bookmark icon is visible, false if selected_bookmark icon is visible.
            if (unselectedIcon) {
                fab.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.selected_bookmark))
                Toast.makeText(requireContext(), itemAdded, Toast.LENGTH_SHORT).show()
                unselectedIcon = false

            } else {
                fab.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.unselected_bookmark))
                unselectedIcon = true
                Toast.makeText(requireContext(), itemRemoved, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSup.setOnClickListener {
            var currFrId = args.currentFr.frId
            var action = viewAFrAllUsersFragmentDirections.actionViewAFrAllUsersFragmentToSendSupportMsgToAFrFragment(currFrId!!)
            findNavController().navigate(action)
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
        dialog = Dialog(this@viewAFrAllUsersFragment.requireContext())
        dialog.requestWindowFeature (Window. FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        dialog.dismiss()
    }




}