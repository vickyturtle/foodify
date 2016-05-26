package com.vikashyap.foodify.core;

import android.os.Bundle;

import com.vikashyap.foodify.card.FoodCardPresenter;
import com.vikashyap.foodify.model.Food;

import java.util.List;

/**
 * Created by Vikas on 5/25/2016.
 * copyright © Fueled
 */
public interface FavoritesScene extends Scene {
	void showFood(List<Food> foodList, FoodCardPresenter presenter);

	void showDetailsScene(Bundle extras);
}
