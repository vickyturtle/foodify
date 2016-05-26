package com.vikashyap.foodify.card;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Vikas on 5/24/2016.
 */
public abstract class BaseCard<T1, T2, T3 extends RecyclerView.ViewHolder> {

	private T1 cardData;
	private T2 cardPresenter;
	T3 viewHolder;

	CardType cardType;

	public BaseCard(T1 data, T2 presenter, @NonNull CardType cardType) {
		this.cardData = data;
		this.cardPresenter = presenter;
		this.cardType = cardType;
	}

	public T1 getCardData() {
		return cardData;
	}

	public T2 getCardPresenter() {
		return cardPresenter;
	}

	public abstract
	@NonNull
	T3 createViewHolder(ViewGroup parent);

	public abstract void bindViewHolder(@NonNull T3 viewHolder);

	public
	@NonNull
	CardType getCardType() {
		return cardType;
	}

}
