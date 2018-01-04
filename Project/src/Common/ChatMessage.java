package eu.vandahl.common;

import java.io.Serializable;

public class ChatMessage implements Serializable {
	private String message;
	private int type;

	private String fileName;
	private String recipient;
	private byte[] fileContent;

	public static final int LOGOUT = 0, MESSAGE = 1, LOGIN = 2, FILE = 3;

	// Constructor for message-sending
	public ChatMessage(int type, String message) {
		this.type = type;
		this.message = message;
	}

	// Constructor for file-sending
	public ChatMessage(int type, byte[] content, String recipient, String fName) {
		this.type = type;
		this.recipient = recipient;
		fileName = fName;
		this.fileContent = content;

	}

	public String getFilename() {
		return fileName;
	}

	public String getRecipient() {
		return recipient;
	}

	public byte[] getContentBytes() {
		return fileContent;
	}

	public String getMessage() {
		return message;
	}

	public int getType() {
		return type;
	}
}
