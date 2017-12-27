//package com.example.savvycom.placedemoapi
//
//import android.content.pm.PackageManager
//import android.location.Address
//import android.location.Geocoder
//import android.location.Location
//import android.os.Build
//import android.support.v7.app.AppCompatActivity
//import android.os.Bundle
//import android.support.v4.app.ActivityCompat
//import android.support.v4.content.ContextCompat
//import android.util.Log
//import android.view.View
//import android.widget.TextView
//import android.widget.Toast
//import com.google.android.gms.common.ConnectionResult
//import com.google.android.gms.common.api.GoogleApiClient
//import com.google.android.gms.location.LocationListener
//import com.google.android.gms.location.LocationRequest
//import com.google.android.gms.location.LocationServices
//
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.BitmapDescriptorFactory
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.Marker
//import com.google.android.gms.maps.model.MarkerOptions
//import kotlinx.android.synthetic.main.activity_maps.*
//import org.w3c.dom.Text
//import java.io.IOException
//import java.io.ObjectInput
//import java.util.jar.Manifest
//
//class MapsActivity : AppCompatActivity(),
//        OnMapReadyCallback,
//        GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener,
//        LocationListener {
//
//    private var mMap: GoogleMap? = null
//    private var client: GoogleApiClient? = null
//    private var locationRequest: LocationRequest? = null
//    private var lastLocation: Location? = null
//    private var currentLocationMarker: Marker? = null
//    internal var latitude: Double = 0.toDouble()
//    internal var longtitude: Double = 0.toDouble()
//    internal var PROXIMITY_RADIUS = 10000
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_maps)
//
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//
//        }
//
//        val mapFragment = supportFragmentManager
//                .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//    }
//
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        when (requestCode) {
//            REQUEST_LOCATION_CODE -> {
//                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        if (client == null) {
//                            bulidGoogleApiClient()
//                        }
//                        mMap!!.isMyLocationEnabled = true
//                    }
//                }
//            }
//        }
//    }
//
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//
//    }
//
//    @Synchronized protected fun bulidGoogleApiClient() {
//        client = GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener { this }.addApi(LocationServices.API).build()
//        client!!.connect()
//    }
//
//    fun onClick(v: View) {
//        val dataTransfer = arrayOfNulls<Any>(2)
//        val getNearbyPlacesData = GetNearByPlacesData();
//
//        when (v.id) {
//            R.id.B_search -> {
//                val location = TF_location.text.toString()
//                val addressList: List<Address>?
//
//
//                if (location != "") {
//                    val geocoder = Geocoder(this)
//
//                    try {
//                        addressList = geocoder.getFromLocationName(location, 5)
//
//                        if (addressList != null) {
//                            for (i in addressList.indices) {
//                                val latLng = LatLng(addressList[i].latitude, addressList[i].longitude)
//                                val markerOptions = MarkerOptions()
//                                markerOptions.position(latLng)
//                                markerOptions.title(location)
//                                mMap!!.addMarker(markerOptions)
//                                mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
//                                mMap!!.animateCamera(CameraUpdateFactory.zoomTo(10f))
//                            }
//                        }
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    }
//
//                }
//            }
//            R.id.B_hopistals -> {
//                mMap!!.clear()
//                val hospital = "hospital"
//                var url = getUrl(latitude, longtitude, hospital)
//                dataTransfer[0] = mMap!!
//                dataTransfer[1] = url
//
//                getNearbyPlacesData.execute(dataTransfer)
//                Toast.makeText(this@MapsActivity, "Showing Nearby Hospitals", Toast.LENGTH_SHORT).show()
//            }
//
//
//            R.id.B_schools -> {
//                mMap!!.clear()
//                val school = "school"
//                var url = getUrl(latitude, longtitude, school)
//                dataTransfer[0] = mMap
//                dataTransfer[1] = url
//
//                getNearbyPlacesData.execute(dataTransfer)
//                Toast.makeText(this@MapsActivity, "Showing Nearby Schools", Toast.LENGTH_SHORT).show()
//            }
//            R.id.B_restaurants -> {
//                mMap!!.clear()
//                val resturant = "restuarant"
//                var url = getUrl(latitude, longtitude, resturant)
//                dataTransfer[0] = mMap
//                dataTransfer[1] = url
//
//                getNearbyPlacesData.execute(dataTransfer)
//                Toast.makeText(this@MapsActivity, "Showing Nearby Restaurants", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    fun checkLocationPermission(): Boolean {
//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
//                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_CODE)
//            } else {
//                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_CODE)
//            }
//            return false
//
//        } else
//            return true
//    }
//
//    private fun getUrl(latitude: Double, longitude: Double, nearbyPlace: String): String {
//
//        val googlePlaceUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
//        googlePlaceUrl.append("location=$latitude,$longitude")
//        googlePlaceUrl.append("&radius=" + PROXIMITY_RADIUS)
//        googlePlaceUrl.append("&type=" + nearbyPlace)
//        googlePlaceUrl.append("&sensor=true")
//        googlePlaceUrl.append("&key=" + "AIzaSyATB--pAzUv-L9nR5GmaYpP1x5OmLCiMj0")
//
//        Log.d("MapsActivity", "url = " + googlePlaceUrl.toString())
//
//        return googlePlaceUrl.toString()
//    }
//
//    override fun onConnectionFailed(p0: ConnectionResult) {
//    }
//
//    override fun onLocationChanged(location: Location) {
//        latitude = location.latitude
//        longtitude = location.longitude
//        lastLocation = location
//        if(currentLocationMarker != null){
//            currentLocationMarker!!.remove()
//        }
//
//        val latLng = LatLng(location.latitude, location.longitude)
//        val markerOptions = MarkerOptions()
//        markerOptions.position(latLng)
//        markerOptions.title("Current Location")
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
//        currentLocationMarker = mMap!!.addMarker(markerOptions)
//        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
//        mMap!!.animateCamera(CameraUpdateFactory.zoomBy(10f))
//
//        if (client != null) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(client, this)
//        }
//    }
//
//    override fun onConnectionSuspended(p0: Int) {
//        locationRequest = LocationRequest()
//        locationRequest!!.interval = 100
//        locationRequest!!.fastestInterval = 1000
//        locationRequest!!.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
//
//        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
//            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest,this)
//        }
//    }
//
//    override fun onConnected(p0: Bundle?) {
//
//    }
//
//
//    companion object {
//        val REQUEST_LOCATION_CODE = 99
//    }
//}
//
//
//
