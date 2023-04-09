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
import com.example.supportapp.DataClasses.FundraisingData
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentViewAFrBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class viewAFrFragment : Fragment() {

    private lateinit var binding: FragmentViewAFrBinding

    //safe args
    private val args: viewAFrFragmentArgs by navArgs()

    lateinit var progressBar: ProgressBar
    private lateinit var fab: FloatingActionButton

    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var dialog: Dialog
    private lateinit var currentFundraiser: FundraisingData


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewAFrBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseReference = FirebaseDatabase.getInstance().getReference("fundraising")

        databaseReference.child(args.currFrId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                currentFundraiser = snapshot.getValue(FundraisingData::class.java)!!

                //retrieve user profile pic
                var frUid = currentFundraiser.uid
                storageReference = FirebaseStorage.getInstance().reference.child("Users/$frUid")
                getUserProfilePicture()

                //binding data
                binding.vMyFrCollapsingToolbar.title = currentFundraiser.title

                binding.tvName.text = currentFundraiser.username
                binding.tvDate.text = currentFundraiser.date
                binding.tvTotAmt.text = currentFundraiser.expectedAmt
                binding.tvReqAmt.text = currentFundraiser.collectedAmt
                binding.tvDes.text = currentFundraiser.description
                binding.tvPhone.text = currentFundraiser.contactNo
                binding.tvWeb.text = currentFundraiser.website
                binding.tvEmail.text = currentFundraiser.email
                binding.tvBankDetails.text = currentFundraiser.bankDetails

                if (currentFundraiser.website.isNullOrBlank()) {
                    binding.tvWeb.text = "No web site..."
                }

                //handle progressbar
                var max = currentFundraiser.expectedAmt!!.toInt()
                var progress = currentFundraiser.collectedAmt!!.toInt()
                progressBar = binding.frProgressBar
                progressBar.max = max
                progressBar.progress = progress

                //handle verified status
                binding.ivIsVerified.visibility = View.GONE;
                if (currentFundraiser.verStatus == true) {
                    binding.ivIsVerified.visibility = View.VISIBLE;
                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
            }
        })

        binding.updateBtn.setOnClickListener {
            var action = viewAFrFragmentDirections.actionViewAFrFragmentToUpdateFrFragment(currentFundraiser.frId.toString())
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

        }.addOnFailureListener {
            hideProgressBar()
            Toast.makeText(activity, "Failed to retrieve user image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProgressBar() {
        dialog = Dialog(this@viewAFrFragment.requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar() {
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

        /*deleteButton.setOnClickListener {
            databaseReference.child(args.currentFr.frId!!).removeValue().addOnCompleteListener {
                if( it.isSuccessful){
                    Toast.makeText(requireContext(), "Item deleted", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_viewAFrFragment_to_myFundraisingsFragment)
                }
            }
            dialog.dismiss()
        }*/

        dialog.show()
    }


}