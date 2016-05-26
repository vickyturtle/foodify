package com.vikashyap.foodify.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Vikas on 5/24/2016.
 */
public class ServingCategory extends RealmObject{

	@PrimaryKey
	public long id;
	public String name;
	public long lastupdated;
	public int linearsize;
	public int linearquantity;
	public String source;
	public int usemedian;
	public long created;
	public int defaultsize;
}
