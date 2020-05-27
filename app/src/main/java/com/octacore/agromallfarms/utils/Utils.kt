package com.octacore.agromallfarms.utils

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import com.makeramen.roundedimageview.RoundedImageView
import java.io.ByteArrayOutputStream
import java.util.regex.Matcher
import java.util.regex.Pattern

object Utils {


    fun isPhoneNumber(phone: String): Boolean {
        val regex = "^[0]\\d{10}$"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(phone)
        return matcher.matches()
    }

    fun isValidEmail(email: String): Boolean {
        val regex =
            "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"
        val pattern = Pattern.compile(regex)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun getEncodedImage(imageView: RoundedImageView, data: Intent) : String{
        val imageBitmap = data.extras?.get("data") as Bitmap
        imageView.setImageBitmap(imageBitmap)
        val drawable = imageView.drawable
        val bitmap = (drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val bts = stream.toByteArray()
        return  Base64.encodeToString(bts, Base64.DEFAULT).replace("\n", "").replace("\r", "")
    }
}