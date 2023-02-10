package com.learning.session;

import com.learning.config.Configuration;
import com.learning.config.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName: SqlSessionFactory
 * @Description: 1、加载配置文件; 2、创建SqlSession连接
 * @author: Apeng
 * @date: 2023/2/10 1:43
 */

public class SqlSessionFactory {

    private final Configuration conf = new Configuration();

    public SqlSessionFactory(){
        //加载配置文件
        loadDbInfo();
        //加载映射文件信息
        loadMappersInfo();
    }

    public SqlSession openSqlSession(){
        return new DefaultSqlSession(conf);
    }

    //记录mapper xml文件存放位置信息
    public static final String MAPPER_CONFIG_LOCATION = "mapper";

    //记录数据库文件连接存放位置信息
    public static final String DB_CONFIG_LOCATION = "db.properties";


    //加载数据库配置信息
    private void loadDbInfo(){
        //加载数据库信息配置文件
        InputStream dbInput = SqlSessionFactory.class.getClassLoader().getResourceAsStream(DB_CONFIG_LOCATION);
        Properties pp = new Properties();
        try {
            pp.load(dbInput);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //将数据库配置信息写入conf对象
        conf.setJdbcDriver(pp.get("jdbc.driver").toString());
        conf.setJdbcPassword(pp.get("jdbc.password").toString());
        conf.setJdbcUrl(pp.get("jdbc.url").toString());
        conf.setJdbcUsername(pp.get("jdbc.username").toString());
    }

    //获取指定文件夹下所有mapper，xml文件
    private void loadMappersInfo() {
        URL resources = null;
        resources = SqlSessionFactory.class.getClassLoader().getResource(MAPPER_CONFIG_LOCATION);
        File mappers = new File(resources.getFile());
        if(mappers.isDirectory()){
            File[] listFiles = mappers.listFiles();
            //遍历文件夹下所有mapper。xml解析信息后注册到配置文件中
            for (File file:listFiles
            ) {
                loadMapperInfo(file);
            }
        }
    }

    private void loadMapperInfo(File file) {
        //创建一个saxReade对象
        SAXReader saxReader = new SAXReader();
        //通过read方法读取一个文件转换为Document对象
        Document document = null;
        try {
            document = saxReader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //获取根节点元素对象
        Element root = document.getRootElement();
        //获取命名空间
        String namespace = root.attribute("namespace").getData().toString();
        //获取select子节点列表
        List<Element> selects = root.elements("select");
        //遍历select节点，将信息记录到MappedStatement对象，并登记到configuration对象中
        for (Element el:selects
             ) {
            MappedStatement mappedStatement = new MappedStatement();
            //获取id
            String id = el.attribute("id").getData().toString();
            //获取resultType
            String resultType = el.attribute("resultType").getData().toString();
            //获取sql信息
            String sql = el.getData().toString();
            //获取sourceId
            String sourceId = namespace+id;
            mappedStatement.setSql(sql);
            mappedStatement.setNamespace(namespace);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSourceId(sourceId);
            conf.getMappedStatementMap().put(sourceId,mappedStatement);
        }
    }
}
