package com.teqgin.image_laboratory.UtilTest;

import cn.hutool.core.date.DateUtil;
import com.teqgin.image_laboratory.util.RecommendUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RecommendUtilTest {

    /**
     * 86400s 为1天
     * date diff: 1s,           weight = 400.600000          1秒钟
     * date diff: 10s,          weight = 400.600000          10秒钟
     * date diff: 100s,         weight = 400.600000          大约2分钟
     * date diff: 1_000s,       weight = 400.600000          大约16分钟
     * date diff: 10_000s,      weight = 399.731505          大约三个小时
     * date diff: 100_000s,     weight = 151.978216          大约一天
     *
     * date diff: 1 day,        weight = 172.996669
     * date diff: 2 day,        weight = 90.972962
     * date diff: 3 day,        weight = 61.242766
     * date diff: 4 day,        weight = 46.090539
     * date diff: 5 day,        weight = 36.931469
     * date diff: 6 day,        weight = 30.803031
     * date diff: 7 day,        weight = 26.416477
     * date diff: 8 day,        weight = 23.122308
     * date diff: 9 day,        weight = 20.557976
     * date diff: 10 day,       weight = 18.505278
     * date diff: 1_000_000s,   weight = 15.991456           大约十一天
     * date diff: 10_000_000s,  weight = 1.599991            大约一百一十五天
     * date diff: 100_000_000s, weight = 0.160000
     * @throws InterruptedException
     */
    @Test
    public void countImageWeightTest() throws InterruptedException {
        Calendar calendar2 = Calendar.getInstance();

        for (int i = 0; i <= 10; i++) {
            calendar2.setTime(new Date());
            calendar2.add(Calendar.DAY_OF_MONTH, -i);
            double weight = RecommendUtil.countImageWeight(calendar2.getTime(),0);
            log.info(String.format("date diff: %d day, weight = %f", i,weight));
        }

    }
}
