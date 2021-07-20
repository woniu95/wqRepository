package com.wq;


import com.wq.num.NumUtils;

import java.math.BigDecimal;

/**
 * @program: crm-cloud
 * @description: 测试CRM 活动
 * @author: 王强
 * @create: 2021-06-19 11:25
 */
public class TestActivity {

    // 奖品价格 单位分
    int prizePrice;
    // 最少人数
    int atLeastMember;
    // 最少新用户
    int atLeastNewMember;
    // 新用户最少砍价
    int newCutLeast;
    // 新用户最多砍价
    int newCutMax;
    // 老用户最少砍价
    int oldCutLeast;
    // 老用户最多砍价
    int oldCutMax;
    // 新用户砍价金额剩余池
    BigDecimal newPoolLave;
    // 旧用户砍价金额剩余池
    BigDecimal oldPoolLave;
    // 新用户砍价人数
    int newCutCount;
    // 已砍加人数
    int hadCutCount;
    //已砍金额
    BigDecimal hadCutPrice;

    public static void main(String[] args) {


        for(int batchCount = 0;batchCount<20;batchCount++){

            TestActivity testActivity = new TestActivity();
            testActivity.prizePrice = 5900;
            testActivity.atLeastMember = 9;
            testActivity.atLeastNewMember = 3;
            testActivity.newCutLeast = 0;
            testActivity.newCutMax = 90;
            testActivity.oldCutLeast = 0;
            testActivity.oldCutMax = 60;

            testActivity.initiate();


            int count =0;BigDecimal curPrice;
            long start = System.currentTimeMillis();
            while (count++ <500 && testActivity.hadCutPrice.intValue()<testActivity.prizePrice) {
                curPrice = testActivity.cut(NumUtils.getRandom(0,9)==1);
                if(curPrice.floatValue()>0){
                    System.out.println(testActivity);
                }

            }
            long end = System.currentTimeMillis();
            System.out.println("==============================cost time:"+(end-start)+"=========================================================================================================");
        }


    }

    public  TestActivity initiate(){
        int perMemberPrice = this.prizePrice/this.atLeastMember;
        int modLavePrice = this.prizePrice%this.atLeastMember;

        this.newPoolLave = new BigDecimal(perMemberPrice*this.atLeastNewMember+modLavePrice);
        this.oldPoolLave = new BigDecimal(perMemberPrice*(this.atLeastMember-this.atLeastNewMember));
        this.newCutCount = 0;
        this.hadCutCount = 0;
        this.hadCutPrice = new BigDecimal(0);
        return this;
    }

    public  BigDecimal cut(boolean isNewMember){

        int cutAdd = 1;
        int newCutAdd = isNewMember ? 1 : 0;
        BigDecimal cutPrice;

        this.hadCutCount+= cutAdd;
        this.newCutCount+= newCutAdd;

        //已满足人数
        if(this.hadCutCount >= this.atLeastMember && this.newCutCount >= this.atLeastNewMember
                && this.oldPoolLave.floatValue() < this.oldCutMax && this.newPoolLave.floatValue() < this.newCutMax){
            cutPrice = new BigDecimal(this.prizePrice).subtract(this.hadCutPrice);
            this.oldPoolLave = BigDecimal.ZERO;
            this.newPoolLave = BigDecimal.ZERO;
        }else{
            if(isNewMember){
                if(this.newPoolLave.intValue() >= this.newCutMax){
                    cutPrice = new BigDecimal(NumUtils.getRandomNotContains(this.newCutLeast, this.newCutMax));
                }else{

                    cutPrice = randomBigDecimal(new BigDecimal(0), this.newPoolLave);
                }
                this.newPoolLave = this.newPoolLave.subtract(cutPrice);
            }else{
                if(this.oldPoolLave.intValue() >= this.oldCutMax){
                    cutPrice = new BigDecimal(NumUtils.getRandomNotContains(this.oldCutLeast, this.oldCutMax));
                }else{
                    cutPrice = randomBigDecimal(new BigDecimal(0), this.oldPoolLave);
                }
                this.oldPoolLave = this.oldPoolLave.subtract(cutPrice);
            }
        }

        this.hadCutPrice = this.hadCutPrice.add(cutPrice);

        System.out.println("isNewMember:"+isNewMember+" cut price:"+cutPrice.toPlainString());


        return cutPrice;
    }


    /**
     * 生成 随机值
     * @param low
     * @param up
     * @return
     */
    public static BigDecimal randomBigDecimal(BigDecimal low, BigDecimal up){

        //生成随机数
        BigDecimal db =  NumUtils.randomBigDecimal(low, up);
        if(checkReRandom(db, low, up)){
            db = NumUtils.randomBigDecimal(low, db);
        }

        return db;
    }

    public static boolean checkReRandom(BigDecimal cutPrice, BigDecimal low, BigDecimal up){
        BigDecimal average = up.subtract(low).divide(new BigDecimal(2)).setScale(up.scale()+1);

        return cutPrice.compareTo(average)>0;
    }
    public static boolean checkReRandom(int cutPrice, int least, int max){

        int average = (max - least)/2;

        return cutPrice > average;
    }

    @Override
    public String toString() {
        return "{" +
                " newCutLeast=" + newCutLeast +
                ", newCutMax=" + newCutMax +
                ", oldCutLeast=" + oldCutLeast +
                ", oldCutMax=" + oldCutMax +
                ", newPoolLave=" + newPoolLave.toPlainString() +
                ", oldPoolLave=" + oldPoolLave.toPlainString() +
                ", newCutCount=" + newCutCount +
                ", hadCutCount=" + hadCutCount +
                ", hadCutPrice=" + hadCutPrice +
                '}';
    }
}
