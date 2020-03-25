package com.hutcwp.game.wuziqi.player

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Point
import com.hutcwp.game.wuziqi.GameManager
import com.hutcwp.game.wuziqi.GamePoint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.hutcwp.log.MLog
import me.hutcwp.util.SingleToastUtil
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by hutcwp on 2020-03-23 10:55
 * email: caiwenpeng@yy.com
 * YY: 909076244
 */
class AI2Player(private val manager: GameManager) : IGamePlayer {

    override fun pointColor(): Int {
        return Color.BLACK
    }

    override fun type(): Int {
        return 1
    }

    override fun name(): String {
        return "AI2玩家"
    }

    override fun makeNewPoint(paint: GamePoint) {}

    @SuppressLint("CheckResult")
    override fun startPlay(userPoints: MutableList<Point>, aiPoints: MutableList<Point>, allFreePoints: MutableList<Point>, block: Function1<Point, Unit>) {
        for (p in userPoints) {
            chessBoard[p.x][p.y] = -1
            toJudge.remove(p)
            for (i in 0..7) {
                val x = p.x + dc[i]
                val y = p.y + dr[i]
                if (x in 1..BoardSize && 1 <= y && y <= BoardSize && chessBoard[x][y] == 0) {
                    val now = Point(x, y)
                    toJudge.add(now)
                }
            }
        }
        for (p in aiPoints) {
            chessBoard[p.x][p.y] = 1
            toJudge.remove(p)
            for (i in 0..7) {
                val x = p.x + dc[i]
                val y = p.y + dr[i]
                if (x in 1..BoardSize && 1 <= y && y <= BoardSize && chessBoard[x][y] == 0) {
                    val now = Point(x, y)
                    toJudge.add(now)
                }
            }
        }
        printToJudge()
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    val startTime = System.currentTimeMillis()
                    MLog.info(TAG, "==============dfs===start==============")
                    val node = Node()
                    dfs(0, node, MINN, MAXN, null)
                    val now = node.bestChild?.p ?: Point(8, 8)
                    val duration = System.currentTimeMillis() - startTime
                    MLog.info(TAG, "==============dfs====end============= time(ms)=$duration")
                    now
                }
                .subscribe { point ->
                    MLog.debug(TAG, "point=%s", point)
                    toJudge.remove(point)
//                    block.invoke(point)

                    if (manager.getCurrentUser() != this) {
//                    SingleToastUtil.showToast("现在是${currentPlayer?.name()}回合，请稍等")
                        MLog.debug(TAG, "current is other user play...")
                    }

                    if (manager.canAddNewPoint(point.x, point.y)) {
                        manager.addNewPoint(Point(point.x, point.y), this)
                    } else {
                        SingleToastUtil.showToast("当前位置不能放置，请重新选择")
                    }
                }
    }

    private fun printToJudge() {
        MLog.debug(TAG, "===============printToJudge start=================")
        for (p in toJudge) {
            MLog.debug(TAG, "p->$p")
        }
        MLog.debug(TAG, "==============printToJudge end==================")
    }

    // 初始化函数，绘图
    fun initChessBoard() {
        toJudge.clear()
        // 初始化棋盘
        for (i in 1..15) {
            for (j in 1..15) {
                chessBoard[i][j] = 0
            }
        }
    }

    // alpha beta dfs
    private fun dfs(deep: Int, root: Node, alpha: Int, beta: Int, p: Point?) {
        var alpha = alpha
        var beta = beta
        MLog.debug(TAG, "dfs->deep=$deep p=$p")
        if (deep == SearchDeep) {
            root.mark = mark()
            MLog.debug(TAG, "searchDeep=" + SearchDeep + "mark=" + root.mark)
            return
        }
        val judgeSet = ArrayList<Point>()
        toJudge.forEach {
            val now = Point(it)
            judgeSet.add(now)
        }

        judgeSet.forEach {
            val now = Point(it)
            val node = Node()
            node.setPoint(now)
            root.addChild(node)
            chessBoard[now.x][now.y] = if (deep and 1 == 1) -1 else 1 //判断是ai还是玩家棋子，1是ai ,-1是玩家
            if (isEnd(now.x, now.y)) {
                root.bestChild = node
                root.mark = MAXN * chessBoard[now.x][now.y]
                chessBoard[now.x][now.y] = 0
                return
            }

            val needDeleteFlags = BooleanArray(8) //标记回溯时要不要删掉
            Arrays.fill(needDeleteFlags, true)
            for (i in 0..7) {
                val next = Point(now.x + dc[i], now.y + dr[i])
                if (now.x + dc[i] in 1..BoardSize && 1 <= now.y + dr[i] && now.y + dr[i] <= BoardSize && chessBoard[next.x][next.y] == 0) {
                    if (!toJudge.contains(next)) {
                        toJudge.add(next)
                    } else {
                        needDeleteFlags[i] = false
                    }
                }
            }
            val flag = toJudge.contains(now)
            if (flag) {
                toJudge.remove(now)
            }
            dfs(deep + 1, root.lastChild, alpha, beta, now)
            //回溯，重置回来
            chessBoard[now.x][now.y] = 0
            if (flag) {
                toJudge.add(now)
            }
            for (i in 0..7) {
                if (needDeleteFlags[i]) {
                    toJudge.remove(Point(now.x + dc[i], now.y + dr[i]))
                }
            }
            // alpha beta剪枝
            // min层，用户玩家，调最小
            if (deep and 1 == 1) {
                if (root.bestChild == null || root.lastChild.mark < root.bestChild!!.mark) {
                    root.bestChild = root.lastChild
                    root.mark = root.bestChild!!.mark
                    if (root.mark <= MINN) {
                        root.mark += deep
                    }
                    beta = Math.min(root.mark, beta)
                }
                if (root.mark <= alpha) {
                    return
                }
            } else {
                if (root.bestChild == null || root.lastChild.mark > root.bestChild!!.mark) {
                    root.bestChild = root.lastChild
                    root.mark = root.bestChild!!.mark
                    if (root.mark == MAXN) {
                        root.mark -= deep
                    }
                    alpha = Math.max(root.mark, alpha)
                }
                if (root.mark >= beta) {
                    return
                }
            }
        }
    }

    fun testMark() {
        initChessBoard()
        chessBoard[1][5] = 1
        chessBoard[2][4] = 1
        chessBoard[3][3] = 1
        chessBoard[4][2] = 1
        chessBoard[5][1] = 1

        val res = mark()
        MLog.info(TAG, "testMark: res=$res")

    }

    // 行
    // 列
    // 左对角线
    // 右对角线
    private fun mark(): Int {
        var res = 0
        for (x in 1..BoardSize) {
            for (y in 1..BoardSize) {
                if (chessBoard[x][y] != 0) { // 行
                    var flag1 = false
                    var flag2 = false
                    var cnt = 1
                    var row = y //行
                    var col = x //列
                    //计算竖
                    while (--row > 0 && chessBoard[col][row] == chessBoard[x][y]) {
                        ++cnt
                    }
                    if (row > 0 && chessBoard[col][row] == 0) {
                        flag1 = true
                    }

                    row = y
                    col = x
                    while (++row <= BoardSize && chessBoard[col][row] == chessBoard[x][y]) {
                        ++cnt
                    }
                    if (row <= BoardSize && chessBoard[col][row] == 0) {
                        flag2 = true
                    }
                    if (flag1 && flag2) {
                        res += chessBoard[x][y] * cnt * cnt
                    } else if (flag1 || flag2) {
                        res += chessBoard[x][y] * cnt * cnt / 4
                    }
                    if (cnt >= 5) {
                        res = MAXN * chessBoard[x][y]
                    }


                    // 列
                    row = y
                    col = x
                    cnt = 1
                    flag1 = false
                    flag2 = false
                    while (--col > 0 && chessBoard[col][row] == chessBoard[x][y]) ++cnt
                    if (col > 0 && chessBoard[col][row] == 0) flag1 = true
                    row = y
                    col = x
                    while (++col <= BoardSize && chessBoard[col][row] == chessBoard[x][y]) ++cnt
                    if (col <= BoardSize && chessBoard[col][row] == 0) flag2 = true
                    if (flag1 && flag2) res += chessBoard[x][y] * cnt * cnt else if (flag1 || flag2) res += chessBoard[x][y] * cnt * cnt / 4
                    if (cnt >= 5) res = MAXN * chessBoard[x][y]


                    // 左对角线
                    row = y
                    col = x
                    cnt = 1
                    flag1 = false
                    flag2 = false
                    while (--row > 0 && --col > 0 && chessBoard[col][row] == chessBoard[x][y]) ++cnt
                    if (row > 0 && col > 0 && chessBoard[col][row] == 0) flag1 = true
                    row = y
                    col = x
                    while (++row <= BoardSize && ++col <= BoardSize && chessBoard[col][row] == chessBoard[x][y]) ++cnt
                    if (row <= BoardSize && col <= BoardSize && chessBoard[col][row] == 0) flag2 = true
                    if (flag1 && flag2) res += chessBoard[x][y] * cnt * cnt else if (flag1 || flag2) res += chessBoard[x][y] * cnt * cnt / 4
                    if (cnt >= 5) res = MAXN * chessBoard[x][y]


                    // 右对角线
                    row = y
                    col = x
                    cnt = 1
                    flag1 = false
                    flag2 = false
                    while (++col <= BoardSize && --row > 0 && chessBoard[col][row] == chessBoard[x][y]) ++cnt
                    if (col <= BoardSize && row > 0 && chessBoard[col][row] == 0) flag1 = true
                    row = y
                    col = x
                    while (--col > 0 && ++row <= BoardSize && chessBoard[col][row] == chessBoard[x][y]) ++cnt
                    if (col > 0 && row <= BoardSize && chessBoard[x][y] == 0) flag2 = true
                    if (flag1 && flag2) res += chessBoard[x][y] * cnt * cnt else if (flag1 || flag2) res += chessBoard[x][y] * cnt * cnt / 4
                    if (cnt >= 5) res = MAXN * chessBoard[x][y]
                }
            }
        }
        return res
    }

    // 判断是否一方取胜
    private fun isEnd(x: Int, y: Int): Boolean { // 判断一行是否五子连珠
        var cnt = 1
        var col = x
        var row = y
        while (--col > 0 && chessBoard[col][row] == chessBoard[x][y]) {
            ++cnt
        }
        col = x
        row = y
        while (++col <= BoardSize && chessBoard[col][row] == chessBoard[x][y]) ++cnt
        if (cnt >= 5) {
            isFinished = true
            MLog.debug(TAG, "一行五子连珠 x=" + x + "y=" + y)
            return true
        }
        // 判断一列是否五子连珠
        col = x
        row = y
        cnt = 1
        while (--row > 0 && chessBoard[col][row] == chessBoard[x][y]) ++cnt
        col = x
        row = y
        while (++row <= BoardSize && chessBoard[col][row] == chessBoard[x][y]) ++cnt
        if (cnt >= 5) {
            isFinished = true
            MLog.debug(TAG, "一列五子连珠 x=" + x + "y=" + y)
            return true
        }
        // 判断左对角线是否五子连珠
        col = x
        row = y
        cnt = 1
        while (--col > 0 && --row > 0 && chessBoard[col][row] == chessBoard[x][y]) ++cnt
        col = x
        row = y
        while (++col <= BoardSize && ++row <= BoardSize && chessBoard[col][row] == chessBoard[x][y]) ++cnt
        if (cnt >= 5) {
            isFinished = true
            MLog.debug(TAG, "左对角线五子连珠 x=" + x + "y=" + y)
            return true
        }
        // 判断右对角线是否五子连珠
        col = x
        row = y
        cnt = 1
        while (++row <= BoardSize && --col > 0 && chessBoard[col][row] == chessBoard[x][y]) ++cnt
        col = x
        row = y
        while (--row > 0 && ++col <= BoardSize && chessBoard[col][row] == chessBoard[x][y]) ++cnt
        if (cnt >= 5) {
            isFinished = true
            MLog.debug(TAG, "右对角线五子连珠 x=" + x + "y=" + y)
            return true
        }
        return false
    }

    // 树节点
    inner class Node internal constructor() {
        var bestChild: Node?
        var child = ArrayList<Node>()
        var p = Point()
        var mark: Int
        fun setPoint(r: Point) {
            p.x = r.x
            p.y = r.y
        }

        fun addChild(r: Node) {
            child.add(r)
        }

        val lastChild: Node
            get() = child[child.size - 1]

        init {
            child.clear()
            bestChild = null
            mark = 0
        }
    }

    companion object {
        private const val TAG = "AI2Player"
        private val chessBoard = Array(17) { IntArray(17) } //棋盘棋子的摆放情况：0无子，1黑子，－1白子
        private val toJudge = HashSet<Point>() // ai可能会下棋的点
        private val dr = intArrayOf(-1, 1, -1, 1, 0, 0, -1, 1) // 方向向量
        private val dc = intArrayOf(1, -1, -1, 1, -1, 1, 0, 0) //方向向量
        private const val MAXN = 1 shl 28
        private const val MINN = -MAXN
        private const val SearchDeep = 2 //搜索深度
        private const val BoardSize = 14 //棋盘大小
        var isFinished = false
    }
}