package com.vikashyap.foodify.card;

/**
 * Created by Vikas on 5/24/2016.
 * copyright © Fueled
 */
public enum CardType {
	FOOD, NUTRITION;

	public int getViewType() {
		return ordinal();
	}

}
