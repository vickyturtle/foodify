package com.vikashyap.foodify.dagger;

import com.vikashyap.foodify.DetailsActivity;
import com.vikashyap.foodify.FavoritesActivity;
import com.vikashyap.foodify.MainActivity;

import dagger.Component;

/**
 * Created by Vikas on 5/23/2016.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
	void inject(MainActivity activity);

	void inject(FavoritesActivity favoritesActivity);

	void inject(DetailsActivity detailsActivity);
}
