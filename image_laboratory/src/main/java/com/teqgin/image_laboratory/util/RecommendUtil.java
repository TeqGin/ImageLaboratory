package com.teqgin.image_laboratory.util;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

public class RecommendUtil {
    private RecommendUtil(){}

    private static final double SEC_ZOOM_FACTOR = 40000;
    private static final int ACTIVE_FUN_ZOOM_FACTOR = 1000;
    private static final double READ_FACTOR = 0.6;
    private static final double DATE_FACTOR = 1 - READ_FACTOR;

    /**
     * 基于内容推荐的算法，有待提高
     * 但综合表现还可以，在不考虑浏览次数的情况下在一天内最少能输出151的权重，六天内最少能输出30的权重
     * 如果认为用户浏览次数不超过200次（输出权重为120）的话，仍能带来不错的时效性。
     * 关于时间和浏览次数的函数，计算用户在某个标签下的权重
     * @param updateDate
     * @param readTimes
     * @return
     */
    public static double  countImageWeight(Date updateDate,int readTimes){
        return readTimes * READ_FACTOR
                + tanh(timeFactor(updateDate)) * ACTIVE_FUN_ZOOM_FACTOR * DATE_FACTOR;
    }
    /**
     * 计算时间因子
     * +1 防止除零异常
     * @param updateDate
     * @return
     */
    private static double timeFactor(Date updateDate){
        return SEC_ZOOM_FACTOR /
                (DateUtil.between(updateDate,new Date(), DateUnit.SECOND) + 1);
    }
    /**
     * 两个归一化函数，但其实不太适合，会造成极端结果。
     * @param z
     * @return
     */
    private static double tanh(double z){
        return (2 / (1 + Math.pow(Math.E, -2 * z))) - 1;
    }

    private static double sigmoid(double z){
        return 1 / ( 1 + Math.pow(Math.E, z));
    }




}
