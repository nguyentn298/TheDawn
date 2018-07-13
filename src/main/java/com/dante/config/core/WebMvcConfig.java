package com.dante.config.core;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc 						//it use for @Bean
@ComponentScan({ "com.dante.*" }) 	//it use for @Controller ...
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	/**
	 * The WebMvcConfigurerAdapter is for configuring Spring MVC, the replacement of
	 * the xml file loaded by the DispatcherServlet for configuring Spring MVC. The
	 * WebMvcConfigurerAdapter should be used for a @Configuration class
	 */
	private static final Charset UTF8 = Charset.forName("UTF-8");

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp().prefix("/WEB-INF/pages/").suffix(".jsp");
		System.out.println("Completed viewResolver version 2: ViewResolverRegistry");
	}

	// Config UTF-8 Encoding.
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
		stringConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "plain", UTF8)));
		converters.add(stringConverter);

		// Add other converters ...
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	// @Bean
	// public InternalResourceViewResolver viewResolver() {
	// InternalResourceViewResolver viewResolver = new
	// InternalResourceViewResolver();
	// viewResolver.setViewClass(JstlView.class);
	// viewResolver.setPrefix("/WEB-INF/pages/");
	// viewResolver.setSuffix(".jsp");
	// System.out.println("Completed viewResolver version 1:
	// InternalResourceViewResolver");
	// return viewResolver;
	// }

}
