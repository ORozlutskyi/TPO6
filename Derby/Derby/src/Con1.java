import java.sql.*;
import java.lang.reflect.*;


public class Con1  {

 String url = "jdbc:derby://localhost/ksidb"; 
 Connection con;
 DatabaseMetaData md;
 boolean exitOnException = true;
 
 public Con1()  {
    try {
     con = DriverManager.getConnection(url);
     md = con.getMetaData();
     reportInfo();
     
     Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
     ResultSet rs = stmt.executeQuery("SELECT AUTOR.NAME, POZYCJE.TYTUL, POZYCJE.CENA  FROM autor, pozycje where POZYCJE.AUTID = AUTOR.AUTID");
     rs.afterLast();
     while (rs.previous()) {
       System.out.println(rs.getString(1) + ". " + rs.getString(2) + " -- " + rs.getString(3) );
     }
     
     if (con != null) con.close();
    } catch (Exception exc)  { 
       System.out.println(exc); 
       if (exitOnException) System.exit(1);
    }
 }

 void reportInfo() throws SQLException { 
   exitOnException = false;  
   info("getDatabaseProductName");
   info("getDatabaseProductVersion");
   info("getDriverName");
   info("getURL");
   info("getUserName");
   
   info("supportsAlterTableWithAddColumn");
   info("supportsAlterTableWithDropColumn");
   info("supportsANSI92FullSQL");
   info("supportsBatchUpdates");
   info("supportsMixedCaseIdentifiers");
   info("supportsMultipleTransactions");
   info("supportsPositionedDelete");
   info("supportsPositionedUpdate");
   info("supportsSchemasInDataManipulation");
   info("supportsTransactions");
   
   System.out.println("ResultSet  TYPE_SCROLL_INSENSITIVE :" + 
       md.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE));  
   System.out.println("ResultSet  TYPE_SCROLL_SENSITIVE :" +
       md.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE));
   System.out.println("insertsAreDetected :" +
       md.insertsAreDetected(ResultSet.TYPE_SCROLL_INSENSITIVE));
   System.out.println("updatesAreDetected :" +
       md.updatesAreDetected(ResultSet.TYPE_SCROLL_INSENSITIVE));         
 }           

 @SuppressWarnings("unchecked")
void info(String metName)  { 
   Class mdc  = DatabaseMetaData.class;
   Class[] paramTypes =  { };
   Object[] params =  { };
   String infoTyp;
   if (metName.startsWith("get")) 
     infoTyp = metName.substring(3,metName.length());
   else infoTyp = metName;
   try  {
     Method m = mdc.getDeclaredMethod(metName, paramTypes);
     System.out.println(infoTyp + ": " + m.invoke(md, params));
   } catch(Exception exc)  { 
     System.out.println(exc);
   }   
 }
    
 public static void main(String[] args)  {
   new Con1();
 }
 
}  