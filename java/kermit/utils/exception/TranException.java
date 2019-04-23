package kermit.utils.exception;

/**
 * @Date: 2019/4/2313:49
 * @Author: BoyuSun
 * @Description: 转换异常
 */
public class TranException extends RuntimeException {
    public TranException(String s) {
        super(s);
    }
    public TranException(){
        super();
    }
}
