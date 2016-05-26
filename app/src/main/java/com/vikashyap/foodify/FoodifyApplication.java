package com.vikashyap.foodify;

import android.app.Application;

import com.vikashyap.foodify.core.Logger;
import com.vikashyap.foodify.dagger.AppComponent;
import com.vikashyap.foodify.dagger.AppModule;
import com.vikashyap.foodify.dagger.DaggerAppComponent;

/**
 * Created by Vikas on 5/23/2016.
 */
public class FoodifyApplication extends Application {

	private AppComponent component;
	private final Logger logger = Logger.getLogger(getClass());

	@Override
	public void onCreate() {
		super.onCreate();
		long time = System.currentTimeMillis();
		component = DaggerAppComponent.builder()
				.appModule(new AppModule(this))
				.build();
		logger.debug("Time for create :%d ms ", (System.currentTimeMillis() - time));
	}

	public AppComponent getComponent() {
		return component;
	}
}
