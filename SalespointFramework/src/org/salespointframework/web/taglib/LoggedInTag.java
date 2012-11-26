package org.salespointframework.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.salespointframework.web.WebLoginLogoutManager;

/**
 * 
 * @author Lars Kreisz
 * @author Uwe Schmidt
 * @author Paul Henke
 */
@SuppressWarnings("serial")
public class LoggedInTag extends BodyTagSupport
{
	private boolean test = true;

	/**
	 * The test condition that determines whether or not the body content should be processed.
	 * @param test a boolean condition
	 */
	public void setTest(boolean test)
	{
		this.test = test;
	}

	@Override
	public int doStartTag() throws JspException
	{
		final boolean loggedIn = WebLoginLogoutManager.INSTANCE.loggedIn(pageContext.getSession());

		if(test) {
			return loggedIn ? EVAL_BODY_INCLUDE : SKIP_BODY;
		} else {
			return loggedIn ? SKIP_BODY : EVAL_BODY_INCLUDE;
		}
	}

}
