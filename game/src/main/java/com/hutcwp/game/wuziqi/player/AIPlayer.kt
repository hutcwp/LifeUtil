package com.hutcwp.game.wuziqi.player

import android.graphics.Point
import com.hutcwp.game.wuziqi.GamePoint
import me.hutcwp.log.MLog
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


/**
 *
 * Created by hutcwp on 2020-03-21 21:43
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/

open class AIPlayer : IGamePlayer {
    override fun type(): Int {
        return 1
    }

    override fun name(): String {
        return "AI玩家"
    }

    override fun makeNewPoint(paint: GamePoint) {

    }

    override fun startPlay(userPoints: MutableList<Point>, aiPoints: MutableList<Point>, allFreePoints: MutableList<Point>, block: (Point) -> Unit) {
        this.myPoints = aiPoints
        this.allFreePoints = allFreePoints

        val result = try {
            doAnalysis(aiPoints, userPoints)
        } catch (e: NullPointerException) { //修复可能出现的罕见bug：当快下满的时候（和棋时）可能出现AI崩溃的bug，暂定的解决方式是：AI随机下子。
            val random = Random()
            val i = random.nextInt(allFreePoints.size)
            allFreePoints[i]
        } catch (e: IndexOutOfBoundsException) {
            val random = Random()
            val i = random.nextInt(allFreePoints.size)
            allFreePoints[i]
        }

        MLog.info("aiplayer", "result=$result")
        block(result!!)

    }

    // 四个方向，横- 、纵| 、正斜/ 、反斜\
    private val HENG = 0
    private val ZHONG = 1
    private val ZHENG_XIE = 2
    private val FAN_XIE = 3
    //往前往后
    private val FORWARD = true
    private val BACKWARD = false


    //标示分析结果当前点位是两头通（ALIVE）还是只有一头通（HALF_ALIVE），封死的棋子分析过程自动屏蔽，不作为待选棋子
    val ALIVE = 1
    private val HALF_ALIVE = 0


    //我已下的棋子
    var myPoints = mutableListOf<Point>()

    //棋盘最大横坐标和纵标，
    protected var maxX = 0
    protected var maxY = 0

    //所有空白棋子
    protected var allFreePoints: MutableList<Point>? = null

    var currentRange = CalcuteRange(1, 1, 14, 14)
    private val RANGE_STEP = 1

    //计算范围，太大的范围会有性能问题
    class CalcuteRange(var xStart: Int, var yStart: Int, var xStop: Int, var yStop: Int) {
    }

    private fun initRange(comuters: List<Point>, humans: List<Point>) {
    }

    // 分析当前形式的入口方法，分析总共分三个步骤，第三步骤可由子类干预以作难度控制
    private fun doAnalysis(comuters: List<Point>, humans: List<Point>): Point? {
        if (humans.size == 1) { //第一步
            return getFirstPoint(humans)
        }
        //初始化计算范围
        initRange(comuters, humans)
        //清除以前的结果
        initAnalysisResults()
        // 开始分析，扫描所有空白点，形成第一次分析结果
        var bestPoint: Point? = doFirstAnalysis(comuters, humans)
        if (bestPoint != null) {
            MLog.info("aiPlayer", "这个棋子最重要，只能下这个棋子 $bestPoint")
            return bestPoint
        }

        // 分析第一次结果，找到自己的最佳点位
        bestPoint = doComputerSencondAnalysis(computerFirstResults, computerSencodResults)
        if (bestPoint != null) { //System.out.println("快要赢了，就下这个棋子");
            return bestPoint
        }
        computerFirstResults.clear()
        System.gc()
        // 分析第一次结果，找到敌人的最佳点位
        bestPoint = doHumanSencondAnalysis(humanFirstResults, humanSencodResults)
        if (bestPoint != null) { //System.out.println("再不下这个棋子就输了");
            return bestPoint
        }
        humanFirstResults.clear()
        System.gc()
        //没找到绝杀点，第三次结果分析
        return doThirdAnalysis()
    }


    private val fMap: HashMap<Int, Int> = HashMap()


    //BUG修复：当电脑是黑棋的时候，白棋下在黑棋右边一颗（即开局黑(7,7)白(7,8)）会出现bug.
    //下第一步棋子，不需要复杂的计算，根据人类第一步棋子X值减1完成
    private fun getFirstPoint(humans: List<Point>): Point? {
        val point: Point = humans[0]
        return if (myPoints.isEmpty()) { //人类已经下了一颗，我还没有下，所以人类是先手
            xm1(point)
        } else if (point.x == 6 && point.y == 6) {
            Point(6, 8)
        } else if (point.x == 6 && point.y == 7) {
            Point(6, 6)
        } else if (point.x == 6 && point.y == 8) {
            Point(6, 6)
        } else if (point.x == 7 && point.y == 6) {
            Point(8, 8)
        } else if (point.x == 7 && point.y == 8) {
            Point(6, 8)
        } else if (point.x == 8 && point.y == 6) {
            Point(6, 6)
        } else if (point.x == 8 && point.y == 7) {
            Point(6, 6)
        } else if (point.x == 8 && point.y == 8) {
            Point(6, 8)
        } else if (point.x == 5 && point.y == 5) {
            Point(6, 8)
        } else {
            Point(6, 6)
        }
    }

    private fun xm1(point: Point): Point? {
        return if (point.x == 0 || point.y == 0 || point.x == maxX && point.y == maxY) Point(maxX / 2, maxY / 2) else {
            Point(point.x - 1, point.y)
        }
    }


    // 开始分析，扫描所有空白点，形成第一次分析结果
    private fun doFirstAnalysis(comuters: List<Point>, humans: List<Point>): Point? {
        val size: Int = allFreePoints?.size ?: 0
        var computerPoint: Point? = null
        var humanPoint: Point? = null
        var x: Int
        var y: Int
        var firstAnalysisResult: FirstAnalysisResult?
        for (i in 0 until size) {
            computerPoint = allFreePoints?.get(i)
            //先把X、Y坐标记下来，因为在分析过程中会改变原来的对象
            x = computerPoint!!.x
            y = computerPoint.y
            if (x < currentRange.xStart || x > currentRange.xStop || y < currentRange.yStart || y > currentRange.yStop) {
                continue
            }

            //尝试在此位置上下一个棋子，并分析在“横向”这个方向上我方可形成的状态，如活4，活3，半活4，活2等所有状态
            firstAnalysisResult = tryAndCountResult(comuters, humans, computerPoint, HENG)
            computerPoint.x = x
            computerPoint.y = y //回复点位的原值，以供下次分析
            if (firstAnalysisResult != null) { //无返回结果此方向上不可能达到五个棋子，
                if (firstAnalysisResult.count == 5) //等于5表示在此点上下棋子即可连成5个，胜利了，不再往下进行分析
                    return computerPoint
                //记录第一次分析结果
                addToFirstAnalysisResult(firstAnalysisResult, computerFirstResults)
            }

            //在“纵向”这个方向上重复上面的步骤
            firstAnalysisResult = tryAndCountResult(comuters, humans, computerPoint, ZHONG)
            computerPoint.x = x
            computerPoint.y = y
            if (firstAnalysisResult != null) { //死棋，不下
                if (firstAnalysisResult.count == 5) return computerPoint
                addToFirstAnalysisResult(firstAnalysisResult, computerFirstResults)
            }

            //正斜向
            firstAnalysisResult = tryAndCountResult(comuters, humans, computerPoint, ZHENG_XIE)
            computerPoint.x = x
            computerPoint.y = y
            if (firstAnalysisResult != null) {
                if (firstAnalysisResult.count == 5) return computerPoint
                addToFirstAnalysisResult(firstAnalysisResult, computerFirstResults)
            }

            //反斜向
            firstAnalysisResult = tryAndCountResult(comuters, humans, computerPoint, FAN_XIE)
            computerPoint.x = x
            computerPoint.y = y
            if (firstAnalysisResult != null) {
                if (firstAnalysisResult.count == 5) return computerPoint
                addToFirstAnalysisResult(firstAnalysisResult, computerFirstResults)
            }

            //在“横向”上分析此棋子可在敌方形成如何状态，如敌方的活3、半活4等
            firstAnalysisResult = tryAndCountResult(humans, comuters, computerPoint, HENG)
            computerPoint.x = x
            computerPoint.y = y
            if (firstAnalysisResult != null) {
                if (firstAnalysisResult.count == 5) humanPoint = computerPoint
                addToFirstAnalysisResult(firstAnalysisResult, humanFirstResults)
            }

            //“纵向”
            firstAnalysisResult = tryAndCountResult(humans, comuters, computerPoint, ZHONG)
            computerPoint.x = x
            computerPoint.y = y
            if (firstAnalysisResult != null) {
                if (firstAnalysisResult.count == 5) humanPoint = computerPoint
                addToFirstAnalysisResult(firstAnalysisResult, humanFirstResults)
            }

            //“正斜”
            firstAnalysisResult = tryAndCountResult(humans, comuters, computerPoint, ZHENG_XIE)
            computerPoint.x = x
            computerPoint.y = y
            if (firstAnalysisResult != null) {
                if (firstAnalysisResult.count == 5) humanPoint = computerPoint
                addToFirstAnalysisResult(firstAnalysisResult, humanFirstResults)
            }

            //“反斜”
            firstAnalysisResult = tryAndCountResult(humans, comuters, computerPoint, FAN_XIE)
            computerPoint.x = x
            computerPoint.y = y
            if (firstAnalysisResult != null) {
                if (firstAnalysisResult.count == 5) humanPoint = computerPoint
                addToFirstAnalysisResult(firstAnalysisResult, humanFirstResults)
            }
        }
        //如果没有绝杀棋子，第一次分析不需要返回结果
        return humanPoint
    }

    //第二次分析，分析第一次形成的结果，第一次分析结果会把一步棋在四个方向上可形成的结果生成最多四个FirstAnalysisResult对象（敌我各四）
    //这里要把这四个对象组合成一个SencondAnalysisResult对象，
    private fun doComputerSencondAnalysis(firstResults: Map<Point?, MutableList<FirstAnalysisResult>?>, sencodResults: MutableList<SencondAnalysisResult>): Point? {
        var list: List<FirstAnalysisResult>? = null
        var sr: SencondAnalysisResult? = null
        for (p in firstResults.keys) {
            sr = SencondAnalysisResult(p)
            list = firstResults[p]
            for (result in list!!) {
                if (result.count == 4) {
                    if (result.aliveState == ALIVE) { //经过前面的过滤，双方都排除了绝杀棋，有活4就下这一步了，再下一步就赢了
                        return result.point //如果有绝杀，第一轮已返回，在此轮活4已经是好的棋子，直接返回，不再往下分析
                    } else {
                        sr.halfAlive4++
                        computer4HalfAlives.add(sr)
                    }
                } else if (result.count == 3) {
                    if (result.aliveState == ALIVE) {
                        sr.alive3++
                        if (sr.alive3 == 1) {
                            computer3Alives.add(sr)
                        } else {
                            computerDouble3Alives.add(sr)
                        }
                    } else {
                        sr.halfAlive3++
                        computer3HalfAlives.add(sr)
                    }
                } else { //半活2在第一阶段已被排除，不再处理
                    sr.alive2++
                    if (sr.alive2 == 1) {
                        computer2Alives.add(sr)
                    } else {
                        computerDouble2Alives.add(sr)
                    }
                }
            }
            sencodResults.add(sr)
        }
        //没有找到活4
        return null
    }

    //这个方法和上面的基本一样，但为了性能，少作几次判断，将人类和电脑的分开了
    private fun doHumanSencondAnalysis(firstResults: Map<Point?, MutableList<FirstAnalysisResult>?>, sencodResults: MutableList<SencondAnalysisResult>): Point? {
        var list: List<FirstAnalysisResult>? = null
        var sr: SencondAnalysisResult? = null
        for (p in firstResults.keys) {
            sr = SencondAnalysisResult(p)
            list = firstResults[p]
            for (result in list!!) {
                if (result.count == 4) {
                    if (result.aliveState == ALIVE) {
                        human4Alives.add(sr)
                    } else {
                        sr.halfAlive4++
                        human4HalfAlives.add(sr)
                    }
                } else if (result.count == 3) {
                    if (result.aliveState == ALIVE) {
                        sr.alive3++
                        if (sr.alive3 == 1) {
                            human3Alives.add(sr)
                        } else {
                            humanDouble3Alives.add(sr)
                        }
                    } else {
                        sr.halfAlive3++
                        human3HalfAlives.add(sr)
                    }
                } else {
                    sr.alive2++
                    if (sr.alive2 == 1) {
                        human2Alives.add(sr)
                    } else {
                        humanDouble2Alives.add(sr)
                    }
                }
            }
            sencodResults.add(sr)
        }
        //没有找到活4
        return null
    }

    private fun sleep(miniSecond: Int) {
        try {
            Thread.sleep(miniSecond.toLong())
        } catch (e: InterruptedException) {
        }
    }


    //第三次分析，双方都不可以制造活4，找双活3棋子，不行就找半活4，再不行就找单活3，双活2
    private fun doThirdAnalysis(): Point? {
        if (!computer4HalfAlives.isEmpty()) {
            return computer4HalfAlives[0].point
        }
        System.gc()
        sleep(300)
        Collections.sort(computerSencodResults)
        System.gc()
        //即将单活4，且我没有半活4以上的，只能堵
        var mostBest: Point? = getBestPoint(human4Alives, computerSencodResults)
        if (mostBest != null) return mostBest
        Collections.sort(humanSencodResults)
        System.gc()
        mostBest = getBestPoint()
        return if (mostBest != null) mostBest else computerSencodResults[0].point
        //拿出各自排第一的，谁好就下谁
    }

    //子类实现这个方法，并改变其顺序可以实现防守为主还是猛攻
    protected fun getBestPoint(): Point? { //即将单活4，且我没有半活4以上的，只能堵
        var mostBest: Point? = getBestPoint(computerDouble3Alives, humanSencodResults)
        if (mostBest != null) return mostBest
        mostBest = getBestPoint(computer3Alives, humanSencodResults)
        if (mostBest != null) return mostBest
        mostBest = getBestPoint(humanDouble3Alives, computerSencodResults)
        if (mostBest != null) return mostBest
        mostBest = getBestPoint(human3Alives, computerSencodResults)
        if (mostBest != null) return mostBest
        mostBest = getBestPoint(computerDouble2Alives, humanSencodResults)
        if (mostBest != null) return mostBest
        mostBest = getBestPoint(computer2Alives, humanSencodResults)
        if (mostBest != null) return mostBest
        mostBest = getBestPoint(computer3HalfAlives, humanSencodResults)
        if (mostBest != null) return mostBest
        mostBest = getBestPoint(human4HalfAlives, computerSencodResults)
        if (mostBest != null) return mostBest
        mostBest = getBestPoint(humanDouble2Alives, computerSencodResults)
        if (mostBest != null) return mostBest
        mostBest = getBestPoint(human2Alives, computerSencodResults)
        if (mostBest != null) return mostBest
        mostBest = getBestPoint(human3HalfAlives, computerSencodResults)
        return mostBest
    }


    //第三次分析的最后一步，第二次结果已经过排序，在此可以从前面选出最好的棋子
    protected fun getBestPoint(myBest: List<SencondAnalysisResult>, yourSencodResults: List<SencondAnalysisResult>): Point? {
        return if (!myBest.isEmpty()) {
            if (myBest.size > 1) {
                for (your in yourSencodResults) {
                    if (myBest.contains(your)) {
                        return your.point
                    }
                }
                myBest[0].point
            } else {
                myBest[0].point
            }
        } else null
    }


    //第一次分析结果
    private val computerFirstResults: MutableMap<Point?, MutableList<FirstAnalysisResult>?> = HashMap()
    private val humanFirstResults: MutableMap<Point?, MutableList<FirstAnalysisResult>?> = HashMap()
    //    //第二次总结果
    protected val computerSencodResults: MutableList<SencondAnalysisResult> = ArrayList()
    protected val humanSencodResults: MutableList<SencondAnalysisResult> = ArrayList()
    //第二次分结果，电脑
    protected val computer4HalfAlives: MutableList<SencondAnalysisResult> = ArrayList(2)
    protected val computerDouble3Alives: MutableList<SencondAnalysisResult> = ArrayList(4)
    protected val computer3Alives: MutableList<SencondAnalysisResult> = ArrayList(5)
    protected val computerDouble2Alives: MutableList<SencondAnalysisResult> = ArrayList()
    protected val computer2Alives: MutableList<SencondAnalysisResult> = ArrayList()
    protected val computer3HalfAlives: MutableList<SencondAnalysisResult> = ArrayList()

    //第二次分结果，人类
    protected val human4Alives: MutableList<SencondAnalysisResult> = ArrayList(2)
    protected val human4HalfAlives: MutableList<SencondAnalysisResult> = ArrayList(5)
    protected val humanDouble3Alives: MutableList<SencondAnalysisResult> = ArrayList(2)
    protected val human3Alives: MutableList<SencondAnalysisResult> = ArrayList(10)
    protected val humanDouble2Alives: MutableList<SencondAnalysisResult> = ArrayList(3)
    protected val human2Alives: MutableList<SencondAnalysisResult> = ArrayList()
    protected val human3HalfAlives: MutableList<SencondAnalysisResult> = ArrayList()

    //第一次分析前清空上一步棋子的分析结果
    private fun initAnalysisResults() {
        computerFirstResults.clear()
        humanFirstResults.clear()
        //第二次总结果
        computerSencodResults.clear()
        humanSencodResults.clear()
        //第二次分结果
        computer4HalfAlives.clear()
        computerDouble3Alives.clear()
        computer3Alives.clear()
        computerDouble2Alives.clear()
        computer2Alives.clear()
        computer3HalfAlives.clear()
        //第二次分结果，人类
        human4Alives.clear()
        human4HalfAlives.clear()
        humanDouble3Alives.clear()
        human3Alives.clear()
        humanDouble2Alives.clear()
        human2Alives.clear()
        human3HalfAlives.clear()
    }

    //加入到第一次分析结果中
    private fun addToFirstAnalysisResult(result: FirstAnalysisResult, dest: MutableMap<Point?, MutableList<FirstAnalysisResult>?>) {
        if (dest.containsKey(result.point)) {
            dest[result.point]!!.add(result)
        } else {
            val list: MutableList<FirstAnalysisResult> = ArrayList(1)
            list.add(result)
            dest[result.point] = list
        }
    }


    //第一次分析结果类
    private class FirstAnalysisResult private constructor(//连续数
            var count: Int, point: Point?, direction: Int, aliveState: Int) {
        //点位
        var point: Point?
        //方向
        var direction: Int
        //状态
        var aliveState: Int

        constructor(count: Int, point: Point?, direction: Int) : this(count, point, direction, 1) {}

        fun init(point: Point?, direction: Int, aliveState: Int): FirstAnalysisResult {
            count = 1
            this.point = point
            this.direction = direction
            this.aliveState = aliveState
            return this
        }

        fun cloneMe(): FirstAnalysisResult {
            return FirstAnalysisResult(count, point, direction, aliveState)
        }

        init {
            this.point = point
            this.direction = direction
            this.aliveState = aliveState
        }
    }

    //第二次分析结果类
    class SencondAnalysisResult(point: Point?) : Comparable<SencondAnalysisResult?> {
        var alive4 = 0
        //活3数量
        var alive3 = 0
        //半活4，一头封的
        var halfAlive4 = 0
        //半活3，一头封的
        var halfAlive3 = 0
        //活2数量
        var alive2 = 0
        //点位
        var point: Point?

        override fun hashCode(): Int {
            val prime = 31
            var result = 1
            result = prime * result + if (point == null) 0 else point.hashCode()
            return result
        }

        override fun equals(obj: Any?): Boolean {
            val other = obj as SencondAnalysisResult?
            if (point == null) {
                if (other!!.point != null) return false
            } else if (point!! != other!!.point) return false
            return true
        }

        //第三次分析时，对第二次分析结果进行排序，此为排序回调函数
        override operator fun compareTo(other: SencondAnalysisResult?): Int {
            return compareTowResult(this, other!!)
        }

        init {
            this.point = point
        }


        //返加-1则第一个参数优先，1则第二个参数优先，0则按原来顺序
        fun compareTowResult(oneResult: SencondAnalysisResult, another: SencondAnalysisResult): Int {
            if (oneResult.alive4 > another.alive4) {
                return -1
            }
            if (oneResult.alive4 < another.alive4) {
                return 1
            }
            if (oneResult.halfAlive4 > another.halfAlive4) {
                return -1
            }
            if (oneResult.halfAlive4 < another.halfAlive4) {
                return 1
            }
            if (oneResult.alive3 > another.alive3) {
                return -1
            }
            if (oneResult.alive3 < another.alive3) {
                return 1
            }
            if (oneResult.alive2 > another.alive2) {
                return -1
            }
            if (oneResult.alive2 < another.alive2) {
                return 1
            }
            if (oneResult.halfAlive3 > another.halfAlive3) {
                return -1
            }
            return if (oneResult.halfAlive3 > another.halfAlive3) {
                1
            } else 0
        }
    }


    //一个临时对象，供第一次分析时临时存放分析结果使用，如果分析出有活1以上（不含）的结果，则调用其cloneMe方法获得结果，否则抛弃此结果
    private val far = FirstAnalysisResult(1, null, HENG)

    // 分析如果在当前位下一子，会形成某个方向上多少个子，参数：当前己方已下的所有点，当前要假设的点，需要判断的方向
    private fun tryAndCountResult(myPoints: List<Point>, enemyPoints: List<Point>, point: Point, direction: Int): FirstAnalysisResult? {
        val x: Int = point.x
        val y: Int = point.y
        val maxCountOnThisDirection = maxCountOnThisDirection(point, enemyPoints, direction, 1)
        val fr = when {
            maxCountOnThisDirection < 5 -> { //无意义的棋子
                return null //此方向不足五个空位，已排除己方已下的棋子
            }
            maxCountOnThisDirection == 5 -> { //半死状态，当是一头通
                far.init(point, direction, HALF_ALIVE)
            }
            else -> { //两头皆通
                far.init(point, direction, ALIVE)
            }
        }
        //在前和后的方向上计算一次
        point.x = x
        point.y = y
        countPoint(myPoints, enemyPoints, point, fr, direction, FORWARD)
        point.x = x
        point.y = y
        countPoint(myPoints, enemyPoints, point, fr, direction, BACKWARD)
        return if (fr.count <= 1 || fr.count == 2 && fr.aliveState == HALF_ALIVE) { //活1，半活2及其以下结果，抛弃
            null
        } else {
            return fr.cloneMe()
            //返回复制的结果
        }
    }

    //棋子出了墙
    private fun isOutSideOfWall(point: Point, direction: Int): Boolean {
        return if (direction == HENG) {
            point.x < 0 || point.x >= maxX //最大的X和Y值均在墙外所以用等号
        } else if (direction == ZHONG) {
            point.y < 0 || point.y >= maxY
        } else { //这里可能有问题
            point.x < 0 || point.y < 0 || point.x >= maxX || point.y >= maxY
        }
    }

    private fun pointToNext(point: Point, direction: Int, forward: Boolean): Point? {
        when (direction) {
            HENG -> if (forward) point.x++ else point.x--
            ZHONG -> if (forward) point.y++ else point.y--
            ZHENG_XIE -> if (forward) {
                point.x++
                point.y--
            } else {
                point.x--
                point.y++
            }
            FAN_XIE -> if (forward) {
                point.x++
                point.y++
            } else {
                point.x--
                point.y--
            }
        }
        return point
    }

    //在某个方向（八个中的一个）可下多少棋子，这个方法是第一分析中的核心方法
    private fun countPoint(myPoints: List<Point>, enemyPoints: List<Point>, point: Point, fr: FirstAnalysisResult, direction: Int, forward: Boolean) {
        if (myPoints.contains(pointToNext(point, direction, forward))) {
            fr.count++
            if (myPoints.contains(pointToNext(point, direction, forward))) {
                fr.count++
                if (myPoints.contains(pointToNext(point, direction, forward))) {
                    fr.count++
                    if (myPoints.contains(pointToNext(point, direction, forward))) {
                        fr.count++
                    } else if (enemyPoints.contains(point) || isOutSideOfWall(point, direction)) {
                        fr.aliveState = HALF_ALIVE
                    }
                } else if (enemyPoints.contains(point) || isOutSideOfWall(point, direction)) {
                    fr.aliveState = HALF_ALIVE
                }
            } else if (enemyPoints.contains(point) || isOutSideOfWall(point, direction)) {
                fr.aliveState = HALF_ALIVE
            }
        } else if (enemyPoints.contains(point) || isOutSideOfWall(point, direction)) {
            fr.aliveState = HALF_ALIVE
        }
    }


    //在某个方向上是否还能下到满五个棋子
    private fun maxCountOnThisDirection(point: Point, enemyPoints: List<Point>, direction: Int, count: Int): Int {
        var count = count
        val x: Int = point.x
        val y: Int = point.y
        when (direction) {
            HENG -> {
                point.x = x - 1
                while (!enemyPoints.contains(point) && point.x >= 0 && count < 6) {
                    point.x = point.x - 1
                    count++
                }
                point.x = x + 1
                while (!enemyPoints.contains(point) && point.x < maxX && count < 6) {
                    point.x = point.x + 1
                    count++
                }
            }
            ZHONG -> {
                point.y = point.y - 1
                while (!enemyPoints.contains(point) && point.y >= 0) {
                    point.y = point.y - 1
                    count++
                }
                point.y = point.y + 1
                while (!enemyPoints.contains(point) && point.y < maxY && count < 6) {
                    point.y = point.y - 1
                    count++
                }
            }
            ZHENG_XIE -> {
                point.x = point.x - 1
                point.y = point.y + 1
                while (!enemyPoints.contains(point) && point.x >= 0 && point.y < maxY) {
                    point.x = point.x - 1
                    point.y = point.y + 1
                    count++
                }
                point.x = point.x + 1
                point.y = point.y - 1
                while (!enemyPoints.contains(point) && point.x < maxX && point.y >= 0 && count < 6) {
                    point.x = point.x + 1
                    point.y = point.y - 1
                    count++
                }
            }
            FAN_XIE -> {
                point.x = point.x - 1
                point.y = point.y - 1
                while (!enemyPoints.contains(point) && point.x >= 0 && point.y >= 0) {
                    point.x = point.x - 1
                    point.y = point.y - 1
                    count++
                }
                point.x = point.x + 1
                point.y = point.y + 1
                while (!enemyPoints.contains(point) && point.x < maxX && point.y < maxY && count < 6) {
                    count++
                }
            }
        }
        return count
    }

}