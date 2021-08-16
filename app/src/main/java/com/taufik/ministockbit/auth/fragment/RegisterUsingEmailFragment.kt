package com.taufik.ministockbit.auth.fragment

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
import com.taufik.ministockbit.databinding.FragmentRegisterUsingEmailBinding

class RegisterUsingEmailFragment : Fragment() {

    private var _binding: FragmentRegisterUsingEmailBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    
    companion object {
        const val TAG = "TAG_REGISTER_USING_EMAIL"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterUsingEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFirebaseAuth()

        setOnClickRegisterButton()
    }

    /*
    * Initialize firebase authentication
    */
    private fun setFirebaseAuth() {
        auth = FirebaseAuth.getInstance()
    }


    /*
    * Handling click for register button
    * If user want to register using email, they will click on this button
    */
    private fun setOnClickRegisterButton() {
        binding.apply {
            btnRegister.setOnClickListener {
                getEditTextInputs()
            }
        }
    }

    /*
    * Before register, get user input from edit text first
    * Add validation if input is empty or false input
    */
    private fun getEditTextInputs() {

        binding.apply {

            val email = etEmail.text.toString().trim()
            val fulName = etFulname.text.toString().trim()
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty()) {
                etEmail.error = "Email harus diisi"
                etEmail.requestFocus()
                return
            }

            if (fulName.isEmpty()) {
                etFulname.error = "Nama lengkap harus diisi"
                etFulname.requestFocus()
                return
            }

            if (username.isEmpty()) {
                etUsername.error = "Username harus diisi"
                etUsername.requestFocus()
                return
            }

            if (password.isEmpty()) {
                etPassword.error = "Password harus diisi"
                etPassword.requestFocus()
                return
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.error = "Email tidak valid"
                etEmail.requestFocus()
                return
            }

            if (password.isEmpty() || password.length < 6) {
                etPassword.error = "Password harus lebih dari 6 karakter"
                etPassword.requestFocus()
                return
            }

            registerUserAccount(email, password)
        }
    }

    /*
    * Handling for register user account
    * By passing email and password
    */
    private fun registerUserAccount(
        email: String,
        password: String
    ){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()){
                if (it.isSuccessful) {
                    val intent = RegisterUsingEmailFragmentDirections.actionRegisterUsingEmailFragmentToLoginFragment()
                    findNavController().navigate(intent)
                } else {
                    Log.d(TAG, "registerUserAccount: ")
                    Toast.makeText(requireActivity(), it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}