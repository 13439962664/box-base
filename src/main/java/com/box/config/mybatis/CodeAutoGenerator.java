package com.box.config.mybatis;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.IFileCreate;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.box.pojo.BasePojo;

public class CodeAutoGenerator {
	
	public static void main(String[] args) {
    	CodeAutoGenerator generator = new CodeAutoGenerator(
    			"sunyizhuo-13439962664","crm",new String[] {"crm_company"});
    	generator.run();
    }
	
	public void run() {
		// 全局配置
        GlobalConfig globalConfig = this.globalConfig();
        // 数据源配置
        DataSourceConfig dataSourceConfig = this.dataSourceConfig();
        // 策略配置
        StrategyConfig strategyConfig = this.strategyConfig();
        // 包配置
        PackageConfig packageConfig = this.packageConfig();
        // 模板配置
        TemplateConfig templateConfig = this.templateConfig();
        // 自定义配置
        InjectionConfig injectionConfig = this.injectionConfig();

        // 执行
        new AutoGenerator().setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                // 因为使用了自定义模板,所以需要把各项置空否则会多生成一次
                .setTemplate(templateConfig)
                // 使用的模板引擎，如果不是默认模板引擎则需要添加模板依赖到pom
                .setTemplateEngine(new FreemarkerTemplateEngine())
                .setCfg(injectionConfig)
                .execute();
	}
	
	public CodeAutoGenerator(String aUTHOR, String oUTPUT_MODULE, String[] tABLES, String username, String password,
			String url) {
		super();
		this.AUTHOR = aUTHOR;
		this.OUTPUT_MODULE = oUTPUT_MODULE;
		this.TABLES = tABLES;
		this.username = username;
		this.password = password;
		this.url = url;
	}

	public CodeAutoGenerator(String aUTHOR, String oUTPUT_MODULE, String[] tABLES) {
		super();
		this.AUTHOR = aUTHOR;
		this.OUTPUT_MODULE = oUTPUT_MODULE;
		this.TABLES = tABLES;
	}

    /** 作者 */
    private String AUTHOR = "sunyizhuo-13439962664";
 // 各层文件输出到模块, 没有则置空
    /** Entity.java, Mapper.java, Mapper.xml输出模块路径 */
    private String OUTPUT_MODULE = "/crm";
    
    /** 表名 */
    private String[] TABLES = {
            "crm_company"
    };
    
	/** 生成的实体类忽略表前缀: 不需要则置空 */
    private String ENTITY_IGNORE_PREFIX = "";
    
    private String JAVA_PATH = "/java";
    private String RESOURCES_PATH = "/resources";

    private String OUTPUT_PACKAGE = "/generator/";

    /** 父包名路径(文件输出路径,也是导包的路径) */
    private String PARENT_PACKAGE_PATH = "/com/box";

    // 各层包名
    private String ENTITY_PATH = "/entity/";
    private String MAPPER_PATH = "/mapper/";
    private String XML_PATH = "/mapper/";
    private String SERVICE_PATH = "/service/";
    private String SERVICE_IMPL_PATH = "/service/impl/";
    private String CONTROLLER_PATH = "/controller/";


    // 数据库
    private String username = "root";
    private String password = "123456";
    private String url = "jdbc:mysql://localhost:3306/box?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2b8";
    private DbType DB_TYPE = DbType.MYSQL;
    private String driverClassName = "com.mysql.cj.jdbc.Driver";


