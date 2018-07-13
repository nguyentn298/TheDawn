package com.dante.config;

import javax.servlet.Filter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.dante.config.core.SiteMeshFilter;
import com.dante.config.core.WebMvcConfig;
import com.dante.config.core.repository.DawnRepositoryConfig;
import com.dante.config.core.repository.DawnRepositoryConfig2;
import com.dante.config.core.repository.MongoDBConfig;
import com.dante.config.security.WebSecurityConfig;

/**
 * WebApplicationcontext extended ApplicationContext which is designed to work
 * with the standard javax.servlet.ServletContext so it's able to communicate
 * with the container
 */
	/**
	 * Use @ComponentScan here for 2 reasons:
	 * 1/ finding beans to unit test.
	 * 2/ Turn on log4j
	 */
//@Order(1)
@ComponentScan({ "com.dante.*" }) // Add this to use junit test
public class WebAppContext extends AbstractAnnotationConfigDispatcherServletInitializer {

	/**
	 * Root register datasource, repository ... If you're living in a SINGLE
	 * DispatherServlet world, it is also possible to have just one root context for
	 * this scenario. It mean, getRootConfigClasses() will contain handle mapping,
	 * controller, viewresolver of servlet WebApplicationContext , if
	 * getServletConfigClasse() is empty
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { DawnRepositoryConfig.class, DawnRepositoryConfig2.class, WebSecurityConfig.class, MongoDBConfig.class };
	}

	/**
	 * If getServletConfigClasses() return null at getRootConfigClasses() method and
	 * set ApplicationContext.class at getServletConfigClasses() method, it will
	 * throw exception below No WebApplicationContext found: no
	 * ContextLoaderListener registered?
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebMvcConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		return new Filter[] { characterEncodingFilter, new SiteMeshFilter() };
	}

}
// public class SpringMvcInitializer implements WebApplicationInitializer {
//
// /**
// * So to start – SpringServletContainerInitializer has to find the right class
// implementing WebApplicationInitializer
// */
// public void onStartup(ServletContext servletContext) throws ServletException
// {
//
// // Create the 'root' Spring application context
// AnnotationConfigWebApplicationContext rootApplicationContext = new
// AnnotationConfigWebApplicationContext();
// rootApplicationContext.register(ApplicationContext.class);
//
// /**
// * Create and register the DispatcherServlet
// * the DispatcherServlet is an expression of the “Front Controller” design
// pattern.
// * Each DispatcherServlet has its own WebApplicationContext, which inherits
// all the beans already defined in the root WebApplicationContext
// * The DispatcherServlet delegates to special beans to process requests and
// render the appropriate responses
// *
// * Workflow of DispatcherServlet:
// * 1/ DispatcherServlet(Front-controller): recieve request and delegate
// request to controller
// * 2/ Controller handle request, create model and return back to
// DispatcherServlet(Front-controller)
// * 3/ DispatcherServlet(Front-controller) render response(Model above) to View
// template
// * 4/ View template return control back to DispatcherServlet(Front-controller)
// * 5/ DispatcherServlet(Front-controller) return response
// */
// ServletRegistration.Dynamic dispatcher =
// servletContext.addServlet("SpringDispatcher", new
// DispatcherServlet(rootApplicationContext));
// dispatcher.setLoadOnStartup(1);
// dispatcher.addMapping("/");
//
// // Manage the lifecycle of the root application context
// servletContext.addListener(new
// ContextLoaderListener(rootApplicationContext));
//
// // Filter's like HiddenHttpMethodFilter(), MultipartFilter()
// FilterRegistration.Dynamic fr = servletContext.addFilter("encodingFilter",
// CharacterEncodingFilter.class);
//
// fr.setInitParameter("encoding", "UTF-8");
// fr.setInitParameter("forceEncoding", "true");
// fr.addMappingForUrlPatterns(null, true, "/*");
// }
// }

/**
 * AbstractAnnotationConfigDispatcherServletInitializer is implementation of
 * WebApplicationInitializer above
 * 
 * @author Dante
 *
 */
