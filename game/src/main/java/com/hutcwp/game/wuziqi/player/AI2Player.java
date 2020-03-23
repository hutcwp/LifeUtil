package com.hutcwp.game.wuziqi.player;

import android.graphics.Point;

import com.hutcwp.game.wuziqi.GamePoint;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import me.hutcwp.log.MLog;

/**
 * Created by hutcwp on 2020-03-23 10:55
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class AI2Player implements IGamePlayer {

    private static final String TAG = "AI2Player";

    @Override
    public int type() {
        return 1;
    }

    @NotNull
    @Override
    public String name() {
        return "ai2";
    }

    @Override
    public void makeNewPoint(@NotNull GamePoint paint) {

    }

    @Override
    public void startPlay(@NotNull List<Point> userPoints, @NotNull List<Point> aiPoints, @NotNull List<Point> allFreePoints, @NotNull final Function1<? super Point, Unit> block) {

        for (Point p : userPoints) {
            chessBoard[p.x][p.y] = -1;
            toJudge.remove(p);
            for (int i = 0; i < 8; ++i) {
                int x = p.x + dc[i];
                int y = p.y + dr[i];
                if (1 <= x && x <= size && 1 <= y && y <= size && (chessBoard[x][y] == 0)) {
                    Point now = new Point(x, y);
                    toJudge.add(now);
                }
            }
        }
        for (Point p : aiPoints) {
            chessBoard[p.x][p.y] = 1;
            toJudge.remove(p);
            for (int i = 0; i < 8; ++i) {
                int x = p.x + dc[i];
                int y = p.y + dr[i];
                if (1 <= x && x <= size && 1 <= y && y <= size && (chessBoard[x][y] == 0)) {
                    Point now = new Point(x, y);
                    toJudge.add(now);
                }
            }
        }
        printtoJudge();
        Observable.timer(10, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Point>() {
                    @Override
                    public Point apply(Long aLong) throws Exception {
                        long startTime = System.currentTimeMillis();
                        MLog.info(TAG, "==============dfs===start==============");
                        Node node = new Node();
                        dfs(0, node, MINN, MAXN, null);
                        Point now = node.bestChild.p;
                        long duration = System.currentTimeMillis() - startTime;
                        MLog.info(TAG, "==============dfs====end============= time(ms)=" + duration);
                        return now;
                    }
                })
                .subscribe(new Consumer<Point>() {
                    @Override
                    public void accept(Point point) throws Exception {
                        MLog.debug("hutcwp", "point=%s", point);
                        toJudge.remove(point);
                        block.invoke(point);
                    }
                });
    }


    private void printtoJudge() {
        MLog.debug(TAG, "================================");
        for (Point p : toJudge) {
            MLog.debug(TAG, "p->" + p);
        }
        MLog.debug(TAG, "================================");
    }

    private static int[][] chessBoard = new int[17][17]; //棋盘棋子的摆放情况：0无子，1黑子，－1白子
    private static HashSet<Point> toJudge = new HashSet<>(); // ai可能会下棋的点
    private static int dr[] = new int[]{-1, 1, -1, 1, 0, 0, -1, 1}; // 方向向量
    private static int dc[] = new int[]{1, -1, -1, 1, -1, 1, 0, 0}; //方向向量
    private static final int MAXN = 1 << 28;
    private static final int MINN = -MAXN;
    private static int searchDeep = 4;    //搜索深度
    private static final int size = 15;   //棋盘大小
    public static boolean isFinished = false;

    // 初始化函数，绘图
    public void initChessBoard() {
        toJudge.clear();

        // 初始化棋盘
        for (int i = 1; i <= 15; ++i)
            for (int j = 1; j <= 15; ++j)
                chessBoard[i][j] = 0;
    }

    private void putChess(int x, int y) {
        chessBoard[x][y] = type();
        Point p = new Point(x, y);
        toJudge.remove(p);
        for (int i = 0; i < 8; ++i) {
            Point now = new Point(p.x + dc[i], p.y + dr[i]);
            if (1 <= now.x && now.x <= size && 1 <= now.y && now.y <= size && chessBoard[now.x][now.y] == 0)
                toJudge.add(now);
        }
    }

    // alpha beta dfs
    private void dfs(int deep, Node root, int alpha, int beta, Point p) {
        MLog.debug(TAG, "dfs->deep=" + deep + " p=" + p);
        if (deep == searchDeep) {
            root.mark = getMark();
            MLog.debug(TAG, "searchDeep=" + searchDeep + "mark=" + root.mark);
            return;
        }
        ArrayList<Point> judgeSet = new ArrayList<>();
        Iterator it = toJudge.iterator();
        while (it.hasNext()) {
            Point now = new Point((Point) it.next());
            judgeSet.add(now);
        }

        it = judgeSet.iterator();
        while (it.hasNext()) {
            Point now = new Point((Point) it.next());
            Node node = new Node();
            node.setPoint(now);
            root.addChild(node);

            chessBoard[now.x][now.y] = ((deep & 1) == 1) ? -1 : 1; //判断是ai还是玩家棋子
            if (isEnd(now.x, now.y)) {
                root.bestChild = node;
                root.mark = MAXN * chessBoard[now.x][now.y];
                chessBoard[now.x][now.y] = 0;
                return;
            }

            boolean[] needDeleteFlags = new boolean[8]; //标记回溯时要不要删掉
            Arrays.fill(needDeleteFlags, true);
            for (int i = 0; i < 8; ++i) {
                Point next = new Point(now.x + dc[i], now.y + dr[i]);
                if (1 <= now.x + dc[i] && now.x + dc[i] <= size && 1 <= now.y + dr[i] && now.y + dr[i] <= size && chessBoard[next.x][next.y] == 0) {
                    if (!toJudge.contains(next)) {
                        toJudge.add(next);
                    } else {
                        needDeleteFlags[i] = false;
                    }
                }
            }

            boolean flag = toJudge.contains(now);
            if (flag) {
                toJudge.remove(now);
            }

            dfs(deep + 1, root.getLastChild(), alpha, beta, now);

            //回溯，重置回来
            chessBoard[now.x][now.y] = 0;
            if (flag) {
                toJudge.add(now);
            }
            for (int i = 0; i < 8; ++i) {
                if (needDeleteFlags[i]) {
                    toJudge.remove(new Point(now.x + dc[i], now.y + dr[i]));
                }
            }
            // alpha beta剪枝
            // min层，用户玩家，调最小
            if ((deep & 1) == 1) {
                if (root.bestChild == null || root.getLastChild().mark < root.bestChild.mark) {
                    root.bestChild = root.getLastChild();
                    root.mark = root.bestChild.mark;
                    if (root.mark <= MINN) {
                        root.mark += deep;
                    }
                    beta = Math.min(root.mark, beta);
                }
                if (root.mark <= alpha) {
                    return;
                }
            }
            // max层,电脑玩家
            else {
                if (root.bestChild == null || root.getLastChild().mark > root.bestChild.mark) {
                    root.bestChild = root.getLastChild();
                    root.mark = root.bestChild.mark;
                    if (root.mark == MAXN) {
                        root.mark -= deep;
                    }
                    alpha = Math.max(root.mark, alpha);
                }
                if (root.mark >= beta) {
                    return;
                }
            }
        }
    }

    private int getMark() {
        int res = 0;
        for (int i = 1; i <= size; ++i) {
            for (int j = 1; j <= size; ++j) {
                if (chessBoard[i][j] != 0) {
                    // 行
                    boolean flag1 = false, flag2 = false;
                    int x = j, y = i;
                    int cnt = 1;
                    int col = x, row = y;
                    while (--col > 0 && chessBoard[row][col] == chessBoard[x][y]) ++cnt;
                    if (col > 0 && chessBoard[row][col] == 0) flag1 = true;
                    col = x;
                    row = y;
                    while (++col <= size && chessBoard[row][col] == chessBoard[x][y]) ++cnt;
                    if (col <= size && chessBoard[row][col] == 0) flag2 = true;
                    if (flag1 && flag2)
                        res += chessBoard[i][j] * cnt * cnt;
                    else if (flag1 || flag2) res += chessBoard[i][j] * cnt * cnt / 4;
                    if (cnt >= 5) res = MAXN * chessBoard[i][j];
                    // 列
                    col = x;
                    row = y;
                    cnt = 1;
                    flag1 = false;
                    flag2 = false;
                    while (--row > 0 && chessBoard[row][col] == chessBoard[x][y]) ++cnt;
                    if (row > 0 && chessBoard[row][col] == 0) flag1 = true;
                    col = x;
                    row = y;
                    while (++row <= size && chessBoard[row][col] == chessBoard[x][y]) ++cnt;
                    if (row <= size && chessBoard[row][col] == 0) flag2 = true;
                    if (flag1 && flag2)
                        res += chessBoard[i][j] * cnt * cnt;
                    else if (flag1 || flag2)
                        res += chessBoard[i][j] * cnt * cnt / 4;
                    if (cnt >= 5) res = MAXN * chessBoard[i][j];
                    // 左对角线
                    col = x;
                    row = y;
                    cnt = 1;
                    flag1 = false;
                    flag2 = false;
                    while (--col > 0 && --row > 0 && chessBoard[row][col] == chessBoard[x][y])
                        ++cnt;
                    if (col > 0 && row > 0 && chessBoard[row][col] == 0) flag1 = true;
                    col = x;
                    row = y;
                    while (++col <= size && ++row <= size && chessBoard[row][col] == chessBoard[x][y])
                        ++cnt;
                    if (col <= size && row <= size && chessBoard[row][col] == 0) flag2 = true;
                    if (flag1 && flag2)
                        res += chessBoard[i][j] * cnt * cnt;
                    else if (flag1 || flag2) res += chessBoard[i][j] * cnt * cnt / 4;
                    if (cnt >= 5) res = MAXN * chessBoard[i][j];
                    // 右对角线
                    col = x;
                    row = y;
                    cnt = 1;
                    flag1 = false;
                    flag2 = false;
                    while (++row <= size && --col > 0 && chessBoard[row][col] == chessBoard[x][y])
                        ++cnt;
                    if (row <= size && col > 0 && chessBoard[row][col] == 0) flag1 = true;
                    col = x;
                    row = y;
                    while (--row > 0 && ++col <= size && chessBoard[row][col] == chessBoard[x][y])
                        ++cnt;
                    if (row > 0 && col <= size && chessBoard[i][j] == 0) flag2 = true;
                    if (flag1 && flag2)
                        res += chessBoard[i][j] * cnt * cnt;
                    else if (flag1 || flag2) res += chessBoard[i][j] * cnt * cnt / 4;
                    if (cnt >= 5) res = MAXN * chessBoard[i][j];
                }
            }
        }
        return res;
    }

    // 判断是否一方取胜
    private boolean isEnd(int x, int y) {
        // 判断一行是否五子连珠
        int cnt = 1;
        int col = x, row = y;
        while (--col > 0 && chessBoard[col][row] == chessBoard[x][y]) {
            ++cnt;
        }
        col = x;
        row = y;
        while (++col <= size && chessBoard[col][row] == chessBoard[x][y]) ++cnt;
        if (cnt >= 5) {
            isFinished = true;
            MLog.debug(TAG, "一行五子连珠 x=" + x + "y=" + y);
            return true;
        }


        // 判断一列是否五子连珠
        col = x;
        row = y;
        cnt = 1;
        while (--row > 0 && chessBoard[col][row] == chessBoard[x][y]) ++cnt;
        col = x;
        row = y;
        while (++row <= size && chessBoard[col][row] == chessBoard[x][y]) ++cnt;
        if (cnt >= 5) {
            isFinished = true;
            MLog.debug(TAG, "一列五子连珠 x=" + x + "y=" + y);
            return true;
        }


        // 判断左对角线是否五子连珠
        col = x;
        row = y;
        cnt = 1;
        while (--col > 0 && --row > 0 && chessBoard[col][row] == chessBoard[x][y]) ++cnt;
        col = x;
        row = y;
        while (++col <= size && ++row <= size && chessBoard[col][row] == chessBoard[x][y]) ++cnt;
        if (cnt >= 5) {
            isFinished = true;
            MLog.debug(TAG, "左对角线五子连珠 x=" + x + "y=" + y);
            return true;
        }


        // 判断右对角线是否五子连珠
        col = x;
        row = y;
        cnt = 1;
        while (++row <= size && --col > 0 && chessBoard[col][row] == chessBoard[x][y]) ++cnt;
        col = x;
        row = y;
        while (--row > 0 && ++col <= size && chessBoard[col][row] == chessBoard[x][y]) ++cnt;
        if (cnt >= 5) {
            isFinished = true;
            MLog.debug(TAG, "右对角线五子连珠 x=" + x + "y=" + y);
            return true;
        }
        return false;
    }


    // 树节点
    public class Node {
        Node bestChild;
        ArrayList<Node> child = new ArrayList<>();
        Point p = new Point();
        int mark;

        Node() {
            this.child.clear();
            bestChild = null;
            mark = 0;
        }

        void setPoint(Point r) {
            p.x = r.x;
            p.y = r.y;
        }

        void addChild(Node r) {
            this.child.add(r);
        }

        Node getLastChild() {
            return child.get(child.size() - 1);
        }
    }

}
