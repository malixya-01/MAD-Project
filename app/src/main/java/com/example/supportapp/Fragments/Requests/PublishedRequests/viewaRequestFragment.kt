package com.example.supportapp.Fragments.Requests.PublishedRequests

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.supportapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class viewaRequestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_view_a_request, container, false)

        var btnEditReq = view.findViewById<FloatingActionButton>(R.id.btnEditReq)
        btnEditReq.setOnClickListener {
            findNavController().navigate(R.id.action_viewSingleRequestFragment_to_editARequestFragment2)
        }

        val viewDonors = view.findViewById<FloatingActionButton>(R.id.viewDonors)
        viewDonors.setOnClickListener {
            findNavController().navigate(R.id.action_viewSingleRequestFragment_to_viewAllDonorsFragment4)
        }

        val deleteBtn = view.findViewById<FloatingActionButton>(R.id.btnDlt)
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