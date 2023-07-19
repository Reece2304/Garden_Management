package com.Horted.app.Services;

import javax.servlet.http.HttpServletRequest;

/**
 * Gets the site's url
 * @author reece
 *
 */
public class Utility {
	public static String getSiteURL(HttpServletRequest request)
	{
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
		
	}
}
