package algorithm.bisearch;

/**
 * @PackageName:algorithm.bisearch
 * @ClassName:BadVersion
 * @Description:
 * @author: wq
 * @date 2022/2/3 19:20
 */
public class BadVersion {


    /* The isBadVersion API is defined in the parent class VersionControl.
      boolean isBadVersion(int version); */
    public static int firstBadVersion(int n) {
        int start=1;
        int end=n;
        int mid;
        while(start<end){
            mid = start + ((end - start)>>1);
            if(isBadVersion(mid)){
                end = mid;
            } else{
                start =  mid+1;
            }
        }
        return start;
    }

    private static int FIRST_BAD_VERSION = 2022222222;
    static boolean isBadVersion(long version){
        return version >= FIRST_BAD_VERSION;
    }

    public static void main(String[] args) {
        System.out.println(firstBadVersion(2126753390));
    }
}
