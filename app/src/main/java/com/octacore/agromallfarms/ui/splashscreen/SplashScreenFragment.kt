package com.octacore.agromallfarms.ui.splashscreen

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.octacore.agromallfarms.R

/**
 * A simple [Fragment] subclass.
 */
class SplashScreenFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onStart() {
        super.onStart()
        object : CountDownTimer(3_000, 1_000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.i("Time Elapsed", millisUntilFinished.toString())
            }

            override fun onFinish() {
                val navController = this@SplashScreenFragment.findNavController()
                val action = SplashScreenFragmentDirections.actionNavSplashscreenToLoginFragment()
                navController.navigate(action)
            }
        }.start()
    }

}
