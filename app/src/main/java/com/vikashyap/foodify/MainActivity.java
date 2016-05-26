package com.vikashyap.foodify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vikashyap.foodify.card.BaseCard;
import com.vikashyap.foodify.card.CardAdapter;
import com.vikashyap.foodify.card.FoodCard;
import com.vikashyap.foodify.card.FoodCardPresenter;
import com.vikashyap.foodify.card.GridItemDecorator;
import com.vikashyap.foodify.core.Logger;
import com.vikashyap.foodify.core.MainPresenter;
import com.vikashyap.foodify.core.MainScene;
import com.vikashyap.foodify.dagger.ActivityComponent;
import com.vikashyap.foodify.model.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindArray;
import butterknife.BindDimen;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainPresenter> implements MainScene {

	public static final String SEARCH_TEXT = "SEARCH_TEXT";
	@BindView(R.id.search_pointer_title) TextView searchPointerTitleView;
	@BindView(R.id.search_pointer_subtitle) TextView subtitleView;
	@BindView(R.id.food_quote) TextView quoteView;
	@BindView(R.id.recycler_view) RecyclerView recyclerView;
	@BindView(R.id.empty_view) View emptyView;
	@BindView(R.id.progress_bar) ProgressBar progressBar;
	@BindArray(R.array.food_quotes) public String[] foodQuotes;
	@BindInt(R.integer.span_count) int spanCount;
	@BindDimen(R.dimen.divider_space) int dividerSize;
	private CardAdapter adapter;
	private SearchView searchView;
	private String query;

	private Logger logger = Logger.getLogger(getClass());

	@Override
	protected void setUpView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);
		progressBar.setVisibility(View.GONE);
		Random random = new Random();
		int index = random.nextInt(foodQuotes.length);
		quoteView.setText(foodQuotes[index]);
		setRecyclerView();
		if(savedInstanceState != null) {
			query = savedInstanceState.getString(SEARCH_TEXT, null);
		}
	}

	private void setRecyclerView() {
		GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.addItemDecoration(new GridItemDecorator(dividerSize, spanCount));
		adapter = new CardAdapter(new ArrayList<BaseCard>());
		recyclerView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu_main, menu);
		MenuItem searchItem = menu.findItem(R.id.menu_search);
		searchView = (SearchView) searchItem.getActionView();
		searchView.setOnQueryTextListener(queryTextListener);
		MenuItemCompat.setOnActionExpandListener(searchItem, actionExpandListener);
		if(query != null) {
			logger.debug("querying :" + query);
			searchView.setQuery(query, true);
		}
		return true;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		CharSequence query = searchView.getQuery();
		if (!TextUtils.isEmpty(query)) {
			outState.putString(SEARCH_TEXT, query.toString());
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.menu_favorites) {
			startActivity(new Intent(this, FavoritesActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void injectFromComponent(ActivityComponent activityComponent) {
		activityComponent.inject(this);
	}

	@Override
	public void showFood(List<Food> foodList, FoodCardPresenter presenter) {
		progressBar.setVisibility(View.GONE);
		adapter.clearAllRecyclerItems();
		if (foodList.isEmpty()) {
			showEmptyView();
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

	private void showEmptyView() {
		emptyView.setVisibility(View.VISIBLE);
		searchPointerTitleView.setText(R.string.no_result_title);
		String message = String.format(Locale.US, getString(R.string.no_result_subtitle),
				searchView.getQuery());
		subtitleView.setText(message);
	}

	SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
		@Override
		public boolean onQueryTextSubmit(String query) {
			return true;
		}

		@Override
		public boolean onQueryTextChange(String newText) {
			logger.debug("querying :" + newText);
			if (TextUtils.isEmpty(newText)) {
				adapter.clearAllRecyclerItems();
				showWelcomeView();
			} else {
				progressBar.setVisibility(View.VISIBLE);
				emptyView.setVisibility(View.GONE);
				presenter.searchFor(newText);
			}
			return true;
		}
	};

	MenuItemCompat.OnActionExpandListener actionExpandListener =
			new MenuItemCompat.OnActionExpandListener() {
				@Override
				public boolean onMenuItemActionExpand(MenuItem item) {
					return true;
				}

				@Override
				public boolean onMenuItemActionCollapse(MenuItem item) {
					showWelcomeView();
					return true;
				}
			};

	private void showWelcomeView() {
		presenter.searchCancelled();
		emptyView.setVisibility(View.VISIBLE);
		searchPointerTitleView.setText(R.string.search_pointer_title);
		subtitleView.setText(R.string.search_pointer_subtitle);
	}
}
