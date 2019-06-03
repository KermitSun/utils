package kermit.utils.basic.enums;

import kermit.utils.basic.exception.NotEnumException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Date: 2019/4/2313:38
 * @Author: BoyuSun
 * @Description: 枚举工具
 */
public class KEnumUtil{
    public static <T extends KEnum> T getById(Class<T> clazz, Integer id){
        if(notEnum(clazz)){
            throw new NotEnumException("传入非枚举类");
        }
        T[] values = values(clazz);
        for(T value:values){
            if(value.getId().equals(id)){
                return value;
            }
        }
        return null;
    }
    public static <T extends KEnum> T getByDesc(Class<T> clazz, String desc){
        if(notEnum(clazz)){
            throw new NotEnumException("传入非枚举类");
        }
        T[] values = values(clazz);
        for(T value:values){
            if(value.getDesc().equals(desc)){
                return value;
            }
        }
        return null;
    }
    public static <T extends KEnum> Boolean haveId(Class<T> clazz, Integer id){
        return getById(clazz, id) != null;
    }
    public static <T extends KEnum> Boolean haveDesc(Class<T> clazz, String desc){
        return getByDesc(clazz, desc) != null;
    }

    private static boolean isEnum(Class clazz){
        return clazz.isEnum();
    }
    private static boolean notEnum(Class clazz){
        return !isEnum(clazz);
    }
    private static <T extends KEnum> T[] values(Class<T> clazz){
        try {
            Method method = clazz.getMethod("values");
            return (T[]) method.invoke(clazz);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
