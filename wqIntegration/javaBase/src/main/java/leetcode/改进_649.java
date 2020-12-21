package leetcode;

import java.util.LinkedList;

public class 改进_649 {


    // 更好解决方案 每轮重构字符串 两整容各自投票数即可 不需使用队列
    public static class StackHolder{


        LinkedList<Integer> rWaitVote = new LinkedList();
        LinkedList<Integer> dWaitVote = new LinkedList();

        LinkedList<Integer> rVotedAlive = new LinkedList();
        LinkedList<Integer> dVotedAlive = new LinkedList();

        public void init(String senate){
            for (int i = 0, length=senate.length(); i < length; i++) {
                Character ch = senate.charAt(i);
                if('R' == ch ){
                    rWaitVote.add(i);
                }else{
                    dWaitVote.add(i);
                }
            }
        }

        public boolean hasNextVote(){
            return rWaitVote.size()>0 || dWaitVote.size()>0;
        }
        public String nextVote(){

            if(hasNextVote()){
                if(rWaitVote.size()>0 && dWaitVote.size()==0){
                    rVotedAlive.add(rWaitVote.pollFirst());
                    if(null == dWaitVote.pollFirst()){
                        dVotedAlive.pollFirst();
                    }
                }else if(dWaitVote.size()>0 && rWaitVote.size()==0){
                    dVotedAlive.add(dWaitVote.pollFirst());
                    if(null == rWaitVote.pollFirst()){
                        rVotedAlive.pollFirst();
                    }
                }else if(rWaitVote.getFirst() < dWaitVote.getFirst()){
                    rVotedAlive.add(rWaitVote.pollFirst());
                    if(null == dWaitVote.pollFirst()){
                        dVotedAlive.pollFirst();
                    }
                }else if(dWaitVote.getFirst() < rWaitVote.getFirst()){
                    dVotedAlive.add(dWaitVote.pollFirst());
                    if(null == rWaitVote.pollFirst()){
                        rVotedAlive.pollFirst();
                    }
                }
            }

            return checkOver();
        }

        public void exchange(){
            LinkedList<Integer> temp = rWaitVote;
            rWaitVote = rVotedAlive;
            rVotedAlive = temp;
            temp = dWaitVote;
            dWaitVote = dVotedAlive;
            dVotedAlive = temp;
        }

        public String checkOver(){

            if(rWaitVote.size() == 0 && rVotedAlive.size() == 0){
                return "Dire";
            }
            if(dWaitVote.size() == 0 && dVotedAlive.size() == 0){
                return "Radiant";
            }
            return null;
        }
    }


    public  String predictPartyVictory(String senate) {

        StackHolder stackHolder = new StackHolder();
        stackHolder.init(senate);
        String result = stackHolder.checkOver();

        while(result==null){
            while(stackHolder.hasNextVote() && result==null){
               result = stackHolder.nextVote();
            }
            stackHolder.exchange();
        }
        return result;
    }


    public static void main(String[] args) {
        改进_649 resolve = new 改进_649();
        System.out.println(resolve.predictPartyVictory("DRRDRDRDRDDRDRDR"));
    }
}
