package com.vikashyap.foodify.core;

/**
 * Created by Vikas on 5/23/2016.
 * Data class for all alert dialogs to be used in app
 */
public class DialogData {
	public enum Type {
		ALERT, PROGRESS
	}

	public DialogData(Type type) {
		this.type = type;
	}

	public Type type;
	public int messageResId;
	public String message;
	public int titleRestId;
	public boolean isDismissible = true;
}
