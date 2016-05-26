package com.vikashyap.foodify.dagger;

import android.app.Application;

import com.vikashyap.foodify.logic.DbManager;
import com.vikashyap.foodify.logic.FoodService;

import javax.inject.Singleton;

import dagger.Component;
import rx.Scheduler;

/**
 * Created by Vikas on 5/23/2016.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

	FoodService getFoodService();

	Application getApplication();

	DbManager getDbManager();

	@IoSched
	Scheduler getIoScheduler();

	@UiSched
	Scheduler getUiScheduler();
}
