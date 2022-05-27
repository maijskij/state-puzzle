package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentForgotPasswordBinding
import com.example.myapplication.ui.utils.ConnectionPopUp.createConnectionStatePopUp
import com.example.myapplication.ui.utils.FragmentViewModelFactory
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: FragmentViewModel by viewModels {
        FragmentViewModelFactory.createFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCheckConnection.setOnClickListener { viewModel.onCheckConnectionClicked() }

        viewModel.connectionState.observe(viewLifecycleOwner) { connectionState ->
            requireActivity().createConnectionStatePopUp(connectionState).show()
        }

        viewModel.checkConnection.observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, R.string.message_sent, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}