package testWeb;

import java.io.IOException;

import java.util.Collections;

import java.util.HashSet;

import java.util.Set;



import javax.websocket.OnClose;

import javax.websocket.OnMessage;

import javax.websocket.OnOpen;

import javax.websocket.Session;

import javax.websocket.server.ServerEndpoint;



@ServerEndpoint("/broadcasting")

public class Broadsocket {



	private static Set<Session> clients = Collections

			.synchronizedSet(new HashSet<Session>());



	@OnMessage

	public void onMessage(String message, Session session) throws IOException {

		System.out.println(message);

		synchronized (clients) {
			// 클라로부터 메세지가 도착했을때
			// Iterate over the connected sessions

			// and broadcast the received message

			for (Session client : clients) {

				if (!client.equals(session)) {

					client.getBasicRemote().sendText(message);

				}

			}

		}

	}



	@OnOpen

	public void onOpen(Session session) {
		// 접속할때에
		// Add session to the connected sessions set

		System.out.println(session);

		clients.add(session);

	}



	@OnClose

	public void onClose(Session session) {
		// 접속이 끊어질때
		// Remove session from the connected sessions set

		clients.remove(session);

	}

}