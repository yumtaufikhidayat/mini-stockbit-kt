package com.taufik.ministockbit.ui.auth.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.taufik.ministockbit.R
import com.taufik.ministockbit.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackFacebookManager: CallbackManager
    private lateinit var auth: FirebaseAuth
    private lateinit var accessTokenTracker: AccessTokenTracker

    companion object {
        const val CODE_GOOGLE_SIGN_IN = 1000
        const val CODE_FACEBOOK_LOGIN = 1001
        const val TAG = "TAG_GOOGLE_SIGN_IN"
        const val TAG_LOGIN = "TAG_LOGIN"
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

        initFirebase()

        setConfigGoogleSignIn()

        setConfigFacebookLogin()

        checkUser()

        setHandlingOnClick()
    }

    /*
    * Handling for all on click events
    */
    private fun setHandlingOnClick() {

        setOnClickGoogleSignIn()

        setOnClickFacebookLoginButton()

        setOnClickLoginButton()

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
    * Handling configuration for Facebook login
    */
    private fun setConfigFacebookLogin() {
        FacebookSdk.sdkInitialize(requireContext())
        callbackFacebookManager = CallbackManager.Factory.create()
        Log.d(TAG, "setConfigFacebookLogin: ")
    }

    /*
    * Initialize firebase
    */
    private fun initFirebase() {
        auth = FirebaseAuth.getInstance()
    }

    /*
    * Check user
    */
    private fun checkUser() {
        // check user is logged in or not
        val firebaseUser = auth.currentUser
        if (firebaseUser != null) {
            // user already logged in, intent to home fragment
            val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
            findNavController().navigate(action)
        }
    }

    /*
    * Google sign in dialog will appear if user click on it 
    */
    private fun setOnClickGoogleSignIn() {
        binding.apply {
            llGoogleLogin.setOnClickListener {
                val intent = googleSignInClient.signInIntent
                startActivityForResult(intent, CODE_GOOGLE_SIGN_IN)
            }
        }
    }

    /*
    * Handling login using Facebook
    */
    private fun setOnClickFacebookLoginButton() {
        binding.apply {

            llFacebookLogin.registerCallback(callbackFacebookManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    Log.d(TAG, "onSuccess: $result")

                    handleFacebookToken(result?.accessToken)
                    val intent = LoginFragmentDirections.actionLoginFragmentToMainFragment()
                    findNavController().navigate(intent)
                }

                override fun onCancel() {
                    Log.d(TAG, "onCancel: ")
                }

                override fun onError(error: FacebookException?) {
                    Log.d(TAG, "onError: ${error.toString()}")
                }
            })

            accessTokenTracker = object : AccessTokenTracker(){
                override fun onCurrentAccessTokenChanged(
                    oldAccessToken: AccessToken?,
                    currentAccessToken: AccessToken?
                ) {
                    if (currentAccessToken == null) {
                        auth.signOut()
                    }
                }
            }
        }
    }

    /*
    * Handle token of facebook login success
    */
    private fun handleFacebookToken(accessToken: AccessToken?) {
        Log.d(TAG, "handleFacebookToken: $accessToken")
        val authCredential: AuthCredential = FacebookAuthProvider.getCredential(accessToken?.token.toString())
        auth.signInWithCredential(authCredential)
            .addOnCompleteListener(requireActivity()) {
                if (it.isSuccessful) {
                    Log.d(TAG, "handleFacebookToken: ")
                    val intent = LoginFragmentDirections.actionLoginFragmentToMainFragment()
                    findNavController().navigate(intent)
                } else {
                    Log.d(TAG, "handleErrorFacebookToken: ${it.exception?.message}")
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_GOOGLE_SIGN_IN) {
            Log.d(TAG, "onActivityResult: Google Sign In Intent Result")
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = accountTask.getResult(ApiException::class.java)
                authWithGoogleAccount(account)
            } catch (e: Exception) {
                Log.d(TAG, "onActivityResult: ${e.message}")
            }
        } else if (requestCode == CODE_FACEBOOK_LOGIN) {
            callbackFacebookManager.onActivityResult(requestCode, resultCode, data)
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

                // start login fragment, navigate to home fragment
                val intent = LoginFragmentDirections.actionLoginFragmentToMainFragment()
                findNavController().navigate(intent)

            }.addOnFailureListener{ e ->
                // login failed
                Log.d(TAG, "authWithGoogleAccount: Login failed due to ${e.message}")
                Toast.makeText(requireActivity(), "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    /*
    * Handling on click for login button
    * If user have account, the will click the button
    */
    private fun setOnClickLoginButton() {
        binding.apply {
            btnLogin.setOnClickListener{
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
            val password = etPassword.text.toString().trim()

            if (email.isEmpty()) {
                etEmail.error = "Email harus diisi"
                etEmail.requestFocus()
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

            loginUser(email, password)
        }
    }

    /*
    * Handling user login
    * By passing email and password
    */
    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()){
                if (it.isSuccessful) {
                    val intent = LoginFragmentDirections.actionLoginFragmentToMainFragment()
                    findNavController().navigate(intent)
                } else {
                    Log.d(TAG_LOGIN, "loginUser: ")
                    Toast.makeText(requireActivity(), it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    /*
    * Handling on click for don't have account text
    * If users don't have account, they will click on text
    */
    private fun setOnClickRegister() {
        binding.apply {
            tvLoginNow.setOnClickListener {
                val intent = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                findNavController().navigate(intent)
            }
        }
    }

    /*
    * Handling click for forgot password text
    * If user forgot their password, the will click on text
    */
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