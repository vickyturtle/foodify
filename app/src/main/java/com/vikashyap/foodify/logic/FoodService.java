package com.vikashyap.foodify.logic;

import com.vikashyap.foodify.entity.TFoodResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Vikas on 5/23/2016.
 */
public interface FoodService {

	@GET("foods/en/in/{food}/")
	Observable<TFoodResponse> searhFood(@Path("food") String query);
}
