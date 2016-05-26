package com.vikashyap.foodify;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vikashyap.foodify.card.BaseCard;
import com.vikashyap.foodify.card.CardAdapter;
import com.vikashyap.foodify.card.FoodCard;
import com.vikashyap.foodify.card.FoodCardPresenter;
import com.vikashyap.foodify.card.FoodHeaderCard;
import com.vikashyap.foodify.card.GridItemDecorator;
import com.vikashyap.foodify.card.NutritionCard;
import com.vikashyap.foodify.card.NutritionCardData;
import com.vikashyap.foodify.core.DetailsPresenter;
import com.vikashyap.foodify.core.DetailsScene;
import com.vikashyap.foodify.dagger.ActivityComponent;
import com.vikashyap.foodify.model.Food;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends BaseActivity<DetailsPresenter> implements DetailsScene {
	@BindView(R.id.recycler_view) RecyclerView recyclerView;
	@BindDimen(R.dimen.one_dp) int dividerHeight;
	private CardAdapter adapter;


	@Override
	protected void setUpView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_details);
		ButterKnife.bind(this);
		recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
		adapter = new CardAdapter(new ArrayList<BaseCard>());
		recyclerView.setAdapter(adapter);
		GridItemDecorator itemDecorator = new GridItemDecorator(dividerHeight, 1);
		recyclerView.addItemDecoration(itemDecorator);
	}

	@Override
	protected void injectFromComponent(ActivityComponent activityComponent) {
		activityComponent.inject(this);
	}

	@Override
	public void showDetails(Food food, FoodCardPresenter foodCardPresenter) {
		assert getSupportActionBar() != null;
		getSupportActionBar().setTitle(food.title);
		FoodHeaderCard foodCard = new FoodHeaderCard(this, food, foodCardPresenter);
		adapter.addCard(foodCard);
	}

	@Override
	public void addNutritionData(List<NutritionCardData> dataList) {
		for (NutritionCardData data : dataList) {
			NutritionCard card = new NutritionCard(data);
			adapter.addCard(card);
		}
	}
}
