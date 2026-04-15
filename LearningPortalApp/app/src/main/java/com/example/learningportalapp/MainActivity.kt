package com.example.learningportalapp

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var urlBar: EditText
    private lateinit var progressBar: ProgressBar

    private val homeUrl = "https://www.google.com"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        urlBar = findViewById(R.id.urlBar)
        progressBar = findViewById(R.id.progressBar)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                progressBar.visibility = ProgressBar.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = ProgressBar.GONE
                urlBar.setText(url)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                webView.loadUrl("file:///android_asset/offline.html")
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                progressBar.progress = newProgress
            }
        }

        webView.loadUrl(homeUrl)

        // Buttons
        findViewById<Button>(R.id.btnGo).setOnClickListener {
            loadUrl(urlBar.text.toString())
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            if (webView.canGoBack()) webView.goBack()
            else Toast.makeText(this, "No more history", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnForward).setOnClickListener {
            if (webView.canGoForward()) webView.goForward()
        }

        findViewById<Button>(R.id.btnRefresh).setOnClickListener {
            webView.reload()
        }

        findViewById<Button>(R.id.btnHome).setOnClickListener {
            webView.loadUrl(homeUrl)
        }

        // Shortcuts
        findViewById<Button>(R.id.btnGoogle).setOnClickListener {
            webView.loadUrl("https://www.google.com")
        }

        findViewById<Button>(R.id.btnYouTube).setOnClickListener {
            webView.loadUrl("https://www.youtube.com")
        }

        findViewById<Button>(R.id.btnWiki).setOnClickListener {
            webView.loadUrl("https://www.wikipedia.org")
        }

        findViewById<Button>(R.id.btnUni).setOnClickListener {
            webView.loadUrl("https://www.aiub.edu/")
        }

        findViewById<Button>(R.id.btnKhan).setOnClickListener {
            webView.loadUrl("https://www.khanacademy.org/")
        }


    }

    private fun loadUrl(url: String) {
        var finalUrl = url
        if (!finalUrl.startsWith("http")) {
            finalUrl = "https://$finalUrl"
        }
        webView.loadUrl(finalUrl)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}