package com.vikashyap.foodify.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Vikas on 5/24/2016.
 * copyright © Fueled
 */
public class Food extends RealmObject{
	@PrimaryKey
	public long id;
	public String brand;
	public String category;
	public boolean verified;
	public String title;
	public long categoryId;
	public int source;
	public float potassium;
	public float calories;
	public float carbohydrates;
	public float fiber;
	public float saturatedfat;
	public float fat;
	public ServingCategory servingCategory;
	public long defaultserving;
	public float cholesterol;
	public float unsaturatedfat;
	public float protein;
	public float sodium;
	public float sugar;
	public float pcsInGram;
	public float mlInGram;
	public String pcsText;
	public float gramsPerServing;
	public boolean isFavorite;
}
