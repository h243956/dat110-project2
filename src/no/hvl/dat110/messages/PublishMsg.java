package no.hvl.dat110.messages;

public class PublishMsg extends Message {
	
	// message sent from client to create publish a message on a topic 

	// TODO:
	// Implement object variables - a topic and a message is required
	private String topic;
	
	public PublishMsg(String user, String topic) {
		super(MessageType.PUBLISH, user);
		this.topic = topic;
	}

	public String getTopic() {
		return topic;
	}
	
	public void setTopic(String topic) {
		this.topic = topic;
	}
	// Constructor, get/set-methods, and toString method
	// as described in the project text
	
}
