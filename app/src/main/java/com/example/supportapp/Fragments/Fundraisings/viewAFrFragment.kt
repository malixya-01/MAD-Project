package com.example.supportapp.Fragments.Fundraisings

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.supportapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class viewAFrFragment : Fragment() {

    // variables to function progress bar
    lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_a_fr, container, false)

        progressBar = view.findViewById(R.id.frProgressBar)
        progressBar.progress = 165000



        val updateBtn = view.findViewById<FloatingActionButton>(R.id.updateBtn)
        updateBtn.setOnClickListener {
            findNavController().navigate(R.id.action_viewAFrFragment_to_updateFrFragment)
        }

        val viewDonors = view.findViewById<FloatingActionButton>(R.id.viewDonors)
        viewDonors.setOnClickListener {
            findNavController().navigate(R.id.action_viewAFrFragment_to_viewAllDonorsToAFrFragment)
        }

        val deleteBtn = view.findViewById<FloatingActionButton>(R.id.btnDltReq)
        deleteBtn.setOnClickListener {
            showDeleteConfirmationDialog()
        }

        return view
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