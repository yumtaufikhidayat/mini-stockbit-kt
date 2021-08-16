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
import com.taufik.ministockbit.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    companion object {
        const val CODE_SIGN_IN = 1000
        const val TAG = "TAG_GOOGLE_SIGN_IN"
        const val TAG_REGISTER = "TAG_REGISTER"
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setConfigGoogleSignIn()

        setHandlingOnClick()
        
        initFirebase()
    }

    /*
    * Handling for all on click events
    */
    private fun setHandlingOnClick() {

        setOnClickGoogleSignIn()

        setOnRegisterButton()

        setOnClickLoginText()
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
    * Initialize firebase
    */
    private fun initFirebase() {
        auth = FirebaseAuth.getInstance()
    }

    /*
    * Google sign in dialog will appear if user click on it 
    */
    private fun setOnClickGoogleSignIn() {
        binding.apply {
            llGoogleRegister.setOnClickListener{
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
                authWithGoogleAccount(account)
            } catch (e: Exception) {
                Log.d(TAG, "onActivityResult: ${e.message}")
            }
        }
    }

    /*
    * Auth firebase using Google account
    */
    private fun authWithGoogleAccount(account: GoogleSignInAccount?) {
        Log.d(TAG, "authWithGoogleAccount: ")
        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                Log.d(TAG, "authWithGoogleAccount: ")

                // get logged in user and get user info
                val firebaseUser = auth.currentUser
                val uid = firebaseUser!!.uid
                val email = firebaseUser.email

                Log.d(TAG, "authWithGoogleAccount: Uid: $uid")
                Log.d(TAG, "authWithGoogleAccount: Email: $email")

                // check user is existing or new
                if (authResult.additionalUserInfo!!.isNewUser) {
                    // create account - user is new
                    Log.d(TAG, "authWithGoogleAccount: Account created...\n$email")
                    Toast.makeText(requireActivity(), "Your account has been created", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d(TAG, "authWithGoogleAccount: User is exist\n$email")
                    Toast.makeText(requireActivity(), "Login successful", Toast.LENGTH_SHORT).show()
                }

                // start main activity, intent to home fragment
                val intent = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                findNavController().navigate(intent)

            }.addOnFailureListener{ e ->
                // login failed
                Log.d(TAG, "authWithGoogleAccount: Login failed due to ${e.message}")
                Toast.makeText(requireActivity(), "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setOnRegisterButton() {
        binding.apply {
            btnRegisterEmail.setOnClickListener {
                val intent = RegisterFragmentDirections.actionRegisterFragmentToRegisterUsingEmailFragment()
                findNavController().navigate(intent)
            }
        }
    }

    private fun setOnClickLoginText() {
        binding.apply {
            tvLoginNow.setOnClickListener {
                val intent = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                findNavController().navigate(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}