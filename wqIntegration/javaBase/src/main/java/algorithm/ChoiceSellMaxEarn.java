package algorithm;

/**
 * 714
 *
 * 之前卖最大总共赚+ 卖那天之后截止今天最大总共赚,  直接一次买卖赚（今天-最小-2）)
 *
 * 输入: prices = [1, 3, 5, 5, 9, 11, 14, 16], fee = 2
 *
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
        System.out.println(getMaxEarnUseDP(prices, fee));

    }

    /**
     * dp 问题关键： 是找到一个自己想的通的且正确的 dp 结构和含义
     * 是否持有
     *      是 无法买入，可以卖出
     *      否 可以买入，不能卖出
     *
     * dp[i][0]: 第i天手上无持有情况最大利润, dp[i][1]:第i天手上持有情况最大利润
     * dp[i+1][0] = Max(dp[i][0], dp[i][1]+prices[i]-fee)
     * dp[i+1][1] = Max(dp[i][0]-prices[i+1], dp[i][1])
     * @param prices
     * @param fee
     * @return
     */
    static int getMaxEarnUseDP(int[] prices, int fee){
        int maxEarn = 0;

        int day =0;
        int holdEarn = -prices[day];
        int notHoldEarn = 0;
        day++;
        while(day<prices.length){
            int preHoldEarn = holdEarn;
            int preNotHoldEarn = notHoldEarn;
            holdEarn = Math.max(preHoldEarn, preNotHoldEarn-prices[day]);
            notHoldEarn = Math.max(preNotHoldEarn, preHoldEarn+prices[day]-fee);
            maxEarn = Math.max(holdEarn, notHoldEarn);
            day++;
        }
        return maxEarn;
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
