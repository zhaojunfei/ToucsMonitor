package com.adtec.toucs.monitor.common;

import java.sql.*;
import javax.naming.*;
import javax.sql.DataSource;


/**
 * <p>Title: moia</p>
 * <p>Description: 这个类实现了对数据库操作的一些封装，增加了一些异常的处理</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: adtec</p>
 * @author dmh
 * @version 1.0
 */

public class SqlAccess {

    //数据库连接
    public Connection conn = null;
    //结果集
    private ResultSet rs = null;
    //数据库访问段
    public Statement stmt = null;
    //是否连接数据库
    private boolean isConnDB = false;
    private boolean isDefault = true;
    private String newDSName = null;

    public SqlAccess() {
    	try{
    		initial();
    		//conn.setAutoCommit(true);
    	}catch(Exception exp) {
    		exp.printStackTrace();
    		System.out.println(exp.getMessage());
    	}
    }
    
    public SqlAccess(String newDS) {
    	if( newDS != null && newDS.trim() != null){
    		isDefault = false;
    		newDSName=newDS;
    	}
    	try{
    		initial();
    	}catch(Exception exp){
    		exp.printStackTrace();
    		System.out.println(exp.getMessage());
    	}
    }

    public static SqlAccess createConn(String ds) throws MonitorException{
    	SqlAccess sq=new SqlAccess(ds);
    	if(sq==null||sq.conn==null)
    		throw new MonitorException(ErrorDefine.FAIL_DB_CONN,ErrorDefine.FAIL_DB_CONN_DESC);
    	return sq;
    }

    public static SqlAccess createConn() throws MonitorException{
    	return createConn(JNDINames.MONITOR_DATASOURCE);
    }

    public static void main(String[] args) {
    	SqlAccess sq = new SqlAccess("java:/DefaultDS");
    	try{
    		ResultSet rst=sq.queryselect("SELECT * FROM Branch");
    		while(rst.next()){
    			Debug.print(rst.getString("CityID")+"\t");
    			Debug.print(rst.getString("BRANCHID")+"\t");
    			Debug.print(rst.getString("BRANCHNAME")+"\n");
    		}
    	}catch(Exception exp){
    		exp.printStackTrace();
    		System.out.println(exp.getMessage());
    	}
    }

    public void initial() throws SQLException{
    	try {
    		Context ctx = new InitialContext();
    		DataSource ds = null ;
    		if(isDefault==true){
    			ds = (DataSource)ctx.lookup(JNDINames.MONITOR_DATASOURCE);
    		} else{
    			Debug.println(newDSName);
    			Context envCtx=(Context)ctx.lookup("java:comp/env");
    			ds=(DataSource)envCtx.lookup("ToucsMonitorPool");
    		}
    		conn = ds.getConnection();
    		stmt = conn.createStatement();
    		isConnDB = true;
    		Debug.println("making a connection...");
    	} catch (SQLException sqle) {
    		Debug.println("SQLException during connection(): " + sqle.getMessage());
    		throw sqle;
    	} catch (Exception e) {
    		e.printStackTrace();
    		Debug.println(e.getMessage());
    	}
    }

    //执行SELECT操作;返回结果集(出错的话返回 null )
    public ResultSet queryselect(String Sql) throws SQLException{
    	rs = null;
    	try {
    		Debug.print("\n"+Sql+"beginning");
    		rs = stmt.executeQuery (Sql);
    		Debug.print("\n"+Sql+"successd");
    	} catch (SQLException sqle) {
    		Debug.fPrint("    SQLState = " + sqle.getSQLState());
    		Debug.fPrint("    Message = " + sqle.getMessage());
    		Debug.fPrint("    SQLCODE = " + sqle.getErrorCode());
            throw sqle;
    	} catch(Exception e) {
    		e.printStackTrace();
            Debug.println(e.getMessage());
    	}
    	return rs;
    }

