package com.octacore.agromallfarms.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.octacore.agromallfarms.R
import com.octacore.agromallfarms.callback.FragmentNavCallback
import com.octacore.agromallfarms.ui.MainActivity
import com.octacore.agromallfarms.ui.splashscreen.SplashScreenFragmentDirections

class LoginActivity : AppCompatActivity(), FragmentNavCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        object : CountDownTimer(3_000, 1_000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.i("Time Elapsed", millisUntilFinished.toString())
            }

            override fun onFinish() {
                val navController = this@LoginActivity.findNavController(R.id.nav_host_fragment)
                val action = SplashScreenFragmentDirections.actionNavSplashscreenToLoginFragment()
                navController.navigate(action)
            }
        }.start()
    }

    override fun loginUser() {
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}
