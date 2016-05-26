package com.vikashyap.foodify.core;

import android.os.Bundle;

/**
 * Created by Vikas on 5/23/2016.
 * copyright © Fueled
 */
public interface Presenter<T extends Scene> {
	void onSceneAdded(T scene);

	void onSceneRemoved();

	void init(Bundle extras);
}
