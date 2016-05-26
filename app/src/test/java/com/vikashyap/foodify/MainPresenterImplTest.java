package com.vikashyap.foodify;

import com.vikashyap.foodify.card.FoodCardPresenter;
import com.vikashyap.foodify.core.DialogData;
import com.vikashyap.foodify.core.MainScene;
import com.vikashyap.foodify.entity.TFood;
import com.vikashyap.foodify.entity.TFoodResponse;
import com.vikashyap.foodify.logic.DbManager;
import com.vikashyap.foodify.logic.FoodService;
import com.vikashyap.foodify.logic.MainPresenterImpl;
import com.vikashyap.foodify.model.Food;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Vikas on 5/26/2016.
 * copyright © Fueled
 */
public class MainPresenterImplTest extends TestCase {

	@Mock FoodService foodService;
	@Mock DbManager dbManager;
	@Mock MainScene mainScene;

	private MainPresenterImpl presenter;
	private TestScheduler scheduler = Schedulers.test();

	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		presenter = new MainPresenterImpl(foodService, dbManager, scheduler, scheduler);
		when(foodService.searhFood(anyString())).thenReturn(Observable.<TFoodResponse>empty());
	}

	@Test
	public void testThrottle() {
		presenter.searchFor("te");
		presenter.searchFor("tes");
		presenter.searchFor("test");
		verify(foodService, times(0)).searhFood(eq("te"));
		verify(foodService, times(0)).searhFood(eq("tes"));
		scheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS);
		verify(foodService, times(1)).searhFood(eq("test"));
	}

	@Test
	public void testOnStart() {
		presenter.searchFor("test");
		scheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS);
		presenter.onSceneAdded(mainScene);
		verify(mainScene, times(0)).showFood(anyListOf(Food.class), any(FoodCardPresenter.class));
	}
}
