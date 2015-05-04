package com.gsgtech.sap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;


public class MetricaRepositorio implements Runnable {
	private final Thread thrd;
	private ConcurrentLinkedQueue<List<MetricaInfo>> queue;
	private boolean running;
	private LoginInfo login;

	//Logger
	private static final int MAXIMO_NRO_METRICAS_ALMACENADAS = 30;

	public MetricaRepositorio(String name, LoginInfo login ) {
		this.login = login;
		this.thrd = new Thread(this, name);
		this.queue = new ConcurrentLinkedQueue<List<MetricaInfo>>();
		thrd.start();			
	}



	private static String convertToIdentifer(final String cs) {    	
		if (cs == null) {
			return null;
		}
		char last=' ';
		StringBuilder out = new StringBuilder();

		final int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isLetterOrDigit(cs.charAt(i)) || cs.charAt(i) == '_') {
				last=cs.charAt(i);
				out.append(last);
			}
			if (cs.charAt(i) == ' ' && last!='_' && last!=' ') {
				last='_';
				out.append(last);
			}
			if (cs.charAt(i) == '%' && last!='%') {
				last='%';
				out.append("PORC");
			}
		}

		return out.toString().toUpperCase();
	}



	private static String convertToValue(final String cs) {    	
		if (cs == null) {
			return null;
		}
		char last=' ';
		StringBuilder out = new StringBuilder();

		final int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(cs.charAt(i))) {
				last=cs.charAt(i);
				out.append(last);
			}
			if (cs.charAt(i) == ',' && last!=',' && last!=' ') {
				last='.';
				out.append(last);
			}
		}

		return out.toString();
	}


	private static String getCurrentTimeStamp() {
		long unix = (new java.util.Date().getTime()/1000);
		return Long.toString(unix);     
	}

	@Override
	public void run() {
		try {
			setRunning(true);
			while (isRunning()) {
				if (this.queue.size()<=MAXIMO_NRO_METRICAS_ALMACENADAS) {
					work();
				} else {					
					Thread.sleep(2000);	
			}
		}                
	} catch (InterruptedException e) {
		
	} finally {
		setRunning(false);
	}
}


private void work() {
	List<MetricaInfo> lote = new ArrayList<MetricaInfo>();

	try {
		SapCall sap = new SapCall(login);
		@SuppressWarnings("unused")
		Map<String, MasterCategoriaInfo> masterCategoria = sap.getMapCategoria(); 
		Map<String, MasterMetricaInfo> masterMetrica = sap.getMapMetrica(); 

		List<MasterConfigInfo> listParametris = sap.getListaConfiguracion(); 
		for (MasterConfigInfo param : listParametris ) {			
			try {
				List<ValorMetricaInfo> val = sap.getMetricValue(param);
				for (ValorMetricaInfo metrica : val) {
					//MasterCategoriaInfo cat = masterCategoria.get(metrica.getServer()+metrica.getCateg());
					MasterMetricaInfo met = masterMetrica.get(metrica.getServer()+metrica.getCateg()+metrica.getMetric());
					
					MetricaInfo value = null;
					String subSource =  metrica.getSubSource();
					if (subSource.isEmpty()) {
						value = new MetricaInfo(convertToIdentifer(metrica.getServer()), convertToIdentifer(met.getText()), convertToValue(metrica.getValue()), getCurrentTimeStamp());
					} else {
						value = new MetricaInfo(convertToIdentifer(metrica.getServer()+"_"+subSource), convertToIdentifer(met.getText()), convertToValue(metrica.getValue()), getCurrentTimeStamp());
					}

					if (value.getValor().trim().length()>0) {
						lote.add(value);						
					}


				}

			} catch (Exception e) {
				System.err.println(e.getMessage());
			}

		}
		this.queue.add(lote);
	} catch (Exception e) {
		System.err.println(e.getMessage());
	}

}





public synchronized boolean isRunning() {
	return running;
}

private synchronized void setRunning(boolean running) {
	this.running = running;
}



public synchronized List<MetricaInfo> getMetrica() {    	

	if (this.queue.size()<=0) {
		return null;
	} else {
		List<MetricaInfo> met = this.queue.poll();
		return met;
	}

}


}
