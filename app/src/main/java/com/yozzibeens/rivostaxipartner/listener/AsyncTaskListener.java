package com.yozzibeens.rivostaxipartner.listener;

import java.util.HashMap;


public interface AsyncTaskListener {
	void onTaskStart();
    void onTaskDownloadedFinished(HashMap<String, Object> result);
    void onTaskUpdate(String result);
	void onTaskComplete(HashMap<String, Object> result);
	void onTaskCancelled(HashMap<String, Object> result);
}
