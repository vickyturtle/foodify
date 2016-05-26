package com.vikashyap.foodify.entity;

import java.util.List;

/**
 * Created by Vikas on 5/24/2016.
 * copyright © Fueled
 */
public class TFoodResponse {
	public String titleCompleted;
	public String titleRequested;
	public String languageRequested;
	public List<TServingCategory> servingCategories;
	public List<TServingSize> servingSizes;
	public List<TFood> food;
}