    // 自定义输出模板和位置
    // 文件位置输出模式: file output path = projectPath + XX_OUTPUT_PATH  + File
    // XX_OUTPUT_PATH = modulePath + packagePath
    /** entity输出模板 */
    private String ENTITY_TEMPLATE = "templates/freemarker/entity.java.ftl";
    private String ENTITY_OUTPUT_PATH =  JAVA_PATH + PARENT_PACKAGE_PATH + OUTPUT_MODULE + ENTITY_PATH;
    /** mapper.xml输出模板 */
    private String XML_TEMPLATE = "templates/freemarker/mapper.xml.ftl";
    private String XML_OUTPUT_PATH =  RESOURCES_PATH  + XML_PATH+ OUTPUT_MODULE;
    /** mapper.java输出模板 */
    private String MAPPER_TEMPLATE = "templates/freemarker/mapper.java.ftl";
    private String MAPPER_OUTPUT_PATH =  JAVA_PATH+ PARENT_PACKAGE_PATH + OUTPUT_MODULE + MAPPER_PATH;
    /** service输出模板 */
    private String SERVICE_TEMPLATE = "templates/freemarker/service.java.ftl";
    private String SERVICE_OUTPUT_PATH = JAVA_PATH + PARENT_PACKAGE_PATH + OUTPUT_MODULE + SERVICE_PATH;
    /** serviceImpl输出模板 */
    private String SERVICE_IMPL_TEMPLATE = "templates/freemarker/serviceImpl.java.ftl";
    private String SERVICE_IMPL_OUTPUT_PATH =  JAVA_PATH + PARENT_PACKAGE_PATH + OUTPUT_MODULE + SERVICE_IMPL_PATH;
    /** controller输出模板 */
    private String CONTROLLER_TEMPLATE = "templates/freemarker/controller.java.ftl";
    private String CONTROLLER_OUTPUT_PATH =  JAVA_PATH + PARENT_PACKAGE_PATH + OUTPUT_MODULE + CONTROLLER_PATH;

    /**
     * 全局配置
     */
    private GlobalConfig globalConfig() {
        return new GlobalConfig().setEnableCache(true)
                // 打开文件
                .setOpen(false)
                // 文件覆盖
                .setFileOverride(true)
                // 开启activeRecord模式
                .setActiveRecord(true)
                // XML ResultMap: mapper.xml生成查询映射结果
                .setBaseResultMap(true)
                // XML ColumnList: mapper.xml生成查询结果列
                .setBaseColumnList(true)
                // swagger注解; 须添加swagger依赖
                .setSwagger2(true)
                // 作者
                .setAuthor(AUTHOR)
                // 设置实体类名称
                .setEntityName("%sEntity")
                .setXmlName("%sMapper")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sController");
    }

    /**
     * 数据源配置
     */
    private DataSourceConfig dataSourceConfig() {
        return new DataSourceConfig()
                // 数据库类型
                .setDbType(DB_TYPE)
                // 连接驱动
                .setDriverName(driverClassName)
                // 地址
                .setUrl(url)
                // 用户名
                .setUsername(username)
                // 密码
                .setPassword(password);
    }

    /**
     * 策略配置
     */
    private StrategyConfig strategyConfig() {
        return new StrategyConfig().setSuperEntityClass(BasePojo.class)
        		.setSuperEntityColumns("id","del_","version_","create_user","create_time","last_user","last_time")
                // 表名生成策略：下划线连转驼峰
                .setNaming(NamingStrategy.underline_to_camel)
                // 表字段生成策略：下划线连转驼峰
                .setColumnNaming(NamingStrategy.underline_to_camel)
                // 需要生成的表
                .setInclude(TABLES)
                // 生成controller
                .setRestControllerStyle(true)
                // 去除表前缀
                .setTablePrefix(ENTITY_IGNORE_PREFIX)
                // controller映射地址：驼峰转连字符
                .setControllerMappingHyphenStyle(true)
                // 是否启用builder 模式
                .setEntityBuilderModel(true)
                // 是否为lombok模型; 需要lombok依赖
                .setEntityLombokModel(true)
                // 生成实体类字段注解
                .setEntityTableFieldAnnotationEnable(true);
    }

