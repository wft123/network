package com.hanains.network.chat;

import java.io.PrintWriter;

public class Client {
	private String name;
	private PrintWriter printWriter;
	
	public Client(String name, PrintWriter printWriter) {
		this.name = name;
		this.printWriter = printWriter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PrintWriter getPrintWriter() {
		return printWriter;
	}

	public void setPrintWriter(PrintWriter printWriter) {
		this.printWriter = printWriter;
	}
}
