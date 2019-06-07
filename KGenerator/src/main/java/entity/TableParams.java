package entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Date: 23:50 2019/6/7
 * @Author: Kermit Sun
 * @Description:
 */
@Data
public class TableParams {
    //config.json中params的配置
    Map<String, String> configParams;
    //表名
    private String name;
    //表注释
    private String remark;

    List<ColumnParams> columns;
}