    //执行INSERT,UPDATE操作;返回操作记录数(出错的话返回 -1 )
    public int queryupdate (String Sql) throws SQLException{
    	int affect = -1;
    	try {
    		Debug.print(Sql+"beginning");
    		affect = stmt.executeUpdate(Sql);
    		Debug.print(Sql+"successd");
    	} catch (SQLException sqle) {
            Debug.fPrint("SQLException during update(): " );
            Debug.fPrint("    SQLState = " + sqle.getSQLState());
            Debug.fPrint("    Message = " + sqle.getMessage());
            Debug.fPrint("    SQLCODE = " + sqle.getErrorCode());
            throw sqle;
    	} catch (Exception e) {
    		e.printStackTrace();
            Debug.println(e.getMessage());
    	}
    	return affect;
    }

    //执行DELETE,INSERT,UPDATE返回操作记录数（出错的话返回 -1，日志记录出错返回-2）
    public int queryupdate (String Sql,String userName,String operType,String operDes) throws SQLException{
        int affect = -1;
        try {
            Debug.print(Sql+"beginning");
            affect = stmt.executeUpdate(Sql);
            Debug.print(Sql+"successd");
        } catch (SQLException sqle) {
            Debug.fPrint("SQLException during update(): " );
            Debug.fPrint("    SQLState = " + sqle.getSQLState());
            Debug.fPrint("    Message = " + sqle.getMessage());
            Debug.fPrint("    SQLCODE = " + sqle.getErrorCode());
            throw sqle;
        } catch (Exception e) {
        	e.printStackTrace();
            Debug.println(e.getMessage());
        }
        return affect;
    }

    //设置AutoCommit
    public void setAutoCommit(boolean autoCommit) throws SQLException{
    	try {
    		if (isConnDB)
    			conn.setAutoCommit(autoCommit);
    	} catch (SQLException sqle) {
            Debug.println("SQLException during autoCommit(): " + sqle.getMessage());
            throw sqle;
        } catch (Exception e) {
        	e.printStackTrace();
            Debug.println(e.getMessage());
        }
    }

    //执行Commit命令
    public void commit()throws SQLException {
    	try {
    		if (isConnDB)
    			conn.commit();
    	} catch (SQLException sqle) {
            Debug.println("SQLException during commit(): " + sqle.getMessage());
            throw sqle;
        } catch (Exception e) {
        	e.printStackTrace();
            Debug.println(e.getMessage());
        }
    }

    //执行Rollback命令
    public void rollback(){
    	try {
    		if (isConnDB)
    			conn.rollback();
    	} catch (SQLException sqle) {
            Debug.println("SQLException during rollback(): " + sqle.getMessage());
        } catch (Exception e) {
        	e.printStackTrace();
            Debug.println(e.getMessage());
        }
    }

    //查询与数据库之间的连接是否关闭,true - 有连接:false - 没连接
    public boolean isConnectDB() {
    	return isConnDB;
    }

    //关闭连接
    public void close(){
    	try {
    		if (isConnDB && conn != null )
    			conn.close();
    		this.isConnDB = false;
    		Debug.println("\n"+"Closing connection..."+"\n");
    	}catch (SQLException sqle) {
    		sqle.printStackTrace();
            Debug.println("SQLException during close(): " + sqle.getMessage());
        } catch (Exception e) {
        	e.printStackTrace();
            Debug.println(e.getMessage());
        }
    }

    public boolean execute(String Sql) throws SQLException{
    	boolean affect =false;
    	try {
		    Debug.print(Sql+"beginning");
		    affect = stmt.execute(Sql);
		    Debug.print(Sql+"successd");
    	}catch (SQLException sqle) {
		    Debug.fPrint("SQLException during update(): " );
		 	Debug.fPrint("    SQLState = " + sqle.getSQLState());
			Debug.fPrint("    Message = " + sqle.getMessage());
			Debug.fPrint("    SQLCODE = " + sqle.getErrorCode());
		    throw sqle;
    	}catch (Exception e) {
    		e.printStackTrace();
    		Debug.println(e.getMessage());
    	}
    	return affect;
    }
}

