package algorithm;

import java.util.ArrayList;

/**
 *   每次卖更新最大利润
 *   MAX( 之前卖最大总共赚+ 卖那天之后截止今天最大总共赚,  直接一次买卖赚（今天-最小-2）)
 *                   2  0  6  -4 5
 *                [0, 0, 2, 2, 4, 4, 5,     4|4]
 *
 * 输入: prices = [1, 3, 5, 5, 9, 11, 14, 16], fee = 2
 * 输出: 8
 * 解释: 能够达到的最大利润:
 * 在此处买入 prices[0] = 1
 * 在此处卖出 prices[3] = 8
 * 在此处买入 prices[4] = 4
 * 在此处卖出 prices[5] = 9
 * 总利润: ((8 - 1) - 2) + ((9 - 4) - 2) = 8.
 *
 * Max(sum(sell[j] - buy[i] - 2)) 0 < i < j < prices.length
 *
 * 是否持有 是 无法买入，可以卖出 否 可以买入，不能卖出
 *
 * @program: wqIntegration
 * @description:
 * @author: 王强
 * @create: 2020-12-17 17:52
 */
public class ChoiceSellMaxEarn {

    public static void main(String[] args) {

        int[] prices = {1, 4, 2, 8, 4, 9};
        int fee = 2;
        int hold = 0;
        ArrayList<Integer> operates = new ArrayList<Integer>();
        int day = 0;
        int preSellDay = 0;
        int preSellCanEarn = 0;
        int dayCanEarn = 0;
        int preMin = 0;
        while(day<prices.length){


            if(preMin>prices[day]){
                preMin = prices[day];
            }

            if(preSellDay>0){
                int afterCanEarn = 0;
                if(preSellDay+1 < day){
                    int min = prices[preSellDay+1];
                    for(int i= preSellDay+1;i<day;i++){
                        if(prices[i]<min){
                            min = prices[i];
                        }
                    }
                    afterCanEarn = prices[day] - min -fee;
                }

                int earn = 0;

                if(prices[day] > prices[preSellDay]+fee){
                    earn = prices[day] - prices[preSellDay];
                }
                dayCanEarn = Math.max(earn+preSellCanEarn, afterCanEarn+preSellCanEarn);

                dayCanEarn = Math.max(dayCanEarn, prices[day] - preMin);

                if(dayCanEarn > preSellCanEarn){
                    preSellDay = day;
                    preSellCanEarn = dayCanEarn;
                }

            }else{
                int i=1;
                while(day+i<prices.length){
                    if(prices[day+i] - prices[day+i-1]-2 > 0){

                        preSellCanEarn = prices[day+i] - prices[day+i-1]-2;
                        preSellDay = day+i;
                        day = day+i-1;

                        break;
                    }
                }

            }

            day++;
        }
        System.out.println(preSellCanEarn);
        System.out.println(getMaxEarnUseBrute(0,0,0, prices));

    }

//    0:nothing 1:buy 2:sell
    static int getMaxEarnUseBrute(int day, int hold, int preEarn, int[] prices){

        int sellMaxEarn = preEarn;
        int buyMaxEarn = preEarn;
        int noMaxEarn = preEarn;
        int nextDay = day + 1;
        if(day<prices.length){
            if(hold > 0){
                //SELL
                int dayEarn = prices[day] - hold -2;
                if(dayEarn > 0 ){
                    int currentEarn = preEarn + dayEarn;
                    sellMaxEarn = getMaxEarnUseBrute(nextDay, 0, currentEarn, prices);
                }
            }else{
                // buy
                    int currentHold = prices[day];
                    buyMaxEarn = getMaxEarnUseBrute(nextDay, currentHold, preEarn, prices);
            }
            //NO
            noMaxEarn = getMaxEarnUseBrute(nextDay, hold, preEarn, prices);
        }
        return Math.max( Math.max(sellMaxEarn, buyMaxEarn), noMaxEarn);
    }
}
