package com.amaris.javatsinterop.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.amaris.javatsinterop.api.TestResource;

public class RestServer {
	public static void main(String[] args) throws Exception {

		// servlets
		ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		servletHandler.setContextPath("/");

		ServletHolder jerseyServlet = servletHandler.addServlet(org.glassfish.jersey.servlet.ServletContainer.class,
				"/*");
		jerseyServlet.setInitOrder(0);

		jerseyServlet.setInitParameter("jersey.config.server.provider.classnames",
				TestResource.class.getCanonicalName());

		// static content
		ResourceHandler staticHandler = new ResourceHandler();
		staticHandler.setDirectoriesListed(true);
		staticHandler.setWelcomeFiles(new String[] { "index.html" });

		staticHandler.setResourceBase("./www");

		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { staticHandler, servletHandler });

		// server
		Server jettyServer = new Server(2222);
		jettyServer.setHandler(handlers);

		try {
			jettyServer.start();
			jettyServer.join();
		} finally {
			jettyServer.destroy();
		}
	}
}