package com.gsg.boundary.sap;

public class index {

	private static long poll = 1000;
	private static String host="localhost";
	private static int port=6100;
	private static String request = "M00";

	public static void main(String[] args) {
		
		//String metrica = "CPU_CORE_FD021\t5\tWIN-M1VS2S14H35";
		if (args.length>0) {
			System.err.println("Nro parameters :"+args.length);
			System.err.println("Polling time :"+args[0]);
			poll = Long.parseLong(args[0]);
			if (args.length>=2) {
				System.err.println("Host :"+args[1]);
				System.err.println("Port :"+args[2]);
				
				host = args[1].trim(); // host
			  	port = Integer.parseInt(args[2]); // puerto
			}
		}

		while (true ) {


			try {
				SocketClient socket = new SocketClient("client #1", host, port, request);
				while (socket.thrd.isAlive())
					Thread.sleep(10);
				String metrica = socket.getResponse();
				if (metrica!=null) {
					System.out.println(metrica);
					//System.err.println(metrica);
				}
					
				
				//Thread.sleep(poll);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
				System.exit(1);
			}

		}


	}

}
