package bbetser.server;

import bbetser.server.rest.NotificationStream;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.media.sse.SseFeature;
import org.glassfish.jersey.message.GZipEncoder;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.EncodingFilter;
import org.glassfish.jersey.servlet.ServletContainer;


public class JettyRunner {

	public static final int DEFAULT_PORT = 8090;

	private int serverPort;
	
	public JettyRunner(int serverPort) throws Exception {
		this.serverPort = serverPort;


		Server server = configureServer();
        server.start();
        server.join();
	}	

	private Server configureServer() {
		ResourceHandler resource_handler = new ResourceHandler();

		resource_handler.setDirectoriesListed(true);
		resource_handler.setWelcomeFiles(new String[]{ "index.html" });
		resource_handler.setResourceBase("./src/webapps/");

		HandlerList handlers = new HandlerList();

		ResourceConfig resourceConfig = new ResourceConfig();
		resourceConfig.packages(NotificationStream.class.getPackage().getName());
		resourceConfig.register(SseFeature.class);
		EncodingFilter.enableFor(resourceConfig, GZipEncoder.class);

		ServletContainer servletContainer = new ServletContainer(resourceConfig);
		ServletHolder sh = new ServletHolder(servletContainer);
		Server server = new Server(serverPort);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(sh, "/*");
		handlers.setHandlers(new Handler[] { resource_handler, context });
		server.setHandler(handlers);
		return server;
	}

	public static void main(String[] args) throws Exception {

		int serverPort = DEFAULT_PORT;
		
		if(args.length >= 1) {
			try {
				serverPort = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		
		new JettyRunner(serverPort);
	}

}
