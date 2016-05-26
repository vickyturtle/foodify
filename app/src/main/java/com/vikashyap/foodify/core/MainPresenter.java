package com.vikashyap.foodify.core;

/**
 * Created by Vikas on 5/23/2016.
 * copyright © Fueled
 */
public interface MainPresenter extends Presenter<MainScene> {

	void searchFor(String query);

	void searchCancelled();
}
