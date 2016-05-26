package com.vikashyap.foodify.logic;

import android.os.Bundle;
import android.support.annotation.CallSuper;

import com.vikashyap.foodify.R;
import com.vikashyap.foodify.core.DialogData;
import com.vikashyap.foodify.core.Logger;
import com.vikashyap.foodify.core.Presenter;
import com.vikashyap.foodify.core.Scene;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by Vikas on 5/23/2016.
 * Base class for the Presenter Implementations
 */
public abstract class PresenterImpl<T extends Scene> implements Presenter<T> {


	protected T scene;
	protected Logger logger = Logger.getLogger(getClass());

	@CallSuper
	@Override
	public void onSceneAdded(T scene) {
		this.scene = scene;
	}

	@Override
	@CallSuper
	public void onSceneRemoved() {
		scene = null;
	}

	@Override
	public void init(Bundle extras) {

	}

	protected boolean handleError(Throwable throwable) {
		logger.warn(throwable);

		if (this.scene == null) {
			logger.debug("Show retrofit error -> Scene is null");
			return false;
		}
		scene.hideDialog();

		DialogData data = new DialogData(DialogData.Type.ALERT);
		data.titleRestId = R.string.error;

		if (throwable instanceof HttpException) {
			data.message = getErrorMessage((HttpException) throwable);
			if (data.message == null) {
				data.messageResId = R.string.error_unknown;
			}
		} else if (throwable instanceof UnknownHostException) {
			data.titleRestId = R.string.error_no_network_title;
			data.messageResId = R.string.error_no_network;
		} else if (throwable instanceof SocketTimeoutException) {
			data.titleRestId = R.string.error_timeout_title;
			data.messageResId = R.string.error_timeout;
		} else {
			data.messageResId = R.string.error_unknown;
		}
		scene.displayDialog(data);
		return true;
	}

	private String getErrorMessage(HttpException throwable) {
		String message = null;
		try {
			message = throwable.response().errorBody().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

}
