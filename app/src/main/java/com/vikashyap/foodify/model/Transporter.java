package com.vikashyap.foodify.model;

import com.vikashyap.foodify.entity.TFood;
import com.vikashyap.foodify.entity.TServingCategory;
import com.vikashyap.foodify.entity.TServingSize;

/**
 * Created by Vikas on 5/24/2016.
 * copyright © Fueled
 */
public class Transporter {

	public static Food toFood(TFood tFood) {
		Food food = new Food();
		food.title = tFood.title;
		food.brand = tFood.brand;
		food.calories = tFood.calories;
		food.carbohydrates = tFood.carbohydrates;
		food.categoryId = tFood.categoryid;
		food.category = tFood.category;
		food.cholesterol = tFood.cholesterol;
		food.defaultserving = tFood.defaultserving;
		food.fat = tFood.fat;
		food.fiber = tFood.fiber;
		food.gramsPerServing = tFood.gramsperserving;
		food.id = tFood.id;
		food.mlInGram = tFood.mlingram;
		food.pcsInGram = tFood.pcsingram;
		food.gramsPerServing = tFood.gramsperserving;
		food.protein = tFood.protein;
		food.potassium = tFood.potassium;
		food.saturatedfat = tFood.saturatedfat;
		food.unsaturatedfat = tFood.unsaturatedfat;
		food.sodium = tFood.sodium;
		food.sugar = tFood.sugar;
		food.verified = tFood.verified;
		food.pcsText = tFood.pcstext;
		return food;
	}

	public static ServingCategory toServingCategory(TServingCategory tServingCategory) {
		ServingCategory servingCategory = new ServingCategory();
		servingCategory.created = tServingCategory.created;
		servingCategory.defaultsize = tServingCategory.defaultsize;
		servingCategory.lastupdated = tServingCategory.lastupdated;
		servingCategory.id = tServingCategory.oid;
		servingCategory.name = tServingCategory.name;
		servingCategory.linearsize = tServingCategory.linearsize;
		servingCategory.source = tServingCategory.source;
		return servingCategory;
	}

	public static ServingSize toServigSize(TServingSize tServingSize) {
		ServingSize servingSize = new ServingSize();
		servingSize.countryfilter = tServingSize.countryfilter;
		servingSize.created = tServingSize.created;
		servingSize.id = tServingSize.oid;
		servingSize.name = tServingSize.name;
		servingSize.proportion = tServingSize.proportion;
		servingSize.source = tServingSize.source;
		servingSize.lastupdated = tServingSize.lastupdated;
		return servingSize;
	}
}
