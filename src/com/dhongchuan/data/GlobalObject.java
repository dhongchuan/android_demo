package com.dhongchuan.data;

import com.dhongchuan.util.WorkQueueMonitor;

public class GlobalObject {
	private WorkQueue workQueue;
	private WorkQueueMonitor imageWorkQueueMonitor;
	
	public WorkQueue getWorkQueue(){
		if(workQueue == null)
			workQueue = new WorkQueue();
		return workQueue;
	}
	
	public WorkQueueMonitor getImageWorkQueueMonitor(){
		if(imageWorkQueueMonitor == null){
//			imageWorkQueueMonitor = new WorkQueueMonitor(activity, workqueue, doingProcess, monitorType);
		}
		return null;
	}
}
