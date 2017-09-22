package com.iplayer.basiclib.http;


import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.iplayer.basiclib.core.Constants;
import com.iplayer.basiclib.util.LogUtils;
import com.iplayer.basiclib.util.NetworkUtils;
import com.iplayer.basiclib.util.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Connection;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient<T> {

    private File cacheFile = new File(Utils.getContext().getExternalCacheDir(), "netCache");   // 缓存的文件名
    private Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
    private String BASE_URL = Constants.BASE_URL;

    private static final int DEFAULT_TIMEOUT = 30;  // 默认30s后超时连接
    private int maxAge;
    private T client;


    public HttpClient(@Nullable String baseUrl, Class<T> clazz) {
        //retrofit的builder配置
        if (!BASE_URL.equals(baseUrl) || client == null) {
            if (baseUrl != null)
                BASE_URL = baseUrl;

            String STORE_PASS = "cai1037399948";
            String STORE_ALIAS = "0";
//          HttpsHelper.SSLParams sslParams = HttpsHelper.getSslSocketFactory(MyApplication.context, R.raw.ssl, STORE_PASS, STORE_ALIAS);

            // 创建一个OkHttpClient并设置超时时间
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(getInterceptor())
                    .cache(cache);
//                .sslSocketFactory(HttpsHelper.getSslSocketFactoryUnsafe().sSLSocketFactory
//                ,HttpsHelper.getSslSocketFactoryUnsafe().trustManager);
//                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
//                .hostnameVerifier(HttpsHelper.getHostnameVerifier());

            Retrofit retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())//创建Gson对象
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();

            //动态代理
            client = retrofit.create(clazz);
        }
    }

    public T getService() {
        return client;
    }

    /**
     * 设置返回数据的  Interceptor  判断网络   没网读取缓存
     */
    private Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                // @Headers注解
                String headers = request.cacheControl().toString();
                if (!NetworkUtils.isConnected() && request.cacheControl().maxAgeSeconds() != 0) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                } else if (TextUtils.isEmpty(headers)) {
                    request = request.newBuilder()
                            .cacheControl(new CacheControl.Builder().maxStale(maxAge, TimeUnit.SECONDS).build())
                            .build();
                } else if (request.cacheControl().maxAgeSeconds() > 0) {
                    // 根据请求头包含的最大缓存时间，设置缓存时间
//                    Log.e("retrofit" ,"缓存时间"+request.cacheControl().maxAgeSeconds());

                    request = request.newBuilder()
                            .cacheControl(new CacheControl.Builder().maxStale(request.cacheControl().maxAgeSeconds(), TimeUnit.SECONDS).build())
                            .build();
                }

                // 添加公共参数
                if (request.method().equals("GET")) {
                    HttpUrl httpUrl = request.url().newBuilder()
                            .addQueryParameter("sourceMode", "android")
                            .addQueryParameter("token", "")
                            .build();
                    request = request.newBuilder()
                            .url(httpUrl)
                            .build();
                }
                if (request.method().equals("POST")) {
                    if (request.body() instanceof FormBody) {
                        FormBody.Builder bodyBuilder = new FormBody.Builder();
                        FormBody formBody = (FormBody) request.body();

                        //把原来的参数添加到新的构造器，（因为没找到直接添加，所以就new新的）
                        for (int i = 0; i < formBody.size(); i++) {
                            bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                        }

                        formBody = bodyBuilder
                                .addEncoded("sourceMode", "android")
                                .addEncoded("token", "")
                                .build();
                        request = request.newBuilder().post(formBody).build();
                    }
                }

                //打印url等信息
                //RequestBody requestBody = request.body();
                Connection connection = chain.connection();
                Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
                String requestStartMessage = "-->method: " + request.method() + ",protocol:" + protocol + "url:" + request.url();

                LogUtils.d("HttpUrl", requestStartMessage);

                Response response = chain.proceed(request);
                //如果是get请求查询数据 设置有网和无网络两种缓存机制
                if (!NetworkUtils.isConnected()) {
                    // 有网络时 设置缓存超时时间60s
                    maxAge = 60;
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为1周
                    int maxStale = 60 * 60 * 24 * 7;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }

                return response;
            }
        };
    }

    private SSLSocketFactory getCertificates() {
        try {

            InputStream inputStream = Utils.getContext().getAssets().open("https.crt");
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);

            int index = 0;
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(inputStream));

            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());

            //Android 4.X 对TLS1.1、TLS1.2的支持
            Tls12SocketFactory sSLSocketFactory = new Tls12SocketFactory(sslContext.getSocketFactory());

            return sSLSocketFactory;
//            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
