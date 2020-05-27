package com.octacore.agromallfarms.ui.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NavUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.octacore.agromallfarms.R

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MapViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    lateinit var polygon1: Polygon

    override fun onMapReady(googleMap: GoogleMap) {
        viewModel.farms.observe(this, Observer { farms ->
            Log.i("Farms Log", farms.toString())
            val latLng = farms.map { farm -> LatLng(farm.latitude.toDouble(), farm.longitude.toDouble()) }

            polygon1 = if(latLng.isEmpty()){
                googleMap.addPolygon(PolygonOptions()
                    .clickable(true)
                    .add(LatLng(9.077751, 8.6774567)))
            } else{
                googleMap.addPolygon(
                    PolygonOptions()
                        .clickable(true)
                        .addAll(latLng))
            }

            polygon1.tag = "alpha"
            stylePolygon(polygon1)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(9.077751, 8.6774567), 6f))
        })
    }

    companion object{
        private const val COLOR_BLACK_ARGB = -0x1000000
        private const val PATTERN_GAP_LENGTH_PX = 20
        private val GAP: PatternItem = Gap(PATTERN_GAP_LENGTH_PX.toFloat())
        private const val COLOR_WHITE_ARGB = -0x1
        private const val COLOR_GREEN_ARGB = -0xc771c4
        private const val COLOR_PURPLE_ARGB = -0x7e387c
        private const val POLYGON_STROKE_WIDTH_PX = 8
        private const val PATTERN_DASH_LENGTH_PX = 20
        private val DASH: PatternItem = Dash(PATTERN_DASH_LENGTH_PX.toFloat())
        private val PATTERN_POLYGON_ALPHA = listOf(
            GAP,
            DASH
        )
    }

    private fun stylePolygon(polygon: Polygon) {
        // Get the data object stored with the polygon.
        val type = polygon.tag?.toString() ?: ""
        var pattern: List<PatternItem>? = null
        var strokeColor =
            COLOR_BLACK_ARGB
        var fillColor =
            COLOR_WHITE_ARGB
        when (type) {
            "alpha" -> {
                // Apply a stroke pattern to render a dashed line, and define colors.
                pattern =
                    PATTERN_POLYGON_ALPHA
                strokeColor =
                    COLOR_GREEN_ARGB
                fillColor =
                    COLOR_PURPLE_ARGB
            }
        }
        polygon.strokePattern = pattern
        polygon.strokeWidth = POLYGON_STROKE_WIDTH_PX.toFloat()
        polygon.strokeColor = strokeColor
        polygon.fillColor = fillColor
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return when(item.itemId){
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                true
            }
           else -> {
               super.onOptionsItemSelected(item)
           }
        }
    }
}
