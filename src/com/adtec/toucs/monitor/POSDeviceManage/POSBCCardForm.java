package com.adtec.toucs.monitor.POSDeviceManage;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class POSBCCardForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FormFile file=null;

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}
	
	
	
}
