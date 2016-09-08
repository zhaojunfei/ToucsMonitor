package com.adtec.toucs.monitor.usermanager;

/**
 * <p>Title: logInfo</p>
 * <p>Description: 日志bean，提供了与日志表对应的机构，并提供了相应的set和get方法。</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: ADTEC</p>
 * @author Fancy
 * @version 1.0
 */

public class logInfo {

    public logInfo() {
    }
    
    private String Log_Id;
    private String Org_Id;
    private String User_Name;
    private String Oper_Date;
    private String Oper_Time;
    private String Term_Addr;
    private String Oper_Memo;
    private String Resource_Id;
    private String Memo;

    public void setLog_Id( String Log_Id ) { this.Log_Id = Log_Id;}
    public void setOrg_Id( String Org_Id ) { this.Org_Id = Org_Id;}
    public void setUser_Name( String User_Name ) { this.User_Name = User_Name;}
    public void setOper_Date( String Oper_Date ) { this.Oper_Date = Oper_Date;}
    public void setOper_Time( String Oper_Time ) { this.Oper_Time = Oper_Time;}
    public void setTerm_Addr( String Term_Addr ) { this.Term_Addr = Term_Addr;}
    public void setOper_Memo( String Oper_Memo ) { this.Oper_Memo = Oper_Memo;}
    public void setResource_Id( String Resource_Id) { this.Resource_Id = Resource_Id;}
    public void setMemo( String Memo) { this.Memo = Memo;}


    public String getLog_Id(){return this.Log_Id;}
    public String getOrg_Id(){return this.Org_Id;}
    public String getUser_Name(){return this.User_Name;}
    public String getOper_Date(){return this.Oper_Date;}
    public String getOper_Time(){return this.Oper_Time;}
    public String getTerm_Addr(){return this.Term_Addr;}
    public String getOper_Memo(){return this.Oper_Memo;}
    public String getResource_Id(){return this.Resource_Id;}
    public String getMemo(){return this.Memo;}
}