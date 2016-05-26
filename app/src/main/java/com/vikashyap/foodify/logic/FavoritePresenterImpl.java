package com.vikashyap.foodify.logic;

import android.os.Bundle;

import com.vikashyap.foodify.card.FoodCardPresenter;
import com.vikashyap.foodify.core.FavoritesPresenter;
import com.vikashyap.foodify.core.FavoritesScene;
import com.vikashyap.foodify.model.Food;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Vikas on 5/25/2016.
 * copyright © Fueled
 */
public class FavoritePresenterImpl extends PresenterImpl<FavoritesScene>
		implements FavoritesPresenter {

	private final DbManager dbManager;
	private Realm realm;

	@Inject
	public FavoritePresenterImpl(DbManager dbManager) {
		this.dbManager = dbManager;
	}

	@Override
	public void onSceneAdded(FavoritesScene scene) {
		super.onSceneAdded(scene);
		realm = dbManager.getDataRealm();
		RealmResults<Food> foods = realm.where(Food.class)
				.equalTo("isFavorite", true)
				.findAllAsync();
		foods.addChangeListener(realmChangeListener);
	}

	@Override
	public void onSceneRemoved() {
		super.onSceneRemoved();
		realm.close();
	}

	RealmChangeListener<RealmResults<Food>> realmChangeListener =
			new RealmChangeListener<RealmResults<Food>>() {
				@Override
				public void onChange(RealmResults<Food> foods) {
					if (scene != null) {
						scene.showFood(foods, presenter);
				}
				}
			};

	FoodCardPresenter presenter = new FoodCardPresenter() {
		@Override
		public void onCardClick(Food food) {
			Realm cacheRealm = dbManager.getCacheRealm();
			cacheRealm.beginTransaction();
			cacheRealm.copyToRealmOrUpdate(food);
			cacheRealm.commitTransaction();
			cacheRealm.close();
			Bundle extras = DetailsPresenterImpl.getExtras(food);
			scene.showDetailsScene(extras);
		}

		@Override
		public void onFavoriteClick(Food food) {
			realm.beginTransaction();
			food.isFavorite = !food.isFavorite;
			realm.copyToRealmOrUpdate(food);
			realm.commitTransaction();
		}
	};
}
