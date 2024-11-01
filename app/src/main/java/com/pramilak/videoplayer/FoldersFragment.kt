package com.pramilak.videoplayer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.rvadapter.AdmobNativeAdAdapter

import com.pramilak.videoplayer.R
import com.pramilak.videoplayer.ads.loadBannerAd
import com.pramilak.videoplayer.databinding.FragmentFoldersBinding


class FoldersFragment : Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireContext().theme.applyStyle(MainActivity.themesList[MainActivity.themeIndex], true)
        val view = inflater.inflate(R.layout.fragment_folders, container, false)
        val binding = FragmentFoldersBinding.bind(view)
        binding.FoldersRV.setHasFixedSize(true)
        binding.FoldersRV.setItemViewCacheSize(10)
        binding.FoldersRV.layoutManager = LinearLayoutManager(requireContext())
        binding.FoldersRV.adapter = FoldersAdapter(requireContext(), MainActivity.folderList)
        binding.totalFolders.text = "Total Folders: ${MainActivity.folderList.size}"
        binding.lnrBannerAd.loadBannerAd()
        val adapter = FoldersAdapter(requireContext(), MainActivity.folderList)

        val admobNativeAdAdapter = AdmobNativeAdAdapter.Builder
            .with(
                getString(R.string.native_ad_unit_id),  // Create a native ad id from admob console
                adapter,  // The adapter you would normally set to your RecyclerView
                "medium"  // Set it with "small", "medium" or "custom"
            )
            .adItemIterval(7)  // Native ad repeating interval in the RecyclerView
            .build()

        binding.FoldersRV.adapter = admobNativeAdAdapter

        return view


    }



}