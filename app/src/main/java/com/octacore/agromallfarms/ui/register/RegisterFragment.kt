package com.octacore.agromallfarms.ui.register

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.makeramen.roundedimageview.RoundedImageView
import com.octacore.agromallfarms.R
import com.octacore.agromallfarms.model.Farm
import com.octacore.agromallfarms.model.Farmer
import com.octacore.agromallfarms.utils.Utils.getEncodedImage
import com.octacore.agromallfarms.utils.Utils.isPhoneNumber
import com.octacore.agromallfarms.utils.Utils.isValidEmail
import java.io.ByteArrayOutputStream
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegisterFragment : Fragment() {
    private lateinit var firstNameText: TextInputEditText
    private lateinit var firstNameLayout: TextInputLayout
    private lateinit var lastNameText: TextInputEditText
    private lateinit var lastNameLayout: TextInputLayout
    private lateinit var otherNameText: TextInputEditText
    private lateinit var otherNameLayout: TextInputLayout
    private lateinit var emailText: TextInputEditText
    private lateinit var emailLayout: TextInputLayout
    private lateinit var phoneNumberText: TextInputEditText
    private lateinit var phoneNumberLayout: TextInputLayout
    private lateinit var birthdayText: TextInputEditText
    private lateinit var birthdayLayout: TextInputLayout
    private lateinit var farmNameText: TextInputEditText
    private lateinit var farmNameLayout: TextInputLayout
    private lateinit var farmLocationText: TextInputEditText
    private lateinit var farmLocationLayout: TextInputLayout
    private lateinit var longitudeText: TextInputEditText
    private lateinit var longitudeLayout: TextInputLayout
    private lateinit var latitudeText: TextInputEditText
    private lateinit var latitudeLayout: TextInputLayout
    private lateinit var registerButton: MaterialButton
    private lateinit var profilePhotoPrompt: MaterialTextView
    private lateinit var farmerProfilePhoto: RoundedImageView
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_register, container, false)
        initViews(root)
        return root
    }

    override fun onStart() {
        super.onStart()
        setTextWatchers()
        setClickListeners()
        registerViewModel.farmers.observe(this, Observer {
            farmerSize = it.size
        })
        registerViewModel.farms.observe(this, Observer {
            farmSize = it.size
        })
    }

    private fun setClickListeners() {
        registerButton.setOnClickListener {
            if (!isRequiredFilled()) {
                clearAllErrors()
                registerFarmer()
            }
        }

        profilePhotoPrompt.setOnClickListener {
            captureImage()
        }
    }

    var farmerSize = 0
    var farmSize = 0

    private fun registerFarmer() {
        val farm = Farm(
            farmId = farmSize + 1L,
            name = registerViewModel.farmName,
            location = registerViewModel.farmLocation,
            latitude = registerViewModel.latitude,
            longitude = registerViewModel.longitude
        )

        val farmer = Farmer(
            farmerSize + 1L,
            registerViewModel.firstName,
            registerViewModel.lastName,
            registerViewModel.otherName,
            registerViewModel.profilePhoto,
            registerViewModel.phoneNumber,
            registerViewModel.birthday,
            registerViewModel.email
        )
        farmer.farm = farm

        registerViewModel.registerFarmer(farmer)
    }

    private fun setTextWatchers() {
        firstNameText.addTextChangedListener(watcher)
        lastNameText.addTextChangedListener(watcher)
        otherNameText.addTextChangedListener(watcher)
        phoneNumberText.addTextChangedListener(watcher)
        emailText.addTextChangedListener(watcher)
        birthdayText.addTextChangedListener(watcher)
        farmNameText.addTextChangedListener(watcher)
        farmLocationText.addTextChangedListener(watcher)
        longitudeText.addTextChangedListener(watcher)
        latitudeText.addTextChangedListener(watcher)
    }

    private val watcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val text = s.toString()
            when (text.hashCode()) {
                firstNameText.text.toString().hashCode() -> {
                    registerViewModel.firstName = text.trim()
                }

                lastNameText.text.toString().hashCode() -> {
                    registerViewModel.lastName = text.trim()
                }

                otherNameText.text.toString().hashCode() -> {
                    registerViewModel.otherName = text.trim()
                }

                emailText.text.toString().hashCode() -> {
                    if(text.length > 5 && !isValidEmail(text)){
                        emailLayout.isErrorEnabled = true
                        emailLayout.error = "Invalid Email Address"
                    } else{
                        emailLayout.isErrorEnabled = false
                    }
                    registerViewModel.email = text.trim()
                }

                phoneNumberText.text.toString().hashCode() -> {
                    if (text.length > 7 && !isPhoneNumber(text)) {
                        phoneNumberLayout.isErrorEnabled = true
                        phoneNumberLayout.error = "Incorrect phone number"
                    } else {
                        phoneNumberLayout.isErrorEnabled = false
                    }
                    registerViewModel.phoneNumber = text.trim()
                }

                birthdayText.text.toString().hashCode() -> {
                    registerViewModel.birthday = text.trim()
                }

                farmNameText.text.toString().hashCode() -> {
                    registerViewModel.farmName = text.trim()
                }

                farmLocationText.text.toString().hashCode() -> {
                    registerViewModel.farmLocation = text.trim()
                }

                latitudeText.text.toString().hashCode() -> {
                    registerViewModel.latitude = text.trim()
                }

                longitudeText.text.toString().hashCode() -> {
                    registerViewModel.longitude = text.trim()
                }
            }
        }
    }


    private fun captureImage() {
        if (requireActivity().checkSelfPermission(Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED ||
            requireActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_DENIED
        ) {
            //permission was not enabled
            val permission =
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            //show popup to request permission
            requestPermissions(permission, 1000)
        } else {
            //permission already granted
            startCameraIntentForResult()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //called when image was captured from camera intent
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == 110) {
                registerViewModel.profilePhoto = getEncodedImage(farmerProfilePhoto, data)
            }
        }
    }

    private fun startCameraIntentForResult() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                startActivityForResult(takePictureIntent, 110)
            }
        }
    }

    private fun initViews(root: View) {
        firstNameText = root.findViewById(R.id.firstNameEditText)
        firstNameLayout = root.findViewById(R.id.firstNameLayout)
        lastNameText = root.findViewById(R.id.lastNameEditText)
        lastNameLayout = root.findViewById(R.id.lastNameLayout)
        otherNameText = root.findViewById(R.id.otherNameEditText)
        otherNameLayout = root.findViewById(R.id.otherNameLayout)
        phoneNumberText = root.findViewById(R.id.phoneNumberEditText)
        phoneNumberLayout = root.findViewById(R.id.phoneNumberLayout)
        farmerProfilePhoto = root.findViewById(R.id.farmerProfilePhoto)
        emailText = root.findViewById(R.id.emailEditText)
        emailLayout = root.findViewById(R.id.emailLayout)
        birthdayText = root.findViewById(R.id.birthdayEditText)
        birthdayLayout = root.findViewById(R.id.birthdayLayout)
        farmNameText = root.findViewById(R.id.farmNameEditText)
        farmNameLayout = root.findViewById(R.id.farmNameLayout)
        farmLocationText = root.findViewById(R.id.farmLocationEditText)
        farmLocationLayout = root.findViewById(R.id.farmLocationLayout)
        longitudeText = root.findViewById(R.id.longitudeEditText)
        longitudeLayout = root.findViewById(R.id.longitudeLayout)
        latitudeText = root.findViewById(R.id.latitudeEditText)
        latitudeLayout = root.findViewById(R.id.latitudeLayout)
        registerButton = root.findViewById(R.id.registerButton)
        profilePhotoPrompt = root.findViewById(R.id.profilePhotoPrompt)
    }

    private fun isRequiredFilled(): Boolean {
        var isEmpty = false
        when {
            firstNameText.text.toString().isEmpty() -> {
                firstNameLayout.isErrorEnabled = true
                firstNameLayout.error = getString(R.string.required)
                isEmpty = true
            }
            lastNameText.text.toString().isEmpty() -> {
                lastNameLayout.isErrorEnabled = true
                lastNameLayout.error = getString(R.string.required)
                isEmpty = true
            }
            emailText.text.toString().isEmpty() -> {
                emailLayout.isErrorEnabled = true
                emailLayout.error = getString(R.string.required)
                isEmpty = true
            }
            phoneNumberText.text.toString().isEmpty() -> {
                phoneNumberLayout.isErrorEnabled = true
                phoneNumberLayout.error = getString(R.string.required)
                isEmpty = true
            }
            birthdayText.text.toString().isEmpty() -> {
                birthdayLayout.isErrorEnabled = true
                birthdayLayout.error = getString(R.string.required)
                isEmpty = true
            }
            farmNameText.text.toString().isEmpty() -> {
                farmNameLayout.isErrorEnabled = true
                farmNameLayout.error = getString(R.string.required)
                isEmpty = true
            }
            farmLocationText.text.toString().isEmpty() -> {
                farmLocationLayout.isErrorEnabled = true
                farmLocationLayout.error = getString(R.string.required)
                isEmpty = true
            }
            latitudeText.text.toString().isEmpty() -> {
                latitudeLayout.isErrorEnabled = true
                latitudeLayout.error = getString(R.string.required)
                isEmpty = true
            }
            longitudeText.text.toString().isEmpty() -> {
                longitudeLayout.isErrorEnabled = true
                longitudeLayout.error = getString(R.string.required)
                isEmpty = true
            }
        }
        return isEmpty
    }

    private fun clearAllErrors() {
        firstNameLayout.isErrorEnabled = false
        lastNameLayout.isErrorEnabled = false
        otherNameLayout.isErrorEnabled = false
        emailLayout.isErrorEnabled = false
        phoneNumberLayout.isErrorEnabled = false
        birthdayLayout.isErrorEnabled = false
        farmNameLayout.isErrorEnabled = false
        farmLocationLayout.isErrorEnabled = false
        latitudeLayout.isErrorEnabled = false
        longitudeLayout.isErrorEnabled = false
    }
}
