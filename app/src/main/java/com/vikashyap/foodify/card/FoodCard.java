package com.vikashyap.foodify.card;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vikashyap.foodify.R;
import com.vikashyap.foodify.model.Food;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vikas on 5/24/2016.
 */
public class FoodCard extends BaseCard<Food, FoodCardPresenter, FoodCard.ViewHolder> {

	public int[] colors = new int[3];

	public FoodCard(Context context, Food data, FoodCardPresenter presenter) {
		super(data, presenter, CardType.FOOD);
		Context appContext = context.getApplicationContext();
		colors[0] = ContextCompat.getColor(appContext, R.color.food_health_1);
		colors[1] = ContextCompat.getColor(appContext, R.color.food_health_2);
		colors[2] = ContextCompat.getColor(appContext, R.color.food_health_3);
	}

	@NonNull
	@Override
	public ViewHolder createViewHolder(ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.card_food, parent, false);
		ViewHolder viewHolder = new ViewHolder(view);
		return viewHolder;
	}

	@Override
	public void bindViewHolder(@NonNull final ViewHolder viewHolder) {
		viewHolder.foodTitle.setText(getCardData().title);
		viewHolder.categoryView.setText(getCardData().category);
		if (getCardData().servingCategory != null) {
			viewHolder.servingView.setText(getCardData().servingCategory.name);
		}
		float cal = getCardData().calories;
		int level = cal < 50 ? 0 : cal < 200 ? 1 : 2;
		viewHolder.levelImageView.setBackgroundColor(colors[level]);
		setFavoriteButton(viewHolder);
		viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getCardPresenter().onCardClick(getCardData());
			}
		});

		viewHolder.favoriteView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getCardPresenter().onFavoriteClick(getCardData());
				animateFavorite(v);
				setFavoriteButton(viewHolder);
			}
		});
	}

	private void animateFavorite(View v) {
		if (!getCardData().isFavorite) {
			PropertyValuesHolder pvsx = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 2f);
			PropertyValuesHolder pvsy = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 2f);
			ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v, pvsx, pvsy);
			animator.setRepeatMode(ValueAnimator.REVERSE);
			animator.setRepeatCount(1);
			animator.start();
		}
	}

	private void setFavoriteButton(@NonNull ViewHolder viewHolder) {
		int color = getCardData().isFavorite ? Color.BLUE : Color.GRAY;
		ColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY);
		viewHolder.favoriteView.setColorFilter(colorFilter);
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.food_category) TextView categoryView;
		@BindView(R.id.food_serving) TextView servingView;
		@BindView(R.id.food_title) TextView foodTitle;
		@BindView(R.id.food_level) ImageView levelImageView;
		@BindView(R.id.favorite_button) ImageView favoriteView;

		public ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
