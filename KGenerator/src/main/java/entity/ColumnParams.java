package entity;

import lombok.Data;

/**
 * @Date: 0:14 2019/6/8
 * @Author: Kermit Sun
 * @Description:
 */
@Data
public class ColumnParams {
    private String columnName;
    private String importPackage;
    private String typeName;
    private String remark;
}