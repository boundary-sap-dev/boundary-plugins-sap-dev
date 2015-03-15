package com.gsg.boundary.sap;

public class index {

	public static void main(String[] args) {
		long poll = 1000;
		String metrica = "CPU_CORE_FD021\t5\tWIN-M1VS2S14H35";
		if (args.length>0) {
			System.err.println("Nro parameters :"+args.length);
			System.err.println("Polling time :"+args[0]);
			poll = Long.parseLong(args[0]); 
		}

		while (true ) {


			try {
				Thread.sleep(poll);
				System.out.println(metrica);
				//System.err.println(metrica);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
				System.exit(1);
			}

		}


	}

}
