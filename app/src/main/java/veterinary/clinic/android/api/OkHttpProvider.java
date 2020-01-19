package veterinary.clinic.android.api;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

public class OkHttpProvider {

    private static OkHttpClient okHttpClient;
    private static final String API_SECRET_KEY = "$2b$10$mFJo2Jz.tdullIwL7gGmPeH687LUzMRDtPVOG.LbU3KNlnWVOUR1S";


    public OkHttpClient getClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }
        return okHttpClient;
    }

    public Request buildRequest(String url) {
        return new Request.Builder()
                .url(url)
                .get()
                .addHeader("secret-key", API_SECRET_KEY)
                .build();
    }

}
