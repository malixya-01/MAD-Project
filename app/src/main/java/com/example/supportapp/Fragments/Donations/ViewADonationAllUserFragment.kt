package com.example.supportapp.Fragments.Donations

import android.app.Dialog
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.supportapp.DataClasses.reqFromDonorData
import com.example.supportapp.DataClasses.supportFundraiserData
import com.example.supportapp.Fragments.Requests.PublishedRequests.viewARequestAllUsersFragmentArgs
import com.example.supportapp.Fragments.Requests.SentRequests.addReqtoTheDonorFragment
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentViewADonationAllUserBinding
import com.example.supportapp.databinding.FragmentViewASingleReqAllUsersBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ViewADonationAllUserFragment : Fragment(),
    addReqtoTheDonorFragment.dialogSubmitButtonClickedListner {

    private lateinit var binding : FragmentViewADonationAllUserBinding
    private lateinit var popupFragment: addReqtoTheDonorFragment
    private val args by navArgs<ViewADonationAllUserFragmentArgs>()
    private lateinit var storageReference : StorageReference
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var dialog: Dialog
    private var uid : String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewADonationAllUserBinding.inflate(inflater, container, false)
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
            .child("Users/${args.currentDon.uid}")
        getUserProfilePicture()

        binding.toolbar.title = args.currentDon.title
        binding.tvName.text = args.currentDon.username
        binding.tvLoc.text = args.currentDon.location
        binding.tvDate.text = args.currentDon.date
        binding.tvDes.text = args.currentDon.description
        binding.tvPhone.text = args.currentDon.phoneNo
    }

    private fun registerEvents() {

        //request btn
        binding.btnReq.setOnClickListener {

            popupFragment = addReqtoTheDonorFragment()  //instantiate pop up fragment
            popupFragment!!.setListner(this)    //connect pop up fragment and host fragment
            popupFragment.show(childFragmentManager, "addReqtoTheDonorFragment")    //display fragment
        }





        //save item btn
        binding.btnSave.setOnClickListener {

            //Variables to set up FAB onclick icon change
            val fab = binding.btnSave
            var flag = true // true if first icon is visible, false if second one is visible.

            //varibles to hold toast msgs
            val itemAdded = resources.getString(R.string.itemAdded)
            val itemRemoved = resources.getString(R.string.itemRemoved)
            if (flag) {
                fab.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.selected_bookmark))
                flag = false
                Toast.makeText(requireContext(), itemAdded, Toast.LENGTH_SHORT).show()

            } else {
                fab.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.unselected_bookmark))
                flag = true
                Toast.makeText(requireContext(), itemRemoved, Toast.LENGTH_SHORT).show()

            }
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
        var currFrId = args.currentDon.donId  //initialize current frID
        databaseReference = FirebaseDatabase.getInstance().reference
            .child("requestFromDonor").child(currFrId!!)

        var id = databaseReference.push().key!! //Id for new record
        var date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        var data = reqFromDonorData(id, uid, email, phone, message, date) // create new supportFr object

        //push created object to the db
        databaseReference.child(id).setValue(data).addOnCompleteListener {
            if (it.isSuccessful){
                hideProgressBar()
                Toast.makeText(context, "Message sent", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
            popupFragment.dismiss()
        }
    }
    private fun showProgressBar(){
        dialog = Dialog(this@ViewADonationAllUserFragment.requireContext())
        dialog.requestWindowFeature (Window. FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        dialog.dismiss()
    }


}