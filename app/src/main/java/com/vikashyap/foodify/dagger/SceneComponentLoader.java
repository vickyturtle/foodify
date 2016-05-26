package com.vikashyap.foodify.dagger;

import android.content.Context;
import android.support.v4.content.Loader;

import com.vikashyap.foodify.FoodifyApplication;

/**
 * Created by Vikas on 5/23/2016.
 */
public class SceneComponentLoader extends Loader<ActivityComponent> {

	private final AppComponent appComponent;
	private ActivityComponent activityComponent;

	/**
	 * Stores away the application context associated with context.
	 * Since Loaders can be used across multiple activities it's dangerous to
	 * store the context directly; always use {@link #getContext()} to retrieve
	 * the Loader's Context, don't use the constructor argument directly.
	 * The Context returned by {@link #getContext} is safe to use across
	 * Activity instances.
	 *
	 * @param context used to retrieve the application context.
	 */
	public SceneComponentLoader(Context context) {
		super(context);
		appComponent = ((FoodifyApplication) context.getApplicationContext()).getComponent();
	}

	@Override
	protected void onStartLoading() {
		super.onStartLoading();
		if(activityComponent != null) {
			deliverResult(activityComponent);
		} else {
			forceLoad();
		}
	}

	@Override
	protected void onForceLoad() {
		super.onForceLoad();
		activityComponent = DaggerActivityComponent.builder()
				.appComponent(appComponent)
				.build();
		deliverResult(activityComponent);
	}

	@Override
	protected void onReset() {
		super.onReset();
		activityComponent = null;
	}
}
