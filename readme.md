## spring boot community

### login

    [Use github Creating an OAuth App](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)
    
    [OkHttp](https://square.github.io/okhttp/)
    
    [h2database](https://h2database.com/)
    
    [mybatis with spring-boot](http://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)


## Anotation

- @Value

​	可通过在application.properties文件下定义变量，类中用 @Value使用

	> application.properties中: 
	>
	> ​	github.client.id=xxxxx
	>
	> 使用类中: 
	>
	> ​	@Value("${github.client.id}")
	>
	>  	private String clientId;

- @Controller

  当前类作为路由API的承载者

- @Component

  把当前类初始化到Spring的上下文（Ioc），调用时不需要new，只需要用 @Autowired加载到需要使用的类中。

  > @Autowired
  >
  > Private GithubProvider githubProvider;



## Notice

- 参数超过2个，用bean去操作
- 网络之间的传输用dto，sql数据之间的传输用model