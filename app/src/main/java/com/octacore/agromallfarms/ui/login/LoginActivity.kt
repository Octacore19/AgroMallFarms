package com.octacore.agromallfarms.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.octacore.agromallfarms.R
import com.octacore.agromallfarms.callback.FragmentNavCallback
import com.octacore.agromallfarms.ui.MainActivity

class LoginActivity : AppCompatActivity(), FragmentNavCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun loginUser() {
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}
