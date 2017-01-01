package bbetser.server.rest;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;

import java.io.IOException;

@Singleton
@Path("/notifications_stream")
public class NotificationStream {

	private static SseBroadcaster broadcaster = new SseBroadcaster();
	static{
		new Thread(new Runnable() {
			@Override
			public void run() {
				int i=0;
				while (1!=2){
					i++;
					// ... code that waits 1 second
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					final OutboundEvent.Builder eventBuilder
							= new OutboundEvent.Builder();
					eventBuilder.name("message-to-client");
					eventBuilder.data(String.class,
							"In Server Message: " + i + "!");
					final OutboundEvent event = eventBuilder.build();
					broadcaster.broadcast(event);
				}
			}
		}).start();

	}

	@GET
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	public EventOutput listenToBroadcast() {
		final EventOutput eventOutput = new EventOutput();
		this.broadcaster.add(eventOutput);
		return eventOutput;
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public String broadcastMessage(String message) {
		OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
		OutboundEvent event = eventBuilder.name("message")
				.mediaType(MediaType.APPLICATION_JSON_TYPE)
				.data(String.class, message)
				.build();

		broadcaster.broadcast(event);

		return "Message '" + message + "' has been broadcast.";
	}

}
