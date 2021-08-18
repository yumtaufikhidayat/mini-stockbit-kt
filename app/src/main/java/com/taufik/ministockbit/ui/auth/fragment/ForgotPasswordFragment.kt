package com.taufik.ministockbit.ui.auth.fragment

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.taufik.ministockbit.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    
    companion object {
        const val TAG = "RESET_PASSWORD"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFirebase()

        setOnClickForgotPasswordButton()
    }

    /*
    * Initialize firebase authentication
    */
    private fun initFirebase() {
        auth = FirebaseAuth.getInstance()
    }

    /*
    * Handling click for forgot password button
    * If user want to send email to reset password, they will click on this button
    */
    private fun setOnClickForgotPasswordButton() {

        binding.apply {
            btnForgotPassword.setOnClickListener {
                getEditTextInputs()
            }
        }
    }

    /*
    * Before add email, get user input from edit text first
    * Add validation if input is empty or false input
    */
    private fun getEditTextInputs() {

        binding.apply {

            val email = etEmail.text.toString().trim()

            if (email.isEmpty()) {
                etEmail.error = "Email harus diisi"
                etEmail.requestFocus()
                return
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.error = "Email tidak valid"
                etEmail.requestFocus()
                return
            }

            resetPassword(email)
        }
    }

    /*
    * Link to reset password will be send
    */
    private fun resetPassword(email: String) {

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(requireActivity(),
                        "Tautan reset password telah dikirim. Periksa email Anda untuk reset password.",
                        Toast.LENGTH_SHORT)
                        .show()

                    // navigate to login
                    val intent = ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment()
                    findNavController().navigate(intent)
                } else {
                    Log.d(TAG, "resetPassword: ")
                    Toast.makeText(requireActivity(), it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}