package utils;

import com.alibaba.fastjson.JSON;
import entity.*;
import transaction.ColumnNameTransaction;
import transaction.DBTypeTransaction;
import transaction.ParamsTrans;
import transaction.TableNameTransaction;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Date: 22:09 2019/6/7
 * @Author: Kermit Sun
 * @Description: 构建工具
 */
public class BuildUtil {
    public void start() {
        try {
            //获取所有配置文件
            String configJson = ConfigUtil.read("/config.json");
            Map<String, List<String>> map = JSON.parseObject(configJson, Map.class);
            List<String> items = map.get("configs");
            //逐一处理每个config.json文件
            for (String item : items) {
                //记牌器cpmfog.json文本内容
                String itemConfigJson = ConfigUtil.read(item);
                //文本转换为配置对象
                Config config = ConfigUtil.createConfig(itemConfigJson);
                //处理配置对象
                analysisConfig(config);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *@Date: 23:47 2019/6/7
     *@Author: Kermit Sun
     *@Description: 解析Congfig
     */
    private static void analysisConfig(Config config) throws SQLException, IOException {
        //获取需要解析的模板
        List<String> templateNames = config.getExport().getItems().stream().map(item -> item.getTemplateName()).collect(Collectors.toList());
        Map<String, Template> templateMap = TemplateUtil.readTemplates(templateNames);
        //获取需要解析的表
        List<String> dbTableNames = config.getDb().getTables();
        //获取数据库连接
        DB db = config.getDb();
        Connection conn = JDBCUtil.getConn(db);
        DatabaseMetaData metaData = conn.getMetaData();
        //获取表
        ResultSet tableRs = metaData.getTables(db.getCatalog(), db.getSchemaPattern(), db.getTableNamePattern(), db.getTypes());
        while (tableRs.next()) {
            TableParams tableParams = new TableParams();
            String dbTableName = tableRs.getString("TABLE_NAME");
            //获取表数据
            if (dbTableNames.isEmpty() || dbTableNames.contains(dbTableName)) {
                tableParams.setConfigParams(config.getParams());
                String tableName = TableNameTransaction.trans(dbTableName);
                tableParams.setName(tableName);
                tableParams.setRemark(tableRs.getString("REMARKS"));
                ResultSet columnRs = metaData.getColumns(null, null, dbTableName, null);
                //获取列数据
                while(columnRs.next()){
                    ColumnParams columnParams = new ColumnParams();
                    String dbColumnName = columnRs.getString("COLUMN_NAME");
                    columnParams.setColumnName(ColumnNameTransaction.trans(dbColumnName));
                    String dbColumnType = columnRs.getString("TYPE_NAME");
                    int columnSize = columnRs.getInt("COLUMN_SIZE");
                    Map<String, String> trans = DBTypeTransaction.trans(dbColumnType, columnSize);
                    columnParams.setImportPackage(trans.get("importPackage"));
                    columnParams.setTypeName(trans.get("typeName"));
                    columnParams.setRemark(columnRs.getString("REMARKS"));
                }
                String outPath = config.getExport().getOutPath();
                if(config.getExport().isClearDirs()){
                    FileUtil.empty(outPath);
                }
                Map<String, String> params = ParamsTrans.trans(tableParams);
                //逐个生成模板
                for (Item item : config.getExport().getItems()) {
                    Template template = templateMap.get(item.getTemplateName());
                    String filePath = (outPath + item.getOutPath() + item.getFileName()).replaceAll("$\\{tableName\\}", tableName);
                    File file = FileUtil.create(filePath);
                    FileWriter fw = new FileWriter(file);
                    //内容写入磁盘
                    TemplateUtil.write(template, params, fw);
                }
            }
        }
    }

    public List<String> readConfigs() throws Exception {
        String config = ConfigUtil.read("/config.json");
        Map<String, List<String>> map = JSON.parseObject(config, Map.class);
        List<String> configs = map.get("configs");
        List<String> configJsons = new ArrayList<String>(configs.size());
        for (String itemConfig : configs) {
            configJsons.add(ConfigUtil.read(itemConfig));
        }
        return configJsons;
    }

    public Map<String, Template> loadTemplates(List<String> templateNames) {
        return TemplateUtil.readTemplates(templateNames);
    }
}