package com.gsgtech.sap;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;




import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class SapCall {
	static String ABAP_AS_POOLED = "BOUNDARY_SAP_POOL";
	private LoginInfo login;
	
	public SapCall(LoginInfo login) {
		super();
		this.login = login;
		makeJcoDest(login);
	}


	private void makeJcoDest(LoginInfo login2) {
        Properties connectProperties = new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, login.getaHost());
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  login.getSysnr());
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, login.getClient());
        connectProperties.setProperty(DestinationDataProvider.JCO_USER,   login.getUser());
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, login.getPasswd());
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   login.getLang());

        connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "3");
        connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT,    "10");
        createDataFile(ABAP_AS_POOLED, "jcoDestination", connectProperties);

    }


	
//	static
//    {
//        Properties connectProperties = new Properties();
//        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "54.210.107.84");
//        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  "00");
//        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "800");
//        connectProperties.setProperty(DestinationDataProvider.JCO_USER,   "IDADMIN");
//        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "ides");
//        connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   "EN");
//
//        connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "3");
//        connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT,    "10");
//        createDataFile(ABAP_AS_POOLED, "jcoDestination", connectProperties);
//
//    }
	
	static private void createDataFile(String name, String suffix, Properties properties) {
		File cfg = new File(name+"."+suffix); 
		//if(!cfg.exists()) {
		try
		{
			FileOutputStream fos = new FileOutputStream(cfg, false);
			properties.store(fos, "for tests only !");
			fos.close();
		}
		catch (Exception e)
		{
			System.err.println("Unable to create the destination file " + cfg.getName());
			throw new RuntimeException("Unable to create the destination file " + cfg.getName(), e);
		}
		//}
	}
	
	
	/**
	 * 
	 * @param buscado
	 * @return
	 * @throws Exception
	 */
	public List<ValorMetricaInfo> getMetricValue(MasterConfigInfo buscado) throws Exception {
		String sapFuntion = "Z_BC_METRICAS_GET_VALUE";
		List<ValorMetricaInfo> lista = new ArrayList<ValorMetricaInfo>();

		JCoDestination destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
		JCoFunction function = destination.getRepository().getFunction(sapFuntion);
		if(function == null) {
			System.err.println(sapFuntion + " not found in SAP.");
			throw new Exception(sapFuntion + " not found in SAP.");
		}
			

		try {

			JCoParameterList in =  function.getImportParameterList();
			JCoStructure ti = in.getStructure("I_WA");

			ti.setValue ("MANDT", buscado.getMandt());
			ti.setValue ("DBNAME", buscado.getDbName());
			ti.setValue ("OPSYS", buscado.getOpSys());
			ti.setValue ("SAPRELEASE", buscado.getSapRelease());
			ti.setValue ("SERVER", buscado.getServer());
			ti.setValue ("CATEG", buscado.getCateg());
			ti.setValue ("METRIC", buscado.getMetric());
			ti.setValue ("ACTIVE", buscado.getActive());

			function.execute(destination);

			JCoParameterList out = function.getExportParameterList();
			JCoTable tabla =  out.getTable("E_IT");

			;

			if (tabla.getNumRows()!=0) {
				do {
					ValorMetricaInfo met = new ValorMetricaInfo();
					met.setServer(tabla.getString("SOURCE").trim());
					met.setSubSource(tabla.getString("SUBSOURCE").trim());
					met.setCateg(tabla.getString("CATEG").trim());
					met.setMetric(tabla.getString("METRIC").trim());
					met.setValue(tabla.getString("VALUE"));
					met.setUnit(tabla.getString("UNIT"));

					lista.add(met);

				} while (tabla.nextRow());				
			}			
		}
		catch(JCoException e) {
			System.err.println(e.getMessage());
			throw e;
		}

		return lista;
	}


	public List<MasterConfigInfo> getListaConfiguracion() throws Exception {
		String sapFuntion = "Z_BC_METRICAS_DATA_GET";
		List<MasterConfigInfo> lista = new ArrayList<MasterConfigInfo>();

		JCoDestination destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
		JCoFunction function = destination.getRepository().getFunction(sapFuntion);
		if(function == null) {
			System.err.println(sapFuntion + " not found in SAP.");
			throw new Exception(sapFuntion + " not found in SAP.");
		}
			

		try {			
			function.execute(destination);
			JCoParameterList out = function.getExportParameterList();
			JCoTable tabla =  out.getTable("E_DAT");

			if (tabla.getNumRows()!=0) {
				do {
					MasterConfigInfo met = new MasterConfigInfo();
					met.setMandt(tabla.getString("MANDT"));
					met.setDbName(tabla.getString("DBNAME"));
					met.setOpSys(tabla.getString("OPSYS"));
					met.setSapRelease(tabla.getString("SAPRELEASE"));
					met.setServer(tabla.getString("SERVER").trim());
					met.setCateg(tabla.getString("CATEG").trim());
					met.setMetric(tabla.getString("METRIC").trim());
					met.setActive(tabla.getString("ACTIVE"));

					lista.add(met);

				} while (tabla.nextRow());
			}
		}
		catch(JCoException e) {
			System.err.println(e.getMessage());
			throw e;
		}


		return lista;
	}


	public Map<String, MasterCategoriaInfo> getMapCategoria()  throws Exception {
		String sapFuntion = "Z_BC_METRICAS_DATA_GET";
		Map<String, MasterCategoriaInfo> lista = new HashMap<String, MasterCategoriaInfo>();

		JCoDestination destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
		JCoFunction function = destination.getRepository().getFunction(sapFuntion);
		if(function == null) {
			System.err.println(sapFuntion + " not found in SAP.");
			throw new Exception(sapFuntion + " not found in SAP.");
		}
		
		try {			
			function.execute(destination);
			JCoParameterList out = function.getExportParameterList();
			JCoTable tabla =  out.getTable("E_CAT");

			if (tabla.getNumRows()!=0) {
				do {
					String key = tabla.getString("SERVER").trim()+tabla.getString("CATEG").trim();
					
					MasterCategoriaInfo met = new MasterCategoriaInfo();
					met.setMandt(tabla.getString("MANDT"));
					met.setServer(tabla.getString("SERVER").trim());
					met.setCateg(tabla.getString("CATEG").trim());
					met.setText(tabla.getString("TEXT"));
					met.setEvent(tabla.getString("EVENT"));

					lista.put(key, met);

				} while (tabla.nextRow());
			} else {
				System.err.println("No metrics defined to monitoring!!!");
			}
		}
		catch(JCoException e) {
			System.err.println(e.getMessage());
			throw e;
		}


		return lista;
	}


	public Map<String, MasterMetricaInfo> getMapMetrica() throws Exception {
		String sapFuntion = "Z_BC_METRICAS_DATA_GET";
		Map<String, MasterMetricaInfo> lista = new HashMap<String, MasterMetricaInfo>();

		JCoDestination destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
		JCoFunction function = destination.getRepository().getFunction(sapFuntion);
		if(function == null) {
			System.err.println(sapFuntion + " not found in SAP.");
			throw new Exception(sapFuntion + " not found in SAP.");
		}
			

		try {			
			function.execute(destination);
			JCoParameterList out = function.getExportParameterList();
			JCoTable tabla =  out.getTable("E_MET");

			if (tabla.getNumRows()!=0) {
				do {
					MasterMetricaInfo met = new MasterMetricaInfo();
					met.setServer(tabla.getString("SERVER").trim());
					met.setCateg(tabla.getString("CATEG").trim());
					met.setMetric(tabla.getString("METRIC").trim());
					
					met.setMandt(tabla.getString("MANDT"));								
					met.setText(tabla.getString("TEXT"));
					met.setUnit(tabla.getString("UNIT"));
					
					String key = tabla.getString("SERVER").trim()+tabla.getString("CATEG").trim()+tabla.getString("METRIC").trim();

					lista.put(key, met);

				} while (tabla.nextRow());
			}
		}
		catch(JCoException e) {
			System.err.println(e.getMessage());
			throw e;
		}


		return lista;
	}


}
