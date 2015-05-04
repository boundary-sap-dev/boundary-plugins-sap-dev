package com.gsgtech.sap;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.JSONObject;


/**
 * Clase principal del Router, esta aplicacion actua como un traductor entre la mensajeria unica y los Socket de Banca de las diferentes series.
 *  
 * @author fd021
 *
 */
public class Index {

	private static Index serviceInstance = null;
	private MetricaRepositorio metRepo=null;

	private static long poll = 1000;
	private static LoginInfo login;


	public Index() {
		super();
		this.metRepo = new MetricaRepositorio("GeneradorMetricas",login);
	}


	/**
	 * Punto de entrada de aplicacion
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws Exception {
		boolean listening = true;

		/*
		 * Fill a string with the param.json and parse it.
		 */
		String parameters = readParam();
		JSONObject obj = new JSONObject(parameters);

		poll = obj.getLong("pollInterval");
		System.err.println("Polling time :"+poll);

		login = new LoginInfo(obj.getString("ashost"), obj.getString("sysnr"), obj.getString("client"), obj.getString("user"), obj.getString("passwd"), obj.getString("lang"));
		System.err.println("Login info :"+login);


		serviceInstance = new Index();

		System.err.println("Delaying 5 seconds.....");
		pause(5000);
		System.err.println("Starting.....");

		Index root = Index.getServiceInstance();
		while (listening) {

			MetricaRepositorio rep = root.getMetRepo();
			List<MetricaInfo> rclist = rep.getMetrica();

			//Informacion de Log
			if (rclist!=null) {
				for (MetricaInfo rc : rclist ) {
					String resp = rc.toString().trim();        		
					/**
					 * Write to stdout, for Boundary meter   		
					 */
					System.out.println(resp);
					pause(25);
				}
			}
			pause(poll);
		}

	}


	private static String readParam() throws Exception {
		StringBuffer stringBuffer = new StringBuffer();
		BufferedReader bufferedReader = null;

		try {
			File arch = new File("param.json");
			bufferedReader = new BufferedReader(new FileReader(arch));


			String line = null;

			while((line =bufferedReader.readLine())!=null){
				stringBuffer.append(line).append("\n");
			}

			bufferedReader.close();		

		} catch (FileNotFoundException e) {
			throw new Exception("param.json not found!");
		} catch (IOException e) {
			throw new Exception(e.getMessage());
		}

		return stringBuffer.toString();
	}


	private static void pause(long i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {

		}

	}


	public synchronized MetricaRepositorio getMetRepo() {
		return metRepo;
	}


	public synchronized static Index getServiceInstance() {
		return serviceInstance;
	}
}
