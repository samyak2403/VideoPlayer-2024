package com.pramilak.videoplayer

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.rvadapter.AdmobNativeAdAdapter

import com.pramilak.videoplayer.VideoAdapter
import com.pramilak.videoplayer.ads.loadBannerAd
import com.pramilak.videoplayer.databinding.ActivityFoldersBinding

import java.io.File

class FoldersActivity : AppCompatActivity() {

    private lateinit var adapter: VideoAdapter

    companion object {
        lateinit var currentFolderVideos: ArrayList<Video>
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(MainActivity.themesList[MainActivity.themeIndex])

        val binding = ActivityFoldersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val position = intent.getIntExtra("position", 0)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = MainActivity.folderList[position].folderName




        binding.toolbar.setNavigationOnClickListener { finish() }
        currentFolderVideos = getAllVideos(MainActivity.folderList[position].id)

        binding.videoRVFA.setHasFixedSize(true)
        binding.videoRVFA.setItemViewCacheSize(10)
        binding.videoRVFA.layoutManager = LinearLayoutManager(this@FoldersActivity)
        adapter = VideoAdapter(this@FoldersActivity, currentFolderVideos, isFolder = true)
        binding.videoRVFA.adapter = adapter
        binding.totalVideosFA.text = "Total Videos: ${currentFolderVideos.size}"
        binding.apply {
            lnrBannerAd.loadBannerAd()
            videoRVFA.layoutManager = LinearLayoutManager(this@FoldersActivity)
            val admobNativeAdAdapter: AdmobNativeAdAdapter = AdmobNativeAdAdapter.Builder
                .with(
                    "ca-app-pub-3940256099942544/2247696110",  //Create a native ad id from admob console
                    adapter,  //The adapter you would normally set to your recyClerView
                    "small" //Set it with "small","medium" or "custom"
                )
                .adItemIterval(8) //native ad repeating interval in the recyclerview
                .build()
            videoRVFA.adapter = admobNativeAdAdapter
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    @SuppressLint("InlinedApi", "Recycle", "Range")
    private fun getAllVideos(folderId: String): ArrayList<Video> {
        val tempList = ArrayList<Video>()
        val selection = MediaStore.Video.Media.BUCKET_ID + " like? "
        val projection = arrayOf(
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.BUCKET_ID
        )
        val cursor = this.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, selection, arrayOf(folderId),
            MainActivity.sortList[MainActivity.sortValue]
        )
        if (cursor != null)
            if (cursor.moveToNext())
                do {
                    val titleC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE))
                    val idC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID))
                    val folderC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME))
                    val sizeC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE))
                    val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
                    val durationC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION))
                            .toLong()

                    try {
                        val file = File(pathC)
                        val artUriC = Uri.fromFile(file)
                        val video = Video(
                            title = titleC,
                            id = idC,
                            folderName = folderC,
                            duration = durationC,
                            size = sizeC,
                            path = pathC,
                            artUri = artUriC
                        )
                        if (file.exists()) tempList.add(video)

                    } catch (_: Exception) {
                    }
                } while (cursor.moveToNext())
        cursor?.close()
        return tempList
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        adapter.onResult(requestCode, resultCode)
    }
}