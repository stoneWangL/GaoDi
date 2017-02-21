package com.example.stonewang.gaodi.db;

import com.example.stonewang.gaodi.db.Data;

import java.util.List;

/**
 * Created by stoneWang on 2017/2/21.
 */

public class JsonBean {

    public String reason;
    public Result result;
    public int error_code;

    public static class Result{

        public String stat;

        public List<Data> data;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

}
