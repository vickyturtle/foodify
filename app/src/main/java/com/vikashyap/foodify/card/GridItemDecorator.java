package com.vikashyap.foodify.card;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Vikas on 5/24/2016.
 */
public class GridItemDecorator extends RecyclerView.ItemDecoration {

	private int mSizeGridSpacingPx;
	private int mGridSize;

	private boolean mNeedLeftSpacing = false;

	public GridItemDecorator(int gridSpacingPx, int gridSize) {
		mSizeGridSpacingPx = gridSpacingPx;
		mGridSize = gridSize;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
	                           RecyclerView.State state) {
		int frameWidth = (int) ((parent.getWidth() - (float) mSizeGridSpacingPx * (mGridSize - 1)) /
				mGridSize);
		int padding = parent.getWidth() / mGridSize - frameWidth;
		GridLayoutManager.LayoutParams params =
				(GridLayoutManager.LayoutParams) view.getLayoutParams();
		int itemPosition = params.getViewAdapterPosition();
		int spanIndex = params.getSpanIndex();
		int spanSize = params.getSpanSize();


		if (itemPosition <= spanIndex) {
			outRect.top = 0;
		} else {
			outRect.top = mSizeGridSpacingPx;
		}

		if (spanIndex % mGridSize == 0) {
			outRect.left = 0;
			if (spanIndex + spanSize == mGridSize) {
				outRect.right = 0;
			} else {
				outRect.right = padding;
			}
			mNeedLeftSpacing = true;
		} else if ((spanIndex + 1) % mGridSize == 0) {
			mNeedLeftSpacing = false;
			outRect.right = 0;
			outRect.left = padding;
		} else if (mNeedLeftSpacing) {
			mNeedLeftSpacing = false;
			outRect.left = mSizeGridSpacingPx - padding;
			if ((spanIndex + 2) % mGridSize == 0) {
				outRect.right = mSizeGridSpacingPx - padding;
			} else {
				outRect.right = mSizeGridSpacingPx / 2;
			}
		} else if ((spanIndex + 2) % mGridSize == 0) {
			mNeedLeftSpacing = false;
			outRect.left = mSizeGridSpacingPx / 2;
			outRect.right = mSizeGridSpacingPx - padding;
		} else {
			mNeedLeftSpacing = false;
			outRect.left = mSizeGridSpacingPx / 2;
			outRect.right = mSizeGridSpacingPx / 2;
		}
		outRect.bottom = 0;
	}
}