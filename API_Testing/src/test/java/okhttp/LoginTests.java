package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ErrorDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTests {

    // token = eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidml0eWFAbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY5ODU5NjY1MSwiaWF0IjoxNjk3OTk2NjUxfQ.FeMJxjEY3hINDFaUZw5xhLDg48hASE0SVvZIZcdaI4o

    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    String baseURL = "https://contactapp-telran-backend.herokuapp.com";
    @Test
    public void loginPositive() throws IOException {
        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("vitya@mail.com")
                .password("Oo54321$")
                .build();
        RequestBody requestBody = RequestBody.create(gson.toJson(requestDTO),JSON);
        Request request = new Request.Builder()
                .url(baseURL + "/v1/user/login/usernamepassword")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            String responseJson = response.body().string();
            AuthResponseDTO responseDTO = gson.fromJson(responseJson,AuthResponseDTO.class);
            System.out.println("Response code is ----> " + response.code());
            System.out.println(responseDTO.getToken());
            Assert.assertTrue(response.isSuccessful());
        }
        else{
            ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
            System.out.println("Response code is ----> " + response.code());
            System.out.println(errorDTO.getStatus() + " ==== " + errorDTO.getMessage() + " ==== "
                    + errorDTO.getError());
            Assert.assertFalse(response.isSuccessful());
        }

    }
}
