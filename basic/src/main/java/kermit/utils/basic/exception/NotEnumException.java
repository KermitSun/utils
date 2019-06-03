package kermit.utils.basic.exception;

/**
 * @Date: 2019/4/2313:49
 * @Author: BoyuSun
 * @Description: 转换异常
 */
public class NotEnumException extends RuntimeException {
    public NotEnumException(String s) {
        super(s);
    }
    public NotEnumException(){
        super();
    }
}
