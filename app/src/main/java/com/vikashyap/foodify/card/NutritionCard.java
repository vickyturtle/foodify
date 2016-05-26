package com.vikashyap.foodify.card;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vikashyap.foodify.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vikas on 5/25/2016.
 */
public class NutritionCard extends BaseCard<NutritionCardData, Void, NutritionCard.ViewHolder> {

	public NutritionCard(NutritionCardData data) {
		super(data, null, CardType.NUTRITION);
	}

	@NonNull
	@Override
	public ViewHolder createViewHolder(ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.card_nutrition, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void bindViewHolder(@NonNull ViewHolder viewHolder) {
		viewHolder.titleView.setText(getCardData().title);
		viewHolder.valuesView.setText(getCardData().value);
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.nutrition_title) TextView titleView;
		@BindView(R.id.nutrition_value) TextView valuesView;

		public ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
