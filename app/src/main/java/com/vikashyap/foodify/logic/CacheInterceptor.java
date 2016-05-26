package com.vikashyap.foodify.logic;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Vikas on 5/25/2016.
 */
public class CacheInterceptor implements Interceptor {

	@Inject
	public CacheInterceptor() {
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		// Add Cache Control only for GET methods
		if (request.method().equals("GET")) {
			// 5 min stale
			request = request.newBuilder()
					.header("Cache-Control", "public, max-stale=300")
					.build();
		}

		Response originalResponse = chain.proceed(request);
		return originalResponse.newBuilder()
				.header("Cache-Control", "max-age=300")
				.build();
	}
}
