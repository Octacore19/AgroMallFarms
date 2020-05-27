package com.octacore.agromallfarms.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.octacore.agromallfarms.R
import com.octacore.agromallfarms.callback.FragmentNavCallback
import com.octacore.agromallfarms.ui.MainActivity
import com.octacore.agromallfarms.utils.Utils.isValidEmail

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {
    private lateinit var loginEmailText: TextInputEditText
    private lateinit var loginEmailLayout: TextInputLayout
    private lateinit var loginPasswordText: TextInputEditText
    private lateinit var loginPasswordLayout: TextInputLayout
    private lateinit var loginButton: MaterialButton
    private lateinit var callback: FragmentNavCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = requireActivity() as FragmentNavCallback
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        initView(root)
        return root
    }

    override fun onStart() {
        super.onStart()
        setTextWatcher()
        setClickListener()
    }

    private fun setClickListener() {
        loginButton.setOnClickListener {
            if(!isRequiredFilled()){
                clearAllErrors()
                loginUser()
            }
        }
    }

    private fun loginUser() {
        val email = loginEmailText.text.toString().trim()
        val password = loginPasswordText.text.toString().trim()
        if (email == getString(R.string.login_email) && password == getString(R.string.login_password)){
            callback.loginUser()
        }
    }

    private fun setTextWatcher() {
        loginEmailText.addTextChangedListener(watcher)
        loginPasswordText.addTextChangedListener(watcher)
    }

    private fun initView(root: View) {
        loginEmailText = root.findViewById(R.id.loginEmailEditText)
        loginEmailLayout = root.findViewById(R.id.loginEmailLayout)
        loginPasswordText = root.findViewById(R.id.loginPasswordEditText)
        loginPasswordLayout = root.findViewById(R.id.loginPasswordLayout)
        loginButton = root.findViewById(R.id.loginButton)
    }

    private val watcher = object: TextWatcher{
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val text = s.toString()
            when(text.hashCode()){
                loginEmailText.text.toString().hashCode() -> {
                    if(text.length > 5 && !isValidEmail(text)){
                        loginEmailLayout.isErrorEnabled = true
                        loginEmailLayout.error = "Invalid Email Address"
                    } else{
                        loginEmailLayout.isErrorEnabled = false
                    }
                }
            }
        }
    }

    private fun isRequiredFilled(): Boolean {
        var isEmpty = false
        when {
            loginEmailText.text.toString().isEmpty() -> {
                loginEmailLayout.isErrorEnabled = true
                loginEmailLayout.error = getString(R.string.required)
                isEmpty = true
            }
            loginPasswordText.text.toString().isEmpty() -> {
                loginPasswordLayout.isErrorEnabled = true
                loginPasswordLayout.error = getString(R.string.required)
                isEmpty = true
            }
        }
        return isEmpty
    }

    private fun clearAllErrors() {
        loginEmailLayout.isErrorEnabled = false
        loginPasswordLayout.isErrorEnabled = false
    }
}
