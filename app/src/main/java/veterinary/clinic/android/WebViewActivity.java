package veterinary.clinic.android;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadWebView();
    }

    private void loadWebView() {
        String contentUrl = getIntent().getStringExtra("content_url");
        WebView webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(contentUrl);
        setContentView(webView);
    }
}
