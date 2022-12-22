package a77777_888.me.t.https.hhcustombasis.ui

import a77777_888.me.t.https.hhcustombasis.R
import a77777_888.me.t.https.hhcustombasis.databinding.FragmentWebViewBinding
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment

class WebViewFragment : Fragment(R.layout.fragment_web_view) {

    private lateinit var binding: FragmentWebViewBinding
    private lateinit var reference: String


    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentWebViewBinding.bind(view)

        reference = requireArguments().getString(REFERENCE)!!

        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = MyWebViewClient()
        }

        binding.webView.loadUrl(reference)
    }

    private class MyWebViewClient : WebViewClient() {
        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            view.loadUrl(request.url.toString())
            return true
        }

        // For old devices
        @Deprecated("Deprecated in Java")
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }

    companion object {
        const val REFERENCE = "reference"
    }
}
