package com.vikashyap.foodify.card;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.vikashyap.foodify.R;

import java.util.List;

/**
 * Created by Vikas on 5/24/2016.
 */
public class CardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	List<BaseCard> cards;

	private int lastAnimatedPos = 4;
	private int animationOffset = 0;

	public CardAdapter(List<BaseCard> cards) {
		this.cards = cards;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (animationOffset == 0) {
			animationOffset =
					parent.getContext().getResources().getDimensionPixelOffset(R.dimen.keyline_2);
		}
		for (BaseCard card : cards) {
			if (card.getCardType().getViewType() == viewType) {
				return card.createViewHolder(parent);
			}
		}
		return null;
	}

	/**
	 * type safety of view holder can only be guaranteed internally in recycler view implementation
	 */
	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		cards.get(position).bindViewHolder(holder);
		if (lastAnimatedPos < position) {
			ObjectAnimator.ofFloat(holder.itemView, View.TRANSLATION_Y, animationOffset, 0).start();
			lastAnimatedPos = position;
		}
	}

	@Override
	public int getItemViewType(int position) {
		return cards.get(position).cardType.getViewType();
	}

	@Override
	public int getItemCount() {
		return cards != null ? cards.size() : 0;
	}

	/**
	 * Add multiple cards to the adapter.
	 *
	 * @param cards the list of cards to be added
	 */
	public void addCardsList(List<? extends BaseCard> cards) {
		this.cards.addAll(cards);
		notifyDataSetChanged();
	}

	/**
	 * Add multiple cards to the adapter at the specified position
	 * and shifts items that are currently at this position to the right.
	 *
	 * @param position the position at which to add the first card in the list
	 * @param cards    the list of card to be added
	 */
	public void addCardsList(int position, List<? extends BaseCard> cards) {
		this.cards.addAll(position, cards);
		notifyDataSetChanged();
	}

	/**
	 * Add card to the adapter.
	 *
	 * @param card the card to be added
	 */
	public void addCard(BaseCard card) {
		addCard(cards.size(), card);
	}

	/**
	 * Add card to the adapter at the specified position.
	 *
	 * @param position the position at which to add the card in the list
	 * @param card     the card to be added
	 */
	public void addCard(int position, BaseCard card) {
//		card.setPositionInAdapter(position);
		cards.add(position, card);
		notifyItemInserted(position);
	}

	/**
	 * Removes the card at the specified position in this adapter view.
	 *
	 * @param position the position of the card to be removed
	 */
	public void removeCardAt(int position) {
		cards.remove(position);
		notifyItemRemoved(position);
	}

	/**
	 * Removes all cards inside the recycler adapter view.
	 */
	public void clearAllRecyclerItems() {
		cards.clear();
		notifyDataSetChanged();
	}

	/**
	 * Returns the card at the specified position in the adapter.
	 *
	 * @param position position of the card to be returned
	 * @return the card at the specified position in the adapter
	 */
	public BaseCard getCard(int position) {
		return cards.get(position);
	}
}
