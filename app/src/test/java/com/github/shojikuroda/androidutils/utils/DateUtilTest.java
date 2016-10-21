package com.github.shojikuroda.androidutils.utils;

import org.exparity.hamcrest.date.DateMatchers;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by shoji.kuroda on 2016/10/21.
 */
public class DateUtilTest {
    @Test
    public void testStrToDate() throws Exception {

        Date target = new DateTime(2016, 2, 29, 0, 0).toDate();

        assertThat(DateUtil.strToDate("2016-02-29T00:00:00Z", "yyyy-MM-dd'T'HH:mm:ss'Z'"), DateMatchers.sameDay(target));
        assertThat(DateUtil.strToDate("2016/02/29T00:00:00Z", "yyyy/MM/dd'T'HH:mm:ss'Z'"), DateMatchers.sameDay(target));
        assertThat(DateUtil.strToDate("2016-02-29", "yyy-MM-dd"), DateMatchers.sameDay(target));
        assertThat(DateUtil.strToDate("2016-02", "yyy-MM"), DateMatchers.sameMonthOfYear(target));
        assertThat(DateUtil.strToDate("2016/02/29", "yyyy/MM/dd"), DateMatchers.sameDay(target));
        assertThat(DateUtil.strToDate("2016年02月29日", "yyyy年MM月dd"), DateMatchers.sameDay(target));
        assertThat(DateUtil.strToDate("2016-02-29", "yyyy/MM/dd"), nullValue());

        assertThat(DateUtil.strToDate("2016-00-01", "yyy-MM-dd"), nullValue());
        assertThat(DateUtil.strToDate("2016-01-00", "yyy-MM-dd"), nullValue());
        assertThat(DateUtil.strToDate("2016-01-32", "yyy-MM-dd"), nullValue());
        assertThat(DateUtil.strToDate("2016-02-31", "yyy-MM-dd"), nullValue());
        assertThat(DateUtil.strToDate("2016-03-32", "yyy-MM-dd"), nullValue());
        assertThat(DateUtil.strToDate("2016-04-31", "yyy-MM-dd"), nullValue());
        assertThat(DateUtil.strToDate("2016-05-32", "yyy-MM-dd"), nullValue());
        assertThat(DateUtil.strToDate("2016-06-31", "yyy-MM-dd"), nullValue());
        assertThat(DateUtil.strToDate("2016-07-32", "yyy-MM-dd"), nullValue());
        assertThat(DateUtil.strToDate("2016-08-32", "yyy-MM-dd"), nullValue());
        assertThat(DateUtil.strToDate("2016-09-31", "yyy-MM-dd"), nullValue());
        assertThat(DateUtil.strToDate("2016-10-32", "yyy-MM-dd"), nullValue());
        assertThat(DateUtil.strToDate("2016-11-31", "yyy-MM-dd"), nullValue());
        assertThat(DateUtil.strToDate("2016-12-32", "yyy-MM-dd"), nullValue());
        assertThat(DateUtil.strToDate("2016-13-01", "yyy-MM-dd"), nullValue());
    }

    @Test
    public void testStrToDateByArray() throws Exception {

        Date target = new DateTime(2016, 2, 29, 0, 0).toDate();
        String[] format = new String[]{
                "yyyy-MM-dd'T'HH:mm:ss'Z'",
                "yyyy/MM/dd'T'HH:mm:ss'Z'",
                "yyyy-MM-dd", "yyyy-MM",
                "yyyy/MM/dd",
                "yyyy年MM月dd日"};

        assertThat(DateUtil.strToDate("2016-02-29T00:00:00Z", format), DateMatchers.sameDay(target));
        assertThat(DateUtil.strToDate("2016/02/29T00:00:00Z", format), DateMatchers.sameDay(target));
        assertThat(DateUtil.strToDate("2016-02-29", format), DateMatchers.sameDay(target));
        assertThat(DateUtil.strToDate("2016-02", format), DateMatchers.sameMonthOfYear(target));
        assertThat(DateUtil.strToDate("2016/02/29", format), DateMatchers.sameDay(target));
        assertThat(DateUtil.strToDate("2016年02月29日", format), DateMatchers.sameDay(target));
        assertThat(DateUtil.strToDate("20160229", format), nullValue());
    }
}