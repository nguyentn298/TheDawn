package com.dante.config.core;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class SiteMeshFilter extends ConfigurableSiteMeshFilter {

	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
	  
		/**
		 *  IMPORTANT: Get path from requestmapping of controller (not from WEB-INF/pages or etc)
		 *  Example: .addExcludedPath("/login"); ==>  @RequestMapping(value = "/login", method = RequestMethod.GET)
		 *  exclude addDecoratorPath(positive path); 
		 */
		builder.addDecoratorPath("/*", "/WEB-INF/decorators/default.jsp").addExcludedPath("/login");
		System.out.println("Completed sitemesh");
		
		/**
		 * Default.jsp contains header, body, footer for any site.
		 */
	}
}
