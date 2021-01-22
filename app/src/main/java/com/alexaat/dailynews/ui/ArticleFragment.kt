package com.alexaat.dailynews.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.alexaat.dailynews.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_article, container, false)
        val webUrl = ArticleFragmentArgs.fromBundle(requireArguments()).webUrl
        val title =  ArticleFragmentArgs.fromBundle(requireArguments()).title

        (activity as AppCompatActivity).supportActionBar?.title = title

        val webView = view.findViewById<WebView>(R.id.web_view)

        webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            overScrollMode = WebView.OVER_SCROLL_NEVER
            loadUrl(webUrl)
        }

        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)
        return view
    }
}