    /**
     * 包配置
     * 设置包路径用于导包时使用，路径示例：com.path
     */
    private PackageConfig packageConfig() {

        String entity = OUTPUT_MODULE + ENTITY_PATH;
        String mapper = OUTPUT_MODULE + MAPPER_PATH;
        String xml = OUTPUT_MODULE + XML_PATH;
        String service = OUTPUT_MODULE + SERVICE_PATH;
        String serviceImpl = OUTPUT_MODULE + SERVICE_IMPL_PATH;
        String controller = OUTPUT_MODULE + CONTROLLER_PATH;

        return new PackageConfig()
                // 父包名
                .setParent(PARENT_PACKAGE_PATH.replace('/', '.').substring(1))
                .setEntity(entity.replace('/', '.').substring(1, entity.length()-1))
                .setMapper(mapper.replace('/', '.').substring(1, mapper.length()-1))
                .setXml(xml.replace('/', '.').substring(1, xml.length()-1))
                .setService(service.replace('/', '.').substring(1, service.length()-1))
                .setServiceImpl(serviceImpl.replace('/', '.').substring(1, serviceImpl.length()-1))
                .setController(controller.replace('/', '.').substring(1, controller.length()-1));
    }

    /**
     * 模板配置
     */
    private TemplateConfig templateConfig() {
        return new TemplateConfig()
                // 置空后方便使用自定义输出位置
                .setEntity(null)
                .setXml(null)
                .setMapper(null)
                .setService(null)
                .setServiceImpl(null)
                .setController(null);
    }

    /**
     * 自定义配置
     */
    private InjectionConfig injectionConfig() {
        return new InjectionConfig() {
            @Override
            public void initMap() {
                // 注入配置
            }
        }
        // 判断是否创建文件
        .setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {

                // 检查文件目录，不存在自动递归创建
                checkDir(filePath.substring(0,filePath.lastIndexOf("/")));

                // 指定需要覆盖的文件
                // 文件结尾名字参照 全局配置 中对各层文件的命名,未修改为默认值
                if (isExists(filePath) && (!filePath.endsWith("Mapper.xml") && !filePath.endsWith("Dao.java") && !filePath.endsWith("Mapper.java"))) {
                    return false;
                }

                return true;
            }
        })
        // 自定义输出文件
        .setFileOutConfigList(fileOutConfigList());
    }

    /**
     * 自定义输出文件配置
     */
    private List<FileOutConfig> fileOutConfigList() {
        List<FileOutConfig> list = new ArrayList<>();
        // 当前项目路径
        String projectPath = System.getProperty("user.dir");

        // 实体类文件输出
        list.add(new FileOutConfig(ENTITY_TEMPLATE) {//
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath +OUTPUT_PACKAGE + tableInfo.getEntityName() + StringPool.DOT_JAVA;//ENTITY_OUTPUT_PATH + 
            }
        });
        // mapper xml文件输出
        list.add(new FileOutConfig(XML_TEMPLATE) {//
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath +OUTPUT_PACKAGE + tableInfo.getMapperName() + StringPool.DOT_XML;//XML_OUTPUT_PATH + 
            }
        });
        // mapper文件输出
        list.add(new FileOutConfig(MAPPER_TEMPLATE) {//
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath +OUTPUT_PACKAGE + tableInfo.getMapperName() + StringPool.DOT_JAVA;//MAPPER_OUTPUT_PATH + 
            }
        });
        // service文件输出
        list.add(new FileOutConfig(SERVICE_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath +OUTPUT_PACKAGE + tableInfo.getServiceName() + StringPool.DOT_JAVA;//SERVICE_OUTPUT_PATH + 
            }
        });
        // service impl文件输出
        list.add(new FileOutConfig(SERVICE_IMPL_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath +OUTPUT_PACKAGE + tableInfo.getServiceImplName() + StringPool.DOT_JAVA;//SERVICE_IMPL_OUTPUT_PATH + 
            }
        });
        // controller文件输出
        list.add(new FileOutConfig(CONTROLLER_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath +OUTPUT_PACKAGE + tableInfo.getControllerName() + StringPool.DOT_JAVA;//CONTROLLER_OUTPUT_PATH + 
            }
        });

        return list;
    }

    /**
     * 判断文件是否存在
     * @param path 路径
     * @return
     */
    private static boolean isExists(String path) {
        File file = new File(path);
        return file.exists();
    }
}