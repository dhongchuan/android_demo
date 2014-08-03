package com.dhongchuan.data;

import java.util.ArrayList;
import java.util.List;

public class WorkQueue {
	private List<String> fileDownList = new ArrayList<String>();
	private List<BaseTask> taskList = new ArrayList<BaseTask>();//通用任务
	
	public List<String> getDownFileUrls(){
		synchronized (fileDownList) {
			if(fileDownList.size() > 0){
				List<String> resultList = new ArrayList<String>();
				resultList.addAll(fileDownList);
				return resultList;
			}else
				return null;
		}
	}
	
	//删除队列中的任务
	public void removeFileDownUrl(List<String> deleteList){
		synchronized (deleteList) {
			if(deleteList != null)
				fileDownList.remove(deleteList);
		}
	}
	
	//添加任务
	public void addFileDownList(String fileUrl) {
		synchronized (fileUrl) {
			fileDownList.add(fileUrl);
		}
	}
	
	//获取一个通用任务
	public List<BaseTask> getDoingTask(){
		synchronized (taskList) {
			List<BaseTask> result = new ArrayList<BaseTask>();
			if(taskList.size() > 0){
				result.add((BaseTask) taskList.get(0));
				return result;
			}else
				return null;
		}
	}
	
	//删除通用任务
	public void removeTast(List<?> tasks){
		synchronized (taskList) {
			if(taskList.size() > 0)
				taskList.removeAll(tasks);
		}
	}
	
	//添加任务
	public void addTask(BaseTask task){
		synchronized (taskList) {
			taskList.add(task);
		}
	}
}
