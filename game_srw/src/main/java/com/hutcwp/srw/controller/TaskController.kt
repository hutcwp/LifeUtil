package com.hutcwp.srw.controller

import me.hutcwp.log.MLog
import java.util.*

/**
 *  author : kevin
 *  date : 2022/3/27 1:12 AM
 *  description : 任务管理器。任务顺序执行
 */
class TaskController {

    private val TAG = "TaskController"

    private val queue: Deque<IRealTask> = LinkedList<IRealTask>()

    @Volatile
    private var taskRunning = false //是否有任务正在执行


    fun createTask(callback: ITask): IRealTask {
        return object : IRealTask {
            override fun start() {
                MLog.info(TAG,"${this@TaskController} taskRunning -> true")
                taskRunning = true
                callback.start()
            }

            override fun end() {
                MLog.info(TAG,"${this@TaskController} taskRunning -> true")
                taskRunning = false
                callback.end()
            }
        }
    }

    fun addTask(task: IRealTask, insertHead: Boolean = false) {
        queue.addLast(task)
    }

    fun taskFinish() {
        taskRunning = false
        MLog.info(TAG,"${this@TaskController} taskRunning -> false")
    }

    fun isEmpty(): Boolean {
        return queue.isEmpty()
    }

    @Synchronized
    fun runTask() {
        if (taskRunning) {
            return
        }

        if (queue.isNotEmpty()) {
            val task = queue.pollFirst()
            task?.start()
        }
    }
}


/**
 * 任务接口，给外部用
 */
interface ITask {
    fun start()
    fun end()
}

interface IRealTask : ITask