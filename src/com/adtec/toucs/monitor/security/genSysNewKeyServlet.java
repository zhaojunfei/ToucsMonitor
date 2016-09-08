package com.adtec.toucs.monitor.security;

import javax.servlet.*;
import javax.servlet.http.*;
import java.security.*;
import javax.crypto.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.security.DESCryptography;

public class genSysNewKeyServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=GBK";
    /**Initialize global variables*/
    public void init() throws ServletException{
    }
    /**Process the HTTP Post request*/
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        //���ݿ�����
        SqlAccess conn = null;
        boolean isSucc = true;
        String isRESET = "";
        try {
            isRESET = request.getParameter("isRESET");
            if (isRESET.equalsIgnoreCase("yes")) {
                conn = new SqlAccess();
                //����°�ȫ�㷨,�����JCE��Ҫ������ӽ�ȥ
                Security.addProvider(new com.sun.crypto.provider.SunJCE());
                //��������Կ
                String Algorithm = "DES";
                KeyGenerator keygen = KeyGenerator.getInstance(Algorithm);
                keygen.init(56);
                SecretKey deskey = keygen.generateKey();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream(4096);
                ObjectOutputStream outStream = new ObjectOutputStream(buffer);
                outStream.writeObject(deskey);
                outStream.close();
                byte[] key = buffer.toByteArray();
                String skey = Base64Encoder.encode(key);
                //ȡ���û����벢����
                String Sql="select user_name,password from sch_userinfo";
                ResultSet rs=conn.queryselect(Sql);
                DESCryptography DC=new DESCryptography();
                Vector oV=new Vector();
                Hashtable userinfo=new Hashtable();
                String user_name="";
                String password="";
                while(rs.next()){
                    userinfo=new Hashtable();
                    user_name=rs.getString("user_name");
                    password=rs.getString("password");
                    password=password.trim();
                    password=DC.decrypt(password);
                    userinfo.put("username",user_name);
                    userinfo.put("password",password);
                    oV.add(userinfo);
                }
                //��ʹԭ������Կʧ��
                String sql = "update sch_key set c_flag = '0'";
                int eff = conn.queryupdate(sql);
                if (eff < 0)
                    isSucc = false;
                //��������Կ
                if (isSucc){
                    Vector daccount = new Vector();
                    daccount.add("0");
                    daccount.add(skey);
                    daccount.add("DES KEY");
                    daccount.add("1");
                    DACCode dac = new DACCode();
                    String code = dac.getPKeyDAC(daccount);
                    sql = "insert into sch_key(c_key,c_keycomment,c_flag,dac) values ('"+skey+"','DES KEY','1','"+code+"')";
                    eff = conn.queryupdate(sql);
                    if (eff != 1)
                        isSucc = false;
                }
                //�޸����в���Ա�ĳ�ʼ������
                if (isSucc) {
                    int i=0;
                    DESCryptography resetDC=new DESCryptography();
                    while(i<oV.size()){
                        user_name=(String)((Hashtable)oV.get(i)).get("username");
                        user_name=user_name.trim();
                        password=(String)((Hashtable)oV.get(i)).get("password");
                        password=password.trim();
                        password=resetDC.encrypt(password);
                        sql="update sch_userinfo set password='"+password+"' where user_name='"+user_name+"'";
                        eff = conn.queryupdate(sql);
                        if (eff < 0)
                            isSucc = false;
                        i++;
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            isSucc = false;
        }
        //��ʾ���
        if (isSucc)
            out.print("<link rel='stylesheet' href='../style/v5_css.css' type='text/css'><br><br><br><br><p align='center'><font size=2  color=#330033>������Կ�ɹ���</p>");
        else
            out.print("<link rel='stylesheet' href='../style/v5_css.css' type='text/css'><br><br><br><br><p align='center'><font size=2  color=#330033>������Կʧ�ܣ���<a href=./userManager/resetPKey.jsp>����</a>��</p>");
    }
    /**Clean up resources*/
    public void destroy() {
    }
}