package com.gsg.boundary.sap;


import java.io.*;
import java.net.*;


public class SocketClient implements Runnable {

	Thread thrd;

	String host = "127.0.0.1";
	int port = 6100;
	String trama = "M00";

	private String response=null; 




	public String getResponse() {
		return response;
	}


	/**
	 * 
	 * @param name
	 */
	public SocketClient(String name, String host, int port, String trama) {
		thrd = new Thread(this, name);

		//Take the values from contructor
		this.host = host;
		this.port = port;
		this.trama = trama;

		thrd.start();
	}


	/**
	 * 
	 */
	public void run() {

		Socket kkSocket = null;
		PrintWriter out = null;
		Reader  in = null;

		try {
			/*
			 * Abro la conexion
			 */
			kkSocket = new Socket(host, port);
			out = new PrintWriter(kkSocket.getOutputStream(), true);
			in = new InputStreamReader(kkSocket.getInputStream());





			/*
			 * Envio Trama
			 */
			out.print(trama);
			out.flush();
			//System.err.println("["+thrd.getName()+"] --> [" + trama+"]");


			/**
			 * Espero hasta NN segundos a que la trama llegue, si no llega se interrumpe el procesameinto por "timeout"
			 */

			char ch;
			int nWait=0;
			while (!in.ready()) { 
				Thread.sleep(100);
				nWait++;
				if  (nWait>600) {
					throw new Exception("Time out! ");
				}
			}



			/*
			 * Leo respuesta
			 */
			StringBuffer resp = new StringBuffer();
			int rt;
			int i=0;
			while (in.ready()) {
				rt = in.read();
				i++;
				ch = (char) rt;
				resp.append(ch);
			}
			response = resp.toString();
			//System.err.println("[" + thrd.getName() + "] <-- ["	+ response + "]");


		} catch (Exception e) {
			System.err.println(e.getMessage());
			//(UnknownHostException e) System.err.println("Don't know about host: " + host);
			//IOException e   System.err.println("Couldn't get I/O for the connection to: " + host);
			//BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		} finally {
			/*
			 * Cierro conexion
			 */
			try {
				out.close();
				in.close();

				kkSocket.close();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}	
		}

	}

}
