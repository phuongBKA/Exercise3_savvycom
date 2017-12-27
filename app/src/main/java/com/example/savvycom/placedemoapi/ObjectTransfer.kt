package com.example.savvycom.placedemoapi

import com.google.android.gms.maps.GoogleMap

/**
 * Created by savvycom on 12/27/2017.
 */
data class ObjectTransfer(var mMap: GoogleMap,var url: String){
    var map: GoogleMap = mMap
    get(){
        return mMap
    }
    var URL: String = url
    get(){
        return url
    }

}



