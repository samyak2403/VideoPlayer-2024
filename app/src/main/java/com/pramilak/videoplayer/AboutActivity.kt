package com.pramilak.videoplayer

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


import com.pramilak.videoplayer.R
import com.pramilak.videoplayer.ads.loadBannerAd
import com.pramilak.videoplayer.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity(), View.OnClickListener {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(MainActivity.themesList[MainActivity.themeIndex]) // Assuming MainActivity contains theme information
        val binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set toolbar and title
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Setting"

        // Initialize Views and Button Listeners
        initViews()

        // Load Firebase ad units and then load ads


        binding.apply {
            lnrBannerAd.loadBannerAd()
        }


        // Get app version and display it
        displayAppVersion()

        // Set navigation click listener for the toolbar back button
        binding.toolbar.setNavigationOnClickListener { finish() }
    }


    // Function to initialize views and set click listeners
    private fun initViews() {
        // Initialize buttons and set click listeners
        findViewById<View>(R.id.btnShare).setOnClickListener(this)
        findViewById<View>(R.id.btnRate).setOnClickListener(this)
        findViewById<View>(R.id.btnClear).setOnClickListener(this)
        findViewById<View>(R.id.btnFeedback).setOnClickListener(this)
        findViewById<View>(R.id.btnPrivacy).setOnClickListener(this)
        findViewById<View>(R.id.btnAbout).setOnClickListener(this)
    }

    // Function to display app version
    private fun displayAppVersion() {
        val version = getAppVersion()
        val versionNameTextView = findViewById<TextView>(R.id.tvVersion)
        versionNameTextView.text = "Version: " + (version ?: "N/A")
    }

    // Function to get the app version
    private fun getAppVersion(): String? {
        return try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnShare -> shareApp()
            R.id.btnRate -> rateApp()
            R.id.btnClear -> clearAppData()
            R.id.btnFeedback -> sendFeedback()
            R.id.btnPrivacy -> openPrivacyPolicy()
            R.id.btnAbout -> showAboutDialog() // Call showAboutDialog when About is clicked
        }
    }

    // Share app functionality
    private fun shareApp() {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Check out this app")
            putExtra(
                Intent.EXTRA_TEXT,
                "Download this amazing video player app from the Play Store!"
            )
        }
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

    // Rate app functionality
    private fun rateApp() {
        try {
            val uri = Uri.parse("market://details?id=$packageName")
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            startActivity(goToMarket)
        } catch (e: Exception) {
            Toast.makeText(this, "Couldn't open Play Store", Toast.LENGTH_SHORT).show()
        }
    }

    // Clear app data functionality
    private fun clearAppData() {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.parse("package:$packageName")
            }
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Couldn't open App Settings", Toast.LENGTH_SHORT).show()
        }
    }

    // Feedback functionality
    private fun sendFeedback() {
        val feedbackIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // Only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, arrayOf("support@pramilak.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Feedback for Video Player App")
        }
        try {
            startActivity(feedbackIntent)
        } catch (e: Exception) {
            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show()
        }
    }

    // Privacy policy functionality
    private fun openPrivacyPolicy() {
        val privacyIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://your-privacy-policy-url.com"))
        startActivity(privacyIntent)
    }

    // Show About dialog
    private fun showAboutDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("About ${getAppName()}")
        builder.setMessage(
            """
            Video Player All Format is a pro video playback tool & audio player for Android tablets and phones.

            XPlayer supports ALL formats & 4K/Ultra HD video files. You can play videos with high definition and listen to music with an easy-to-use equalizer.

            App Name: ${getAppName()}
            Version: ${getAppVersion()}

            KEY FEATURES:
            ● Keep your video safe with a private folder.
            ● Support ALL video formats: MKV, MP4, M4V, AVI, MOV, 3GP, FLV, WMV, RMVB, TS etc.
            ● Ultra HD video player with 4K support.
            ● Hardware acceleration.
            ● Cast videos to TV with Chromecast.
            ● Support subtitle downloader.
            ● Play video in pop-up window, split screen, or background.
            ● Night Mode, Quick Mute & Playback Speed control.
            ● Automatically identify all video files on your device and SD card.
            ● Manage or share videos easily.
            ● Easy volume, brightness, and playback control.
            ● Multi-playback options: auto-rotation, aspect-ratio, screen lock.
            """.trimIndent()
        )
        builder.setIcon(getAppIcon())

        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }

    // Get app name
    private fun getAppName(): String {
        return getString(R.string.app_name)
    }

    // Get app icon
    private fun getAppIcon(): Drawable? {
        return try {
            packageManager.getApplicationIcon(packageName)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            null
        }
    }
}
