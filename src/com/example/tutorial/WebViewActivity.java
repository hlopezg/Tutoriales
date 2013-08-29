package com.example.tutorial;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import android.webkit.WebView;

public class WebViewActivity extends SherlockActivity{
	WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		
		mWebView = (WebView) findViewById(R.id.webview_1);
		
		int id = getIntent().getExtras().getInt("id");

		if (id == 0)
			mWebView.loadUrl("http://www.windowsphone.com/es-cl");
	}
}
