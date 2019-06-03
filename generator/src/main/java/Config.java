import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Date: 2019/6/312:59
 * @Author: BoyuSun
 * @Description: 读取配置文件
 */
public class Config {
    /**
     *@Date: 13:08 2019/6/3
     *@Description: 读取json文件，返回json字符串
     */
    public static String read(String path) throws Exception{
        StringBuffer sb = new StringBuffer();
        try(InputStream is = Config.class.getResourceAsStream(path);
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);){
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }
}
