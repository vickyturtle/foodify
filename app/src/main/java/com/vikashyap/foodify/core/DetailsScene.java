package com.vikashyap.foodify.core;

import com.vikashyap.foodify.card.FoodCardPresenter;
import com.vikashyap.foodify.card.NutritionCardData;
import com.vikashyap.foodify.model.Food;

import java.util.List;

/**
 * Created by Vikas on 5/25/2016.
 * copyright © Fueled
 */
public interface DetailsScene extends Scene {

	void showDetails(Food food, FoodCardPresenter foodCardPresenter);

	void addNutritionData(List<NutritionCardData> dataList);
}
