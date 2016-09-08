package com.adtec.toucs.monitor.common;

/**
 * <p>Title: moia</p>
 * <p>Description: dmh for moia</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: adtec</p>
 * @author dmh
 * @version 1.0
 */

/**
 * This class is the central location to store the internal JNDI names of
 * various entities. Any change here should also be reflected in the deployment
 * descriptors. �������Ҫ���jndi�ĺ궨��,������µ�jndi���������������
 */
public interface JNDINames {

	/**
	 * weblogic server�������Ļ�����INITIAL_CONTEXT_FACTORY
	 * 
	 */
	public static final String WLS_INITIAL_CONTEXT_FACTORY = "weblogic.jndi.WLInitialContextFactory";

	/**
	 * ���ݿ�DATA SOURCE��JNDI��
	 */
	//public static final String MOIA_DATASOURCE = "moia";

	//public static final String TEST_DATASOURCE = "java:/TestPool";

	public static final String MONITOR_DATASOURCE = "ToucsMonitorPool";

	//public static final String FCARDPOS_DATASOURCE = "FCardPosPool";

}
