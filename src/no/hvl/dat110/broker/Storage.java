package no.hvl.dat110.broker;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import no.hvl.dat110.messages.Message;
import no.hvl.dat110.messagetransport.Connection;

public class Storage {

	// data structure for managing subscriptions
	// maps from a topic to set of subscribed users
	protected ConcurrentHashMap<String, Set<String>> subscriptions;
	
	// data structure for managing currently connected clients
	// maps from user to corresponding client session object
	protected ConcurrentHashMap<String, ClientSession> clients;
	
	// data structure for managing a buffer of messages for disconnected users
	// maps from a user to a set of messages
	protected ConcurrentHashMap<String, Set<Message>> messagebuffer;


	public Storage() {
		subscriptions = new ConcurrentHashMap<String, Set<String>>();
		clients = new ConcurrentHashMap<String, ClientSession>();
		messagebuffer=new ConcurrentHashMap<String, Set<Message>>();
	}

	public Collection<ClientSession> getSessions() {
		return clients.values();
	}

	public Set<String> getTopics() {

		return subscriptions.keySet();

	}

	// get the session object for a given user
	// session object can be used to send a message to the user
	
	public ClientSession getSession(String user) {

		ClientSession session = clients.get(user);

		return session;
	}
	
	

	public Set<String> getSubscribers(String topic) {

		return (subscriptions.get(topic));

	}

	public void addClientSession(String user, Connection connection) {
		ClientSession cs = new ClientSession(user, connection);
		clients.put(user, cs);		
	}

	public void removeClientSession(String user) {
		clients.remove(user);
	}

	public void createTopic(String topic) {

		if(!subscriptions.containsKey(topic)) {
			
			Set<String> subscribers = ConcurrentHashMap.newKeySet();
			
			subscriptions.put(topic, subscribers);
		}
	}

	public void deleteTopic(String topic) {
		subscriptions.remove(topic);
	}

	public void addSubscriber(String user, String topic) {		
		subscriptions.get(topic).add(user);
	}

	public void removeSubscriber(String user, String topic) {
		subscriptions.get(topic).remove(user);
	}
	
	public void createMessageBuffer(String user) {

		if(!messagebuffer.containsKey(user)) {
			
			Set<Message> msgs = ConcurrentHashMap.newKeySet();
			
			messagebuffer.put(user, msgs);
		}
	}
	
	public void addMessage(String user, Message msg) {		
		messagebuffer.get(user).add(msg);
	}
	
	public Set<Message> getMessages(String user) {
		return (messagebuffer.get(user));
	}
}
