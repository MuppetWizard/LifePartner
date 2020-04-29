package com.muppet.lifepartner.mode;

import com.muppet.lifepartner.util.JsonRespondParse;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;
import java.util.List;

@HttpResponse(parser = JsonRespondParse.class)
public class ExCompany implements Serializable {

    /**
     * resultcode : 200
     * reason : 查询支持的快递公司成功
     * result : [{"com":"顺丰","no":"sf"},{"com":"申通","no":"sto"},{"com":"圆通","no":"yt"},{"com":"韵达","no":"yd"},{"com":"天天","no":"tt"}]
     * error_code : 0
     */

    private String resultcode;
    private String reason;
    private int error_code;
    private List<ResultBean> result;

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

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        /**
         * com : 顺丰
         * no : sf
         */

        private String com;
        private String no;

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
    }
}
