package com.user.center.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

///**
// * Swagger2 configuration class<br>
// * By adding @Configuration annotation, delegate Spring to manage<br>
// * By adding @EnableSwagger2 to enable swagger
// */
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//	/**
//	 * Create apiInfo() to display more info of the API<br>
//	 * Use select() to return ApiSelectorBuilder instance to control which interface
//	 * to be displayed to Swagger<br>
//	 * The example implement by scanning the specified package
//	 * 
//	 * @return
//	 */
////	@Bean
////	public Docket createRestApi() {
////		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.any())
////				.paths(PathSelectors.any()).build();
////	}
//
//	@Bean
//	public Docket api() {
//		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).enable(true).select().apis(RequestHandlerSelectors.any())
//				.paths(PathSelectors.any()).build().securitySchemes(Arrays.asList(securityScheme()))
//				.securityContexts(Arrays.asList(securityContext()));
//	}
//
//	@Bean
//	public SecurityConfiguration security() {
//		return SecurityConfigurationBuilder.builder().clientId("CLIENT_ID").clientSecret("CLIENT_SECRET")
//				.scopeSeparator(" ").useBasicAuthenticationWithAccessCodeGrant(true).build();
//	}
//
//	/**
//	 * Set up basic information, which will be displayed in the page<br>
//	 * URL: http://(actual path of the project)/swagger-ui.html
//	 * 
//	 * @return
//	 */
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder().title("Profile API").description("To get more info: http://www.baidu.com")
//				.termsOfServiceUrl("http://www.baidu.com").contact(new Contact("hai.huang.a@outlook.com", "", ""))
//				.version("1.0").build();
//	}
//
//	private SecurityScheme securityScheme() {
//		GrantType grantType = new AuthorizationCodeGrantBuilder()
//				.tokenEndpoint(new TokenEndpoint("123456789" + "/token", "oauthtoken")).tokenRequestEndpoint(
//						new TokenRequestEndpoint("123456789" + "/authorize", "CLIENT_ID", "CLIENT_SECRET"))
//				.build();
//
//		SecurityScheme oauth = new OAuthBuilder().name("spring_oauth").grantTypes(Arrays.asList(grantType))
//				.scopes(Arrays.asList(scopes())).build();
//		return oauth;
//	}
//
//	private AuthorizationScope[] scopes() {
//		AuthorizationScope[] scopes = { new AuthorizationScope("read", "for read operations"),
//				new AuthorizationScope("write", "for write operations"),
//				new AuthorizationScope("foo", "Access foo API") };
//		return scopes;
//	}
//
//	private SecurityContext securityContext() {
//		return SecurityContext.builder()
//				.securityReferences(Arrays.asList(new SecurityReference("spring_oauth", scopes())))
//				.forPaths(PathSelectors.regex("/foos.*")).build();
//	}
//
//}

//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//	@Bean
//	public Docket createRestApi() {
//		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
//				.apis(RequestHandlerSelectors.basePackage("com.profile.controller")).paths(PathSelectors.any())
//				.build().securitySchemes(Lists.newArrayList(apiKey()));
//	}
//
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder().title(" RestApi Document").description("decription for api")
//				.termsOfServiceUrl("www.test.com").contact("lytech").version("1.0").build();
//	}
//
//	private ApiKey apiKey() {
//		return new ApiKey("apikey", "Authorization", "header");
//	}
//}

//@EnableSwagger2
//@Configuration
//public class SwaggerConfig {
//
//	// 是否开启swagger，正式环境一般是需要关闭的，可根据springboot的多环境配置进行设置
//	@Value(value = "${swagger.enabled}")
//	Boolean swaggerEnabled;
//	@Value(value = "${swagger.version}")
//	String version;
//
//	@Bean
//	public Docket createRestApi() {
//
//		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
//				// 是否开启
//				.enable(swaggerEnabled).select()
//				// 扫描的路径包
//				.apis(RequestHandlerSelectors.basePackage("com.user.center.controller"))
//				// 指定路径处理PathSelectors.any()代表所有的路径
//				.paths(PathSelectors.any()).build().pathMapping("/").securitySchemes(securitySchemes())
//				.securityContexts(securityContexts());
//	}
//
//	private List<ApiKey> securitySchemes() {
//		List<ApiKey> apiKeyList = new ArrayList<ApiKey>();
//		apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
//		return apiKeyList;
//	}
//
//	private List<SecurityContext> securityContexts() {
//		List<SecurityContext> securityContexts = new ArrayList<>();
//		securityContexts.add(SecurityContext.builder().securityReferences(defaultAuth())
//				.forPaths(PathSelectors.regex("^(?!auth).*$")).build());
//		return securityContexts;
//	}
//
//	List<SecurityReference> defaultAuth() {
//		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//		authorizationScopes[0] = authorizationScope;
//		List<SecurityReference> securityReferences = new ArrayList<>();
//		securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
//		return securityReferences;
//	}
//
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder().title("用户管理 USER-SERVICE API接口文档示例").version(version).description("用户管理接口服务")
//				.build();
//	}
//}
//
///**
// * TODO Learn
// @author lism
// * @date 2018/08/12 10:52
// */
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//	/**
//	 * Every Docket bean is picked up by the swagger-mvc framework - allowing for
//	 * multiple swagger groups i.e. same code base multiple swagger resource
//	 * listings.
//	 */
//	@Bean
//	public Docket customDocket() {
//		ParameterBuilder ticketPar = new ParameterBuilder();
//		List<Parameter> pars = new ArrayList<Parameter>();
//		ticketPar.name("Authorization").description("认证token").modelRef(new ModelRef("string")).parameterType("header")
//				.required(false).build(); // header中的ticket参数非必填，传空也可以
//		pars.add(ticketPar.build()); // 根据每个方法名也知道当前方法在设置什么参数
//
//		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).build()
//				.globalOperationParameters(pars).apiInfo(apiInfo());
//	}
//
//	// 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder()
//				// 页面标题
//				.title("demo")
//				// 创建人
//				.contact(new Contact("demo", "", ""))
//				// 版本号
//				.version("1.0")
//				// 描述
//				.description("Demo REST API").build();
//	}
//}

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).enable(true).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("User Center").description("UserCenterApplication to handle any request of user. To get more info: https://github.com/HaiHuangCHN/profile")
                .termsOfServiceUrl("http://www.online.shopping.com").contact(new Contact("hai.huang.a@outlook.com", "", "")).version("1.0").build();
    }

}
