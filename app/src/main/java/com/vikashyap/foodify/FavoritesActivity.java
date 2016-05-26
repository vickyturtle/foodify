package com.vikashyap.foodify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vikashyap.foodify.card.BaseCard;
import com.vikashyap.foodify.card.CardAdapter;
import com.vikashyap.foodify.card.FoodCard;
import com.vikashyap.foodify.card.FoodCardPresenter;
import com.vikashyap.foodify.card.GridItemDecorator;
import com.vikashyap.foodify.core.DialogData;
import com.vikashyap.foodify.core.FavoritesPresenter;
import com.vikashyap.foodify.core.FavoritesScene;
import com.vikashyap.foodify.dagger.ActivityComponent;
import com.vikashyap.foodify.model.Food;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDimen;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vikas on 5/25/2016.
 * copyright © Fueled
 */
public class FavoritesActivity extends BaseActivity<FavoritesPresenter> implements FavoritesScene {

	@BindView(R.id.recycler_view) RecyclerView recyclerView;
	@BindInt(R.integer.span_count) int spanCount;
	@BindDimen(R.dimen.divider_space) int dividerSize;

	private CardAdapter adapter;


	@Override
	protected void setUpView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_favorites);
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);
		setRecyclerView();
		assert getSupportActionBar() != null;
		getSupportActionBar().setTitle(R.string.title_favorite);
	}

	private void setRecyclerView() {
		GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.addItemDecoration(new GridItemDecorator(dividerSize, spanCount));
		adapter = new CardAdapter(new ArrayList<BaseCard>());
		recyclerView.setAdapter(adapter);
	}

	@Override
	protected void injectFromComponent(ActivityComponent activityComponent) {
		activityComponent.inject(this);
	}

	@Override
	public void showFood(List<Food> foodList, FoodCardPresenter presenter) {
		adapter.clearAllRecyclerItems();
		if (foodList.isEmpty()) {
			showEmptyListDialog();
		} else {
			List<FoodCard> foodCards = new ArrayList<>();
			for (Food food : foodList) {
				FoodCard card = new FoodCard(this, food, presenter);
				foodCards.add(card);
			}
			adapter.addCardsList(foodCards);
		}
	}

	@Override
	public void showDetailsScene(Bundle extras) {
		Intent intent = new Intent(this, DetailsActivity.class);
		intent.putExtras(extras);
		startActivity(intent);
	}

	private void showEmptyListDialog() {
		DialogData dialogData = new DialogData(DialogData.Type.ALERT);
		dialogData.titleRestId = R.string.no_result_title;
		dialogData.messageResId = R.string.empty_favorites;
		displayDialog(dialogData);
	}
}
