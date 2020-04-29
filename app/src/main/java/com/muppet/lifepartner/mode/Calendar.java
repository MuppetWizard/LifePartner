package com.muppet.lifepartner.mode;


import com.muppet.lifepartner.util.JsonRespondParse;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;

@HttpResponse(parser = JsonRespondParse.class)
public class Calendar implements Serializable {


    /**
     * reason : Success
     * result : {"data":{"avoid":"开市.动土.掘井.开池.","animalsYear":"猪","weekday":"星期四","suit":"祭祀.祈福.开光.求嗣.斋醮.纳采.订盟.求医.治病.起基.定磉.造船.取渔.解除.安葬.启攒.谢土.入殓.","lunarYear":"己亥年","lunar":"三月十四","year-month":"2019-4","date":"2019-4-18"}}
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean implements Serializable{
        /**
         * data : {"avoid":"开市.动土.掘井.开池.","animalsYear":"猪","weekday":"星期四","suit":"祭祀.祈福.开光.求嗣.斋醮.纳采.订盟.求医.治病.起基.定磉.造船.取渔.解除.安葬.启攒.谢土.入殓.","lunarYear":"己亥年","lunar":"三月十四","year-month":"2019-4","date":"2019-4-18"}
         */

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean implements Serializable {
            /**
             * avoid : 开市.动土.掘井.开池.
             * animalsYear : 猪
             * weekday : 星期四
             * suit : 祭祀.祈福.开光.求嗣.斋醮.纳采.订盟.求医.治病.起基.定磉.造船.取渔.解除.安葬.启攒.谢土.入殓.
             * lunarYear : 己亥年
             * lunar : 三月十四
             * year-month : 2019-4
             * date : 2019-4-18
             */

            private String avoid;
            private String animalsYear;
            private String weekday;
            private String suit;
            private String lunarYear;
            private String lunar;
            private String yearmonth;
            private String date;

            public String getAvoid() {
                return avoid;
            }

            public void setAvoid(String avoid) {
                this.avoid = avoid;
            }

            public String getAnimalsYear() {
                return animalsYear;
            }

            public void setAnimalsYear(String animalsYear) {
                this.animalsYear = animalsYear;
            }

            public String getWeekday() {
                return weekday;
            }

            public void setWeekday(String weekday) {
                this.weekday = weekday;
            }

            public String getSuit() {
                return suit;
            }

            public void setSuit(String suit) {
                this.suit = suit;
            }

            public String getLunarYear() {
                return lunarYear;
            }

            public void setLunarYear(String lunarYear) {
                this.lunarYear = lunarYear;
            }

            public String getLunar() {
                return lunar;
            }

            public void setLunar(String lunar) {
                this.lunar = lunar;
            }

            public String getYearmonth() {
                return yearmonth;
            }

            public void setYearmonth(String yearmonth) {
                this.yearmonth = yearmonth;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }
    }
}
