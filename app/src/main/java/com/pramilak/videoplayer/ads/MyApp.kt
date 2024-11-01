package com.pramilak.videoplayer.ads

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.firebase.database.FirebaseDatabase

//
class MyApp() : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        MobileAds.initialize(this) {
            loadAdUnits {
                // TODO: handle success here
                loadInterstitialAdIfNull(this)
            }
        }

    }

}
