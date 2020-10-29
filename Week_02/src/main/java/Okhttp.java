import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class Okhttp {

    public static void main(String[] args) {
        OkHttpClient c = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:8088/api/hello")
                .get()
                .build();
        Call call = c.newCall(request);

        Response resp = null;
        try {
            resp = call.execute();
            System.out.println(resp.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (resp != null) {
                resp.close();
            }
        }
    }

}
