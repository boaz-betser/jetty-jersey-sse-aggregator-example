package bbetser.resttest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Before;
import org.junit.Test;

import static bbetser.server.JettyRunner.DEFAULT_PORT;


public class RestServiceTest {

	private static final String SSE_STREAM_PATH = "notifications_stream";
	private static final String TARGET_URI = "http://localhost:" + DEFAULT_PORT + "/" + SSE_STREAM_PATH;

	private WebTarget target;
	
	@Before
	public void setUp() {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(JacksonFeature.class);
		Client client = ClientBuilder.newClient(clientConfig);
		
		target = client.target(TARGET_URI);
	}
	
	@Test
	public void testNotificationStreamJson() {
			
//		String result = (String) target.path(NAME).request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
//		System.out.println(result);
//		assertThat(result, is(equalTo(EXPECTED_JSON)));
	}
	
//	@Test
/*
	public void testHelloWorldObject() {
		Saying result = (Saying) target.path(NAME).request(MediaType.APPLICATION_JSON_TYPE).get(Saying.class);
		
		assertThat(result.name, is(equalTo(EXPECTED)));
	}
*/

}
