package com.vikashyap.foodify.card;

import com.vikashyap.foodify.model.Food;

/**
 * Created by Vikas on 5/24/2016.
 */
public interface FoodCardPresenter {
	void onCardClick(Food food);

	void onFavoriteClick(Food food);
}
