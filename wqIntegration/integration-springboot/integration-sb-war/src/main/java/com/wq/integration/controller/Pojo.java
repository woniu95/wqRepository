package com.wq.integration.controller;

/**
 * @PackageName:com.wq.integration.spring.boot.controller
 * @ClassName:Pojo
 * @Description:
 * @author: wq
 * @date 2021/11/10 22:28
 */
public class Pojo {
    private Long aLong;

    public Long getaLong() {
        return aLong;
    }

    public void setaLong(Long aLong) {
        this.aLong = aLong;
    }

    @Override
    public String toString() {
        return "Pojo{" +
                "aLong=" + aLong +
                '}';
    }
}
