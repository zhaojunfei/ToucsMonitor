package com.adtec.toucs.monitor.devicemanage;

import java.net.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class TestServer implements Runnable{

	public TestServer() {
	}

	public void run(){
		try{
			ServerSocket serverSocket=new ServerSocket(2056);
			while(true){
				try{
					Socket clientSocket=serverSocket.accept();
					ClientProc proc=new ClientProc(clientSocket);
					proc.start();
				}catch(Exception e2){
					e2.printStackTrace();
				}
			}
		}catch(Exception e1){
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TestServer server = new TestServer();
		Thread t=new Thread(server);
		t.start();
	}
}

class ClientProc extends Thread{
	private Socket socket;

	public ClientProc(Socket skt){
		socket=skt;
	}

	public void run(){
		System.out.println("in...");
		try{
			StringBuffer sin=new StringBuffer();
			BufferedReader bfr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String sline="";
			boolean end=false;
			while(!end){
				sline=bfr.readLine();
				sin.append(sline);
				if(sline.equals("</Deal>"))
					end=true;
			}
			System.out.println("In:"+sin);
			PrintWriter pr=new PrintWriter(socket.getOutputStream());
			pr.println(sendStr);
			pr.flush();
			socket.close();
		}catch(Exception exp){
			exp.printStackTrace();
		}
	}	

	private static final String sendStr="<Deal>"
                                     +"<DealCode>MG7510</DealCode>"
                                     +"<RequestCode>0009</RequestCode>"
                                     +"<RequestMsg>正在处理，稍后通知</RequestMsg>"
                                     +"</Deal>";
}
