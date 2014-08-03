package com.dhongchuan.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.dhongchuan.data.BaseTask;
import com.dhongchuan.data.WorkQueue;

//任务监视
public class WorkQueueMonitor implements Runnable{
	private WorkQueue workingQueue;
	@SuppressLint("UseSparseArrays") private Map<Integer, DoneProcess> doneProcessMap = new HashMap<Integer, DoneProcess>();
	private DoingProcess doingProcess;
	private Boolean stopFlag = false;
	private int monitorType;
	private Thread thread;
	private Activity activity;
	
	public WorkQueueMonitor (Activity activity, WorkQueue workqueue, DoingProcess doingProcess, int monitorType){
		this.activity = activity;
		this.workingQueue = workqueue;
		this.doingProcess = doingProcess;
		this.monitorType = monitorType;
	}
	
	public void addDoneProcess(int type, DoneProcess doneProcess){
		if(doneProcess != null)
			doneProcessMap.put(type, doneProcess);
	}
	
	//任务完成后，处理剩余的工作，主要执行doneProcess
	@SuppressLint("HandlerLeak") 
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			BaseTask baseTask = (BaseTask) msg.obj;
			
			//如果任务本身有doneProcess方法，执行此方法
			if(baseTask != null && baseTask.doneProcess != null)
				baseTask.doneProcess.doneProcess(baseTask);
			else//否则执行由构造方法传入的
			{
				Collection<DoneProcess> doneProcesses = doneProcessMap.values();
				for(DoneProcess doneProcess : doneProcesses){
					doneProcess.doneProcess(baseTask);
				}
			}
		}
	};
	
	//启动监视线程
	public void start(){
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}
	
	//停止线程
	public void stop(){
		stopFlag = true;
	}
	
	//扫描文件下载任务队列
	private void imageQueueScan(){
		List<String> fileDoingList = workingQueue.getDownFileUrls();
		while(fileDoingList != null){
			try {
				//处理文件下载任务
				doingProcess.doingProcess(fileDoingList);
				//在Handler中完成收尾工作
				handler.sendEmptyMessage(0);
			} catch (Exception e) {
				// TODO: handle exception
			}finally{
				//删除处理完的任务
				workingQueue.removeFileDownUrl(fileDoingList);
			}
			fileDoingList = workingQueue.getDownFileUrls();
		}
	}
	
	//扫描通用任务
	private void taskScan(){
		List<BaseTask> taskList = workingQueue.getDoingTask();
		while(taskList != null){
			try {
				doingProcess.doingProcess(taskList);
				Message msg = new Message();
				if(taskList.size() > 0){
					msg.obj = taskList.get(0);
					handler.sendMessage(msg);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}finally{
				//删除任务
				workingQueue.removeTast(taskList);
			}
			taskList = workingQueue.getDoingTask();
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!stopFlag){
			//处理文件下载任务
			if(monitorType == 1)
				imageQueueScan();
			else if(monitorType == 2)
				taskScan();
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
