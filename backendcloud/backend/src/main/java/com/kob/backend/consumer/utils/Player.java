package com.kob.backend.consumer.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private Integer id;
    private Integer sx; // 起始位置的行坐标
    private Integer sy; // 起始位置的列坐标
    private List<Integer> steps; // 操作序列: 0上 1右 2下 3左

    /**
     * 判断第step回合时，蛇尾是否增长（即蛇身是否变长）
     * 规则：前10回合每回合都增长，之后每3回合增长1次（第1、4、7...回合增长）
     */
    private boolean check_tail_increasing(int step) {
        if (step <= 10) return true;
        return step % 3 == 1;
    }

    /**
     * 根据操作序列，计算蛇当前占据的所有格子
     * @return 蛇身体所有格子的列表，索引0是蛇尾，最后一个是蛇头
     */
    public List<Cell> getCells() {
        List<Cell> res = new ArrayList<>();

        // 方向数组：上、右、下、左 对应 0、1、2、3
        int[] dx = {-1, 0, 1, 0};  // 行偏移
        int[] dy = {0, 1, 0, -1};  // 列偏移

        int x = sx, y = sy;
        int step = 0;
        res.add(new Cell(x, y)); // 加入起点

        for (int d : steps) {
            // 根据方向移动到新位置
            x += dx[d];
            y += dy[d];
            res.add(new Cell(x, y)); // 蛇头前进一格

            // 如果这回合蛇不增长，则移除蛇尾（模拟蛇的移动）
            if (!check_tail_increasing(++step)) {
                res.remove(0);
            }
        }
        return res;
    }

    public String getStepsString() {
        StringBuilder res = new StringBuilder();
        for (int step : steps) {
            res.append(step);
        }
        return res.toString();
    }
}