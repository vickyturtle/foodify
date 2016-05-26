package com.vikashyap.foodify.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Vikas on 5/24/2016.
 */
public class ServingSize extends RealmObject {

	@PrimaryKey
	public long id;
	public String name;
	public long lastupdated;
	public long created;
	public ServingCategory servingCategory;
	public String countryfilter;
	public String source;
	public float proportion;
}
