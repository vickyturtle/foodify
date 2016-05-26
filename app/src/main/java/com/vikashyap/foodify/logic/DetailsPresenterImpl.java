package com.vikashyap.foodify.logic;

import android.app.Application;
import android.os.Bundle;

import com.vikashyap.foodify.R;
import com.vikashyap.foodify.card.FoodCardPresenter;
import com.vikashyap.foodify.card.NutritionCard;
import com.vikashyap.foodify.card.NutritionCardData;
import com.vikashyap.foodify.core.DetailsPresenter;
import com.vikashyap.foodify.core.DetailsScene;
import com.vikashyap.foodify.model.Food;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by Vikas on 5/25/2016.
 * copyright © Fueled
 */
public class DetailsPresenterImpl extends PresenterImpl<DetailsScene> implements DetailsPresenter {

	private static final String EXTRA_ID = "EXTRA_ID";
	private final Application application;
	private final DbManager dbManager;
	private long id;

	@Inject
	public DetailsPresenterImpl(Application application, DbManager dbManager) {
		this.application = application;
		this.dbManager = dbManager;
	}

	public static Bundle getExtras(Food food) {
		Bundle bundle = new Bundle();
		bundle.putLong(EXTRA_ID, food.id);
		return bundle;
	}

	@Override
	public void init(Bundle extras) {
		super.init(extras);
		id = extras.getLong(EXTRA_ID);
	}

	@Override
	public void onSceneAdded(DetailsScene scene) {
		super.onSceneAdded(scene);
		Realm realm = dbManager.getCacheRealm();
		Food first = realm.where(Food.class)
				.equalTo("id", this.id)
				.findFirst();
		Food food = realm.copyFromRealm(first);
		scene.showDetails(food, cardPresenter);
		List<NutritionCardData> dataList = createCardsData(food);
		scene.addNutritionData(dataList);
	}

	FoodCardPresenter cardPresenter = new FoodCardPresenter() {
		@Override
		public void onCardClick(Food food) {

		}

		@Override
		public void onFavoriteClick(Food food) {
			Realm realm = dbManager.getDataRealm();
			realm.beginTransaction();
			food.isFavorite = !food.isFavorite;
			realm.copyToRealmOrUpdate(food);
			realm.commitTransaction();
			realm.close();
		}
	};

	private List<NutritionCardData> createCardsData(Food food) {
		List<NutritionCardData> dataList = new ArrayList<>();
		NutritionCardData data = new NutritionCardData();
		data.title = application.getString(R.string.details_calories);
		data.value = food.calories + "";
		dataList.add(data);

		data = new NutritionCardData();
		data.title = application.getString(R.string.details_carbohydrates);
		data.value = food.carbohydrates + "";
		dataList.add(data);

		data = new NutritionCardData();
		data.title = application.getString(R.string.details_proteins);
		data.value = food.protein + "";
		dataList.add(data);

		data = new NutritionCardData();
		data.title = application.getString(R.string.details_fat);
		data.value = food.fat + "";
		dataList.add(data);

		data = new NutritionCardData();
		data.title = application.getString(R.string.details_saturated_fat);
		data.value = food.saturatedfat + "";
		dataList.add(data);

		data = new NutritionCardData();
		data.title = application.getString(R.string.details_unsaturated_fat);
		data.value = food.unsaturatedfat + "";
		dataList.add(data);

		data = new NutritionCardData();
		data.title = application.getString(R.string.details_cholesterol);
		data.value = food.cholesterol + "";
		dataList.add(data);

		data = new NutritionCardData();
		data.title = application.getString(R.string.details_fiber);
		data.value = food.fiber + "";
		dataList.add(data);

		data = new NutritionCardData();
		data.title = application.getString(R.string.details_potassium);
		data.value = food.potassium + "";
		dataList.add(data);

		data = new NutritionCardData();
		data.title = application.getString(R.string.details_sodium);
		data.value = food.sodium + "";
		dataList.add(data);

		data = new NutritionCardData();
		data.title = application.getString(R.string.details_sugar);
		data.value = food.sugar + "";
		dataList.add(data);

		data = new NutritionCardData();
		data.title = application.getString(R.string.details_serving);
		data.value = food.gramsPerServing + " gms";
		dataList.add(data);
		return dataList;
	}
}
