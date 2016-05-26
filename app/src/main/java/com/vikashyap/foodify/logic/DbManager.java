package com.vikashyap.foodify.logic;

import android.app.Application;
import android.support.annotation.NonNull;

import com.vikashyap.foodify.model.Food;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Vikas on 5/25/2016.
 */
@Singleton
public class DbManager {

	public static final String CACHE_DB = "cache_db";
	public static final int CACHE_SCHEMA_VERSION = 1;
	public static final String DATA_DB = "data_db";
	public static final int DATA_SCHEMA_VERSION = 1;

	private final RealmConfiguration cacheConfig;

	@Inject
	public DbManager(Application application) {
		cacheConfig = getCacheConfig(application);
		RealmConfiguration config = new RealmConfiguration.Builder(application)
				.name(DATA_DB)
				.schemaVersion(DATA_SCHEMA_VERSION)
				.deleteRealmIfMigrationNeeded()
				.build();

		Realm.setDefaultConfiguration(config);

		doHouseCleaning();
	}

	private void doHouseCleaning() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Realm cacheRealm = getCacheRealm();
				cacheRealm.beginTransaction();
				cacheRealm.deleteAll();
				cacheRealm.commitTransaction();
				cacheRealm.close();

				Realm realm = Realm.getDefaultInstance();
				realm.beginTransaction();
				realm.where(Food.class)
						.equalTo("isFavorite", false)
						.findAll()
						.deleteAllFromRealm();
				realm.commitTransaction();
				realm.close();

			}
		}).start();
	}

	public Realm getDataRealm() {
		return Realm.getDefaultInstance();
	}

	public Realm getCacheRealm() {
		return Realm.getInstance(cacheConfig);
	}

	@NonNull
	private RealmConfiguration getCacheConfig(Application application) {
		return new RealmConfiguration.Builder(application)
				.name(CACHE_DB)
				.schemaVersion(CACHE_SCHEMA_VERSION)
				.deleteRealmIfMigrationNeeded()
				.build();
	}
}
