package com.vikashyap.foodify.dagger;

import com.vikashyap.foodify.core.DetailsPresenter;
import com.vikashyap.foodify.core.FavoritesPresenter;
import com.vikashyap.foodify.core.MainPresenter;
import com.vikashyap.foodify.logic.DetailsPresenterImpl;
import com.vikashyap.foodify.logic.FavoritePresenterImpl;
import com.vikashyap.foodify.logic.MainPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Vikas on 5/23/2016.
 */
@Module
@PerActivity
public class ActivityModule {

	@PerActivity
	@Provides
	public MainPresenter getMainPresenter(MainPresenterImpl presenter) {
		return presenter;
	}

	@PerActivity
	@Provides
	public FavoritesPresenter getFavoritePresenter(FavoritePresenterImpl presenter) {
		return presenter;
	}

	@PerActivity
	@Provides
	public DetailsPresenter getDetailsPresenter(DetailsPresenterImpl presenter) {
		return presenter;
	}
}
