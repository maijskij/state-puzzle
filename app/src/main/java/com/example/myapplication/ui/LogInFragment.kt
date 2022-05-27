package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.ui.utils.ConnectionPopUp.createConnectionStatePopUp
import com.example.myapplication.ui.utils.FragmentViewModelFactory
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LogInFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private val viewModel: FragmentViewModel by viewModels {
        FragmentViewModelFactory.createFactory()
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCheckConnection.setOnClickListener { viewModel.onCheckConnectionClicked() }

        binding.runQueue.setOnClickListener { viewModel.populateOperationQueue() }

        binding.buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_SignUpFragment)
        }

        viewModel.checkConnection.observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, R.string.message_sent, Snackbar.LENGTH_LONG).show()
        }

        viewModel.connectionState.observe(viewLifecycleOwner) { connectionState ->
            requireActivity().createConnectionStatePopUp(connectionState).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}