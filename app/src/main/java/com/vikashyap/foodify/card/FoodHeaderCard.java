package com.vikashyap.foodify.card;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vikashyap.foodify.R;
import com.vikashyap.foodify.model.Food;

/**
 * Created by Vikas on 5/26/2016.
 * copyright © Fueled
 */
public class FoodHeaderCard extends FoodCard {

	public FoodHeaderCard(Context context, Food data, FoodCardPresenter presenter) {
		super(context, data, presenter);
	}

	@NonNull
	@Override
	public ViewHolder createViewHolder(ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.card_food_header, parent, false);
		return new ViewHolder(view);
	}
}
