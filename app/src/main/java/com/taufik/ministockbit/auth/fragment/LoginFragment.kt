package com.taufik.ministockbit.auth.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.taufik.ministockbit.R
import com.taufik.ministockbit.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        const val CODE_SIGN_IN = 1000
        const val TAG = "TAG_GOOGLE_SIGN_IN"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setConfigGoogleSignIn()

        setInitFirebase()

        checkUser()

        setGoogleSignIn()

        setLoginButton()
        
        setOnClickRegister()

        setOnClickForgotPassword()
    }

    /*
    * Set configuration for Google sign in
    * Only need email from Google account
    */
    private fun setConfigGoogleSignIn() {

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)
    }

    /*
    * Init firebase
    */
    private fun setInitFirebase() {
        firebaseAuth = FirebaseAuth.getInstance()
    }

    /*
    * Check user
    */
    private fun checkUser() {
        // check user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            // user already logged in, intent to home fragment
            val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
            findNavController().navigate(action)
        }
    }

    private fun setGoogleSignIn() {
        binding.apply {
            llGoogleLogin.setOnClickListener{
                val intent = googleSignInClient.signInIntent
                startActivityForResult(intent, CODE_SIGN_IN)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_SIGN_IN) {
            Log.d(TAG, "onActivityResult: Google Sign In Intent Result")
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)
            } catch (e: Exception) {
                Log.d(TAG, "onActivityResult: ${e.message}")
            }
        }
    }

    /*
    * Auth firebase using Google account
    */
    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {
        Log.d(TAG, "firebaseAuthWithGoogleAccount: ")
        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                Log.d(TAG, "firebaseAuthWithGoogleAccount: ")

                // get logged in user and get user info
                val firebaseUser = firebaseAuth.currentUser
                val uid = firebaseUser!!.uid
                val email = firebaseUser.email

                Log.d(TAG, "firebaseAuthWithGoogleAccount: Uid: $uid")
                Log.d(TAG, "firebaseAuthWithGoogleAccount: Email: $email")

                // check user is existing or new
                if (authResult.additionalUserInfo!!.isNewUser) {
                    // create account - user is new
                    Log.d(TAG, "firebaseAuthWithGoogleAccount: Account created...\n$email")
                    Toast.makeText(requireActivity(), "Your account has been created", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d(TAG, "firebaseAuthWithGoogleAccount: User is exist\n$email")
                    Toast.makeText(requireActivity(), "Login successful", Toast.LENGTH_SHORT).show()
                }

                // start main activity, intent to home fragment
//                val intent = LoginFragmentDirections.actionLoginFragmentToMainFragment()
//                findNavController().navigate(intent)

            }.addOnFailureListener{ e ->
                // login failed
                Log.d(TAG, "firebaseAuthWithGoogleAccount: Login failed due to ${e.message}")
                Toast.makeText(requireActivity(), "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setLoginButton() {
        binding.apply {
            btnLogin.setOnClickListener{
                val intent = LoginFragmentDirections.actionLoginFragmentToMainFragment()
                findNavController().navigate(intent)
            }
        }
    }

    private fun setOnClickRegister() {
        binding.apply {
            tvLoginNow.setOnClickListener {
                val intent = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                findNavController().navigate(intent)
            }
        }
    }

    private fun setOnClickForgotPassword() {
        binding.apply {
            tvForgotPassword.setOnClickListener {
                val intent = LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
                findNavController().navigate(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}