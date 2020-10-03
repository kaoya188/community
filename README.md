## YH中文社区

## 资料
[Spring 文档](https://spring.io/guides)   
[Spring Web](https://spring.io/guides/gs/serving-web-content/)  
[ES社区](https://elasticsearch.cn/)     
[Github deploy key](https://docs.github.com/en/developers/overview/managing-deploy-keys#deploy-keys)   
[Bootstrap](https://v3.bootcss.com/getting-started/) 
[Github OAuth Document](https://docs.github.com/en/developers/apps/building-oauth-apps)        
[菜鸟教程 MySQL](https://www.runoob.com/mysql/mysql-drop-database.html)


## 工具
[Git](https://git-scm.com/)    
[Visual Paradigm](https://www.visual-paradigm.com/cn/)  
[Flyway](https://flywaydb.org/getstarted/firststeps/maven#creating-the-first-migration)     
[Lombok](https://projectlombok.org/)    

## 脚本
```sql
create table USER
(
	ID INT auto_increment,
	ACCOUNT_ID VARCHAR(100),
	NAME VARCHAR(50),
	TOKEN CHAR(36),
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT,
	constraint TABLE_NAME_PK
	primary key (ID)
);
```
```bash
mvn flyway:migrate
```

## Bug

1. 描述 : 自定义Interceptor, 静态资源被拦截问题
   1. a. 去掉`EnableWebMvc`注解 
      b. 在我们自己定义的类Webconfig中覆盖方法addResourceHandlers加入静态资源路径。
   2. [参看文档](https://www.jianshu.com/p/2ce1c7097da4)

```java
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/**")
          .addResourceLocations("classpath:/static/","classpath:/templates/");
    }
}
```

