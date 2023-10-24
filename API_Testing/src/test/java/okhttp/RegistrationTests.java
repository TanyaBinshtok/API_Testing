package okhttp;

import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ErrorDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class RegistrationTests extends TestBase {

    // token = eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoia29seWEyMzgzQG1haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE2OTg3Mjk1ODQsImlhdCI6MTY5ODEyOTU4NH0.4GIrpgkxzSzpgf5rCfCp83pprFCd2j8dp--vEjFviPY
    public static final MediaType JSON = MediaType.get("application/json");
    int i = (int)(System.currentTimeMillis()/1000)%3600;
   @Test
    public void RegistrationPositive() throws IOException {
        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("kolya"+i+"@mail.com")
                .password("Kk123456$")
                .build();
        RequestBody requestBody = RequestBody.create(gson.toJson(requestDTO),JSON);
        Request request = new Request.Builder()
                .url(baseURL + "/v1/user/registration/usernamepassword")
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
