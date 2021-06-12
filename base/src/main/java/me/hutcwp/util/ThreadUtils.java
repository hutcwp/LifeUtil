package me.hutcwp.util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import androidx.annotation.CallSuper;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/05/08
 *     desc  : utils about thread
 * </pre>
 * 线程工具类
 * Threading tool class
 */
public final class ThreadUtils {

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    private static final Map<Integer, Map<Integer, ExecutorService>> TYPE_PRIORITY_POOLS = new HashMap<>();

    private static final Map<Task, ExecutorService> TASK_POOL_MAP = new ConcurrentHashMap<>();

    private static final int   CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final Timer TIMER     = new Timer();

    private static final byte TYPE_SINGLE = -1;
    private static final byte TYPE_CACHED = -2;
    private static final byte TYPE_IO     = -4;
    private static final byte TYPE_CPU    = -8;

    private static Executor sDeliver;

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * Gets main handler.
     *获得主handler
     * @return the main handler 主handler
     */
    public static Handler getMainHandler() {
        return HANDLER;
    }

    /**
     * Run on ui thread.
     * 在ui线程上运行
     * @param runnable the runnable 运行
     */
    public static void runOnUiThread(final Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            HANDLER.post(runnable);
        }
    }

    /**
     * Run on ui thread delayed.
     * 运行在ui线程延迟
     * @param runnable    the runnable 运行
     * @param delayMillis the delay millis 延迟
     */
    public static void runOnUiThreadDelayed(final Runnable runnable, long delayMillis) {
        HANDLER.postDelayed(runnable, delayMillis);
    }

    /**
     * Return a thread pool that reuses a fixed number of threads
     * operating off a shared unbounded queue, using the provided
     * ThreadFactory to create new threads when needed.
     *
     * 返回一个重用固定数目线程的线程池
     * 操作一个共享的无界队列，使用提供的
     * ThreadFactory在需要时创建新线程。
     *
     * @param size The size of thread in the pool. 池中线程的大小
     * @return a fixed thread pool 固定线程池
     */
    public static ExecutorService getFixedPool(@IntRange(from = 1) final int size) {
        return getPoolByTypeAndPriority(size);
    }

    /**
     * Return a thread pool that reuses a fixed number of threads
     * operating off a shared unbounded queue, using the provided
     * ThreadFactory to create new threads when needed.
     * 返回一个重用固定数目线程的线程池
     * 操作一个共享的无界队列，使用提供的
     *  ThreadFactory在需要时创建新线程。
     * @param size     The size of thread in the pool. 池中线程的大小
     * @param priority The priority of thread in the poll. 轮询中线程的优先级
     * @return a fixed thread pool 固定线程池
     */
    public static ExecutorService getFixedPool(@IntRange(from = 1) final int size,
                                               @IntRange(from = 1, to = 10) final int priority) {
        return getPoolByTypeAndPriority(size, priority);
    }

    /**
     * Return a thread pool that uses a single worker thread operating
     * off an unbounded queue, and uses the provided ThreadFactory to
     * create a new thread when needed.
     * 返回使用单个工作线程操作的线程池
     * 关闭一个未绑定队列，并使用所提供的线程工厂来
     * 在需要时创建一个新线程。
     * @return a single thread pool 单线程池
     */
    public static ExecutorService getSinglePool() {
        return getPoolByTypeAndPriority(TYPE_SINGLE);
    }

    public static ExecutorService getSinglePool(@IntRange(from = 1, to = 10) final int priority) {
        return getPoolByTypeAndPriority(TYPE_SINGLE, priority);
    }

    public static ExecutorService getCachedPool() {
        return getPoolByTypeAndPriority(TYPE_CACHED);
    }

    public static ExecutorService getCachedPool(@IntRange(from = 1, to = 10) final int priority) {
        return getPoolByTypeAndPriority(TYPE_CACHED, priority);
    }

    /**
     * Return a thread pool that creates (2 * CPU_COUNT + 1) threads
     * operating off a queue which size is 128.
     * 返回创建(2 * CPU_COUNT + 1)线程的线程池
     * 操作大小为128的队列。
     * @return a IO thread pool IO线程池
     */
    public static ExecutorService getIoPool() {
        return getPoolByTypeAndPriority(TYPE_IO);
    }

    public static ExecutorService getIoPool(@IntRange(from = 1, to = 10) final int priority) {
        return getPoolByTypeAndPriority(TYPE_IO, priority);
    }

    /**
     * Return a thread pool that creates (CPU_COUNT + 1) threads
     * operating off a queue which size is 128 and the maximum
     * number of threads equals (2 * CPU_COUNT + 1).
     * 返回创建(CPU_COUNT + 1)线程的线程池
     * 操作大小为128的最大队列
     * 线程数等于(2 * CPU_COUNT + 1)
     * @return a cpu thread pool for 一个cpu线程池
     */
    public static ExecutorService getCpuPool() {
        return getPoolByTypeAndPriority(TYPE_CPU);
    }

    public static ExecutorService getCpuPool(@IntRange(from = 1, to = 10) final int priority) {
        return getPoolByTypeAndPriority(TYPE_CPU, priority);
    }

    /**
     * Executes the given task in a fixed thread pool.
     * 在固定的线程池中执行给定的任务
     * @param <T>  The type of the task's result. 任务结果的类型
     * @param size The size of thread in the fixed thread pool. 固定线程池中线程的大小
     * @param task The task to execute. 要执行的任务
     */
    public static <T> void executeByFixed(@IntRange(from = 1) final int size, final Task<T> task) {
        execute(getPoolByTypeAndPriority(size), task);
    }

    public static <T> void executeByFixed(@IntRange(from = 1) final int size,
                                          final Task<T> task,
                                          @IntRange(from = 1, to = 10) final int priority) {
        execute(getPoolByTypeAndPriority(size, priority), task);
    }

    /**
     * Executes the given task in a fixed thread pool after the given delay.
     * 在给定的延迟之后，在固定的线程池中执行给定的任务
     * @param <T>   The type of the task's result. 任务结果的类型
     * @param size  The size of thread in the fixed thread pool. 固定线程池中线程的大小
     * @param task  The task to execute. 要执行的任务
     * @param delay The time from now to delay execution. 从现在开始延迟执行的时间
     * @param unit  The time unit of the delay parameter. 延迟参数的时间单位
     */
    public static <T> void executeByFixedWithDelay(@IntRange(from = 1) final int size,
                                                   final Task<T> task,
                                                   final long delay,
                                                   final TimeUnit unit) {
        executeWithDelay(getPoolByTypeAndPriority(size), task, delay, unit);
    }

    public static <T> void executeByFixedWithDelay(@IntRange(from = 1) final int size,
                                                   final Task<T> task,
                                                   final long delay,
                                                   final TimeUnit unit,
                                                   @IntRange(from = 1, to = 10) final int priority) {
        executeWithDelay(getPoolByTypeAndPriority(size, priority), task, delay, unit);
    }

    /**
     * Executes the given task in a fixed thread pool at fix rate.
     * 在固定的线程池中以固定的速率执行给定的任务
     * @param <T>    The type of the task's result. 任务结果的类型
     * @param size   The size of thread in the fixed thread pool. 固定线程池中线程的大小
     * @param task   The task to execute. 要执行的任务
     * @param period The period between successive executions. 两次执行之间的时间间隔
     * @param unit   The time unit of the period parameter. 周期参数的时间单位
     */
    public static <T> void executeByFixedAtFixRate(@IntRange(from = 1) final int size,
                                                   final Task<T> task,
                                                   final long period,
                                                   final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(size), task, 0, period, unit);
    }

    public static <T> void executeByFixedAtFixRate(@IntRange(from = 1) final int size,
                                                   final Task<T> task,
                                                   final long period,
                                                   final TimeUnit unit,
                                                   @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(getPoolByTypeAndPriority(size, priority), task, 0, period, unit);
    }

    public static <T> void executeByFixedAtFixRate(@IntRange(from = 1) final int size,
                                                   final Task<T> task,
                                                   long initialDelay,
                                                   final long period,
                                                   final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(size), task, initialDelay, period, unit);
    }

    public static <T> void executeByFixedAtFixRate(@IntRange(from = 1) final int size,
                                                   final Task<T> task,
                                                   long initialDelay,
                                                   final long period,
                                                   final TimeUnit unit,
                                                   @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(getPoolByTypeAndPriority(size, priority), task, initialDelay, period, unit);
    }

    /**
     * Executes the given task in a single thread pool.
     * 在单个线程池中执行给定的任务
     * @param <T>  The type of the task's result. 任务结果的类型
     * @param task The task to execute. 要执行的任务
     */
    public static <T> void executeBySingle(final Task<T> task) {
        execute(getPoolByTypeAndPriority(TYPE_SINGLE), task);
    }

    public static <T> void executeBySingle(final Task<T> task,
                                           @IntRange(from = 1, to = 10) final int priority) {
        execute(getPoolByTypeAndPriority(TYPE_SINGLE, priority), task);
    }

    /**
     * Executes the given task in a single thread pool after the given delay.
     * 在给定的延迟之后，在单个线程池中执行给定的任务
     * @param <T>   The type of the task's result. 任务结果的类型
     * @param task  The task to execute. 要执行的任务
     * @param delay The time from now to delay execution. 从现在开始延迟执行的时间
     * @param unit  The time unit of the delay parameter. 延迟参数的时间单位
     */
    public static <T> void executeBySingleWithDelay(final Task<T> task,
                                                    final long delay,
                                                    final TimeUnit unit) {
        executeWithDelay(getPoolByTypeAndPriority(TYPE_SINGLE), task, delay, unit);
    }

    public static <T> void executeBySingleWithDelay(final Task<T> task,
                                                    final long delay,
                                                    final TimeUnit unit,
                                                    @IntRange(from = 1, to = 10) final int priority) {
        executeWithDelay(getPoolByTypeAndPriority(TYPE_SINGLE, priority), task, delay, unit);
    }

    public static <T> void executeBySingleAtFixRate(final Task<T> task,
                                                    final long period,
                                                    final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_SINGLE), task, 0, period, unit);
    }

    public static <T> void executeBySingleAtFixRate(final Task<T> task,
                                                    final long period,
                                                    final TimeUnit unit,
                                                    @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_SINGLE, priority), task, 0, period, unit);
    }

    public static <T> void executeBySingleAtFixRate(final Task<T> task,
                                                    long initialDelay,
                                                    final long period,
                                                    final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_SINGLE), task, initialDelay, period, unit);
    }

    public static <T> void executeBySingleAtFixRate(final Task<T> task,
                                                    long initialDelay,
                                                    final long period,
                                                    final TimeUnit unit,
                                                    @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(
                getPoolByTypeAndPriority(TYPE_SINGLE, priority), task, initialDelay, period, unit
        );
    }

    /**
     * Executes the given task in a cached thread pool.
     * 在一个缓存的线程便便中执行给定的任务
     * @param <T>  The type of the task's result. 任务结果的类型
     * @param task The task to execute. 要执行的任务
     */
    public static <T> void executeByCached(final Task<T> task) {
        execute(getPoolByTypeAndPriority(TYPE_CACHED), task);
    }

    public static <T> void executeByCached(final Task<T> task,
                                           @IntRange(from = 1, to = 10) final int priority) {
        execute(getPoolByTypeAndPriority(TYPE_CACHED, priority), task);
    }

    /**
     * Executes the given task in a cached thread pool after the given delay.
     * 在给定的延迟之后，在缓存的线程池中执行给定的任务
     * @param <T>   The type of the task's result. 任务结果的类型
     * @param task  The task to execute. 要执行的任务
     * @param delay The time from now to delay execution. 从现在开始延迟执行的时间
     * @param unit  The time unit of the delay parameter. 延迟参数的时间单位
     */
    public static <T> void executeByCachedWithDelay(final Task<T> task,
                                                    final long delay,
                                                    final TimeUnit unit) {
        executeWithDelay(getPoolByTypeAndPriority(TYPE_CACHED), task, delay, unit);
    }

    public static <T> void executeByCachedWithDelay(final Task<T> task,
                                                    final long delay,
                                                    final TimeUnit unit,
                                                    @IntRange(from = 1, to = 10) final int priority) {
        executeWithDelay(getPoolByTypeAndPriority(TYPE_CACHED, priority), task, delay, unit);
    }

    public static <T> void executeByCachedAtFixRate(final Task<T> task,
                                                    final long period,
                                                    final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_CACHED), task, 0, period, unit);
    }

    public static <T> void executeByCachedAtFixRate(final Task<T> task,
                                                    final long period,
                                                    final TimeUnit unit,
                                                    @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_CACHED, priority), task, 0, period, unit);
    }

    public static <T> void executeByCachedAtFixRate(final Task<T> task,
                                                    long initialDelay,
                                                    final long period,
                                                    final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_CACHED), task, initialDelay, period, unit);
    }

    public static <T> void executeByCachedAtFixRate(final Task<T> task,
                                                    long initialDelay,
                                                    final long period,
                                                    final TimeUnit unit,
                                                    @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(
                getPoolByTypeAndPriority(TYPE_CACHED, priority), task, initialDelay, period, unit
        );
    }

    /**
     * Executes the given task in an IO thread pool.
     * 在IO线程池中执行给定的任务
     * @param <T>  The type of the task's result. 任务结果的类型
     * @param task The task to execute. 要执行的任务
     */
    public static <T> void executeByIo(final Task<T> task) {
        execute(getPoolByTypeAndPriority(TYPE_IO), task);
    }

    public static <T> void executeByIo(final Task<T> task,
                                       @IntRange(from = 1, to = 10) final int priority) {
        execute(getPoolByTypeAndPriority(TYPE_IO, priority), task);
    }

    public static <T> void executeByIoWithDelay(final Task<T> task,
                                                final long delay,
                                                final TimeUnit unit) {
        executeWithDelay(getPoolByTypeAndPriority(TYPE_IO), task, delay, unit);
    }

    public static <T> void executeByIoWithDelay(final Task<T> task,
                                                final long delay,
                                                final TimeUnit unit,
                                                @IntRange(from = 1, to = 10) final int priority) {
        executeWithDelay(getPoolByTypeAndPriority(TYPE_IO, priority), task, delay, unit);
    }

    public static <T> void executeByIoAtFixRate(final Task<T> task,
                                                final long period,
                                                final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_IO), task, 0, period, unit);
    }

    public static <T> void executeByIoAtFixRate(final Task<T> task,
                                                final long period,
                                                final TimeUnit unit,
                                                @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_IO, priority), task, 0, period, unit);
    }

    public static <T> void executeByIoAtFixRate(final Task<T> task,
                                                long initialDelay,
                                                final long period,
                                                final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_IO), task, initialDelay, period, unit);
    }

    public static <T> void executeByIoAtFixRate(final Task<T> task,
                                                long initialDelay,
                                                final long period,
                                                final TimeUnit unit,
                                                @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(
                getPoolByTypeAndPriority(TYPE_IO, priority), task, initialDelay, period, unit
        );
    }

    public static <T> void executeByCpu(final Task<T> task) {
        execute(getPoolByTypeAndPriority(TYPE_CPU), task);
    }

    /**
     * Executes the given task in a cpu thread pool.
     * 在cpu线程池中执行给定的任务
     * @param <T>      The type of the task's result. 任务结果的类型
     * @param task     The task to execute. 要执行的任务
     * @param priority The priority of thread in the poll. 轮询中线程的优先级
     */
    public static <T> void executeByCpu(final Task<T> task,
                                        @IntRange(from = 1, to = 10) final int priority) {
        execute(getPoolByTypeAndPriority(TYPE_CPU, priority), task);
    }

    public static <T> void executeByCpuWithDelay(final Task<T> task,
                                                 final long delay,
                                                 final TimeUnit unit) {
        executeWithDelay(getPoolByTypeAndPriority(TYPE_CPU), task, delay, unit);
    }

    public static <T> void executeByCpuWithDelay(final Task<T> task,
                                                 final long delay,
                                                 final TimeUnit unit,
                                                 @IntRange(from = 1, to = 10) final int priority) {
        executeWithDelay(getPoolByTypeAndPriority(TYPE_CPU, priority), task, delay, unit);
    }

    public static <T> void executeByCpuAtFixRate(final Task<T> task,
                                                 final long period,
                                                 final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_CPU), task, 0, period, unit);
    }

    public static <T> void executeByCpuAtFixRate(final Task<T> task,
                                                 final long period,
                                                 final TimeUnit unit,
                                                 @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_CPU, priority), task, 0, period, unit);
    }

    /**
     * Executes the given task in a cpu thread pool at fix rate.
     * 以固定的速率在cpu线程池中执行给定的任务
     * @param <T>          The type of the task's result. 任务结果的类型
     * @param task         The task to execute. 要执行的任务
     * @param initialDelay The time to delay first execution. 延迟第一次执行的时间
     * @param period       The period between successive executions. 两次执行之间的时间间隔
     * @param unit         The time unit of the initialDelay and period parameters. 初始延迟和周期参数的时间单位
     */
    public static <T> void executeByCpuAtFixRate(final Task<T> task,
                                                 long initialDelay,
                                                 final long period,
                                                 final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_CPU), task, initialDelay, period, unit);
    }

    public static <T> void executeByCpuAtFixRate(final Task<T> task,
                                                 long initialDelay,
                                                 final long period,
                                                 final TimeUnit unit,
                                                 @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(
                getPoolByTypeAndPriority(TYPE_CPU, priority), task, initialDelay, period, unit
        );
    }

    public static <T> void executeByCustom(final ExecutorService pool, final Task<T> task) {
        execute(pool, task);
    }

    public static <T> void executeByCustomWithDelay(final ExecutorService pool,
                                                    final Task<T> task,
                                                    final long delay,
                                                    final TimeUnit unit) {
        executeWithDelay(pool, task, delay, unit);
    }

    public static <T> void executeByCustomAtFixRate(final ExecutorService pool,
                                                    final Task<T> task,
                                                    final long period,
                                                    final TimeUnit unit) {
        executeAtFixedRate(pool, task, 0, period, unit);
    }

    public static <T> void executeByCustomAtFixRate(final ExecutorService pool,
                                                    final Task<T> task,
                                                    long initialDelay,
                                                    final long period,
                                                    final TimeUnit unit) {
        executeAtFixedRate(pool, task, initialDelay, period, unit);
    }

    /**
     * Cancel the given task.
     * 取消给定的任务
     * @param task The task to cancel. 要取消的任务
     */
    public static void cancel(final Task task) {
        if (task == null) {
            return;
        }
        task.cancel();
    }

    /**
     * Cancel the given tasks.
     * 取消给定的任务
     * @param tasks The tasks to cancel. 要取消的任务
     */
    public static void cancel(final Task... tasks) {
        if (tasks == null || tasks.length == 0) {
            return;
        }
        for (Task task : tasks) {
            if (task == null) {
                continue;
            }
            task.cancel();
        }
    }

    public static void cancel(final List<Task> tasks) {
        if (tasks == null || tasks.size() == 0) {
            return;
        }
        for (Task task : tasks) {
            if (task == null) {
                continue;
            }
            task.cancel();
        }
    }

    public static void cancel(ExecutorService executorService) {
        if (executorService instanceof ThreadPoolExecutor4Util) {
            for (Map.Entry<Task, ExecutorService> taskTaskInfoEntry : TASK_POOL_MAP.entrySet()) {
                if (taskTaskInfoEntry.getValue() == executorService) {
                    cancel(taskTaskInfoEntry.getKey());
                }
            }
        } else {
            Log.e("ThreadUtils", "The executorService is not ThreadUtils's pool.");
        }
    }

    public static void setDeliver(final Executor deliver) {
        sDeliver = deliver;
    }

    private static <T> void execute(final ExecutorService pool, final Task<T> task) {
        execute(pool, task, 0, 0, null);
    }

    private static <T> void executeWithDelay(final ExecutorService pool,
                                             final Task<T> task,
                                             final long delay,
                                             final TimeUnit unit) {
        execute(pool, task, delay, 0, unit);
    }

    private static <T> void executeAtFixedRate(final ExecutorService pool,
                                               final Task<T> task,
                                               long delay,
                                               final long period,
                                               final TimeUnit unit) {
        execute(pool, task, delay, period, unit);
    }

    private static <T> void execute(final ExecutorService pool, final Task<T> task,
                                    long delay, final long period, final TimeUnit unit) {
        synchronized (TASK_POOL_MAP) {
            if (TASK_POOL_MAP.get(task) != null) {
                Log.e("ThreadUtils", "Task can only be executed once.");
                return;
            }
            TASK_POOL_MAP.put(task, pool);
        }
        if (period == 0) {
            if (delay == 0) {
                pool.execute(task);
            } else {
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        pool.execute(task);
                    }
                };
                TIMER.schedule(timerTask, unit.toMillis(delay));
            }
        } else {
            task.setSchedule(true);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    pool.execute(task);
                }
            };
            TIMER.scheduleAtFixedRate(timerTask, unit.toMillis(delay), unit.toMillis(period));
        }
    }

    private static ExecutorService getPoolByTypeAndPriority(final int type) {
        return getPoolByTypeAndPriority(type, Thread.NORM_PRIORITY);
    }

    private static ExecutorService getPoolByTypeAndPriority(final int type, final int priority) {
        synchronized (TYPE_PRIORITY_POOLS) {
            ExecutorService pool;
            Map<Integer, ExecutorService> priorityPools = TYPE_PRIORITY_POOLS.get(type);
            if (priorityPools == null) {
                priorityPools = new ConcurrentHashMap<>();
                pool = ThreadPoolExecutor4Util.createPool(type, priority);
                priorityPools.put(priority, pool);
                TYPE_PRIORITY_POOLS.put(type, priorityPools);
            } else {
                pool = priorityPools.get(priority);
                if (pool == null) {
                    pool = ThreadPoolExecutor4Util.createPool(type, priority);
                    priorityPools.put(priority, pool);
                }
            }
            return pool;
        }
    }

    /**
     * The type Thread pool executor 4 util.
     * 类型线程池执行器
     */
    static final class ThreadPoolExecutor4Util extends ThreadPoolExecutor {

        private static ExecutorService createPool(final int type, final int priority) {
            if (type == TYPE_SINGLE) {
                return new ThreadPoolExecutor4Util(1, 1,
                        0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue4Util(),
                        new UtilsThreadFactory("single", priority)
                );
            } else if (type == TYPE_CACHED) {
                return new ThreadPoolExecutor4Util(0, 128,
                        60L, TimeUnit.SECONDS,
                        new LinkedBlockingQueue4Util(true),
                        new UtilsThreadFactory("cached", priority)
                );
            } else if (type == TYPE_IO) {
                return new ThreadPoolExecutor4Util(2 * CPU_COUNT + 1, 2 * CPU_COUNT + 1,
                        30, TimeUnit.SECONDS,
                        new LinkedBlockingQueue4Util(),
                        new UtilsThreadFactory("io", priority)
                );
            } else if (type == TYPE_CPU) {
                return new ThreadPoolExecutor4Util(CPU_COUNT + 1, 2 * CPU_COUNT + 1,
                        30, TimeUnit.SECONDS,
                        new LinkedBlockingQueue4Util(true),
                        new UtilsThreadFactory("cpu", priority)
                );
            }
            return new ThreadPoolExecutor4Util(type, type,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue4Util(),
                    new UtilsThreadFactory("fixed(" + type + ")", priority)
            );
        }

        private final AtomicInteger mSubmittedCount = new AtomicInteger();

        private LinkedBlockingQueue4Util mWorkQueue;

        ThreadPoolExecutor4Util(int corePoolSize, int maximumPoolSize,
                                long keepAliveTime, TimeUnit unit,
                                LinkedBlockingQueue4Util workQueue,
                                ThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize,
                    keepAliveTime, unit,
                    workQueue,
                    threadFactory
            );
            workQueue.mPool = this;
            mWorkQueue = workQueue;
        }

        private int getSubmittedCount() {
            return mSubmittedCount.get();
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            mSubmittedCount.decrementAndGet();
            super.afterExecute(r, t);
        }

        @Override
        public void execute(@NonNull Runnable command) {
            if (this.isShutdown()) {
                return;
            }
            mSubmittedCount.incrementAndGet();
            try {
                super.execute(command);
            } catch (RejectedExecutionException ignore) {
                Log.e("ThreadUtils", "This will not happen!");
                mWorkQueue.offer(command);
            } catch (Throwable t) {
                mSubmittedCount.decrementAndGet();
            }
        }
    }

    private static final class LinkedBlockingQueue4Util extends LinkedBlockingQueue<Runnable> {

        private volatile ThreadPoolExecutor4Util mPool;

        private int mCapacity = Integer.MAX_VALUE;

        LinkedBlockingQueue4Util() {
            super();
        }

        LinkedBlockingQueue4Util(boolean isAddSubThreadFirstThenAddQueue) {
            super();
            if (isAddSubThreadFirstThenAddQueue) {
                mCapacity = 0;
            }
        }

        LinkedBlockingQueue4Util(int capacity) {
            super();
            mCapacity = capacity;
        }

        @Override
        public boolean offer(@NonNull Runnable runnable) {
            if (mCapacity <= size() &&
                    mPool != null && mPool.getPoolSize() < mPool.getMaximumPoolSize()) {
                // create a non-core thread
                return false;
            }
            return super.offer(runnable);
        }
    }

    /**
     * The type Utils thread factory.
     * 线程工厂工具类
     */
    static final class UtilsThreadFactory extends AtomicLong
            implements ThreadFactory {
        private static final AtomicInteger POOL_NUMBER      = new AtomicInteger(1);
        private static final long          serialVersionUID = -9209200509960368598L;
        private final String namePrefix;
        private final        int           priority;
        private final        boolean       isDaemon;

        UtilsThreadFactory(String prefix, int priority) {
            this(prefix, priority, false);
        }

        UtilsThreadFactory(String prefix, int priority, boolean isDaemon) {
            namePrefix = prefix + "-pool-" +
                    POOL_NUMBER.getAndIncrement() +
                    "-thread-";
            this.priority = priority;
            this.isDaemon = isDaemon;
        }

        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread t = new Thread(r, namePrefix + getAndIncrement()) {
                @Override
                public void run() {
                    try {
                        super.run();
                    } catch (Throwable t) {
                        Log.e("ThreadUtils", "Request threw uncaught throwable", t);
                    }
                }
            };
            t.setDaemon(isDaemon);
            t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    System.out.println(e);
                }
            });
            t.setPriority(priority);
            return t;
        }
    }

    /**
     * The type Simple task.
     * 简单任务类
     * @param <T> the type parameter 类型参数
     */
    public abstract static class SimpleTask<T> extends Task<T> {

        @Override
        public void onCancel() {
            Log.e("ThreadUtils", "onCancel: " + Thread.currentThread());
        }

        @Override
        public void onFail(Throwable t) {
            Log.e("ThreadUtils", "onFail: ", t);
        }

    }

    /**
     * The type Task.
     * 任务类
     * @param <T> the type parameter
     */
    public abstract static class Task<T> implements Runnable {

        private static final int NEW         = 0;
        private static final int RUNNING     = 1;
        private static final int EXCEPTIONAL = 2;
        private static final int COMPLETING  = 3;
        private static final int CANCELLED   = 4;
        private static final int INTERRUPTED = 5;
        private static final int TIMEOUT     = 6;

        private final AtomicInteger state = new AtomicInteger(NEW);

        private volatile boolean isSchedule;
        private volatile Thread runner;

        private Timer mTimer;
        private long              mTimeoutMillis;
        private OnTimeoutListener mTimeoutListener;

        private Executor deliver;

        public abstract T doInBackground() throws Throwable;

        public abstract void onSuccess(T result);
        public abstract void onCancel();


        public abstract void onFail(Throwable t);

        @Override
        public void run() {
            if (isSchedule) {
                if (runner == null) {
                    if (!state.compareAndSet(NEW, RUNNING)) {
                        return;
                    }
                    runner = Thread.currentThread();
                    if (mTimeoutListener != null) {
                        Log.w("ThreadUtils", "Scheduled task doesn't support timeout.");
                    }
                } else {
                    if (state.get() != RUNNING) {
                        return;
                    }
                }
            } else {
                if (!state.compareAndSet(NEW, RUNNING)) {
                    return;
                }
                runner = Thread.currentThread();
                if (mTimeoutListener != null) {
                    mTimer = new Timer();
                    mTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!isDone() && mTimeoutListener != null) {
                                timeout();
                                mTimeoutListener.onTimeout();
                            }
                        }
                    }, mTimeoutMillis);
                }
            }
            try {
                final T result = doInBackground();
                if (isSchedule) {
                    if (state.get() != RUNNING) {
                        return;
                    }
                    getDeliver().execute(new Runnable() {
                        @Override
                        public void run() {
                            onSuccess(result);
                        }
                    });
                } else {
                    if (!state.compareAndSet(RUNNING, COMPLETING)) {
                        return;
                    }
                    getDeliver().execute(new Runnable() {
                        @Override
                        public void run() {
                            onSuccess(result);
                            onDone();
                        }
                    });
                }
            } catch (InterruptedException ignore) {
                state.compareAndSet(CANCELLED, INTERRUPTED);
            } catch (final Throwable throwable) {
                if (!state.compareAndSet(RUNNING, EXCEPTIONAL)) {
                    return;
                }
                getDeliver().execute(new Runnable() {
                    @Override
                    public void run() {
                        onFail(throwable);
                        onDone();
                    }
                });
            }
        }

        /**
         * Cancel.
         * 取消
         */
        public void cancel() {
            cancel(true);
        }

        public void cancel(boolean mayInterruptIfRunning) {
            synchronized (state) {
                if (state.get() > RUNNING) {
                    return;
                }
                state.set(CANCELLED);
            }
            if (mayInterruptIfRunning) {
                if (runner != null) {
                    runner.interrupt();
                }
            }

            getDeliver().execute(new Runnable() {
                @Override
                public void run() {
                    onCancel();
                    onDone();
                }
            });
        }

        private void timeout() {
            synchronized (state) {
                if (state.get() > RUNNING) {
                    return;
                }
                state.set(TIMEOUT);
            }
            if (runner != null) {
                runner.interrupt();
            }
            onDone();
        }


        public boolean isCanceled() {
            return state.get() >= CANCELLED;
        }

        public boolean isDone() {
            return state.get() > RUNNING;
        }

        public Task<T> setDeliver(Executor deliver) {
            this.deliver = deliver;
            return this;
        }

        /**
         * Scheduled task doesn't support timeout.
         * 调度任务不支持超时
         * @param timeoutMillis the timeout  millis 超时
         * @param listener      the listener 监听
         * @return the timeout
         */
        public Task<T> setTimeout(final long timeoutMillis, final OnTimeoutListener listener) {
            mTimeoutMillis = timeoutMillis;
            mTimeoutListener = listener;
            return this;
        }

        private void setSchedule(boolean isSchedule) {
            this.isSchedule = isSchedule;
        }

        private Executor getDeliver() {
            if (deliver == null) {
                return getGlobalDeliver();
            }
            return deliver;
        }

        @CallSuper
        protected void onDone() {
            TASK_POOL_MAP.remove(this);
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
                mTimeoutListener = null;
            }
        }

        /**
         * The interface On timeout listener.
         * 监听超时接口
         */
        public interface OnTimeoutListener {
            /**
             * On timeout.
             * 超时
             */
            void onTimeout();
        }
    }

    /**
     * The type Sync value.
     * 同步值类
     * @param <T> the type parameter
     */
    public static class SyncValue<T> {

        private CountDownLatch mLatch = new CountDownLatch(1);
        private AtomicBoolean mFlag  = new AtomicBoolean();
        private T              mValue;

        public void setValue(T value) {
            if (mFlag.compareAndSet(false, true)) {
                mValue = value;
                mLatch.countDown();
            }
        }

        public T getValue() {
            if (!mFlag.get()) {
                try {
                    mLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return mValue;
        }
    }

    private static Executor getGlobalDeliver() {
        if (sDeliver == null) {
            sDeliver = new Executor() {
                @Override
                public void execute(@NonNull Runnable command) {
                    runOnUiThread(command);
                }
            };
        }
        return sDeliver;
    }
}
