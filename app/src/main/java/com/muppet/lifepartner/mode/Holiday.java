package com.muppet.lifepartner.mode;

import com.muppet.lifepartner.util.JsonRespondParse;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;
import java.util.List;

@HttpResponse(parser = JsonRespondParse.class)
public class Holiday implements Serializable {

    /**
     * reason : Success
     * result : {"data":{"year":"2019","year-month":"2019-6","holiday":"[{\"desc\":\"5月1日放假\",\"festival\":\"2019-5-1\",\"list\":[{\"date\":\"2019-5-1\",\"status\":\"1\"},{\"date\":\"2019-5-2\",\"status\":\"1\"},{\"date\":\"2019-5-3\",\"status\":\"1\"},{\"date\":\"2019-5-4\",\"status\":\"1\"},{\"date\":\"2019-4-28\",\"status\":\"2\"},{\"date\":\"2019-5-5\",\"status\":\"2\"}],\"list#num#\":6,\"name\":\"劳动节\",\"rest\":\"拼假建议：4月28日（周日）~4月30日（周二）请假3天，可拼8天劳动节小长假\"},{\"desc\":\"6月7日放假，与周末连休。\",\"festival\":\"2019-6-7\",\"list\":[{\"date\":\"2019-6-7\",\"status\":\"1\"},{\"date\":\"2019-6-8\",\"status\":\"1\"},{\"date\":\"2019-6-9\",\"status\":\"1\"}],\"list#num#\":3,\"name\":\"端午节\",\"rest\":\"拼假建议：2019年6月3日（周一）~2019年6月6日（周四）请假4天，可拼9天端午节小长假\"}]","holiday_array":[{"desc":"5月1日放假","festival":"2019-5-1","list":[{"date":"2019-5-1","status":"1"},{"date":"2019-5-2","status":"1"},{"date":"2019-5-3","status":"1"},{"date":"2019-5-4","status":"1"},{"date":"2019-4-28","status":"2"},{"date":"2019-5-5","status":"2"}],"list#num#":6,"name":"劳动节","rest":"拼假建议：4月28日（周日）~4月30日（周二）请假3天，可拼8天劳动节小长假","list_num":6},{"desc":"6月7日放假，与周末连休。","festival":"2019-6-7","list":[{"date":"2019-6-7","status":"1"},{"date":"2019-6-8","status":"1"},{"date":"2019-6-9","status":"1"}],"list#num#":3,"name":"端午节","rest":"拼假建议：2019年6月3日（周一）~2019年6月6日（周四）请假4天，可拼9天端午节小长假","list_num":3}]}}
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
         * data : {"year":"2019","year-month":"2019-6","holiday":"[{\"desc\":\"5月1日放假\",\"festival\":\"2019-5-1\",\"list\":[{\"date\":\"2019-5-1\",\"status\":\"1\"},{\"date\":\"2019-5-2\",\"status\":\"1\"},{\"date\":\"2019-5-3\",\"status\":\"1\"},{\"date\":\"2019-5-4\",\"status\":\"1\"},{\"date\":\"2019-4-28\",\"status\":\"2\"},{\"date\":\"2019-5-5\",\"status\":\"2\"}],\"list#num#\":6,\"name\":\"劳动节\",\"rest\":\"拼假建议：4月28日（周日）~4月30日（周二）请假3天，可拼8天劳动节小长假\"},{\"desc\":\"6月7日放假，与周末连休。\",\"festival\":\"2019-6-7\",\"list\":[{\"date\":\"2019-6-7\",\"status\":\"1\"},{\"date\":\"2019-6-8\",\"status\":\"1\"},{\"date\":\"2019-6-9\",\"status\":\"1\"}],\"list#num#\":3,\"name\":\"端午节\",\"rest\":\"拼假建议：2019年6月3日（周一）~2019年6月6日（周四）请假4天，可拼9天端午节小长假\"}]","holiday_array":[{"desc":"5月1日放假","festival":"2019-5-1","list":[{"date":"2019-5-1","status":"1"},{"date":"2019-5-2","status":"1"},{"date":"2019-5-3","status":"1"},{"date":"2019-5-4","status":"1"},{"date":"2019-4-28","status":"2"},{"date":"2019-5-5","status":"2"}],"list#num#":6,"name":"劳动节","rest":"拼假建议：4月28日（周日）~4月30日（周二）请假3天，可拼8天劳动节小长假","list_num":6},{"desc":"6月7日放假，与周末连休。","festival":"2019-6-7","list":[{"date":"2019-6-7","status":"1"},{"date":"2019-6-8","status":"1"},{"date":"2019-6-9","status":"1"}],"list#num#":3,"name":"端午节","rest":"拼假建议：2019年6月3日（周一）~2019年6月6日（周四）请假4天，可拼9天端午节小长假","list_num":3}]}
         */

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean implements Serializable{
            /**
             * year : 2019
             * year-month : 2019-6
             * holiday : [{"desc":"5月1日放假","festival":"2019-5-1","list":[{"date":"2019-5-1","status":"1"},{"date":"2019-5-2","status":"1"},{"date":"2019-5-3","status":"1"},{"date":"2019-5-4","status":"1"},{"date":"2019-4-28","status":"2"},{"date":"2019-5-5","status":"2"}],"list#num#":6,"name":"劳动节","rest":"拼假建议：4月28日（周日）~4月30日（周二）请假3天，可拼8天劳动节小长假"},{"desc":"6月7日放假，与周末连休。","festival":"2019-6-7","list":[{"date":"2019-6-7","status":"1"},{"date":"2019-6-8","status":"1"},{"date":"2019-6-9","status":"1"}],"list#num#":3,"name":"端午节","rest":"拼假建议：2019年6月3日（周一）~2019年6月6日（周四）请假4天，可拼9天端午节小长假"}]
             * holiday_array : [{"desc":"5月1日放假","festival":"2019-5-1","list":[{"date":"2019-5-1","status":"1"},{"date":"2019-5-2","status":"1"},{"date":"2019-5-3","status":"1"},{"date":"2019-5-4","status":"1"},{"date":"2019-4-28","status":"2"},{"date":"2019-5-5","status":"2"}],"list#num#":6,"name":"劳动节","rest":"拼假建议：4月28日（周日）~4月30日（周二）请假3天，可拼8天劳动节小长假","list_num":6},{"desc":"6月7日放假，与周末连休。","festival":"2019-6-7","list":[{"date":"2019-6-7","status":"1"},{"date":"2019-6-8","status":"1"},{"date":"2019-6-9","status":"1"}],"list#num#":3,"name":"端午节","rest":"拼假建议：2019年6月3日（周一）~2019年6月6日（周四）请假4天，可拼9天端午节小长假","list_num":3}]
             */

            private String year;
            private String yearmonth;
            private String holiday;
            private List<HolidayArrayBean> holiday_array;

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            public String getYearmonth() {
                return yearmonth;
            }

            public void setYearmonth(String yearmonth) {
                this.yearmonth = yearmonth;
            }

            public String getHoliday() {
                return holiday;
            }

            public void setHoliday(String holiday) {
                this.holiday = holiday;
            }

            public List<HolidayArrayBean> getHoliday_array() {
                return holiday_array;
            }

            public void setHoliday_array(List<HolidayArrayBean> holiday_array) {
                this.holiday_array = holiday_array;
            }

            public static class HolidayArrayBean implements Serializable{
                /**
                 * desc : 5月1日放假
                 * festival : 2019-5-1
                 * list : [{"date":"2019-5-1","status":"1"},{"date":"2019-5-2","status":"1"},{"date":"2019-5-3","status":"1"},{"date":"2019-5-4","status":"1"},{"date":"2019-4-28","status":"2"},{"date":"2019-5-5","status":"2"}]
                 * list#num# : 6
                 * name : 劳动节
                 * rest : 拼假建议：4月28日（周日）~4月30日（周二）请假3天，可拼8天劳动节小长假
                 * list_num : 6
                 */

                private String desc;
                private String festival;
                private int _$ListNum267; // FIXME check this code
                private String name;
                private String rest;
                private int list_num;
                private List<ListBean> list;

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public String getFestival() {
                    return festival;
                }

                public void setFestival(String festival) {
                    this.festival = festival;
                }

                public int get_$ListNum267() {
                    return _$ListNum267;
                }

                public void set_$ListNum267(int _$ListNum267) {
                    this._$ListNum267 = _$ListNum267;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getRest() {
                    return rest;
                }

                public void setRest(String rest) {
                    this.rest = rest;
                }

                public int getList_num() {
                    return list_num;
                }

                public void setList_num(int list_num) {
                    this.list_num = list_num;
                }

                public List<ListBean> getList() {
                    return list;
                }

                public void setList(List<ListBean> list) {
                    this.list = list;
                }

                public static class ListBean implements Serializable{
                    /**
                     * date : 2019-5-1
                     * status : 1
                     */

                    private String date;
                    private String status;

                    public String getDate() {
                        return date;
                    }

                    public void setDate(String date) {
                        this.date = date;
                    }

                    public String getStatus() {
                        return status;
                    }

                    public void setStatus(String status) {
                        this.status = status;
                    }
                }
            }
        }
    }
}
