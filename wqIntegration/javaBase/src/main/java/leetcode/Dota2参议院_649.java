package leetcode;

import java.util.ArrayList;
import java.util.List;

public class Dota2参议院_649 {


    //"DRRDRDRDRDDRDRDR"
    public  String predictPartyVictory(String senate) {
        // R:true D:false del: null
        List<Boolean> senates = new ArrayList();
        for(int i=0;i<senate.length();i++){
            if('R' == senate.charAt(i) ){
                senates.add(true);
            }else{
                senates.add(false);
            }
        }

        while(senates.size()>0){
            int existCount = senates.size();
            for(int index = 0, length = senates.size(); index<length; index++){
                Boolean current = senates.get(index);
                if(current!= null){
                    int diffIndex = getAfterOrCycle(senates, !current, index);
                    if(diffIndex == -1){
                        return convertResult(current);
                    }else{
                        senates.set(diffIndex, null);
                        existCount--;
                    }
                }
            }
            senates = copyNotNull(senates, existCount);
        }
        return "Dire";
    }

    public int getAfterOrCycle(List<Boolean> senates, Boolean tartget, int nowIndex){
        int result = -1;
        for(int index = nowIndex+1, length = senates.size(); index<length; index++){
            if(senates.get(index)!= null && tartget.equals(senates.get(index)) ){
                result = index;
                break;
            }
        }

        if(result == -1){
            for(int index=0;index<nowIndex;index++){
                if(senates.get(index)!= null && tartget.equals(senates.get(index)) ){
                    result = index;
                    break;
                }
            }
        }
        return result;
    }

    public List<Boolean> copyNotNull(List<Boolean> senates, int existCount){
        List<Boolean> existSenates = new ArrayList<Boolean>(existCount+1);
        for(Boolean senate : senates){
            if(senate!=null){
                existSenates.add(senate);
            }
        }
        return existSenates;
    }

    public String convertResult(Boolean needConvert){
        return needConvert ? "Radiant" : "Dire";
    }

    public static void main(String[] args) {
        Dota2参议院_649 dota2参议院649 = new Dota2参议院_649();
        System.out.println(dota2参议院649.predictPartyVictory("DRRDRDRDRDDRDRDR"));
    }

}
