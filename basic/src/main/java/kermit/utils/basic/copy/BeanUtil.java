package kermit.utils.basic.copy;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.*;

/**
 * @Date: 2019/4/2314:17
 * @Author: BoyuSun
 * @Description: BeanUtils扩展
 * org.apache.commons.beanutils.BeanUtils的
 */
public class BeanUtil extends BeanUtils {
    private BeanUtil() {}

    static {
        //注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空
        ConvertUtils.register(new SqlDateConverter(null), java.sql.Date.class);
        ConvertUtils.register(new SqlDateConverter(null), java.util.Date.class);
        ConvertUtils.register(new SqlTimestampConverter(null), java.sql.Timestamp.class);
        ConvertUtils.register(new IntegerConverter(null), Integer.class);
        ConvertUtils.register(new DoubleConverter(null), Double.class);
        ConvertUtils.register(new FloatConverter(null), Float.class);
        ConvertUtils.register(new LongConverter(null), Long.class);
    }
}
