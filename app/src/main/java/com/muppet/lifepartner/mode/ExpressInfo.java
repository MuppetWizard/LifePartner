package com.muppet.lifepartner.mode;

import com.muppet.lifepartner.util.JsonRespondParse;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;
import java.util.List;

@HttpResponse(parser = JsonRespondParse.class)
public class ExpressInfo implements Serializable {
    /**
     * resultcode : 200
     * reason : 查询成功
     * result : {"company":"圆通","com":"yt","no":"805998172010856441","status":"1","status_detail":"SIGNED","list":[{"datetime":"2019-05-22 19:21:12","remark":"浙江省温州市平阳县公司取件人:潘志东（13868557152）已收件","zone":""},{"datetime":"2019-05-22 20:13:55","remark":"快件已发往温州转运中心","zone":""},{"datetime":"2019-05-22 21:34:49","remark":"快件已到达温州转运中心","zone":""},{"datetime":"2019-05-22 21:47:34","remark":"快件已发往成都转运中心","zone":""},{"datetime":"2019-05-24 09:39:02","remark":"快件已到达成都转运中心","zone":""},{"datetime":"2019-05-24 10:35:21","remark":"快件已发往四川省成都市高新区三部公司","zone":""},{"datetime":"2019-05-24 12:39:29","remark":"快件已到达四川省成都市高新区三部公司","zone":""},{"datetime":"2019-05-24 13:11:37","remark":"四川省成都市高新区三部公司派件人:张杰（18328138847）正在派件","zone":""},{"datetime":"2019-05-24 15:33:36","remark":"快件已存放至成都职业技术学院速递易【自提柜】，请及时取件。有问题请联系派件员17844601876","zone":""},{"datetime":"2019-05-24 18:20:12","remark":"快件已签收签收人:已签收，签收人凭取货码签收。感谢使用圆通速递，期待再次为您服务","zone":""}]}
     * error_code : 0
     */

    private String resultcode;
    private String reason;
    private ResultBean result;
    private int error_code;  //错误码，0表示查询正常，其他表示查询不到物流信息或发生了其他错误

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

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
         * company : 圆通
         * com : yt
         * no : 805998172010856441
         * status : 1
         * status_detail : SIGNED
         * list : [{"datetime":"2019-05-22 19:21:12","remark":"浙江省温州市平阳县公司取件人:潘志东（13868557152）已收件","zone":""},{"datetime":"2019-05-22 20:13:55","remark":"快件已发往温州转运中心","zone":""},{"datetime":"2019-05-22 21:34:49","remark":"快件已到达温州转运中心","zone":""},{"datetime":"2019-05-22 21:47:34","remark":"快件已发往成都转运中心","zone":""},{"datetime":"2019-05-24 09:39:02","remark":"快件已到达成都转运中心","zone":""},{"datetime":"2019-05-24 10:35:21","remark":"快件已发往四川省成都市高新区三部公司","zone":""},{"datetime":"2019-05-24 12:39:29","remark":"快件已到达四川省成都市高新区三部公司","zone":""},{"datetime":"2019-05-24 13:11:37","remark":"四川省成都市高新区三部公司派件人:张杰（18328138847）正在派件","zone":""},{"datetime":"2019-05-24 15:33:36","remark":"快件已存放至成都职业技术学院速递易【自提柜】，请及时取件。有问题请联系派件员17844601876","zone":""},{"datetime":"2019-05-24 18:20:12","remark":"快件已签收签收人:已签收，签收人凭取货码签收。感谢使用圆通速递，期待再次为您服务","zone":""}]
         */

        private String company;
        private String com;
        private String no;
        private String status;//1表示此快递单的物流信息不会发生变化，此时您可缓存下来；0表示有变化的可能性
        private List<ListBean> list;
        private String status_detail;/*详细的状态信息，可能为null，仅作参考。其中：
        PENDING 待查询
        NO_RECORD 无记录
        ERROR 查询异常
        IN_TRANSIT 运输中
        DELIVERING 派送中
        SIGNED 已签收
        REJECTED 拒签
        PROBLEM 疑难件
        INVALID 无效件
        TIMEOUT 超时件
        FAILED 派送失败
        SEND_BACK 退回
        TAKING 揽件*/

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCom() {
            return com;
        }

        public void setCom(String com) {
            this.com = com;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus_detail() {
            return status_detail;
        }

        public void setStatus_detail(String status_detail) {
            this.status_detail = status_detail;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable{
            /**
             * datetime : 2019-05-22 19:21:12
             * remark : 浙江省温州市平阳县公司取件人:潘志东（13868557152）已收件
             * zone :
             */

            private String datetime;
            private String remark;
            private String zone;

            public String getDatetime() {
                return datetime;
            }

            public void setDatetime(String datetime) {
                this.datetime = datetime;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getZone() {
                return zone;
            }

            public void setZone(String zone) {
                this.zone = zone;
            }
        }
    }
}
