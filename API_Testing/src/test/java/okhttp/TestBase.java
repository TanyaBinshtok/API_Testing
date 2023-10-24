package okhttp;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;

public class TestBase {
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    String baseURL = "https://contactapp-telran-backend.herokuapp.com";

    }

