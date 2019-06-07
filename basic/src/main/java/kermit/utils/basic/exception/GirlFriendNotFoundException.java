package kermit.utils.basic.exception;

/**
 * @Date: 21:01 2019/6/7
 * @Author: Kermit Sun
 * @Description: this is the common exception
 */
public class GirlFriendNotFoundException extends RuntimeException {
    public GirlFriendNotFoundException(String s) {
        super(s);
    }
    public GirlFriendNotFoundException(){
        super();
    }
}