package functions;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MakeRequest {
    public static String MakeRequest(final Request request) {
        try {
            ExecutorService pool = Executors.newFixedThreadPool(1); // creates a pool of threads for the Future to draw from
            Future<String> result = pool.submit(new Callable<String>() {
                @Override
                public String call() {
                    try {
                        Response response = new OkHttpClient().newCall(request).execute();
                        return response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            });

            String response = result.get();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
