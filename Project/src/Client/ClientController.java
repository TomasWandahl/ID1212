package eu.vandahl.Client;

import eu.vandahl.common.ChatMessage;

public class ClientController {
	
	ClientNet cn;
	
	ClientController(ClientNet cn) {
		this.cn = cn;
	}
	
	public void forwardMessage(String msg) {
		
	}
	
	public void forwardMessage(ChatMessage cMsg) {
		cn.sendMsg(cMsg);
	}
}
