package com.vikashyap.foodify.dagger;

import android.app.Application;
import android.net.http.HttpResponseCache;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.vikashyap.foodify.logic.CacheInterceptor;
import com.vikashyap.foodify.logic.FoodService;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Vikas on 5/23/2016.
 * All app specific components go in here.
 */
@Singleton
@Module
public class AppModule {
	public static final String BASE_URL = "http://www.api.lifesum.com/icebox/v1/";
	public static final String TOKEN =
			"5055538:e52b2981d949fea96d3a103643f377e1ab85c08e347e310adf5ed927831e1018";
	private final Application application;

	public AppModule(Application application){
		this.application = application;
	}

	@Provides
	public Application getApplication() {
		return application;
	}

	@Provides
	@Singleton
	public Retrofit getRetrofit(OkHttpClient client, GsonConverterFactory gsonFactory) {
		return new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.client(client)
				.addConverterFactory(gsonFactory)
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build();
	}

	@Provides
	@Singleton
	public OkHttpClient getClient(HttpLoggingInterceptor loggingInterceptor, Cache cache,
	                              Interceptor headerInterceptor, CacheInterceptor cacheInterceptor) {
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.cache(cache)
				.addInterceptor(loggingInterceptor)
				.addInterceptor(headerInterceptor)
				.addInterceptor(cacheInterceptor)
				.connectTimeout(60, TimeUnit.SECONDS)
				.readTimeout(60, TimeUnit.SECONDS)
				.writeTimeout(60, TimeUnit.SECONDS)
				.build();

		return okHttpClient;
	}

	@Provides
	@Singleton
	public Cache getCache(Application application) {
		File httpCacheDir;
		Cache cache = null;
		try {
			httpCacheDir = new File(application.getCacheDir(), "http");
			httpCacheDir.setReadable(true);
			long httpCacheSize = 10 * 1024 * 1024; // 10 MB
			HttpResponseCache.install(httpCacheDir, httpCacheSize);

			cache = new Cache(httpCacheDir, httpCacheSize);
			Log.i("HTTP Caching", "HTTP response cache installation success");
		} catch (IOException e) {
			Log.i("HTTP Caching", "HTTP response cache installation failed:" + e);
		}
		return cache;
	}

	@Provides
	public Interceptor getTokenInterceptor() {
		return new Interceptor() {
			@Override
			public Response intercept(Chain chain) throws IOException {
				Request request = chain.request()
						.newBuilder()
						.addHeader("Authorization", TOKEN)
						.build();
				return chain.proceed(request);
			}
		};
	}

	@Provides
	@Singleton
	public FoodService getFlyerService(Retrofit retrofit) {
		return retrofit.create(FoodService.class);
	}

	@Provides
	@Singleton
	Picasso getPicasso(Application application) {
		Picasso picasso = Picasso.with(application);
		return picasso;
	}

	@Provides
	public HttpLoggingInterceptor getLoggingInterceptor() {
		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
		loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		return loggingInterceptor;
	}

	@Provides
	public GsonConverterFactory getGsonFactory(Gson gson) {
		return GsonConverterFactory.create(gson);
	}

	@Provides
	public Gson getGson() {
		return new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
				.create();
	}

	@Provides
	@IoSched
	Scheduler getIoScheduler() {
		return Schedulers.io();
	}

	@Provides
	@UiSched
	Scheduler getUiScheduler() {
		return AndroidSchedulers.mainThread();
	}

}
