package com.vikashyap.foodify.logic;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.vikashyap.foodify.card.FoodCardPresenter;
import com.vikashyap.foodify.core.DetailsPresenter;
import com.vikashyap.foodify.core.MainPresenter;
import com.vikashyap.foodify.core.MainScene;
import com.vikashyap.foodify.dagger.IoSched;
import com.vikashyap.foodify.dagger.UiSched;
import com.vikashyap.foodify.entity.TFood;
import com.vikashyap.foodify.entity.TFoodResponse;
import com.vikashyap.foodify.entity.TServingCategory;
import com.vikashyap.foodify.entity.TServingSize;
import com.vikashyap.foodify.model.Food;
import com.vikashyap.foodify.model.ServingCategory;
import com.vikashyap.foodify.model.ServingSize;
import com.vikashyap.foodify.model.Transporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by Vikas on 5/23/2016.
 */
public class MainPresenterImpl extends PresenterImpl<MainScene> implements MainPresenter {

	private final FoodService foodService;
	private final DbManager dbManager;
	private final Scheduler ioSched;
	private final Scheduler uiSched;
	private PublishSubject<String> querySubject = PublishSubject.create();
	private Subscription searchSubscription;
	private List<Food> foods;

	@Inject
	public MainPresenterImpl(FoodService foodService, DbManager dbManager,
	                         @IoSched Scheduler ioSched, @UiSched Scheduler uiSched) {
		this.foodService = foodService;
		this.dbManager = dbManager;
		this.ioSched = ioSched;
		this.uiSched = uiSched;
		querySubject.throttleWithTimeout(500, TimeUnit.MILLISECONDS, ioSched)
				.subscribe(new Action1<String>() {
					@Override
					public void call(String s) {
						//Not used flat map here as in case of errors this subscription will unsubscribe
						if (searchSubscription != null && !searchSubscription.isUnsubscribed()) {
							searchSubscription.unsubscribe();
						}
						searchSubscription = doSearch(s);
					}
				});

	}

	private Subscription doSearch(String s) {
		return searchServer(s)
				.observeOn(uiSched)
				.subscribe(new Action1<List<Food>>() {
					@Override
					public void call(List<Food> foods) {
						if (scene != null) {
							scene.showFood(foods, foodCardPresenter);
						}

					}
				}, new Action1<Throwable>() {
					@Override
					public void call(Throwable throwable) {
						handleError(throwable);
					}
				});
	}

	private Observable<List<Food>> searchServer(String s) {
		return foodService.searhFood(s).subscribeOn(ioSched)
				.flatMap(new Func1<TFoodResponse, Observable<List<Food>>>() {
					@Override
					public Observable<List<Food>> call(TFoodResponse tFoodResponse) {
						return Observable.just(processResponse(tFoodResponse));
					}
				});
	}

	private List<Food> processResponse(TFoodResponse tFoodResponse) {
		Map<Long, ServingCategory> categoryMap = new HashMap<>();
		if (tFoodResponse == null) {
			return new ArrayList<>();
		}
		for (TServingCategory tCategory : tFoodResponse.servingCategories) {
			ServingCategory category = Transporter.toServingCategory(tCategory);
			categoryMap.put(category.id, category);
		}

		Map<Long, ServingSize> sizeMap = new HashMap<>();
		for (TServingSize tSize : tFoodResponse.servingSizes) {
			ServingSize servingSize = Transporter.toServigSize(tSize);
			sizeMap.put(servingSize.id, servingSize);
		}

		Set<Long> favoriteFoods = getFavoriteSet();
		foods = new ArrayList<>();
		for (TFood tFood : tFoodResponse.food) {
			Food food = Transporter.toFood(tFood);
			food.servingCategory = categoryMap.get(tFood.servingcategory);
			food.isFavorite = favoriteFoods.contains(food.id);
			foods.add(food);
		}
		return foods;
	}

	@NonNull
	private Set<Long> getFavoriteSet() {
		Realm realm = dbManager.getDataRealm();
		RealmResults<Food> realmResults = realm.where(Food.class)
				.equalTo("isFavorite", true)
				.findAll();
		Set<Long> favoriteFoods = new HashSet<>();
		for (Food food : realmResults) {
			logger.debug("Favorite Food id : " + food.id);
			favoriteFoods.add(food.id);
		}
		realm.close();
		return favoriteFoods;
	}

	@Override
	public void onSceneAdded(MainScene scene) {
		super.onSceneAdded(scene);
		if (foods != null) {
			Set<Long> favoriteSet = getFavoriteSet();
			for (Food food : foods) {
				food.isFavorite = favoriteSet.contains(food.id);
			}
			scene.showFood(foods, foodCardPresenter);
		}

	}

	@Override
	public void searchFor(String query) {
		querySubject.onNext(query);
	}

	@Override
	public void searchCancelled() {
		foods = null;
	}

	FoodCardPresenter foodCardPresenter = new FoodCardPresenter() {
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
			logger.debug("Saving food to favorites");
			Realm realm = dbManager.getDataRealm();
			realm.beginTransaction();
			food.isFavorite = !food.isFavorite;
			realm.copyToRealmOrUpdate(food);
			realm.commitTransaction();
			realm.close();
		}
	};
}
