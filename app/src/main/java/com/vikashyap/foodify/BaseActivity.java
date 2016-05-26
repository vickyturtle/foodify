package com.vikashyap.foodify;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.vikashyap.foodify.core.DialogData;
import com.vikashyap.foodify.core.Presenter;
import com.vikashyap.foodify.core.Scene;
import com.vikashyap.foodify.dagger.ActivityComponent;
import com.vikashyap.foodify.dagger.SceneComponentLoader;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Vikas on 5/23/2016.
 */
public abstract class BaseActivity<T extends Presenter> extends AppCompatActivity
		implements Scene, LoaderManager.LoaderCallbacks<ActivityComponent> {

	@Nullable
	@BindView(R.id.toolbar) Toolbar toolbar;
	@Inject
	protected T presenter;
	protected ActivityComponent activityComponent;
	private Dialog dialog;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportLoaderManager().initLoader(0x11, null, this);
		setUpView(savedInstanceState);
		if(toolbar != null) {
			setSupportActionBar(toolbar);
		}
	}

	protected abstract void setUpView(Bundle savedInstanceState);

	@Override
	protected void onStart() {
		super.onStart();
		presenter.onSceneAdded(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		presenter.onSceneRemoved();
	}

	@Override
	public void displayDialog(DialogData data) {
		String message = data.messageResId > 0 ? getString(data.messageResId) : data.message;
		switch (data.type) {
			case ALERT:
				AlertDialog.Builder builder = new AlertDialog.Builder(this)
						.setMessage(message)
						.setPositiveButton(android.R.string.ok, null);
				if (data.titleRestId > 0) {
					builder.setTitle(data.titleRestId);
				}
				dialog = builder.create();
				dialog.show();
				break;

			case PROGRESS:
				dialog = new ProgressDialog(this);
				dialog.setCancelable(data.isDismissible);
				((ProgressDialog) dialog).setMessage(message);
				if (data.titleRestId > 0) {
					((ProgressDialog) dialog).setTitle(getString(data.titleRestId));
				}
				dialog.show();
				break;

			default:
		}
	}

	@Override
	public void hideDialog() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	@Override
	public Loader<ActivityComponent> onCreateLoader(int id, Bundle args) {
		return new SceneComponentLoader(this);
	}

	@Override
	public void onLoadFinished(Loader<ActivityComponent> loader, ActivityComponent data) {
		if (activityComponent == null) {
			activityComponent = data;
			injectFromComponent(activityComponent);
			presenter.init(getIntent().getExtras());
		}
	}

	protected abstract void injectFromComponent(ActivityComponent activityComponent);

	@Override
	public void onLoaderReset(Loader<ActivityComponent> loader) {
		activityComponent = null;
	}

}
