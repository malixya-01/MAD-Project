package com.example.supportapp.Fragments.Fundraisings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentViewAFrAllUsersBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class viewAFrAllUsersFragment : Fragment() {

    private lateinit var binding: FragmentViewAFrAllUsersBinding

    // variables to function progress bar
    lateinit var progressBar: ProgressBar
    private lateinit var fab: FloatingActionButton
    private var unselectedIcon = true

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
        progressBar = binding.frProgressBar
        progressBar.max = 500000
        progressBar.progress = 210000
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
            findNavController().navigate(R.id.action_viewAFrAllUsersFragment_to_sendSupportMsgToAFrFragment)
        }
    }


}