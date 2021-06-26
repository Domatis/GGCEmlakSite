/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Calendar;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.ArrayList;

/**
 *
 * @author Cem
 */
@Named(value = "databaseManager")
@SessionScoped
public class DatabaseManager implements Serializable {

    private Connection conn;
  String dburl="jdbc:derby://localhost:1527/aaaa";
  String user ="aaaa";
  String pass="aaaa";
  String surname;
   String number;
     private int lastIlanId;
  //DATABASE BAĞLANAM FONKSİYONU
     
     public int getLastIlanId()
     {
         return lastIlanId;
     }
     
     public void setLastIlanId(int val)
     {
         lastIlanId = val;
     }
  
     
  public Connection connect(){
      try{
          Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
          System.out.println("CONNECTİON ESTABLİSHED");
          conn=DriverManager.getConnection(dburl, user, pass);
             
      }
      catch(Exception e){
          System.out.println("CONNECTİON NOT ESTABLİSHED");
      }
      
      return conn;
  }
  //ADI VE ŞİFRESİ KONTROL
  
  public boolean KullaniciGirisKontrol(String email , String pass){  //Name yerine email ile değiştirildi.
      connect();
       if(conn==null){
          System.out.println("Veri tabanı bağlı değil, bağlanıyorum");
          connect();
      }
       try
       {
           Statement stmt=conn.createStatement();
           ResultSet rs =stmt.executeQuery("select pass from users where email='"+email+"'");
       while(rs.next()){
        
          if(pass.equals(rs.getString(1)))   return true;         
       }
  return false;
       }
        catch(Exception e)
        {
             e.printStackTrace();
        }
      return false;
  }
  
  //Emaile göre kullanıcı bilgisi alınması için eklendi.
  public UserInfo KullanıcıInfoAl(String email)
  {
      UserInfo user = new UserInfo();
      connect();
      if(conn==null){
          System.out.println("Veri tabanı bağlı değil, bağlanıyorum");
          connect();
      }
      
      try
       {
           Statement stmt=conn.createStatement();
           ResultSet rs =stmt.executeQuery("select * from users where email='"+email+"'");
       while(rs.next()){
        
          user.email = rs.getString(1);
          user.name = rs.getString(2);
          user.surname = rs.getString(3);
          user.number = rs.getString(4);
          user.city = rs.getString(5);
          user.pass = rs.getString(6);
       }
  
       }
        catch(Exception e)
        {
             e.printStackTrace();
             return null;
        }
      
      
      return user;
      
      
  }
//KİRALIK İLANLARI LİSTELER
  public void  kiralikList(ArrayList<Ilan> ilanlar){
      connect();
      if(conn==null){
          System.out.println("Veri tabanı bağlı değil, bağlanıyorum");
          connect();
      }
    try{
            
      Statement stmt=conn.createStatement();
      ResultSet rs =stmt.executeQuery("select * from forRent");
      ResultSetMetaData rsmd=rs.getMetaData();
      
          
    while(rs.next())
    {
      //rs1=ilan no   rs2=il rs3=ilçe rs4=m2  rs5=fiyat  rs6=isim rs7=soyisim rs8=numara rs9 = email rs10 = imagelink     
        Ilan yeniilan = new Ilan(rs.getString(1),rs.getString(8),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(2),rs.getString(3),Ilan.IlanTipi.Kiralik,rs.getString(10));
       
       ilanlar.add(yeniilan);
    }
    }
    catch(Exception e){
    e.printStackTrace();
    }
    
  }
  //SATİLİK İLANLARİ LİSTELER
  public void  satilikkList(ArrayList<Ilan> ilanlar){
      connect();
      if(conn==null){
          System.out.println("Veri tabanı bağlı değil, bağlanıyorum");
          connect();
      }
    try{
            
      Statement stmt=conn.createStatement();
      ResultSet rs =stmt.executeQuery("select * from forSale");
      ResultSetMetaData rsmd=rs.getMetaData();
     
          
    while(rs.next())
    {
      //rs1=ilan no   rs2=il rs3=ilçe rs4=m2  rs5=fiyat  rs6=isim rs7=soyisim rs8=numara
       
        
        Ilan yeniilan = new Ilan(rs.getString(1),rs.getString(8),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(2),rs.getString(3),Ilan.IlanTipi.Satilik,rs.getString(10));
        
        ilanlar.add(yeniilan);
        
    }
    }
    catch(Exception e){
    e.printStackTrace();
    }
    
  }
 
//USERS DB ELEMAN EKLER
public void ElemanEkle(String email,String name,String surname,String number,String location,String pass){
    connect();
       if(conn==null){
          System.out.println("Veri tabanı bağlı değil, bağlanıyorum");
          connect();
      }
      try
    {
     
  
      Calendar calendar = Calendar.getInstance();
      java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

   
      String query = " insert into users (email,name, surname,number,location,pass)"
        + " values (?, ?,?,?,?,?)";

   
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setString (1, email);
      preparedStmt.setString (2, name);
      preparedStmt.setString (3, surname);
      preparedStmt.setString (4, number);
      preparedStmt.setString (5, location);
      preparedStmt.setString (6, pass);
     
      preparedStmt.execute();
      
      conn.close();
    }
    catch (Exception e)
    {
      System.err.println("Got an exception!");
      System.err.println(e.getMessage());
    }
    
    
    
}
//KİRALIK İLAN EKLER
public boolean kiralikEkle(String il,String ilce,String m2,String fiyat,String name,String surname,String number,String email,String imagelink,StringBuilder errormessage){
    connect();
       if(conn==null){
          System.out.println("Veri tabanı bağlı değil, bağlanıyorum");
          connect();
      }
      try
    {
      // create a mysql database connection
  
      // create a sql date object so we can use it in our INSERT statement
      Calendar calendar = Calendar.getInstance();
      java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

      // the mysql insert statement
      String query = " insert into forRent (ilanno,il,ilce,m2,fiyat,name,surname,number,email,image)"
        + " values (?,?,?,?,?,?,?,?,?,?)";

      // create the mysql insert preparedstatement
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setString (1, String.valueOf(ilanNo()));
      preparedStmt.setString (2, il);
      preparedStmt.setString (3, ilce);
      preparedStmt.setString (4, m2);
      preparedStmt.setString (5, fiyat);
       preparedStmt.setString (6, name);
        preparedStmt.setString (7, surname);
       preparedStmt.setString (8, number);
       preparedStmt.setString (9, email);
       preparedStmt.setString (10, imagelink);
       
       
      // execute the preparedstatement
      preparedStmt.execute();
      
      conn.close();
      return true;
      
      
    }
    catch (Exception e)
    {
      errormessage.append(e);
      return false;
    }
    
    
    
}
//SATİLİK İLAN EKLER
public boolean satilikEkle(String il,String ilce,String m2,String fiyat,String name,String surname,String number,String email,String imagelink,StringBuilder errormessage){
    connect();
       if(conn==null){
          System.out.println("Veri tabanı bağlı değil, bağlanıyorum");
          connect();
      }
      try
    {
      // create a mysql database connection
  
      // create a sql date object so we can use it in our INSERT statement
      Calendar calendar = Calendar.getInstance();
      java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

      // the mysql insert statement
      String query = " insert into forSale (ilanno,il,ilce,m2,fiyat,name,surname,number,email,image)"
        + " values (?,?,?,?,?,?,?,?,?,?)";

      // create the mysql insert preparedstatement
      PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString (1, String.valueOf(ilanNo()));
      preparedStmt.setString (2, il);
      preparedStmt.setString (3, ilce);
      preparedStmt.setString (4, m2);
      preparedStmt.setString (5, fiyat);
       preparedStmt.setString (6, name);
        preparedStmt.setString (7, surname);
       preparedStmt.setString (8, number);
       preparedStmt.setString (9, email);
       preparedStmt.setString (10, imagelink);
      // execute the preparedstatement
      preparedStmt.execute();
      
      conn.close();
      return true;
    }
    catch (Exception e)
    {
      errormessage.append(e);
      return false;
    }  
}
//İSTEK DB ELEMAN EKLER
public void mesajEkle(String mesaj){
    connect();
       if(conn==null){
          System.out.println("Veri tabanı bağlı değil, bağlanıyorum");
          connect();
      }
      try
    {
    
      Calendar calendar = Calendar.getInstance();
      java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());


      String query = " insert into istek"
        + " values (?)";

    
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setString (1, mesaj);
     
  
      preparedStmt.execute();
      
      conn.close();
    }
    catch (Exception e)
    {
      System.err.println("Got an exception!");
      System.err.println(e.getMessage());
    }
    
    
    
}
//KİRALIK İKAN SİLER    
 public void  kiralikDelete(String ilanno){
     connect();
      if(conn==null){
          System.out.println("Veri tabanı bağlı değil, bağlanıyorum");
          connect();
      }
    try{
            
      Statement stmt=conn.createStatement();
   int count = stmt.executeUpdate(
        "DELETE FROM forRent WHERE ilanno='"+ilanno+"'");
     
  
    }
    catch(Exception e){
    e.printStackTrace();
    }
    
  }
 //SATİLİK İLAN SİLER
public void  satilikDelete(String ilanno){
    connect();
      if(conn==null){
          System.out.println("Veri tabanı bağlı değil, bağlanıyorum");
          connect();
      }
    try{
            
      Statement stmt=conn.createStatement();
   int count = stmt.executeUpdate(
        "DELETE FROM forSale WHERE ilanno='"+ilanno+"'");
     
  
    }
    catch(Exception e){
    e.printStackTrace();
    }
    
  }
// forRent talosuna kişinin soyadı ve numara bilgisi gönderiliyor
public void ilanBilgiRent(String name) {
   connect();
    if(conn==null){
          System.out.println("Veri tabanı bağlı değil, bağlanıyorum");
          connect();
      }
    try{
            
      Statement stmt=conn.createStatement();
      ResultSet rs =stmt.executeQuery("select surname , number from users where name='"+name+"'");
      ResultSetMetaData rsmd=rs.getMetaData();
 
          
    while(rs.next())
    {
      
    surname=rs.getString(1);
   number=rs.getString(2);
       
    }
    }
    catch(Exception e){
    e.printStackTrace();
    }
  
     try
    {
   
  
      Calendar calendar = Calendar.getInstance();
      java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
// UPDATE FORRENT SET surname='daas' , number='ggg' where name='gokhan'
   
      String query ="update forRent set surname='"+surname+"',number='"+number+"' where name='"+name+"'";
       

   
      PreparedStatement preparedStmt = conn.prepareStatement(query);
    
      
    
      preparedStmt.execute();
      
      conn.close();
    }
    catch (Exception e)
    {
      System.err.println("Got an exception!");
      System.err.println(e.getMessage());
    }
    
    
 
}
//forSale tablosuna kişinin soyadı ve numarası bilgisi gönderiliyor
public void ilanBilgiSale(String name) {
   connect();
    if(conn==null){
          System.out.println("Veri tabanı bağlı değil, bağlanıyorum");
          connect();
      }
    try{
            
      Statement stmt=conn.createStatement();
      ResultSet rs =stmt.executeQuery("select surname , number from users where name='"+name+"'");
      ResultSetMetaData rsmd=rs.getMetaData();
 
          
    while(rs.next())
    {
      
    surname=rs.getString(1);
   number=rs.getString(2);
       
    }
    }
    catch(Exception e){
    e.printStackTrace();
    }
  
     try
    {
   
  
      Calendar calendar = Calendar.getInstance();
      java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
   
      String query ="update forSale set surname='"+surname+"',number='"+number+"' where name='"+name+"'";
       

   
      PreparedStatement preparedStmt = conn.prepareStatement(query);
    
      
    
      preparedStmt.execute();
      
      conn.close();
    }
    catch (Exception e)
    {
      System.err.println("Got an exception!");
      System.err.println(e.getMessage());
    }
    
    
 
}
//String olarak verilen email database de varsa true yoksa false dönüyor.
public boolean emailKontrol(String email,StringBuilder message){
    connect();
     if(conn==null){
          System.out.println("Veri tabanı bağlı değil, bağlanıyorum");
          connect();
      }
       try
       {
           Statement stmt=conn.createStatement();
           ResultSet rs =stmt.executeQuery("select email from users ");
            while(rs.next())
            { 
                if(email.equals(rs.getString(1)))
                {
                    message.append("Email kullanılıyor,tekrar deneyiniz");
                    //rs.close();
                    return true;
                }
                
                }
            
  return false;
       }
        catch(Exception e){
             message.append("Database error = " + e.getMessage());
             return true;   //error durumunda onay vermemesi için.
               
        }
      
}
// Emaile göre tekrar düzeltildi.
public void UserIlanlarıAl(String email, ArrayList<Ilan> ilanlar){
    connect();
      if(conn==null){
          System.out.println("Veri tabanı bağlı değil, bağlanıyorum");
          connect();
      }
      
     try{
            
      Statement stmt=conn.createStatement();
      ResultSet rs =stmt.executeQuery("select * from forSale where email ='"+email+"'");
     
      
          //SATILIK İLAN
    while(rs.next())
    {
      //rs1=ilan no   rs2=il rs3=ilçe rs4=m2  rs5=fiyat  rs6=isim rs7=soyisim rs8=numara rs9= email rs10 = imagelink
        Ilan yeniilan = new Ilan(rs.getString(1),rs.getString(8),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(2),rs.getString(3),Ilan.IlanTipi.Satilik,rs.getString(10));
        
        ilanlar.add(yeniilan);
       
    }
    }
    catch(Exception e){
    e.printStackTrace();
    }
     try{
            
      Statement stmt=conn.createStatement();
      ResultSet rs =stmt.executeQuery("select * from forRent where email='"+email+"'");
      
      
          //KİRALIK İLAN
    while(rs.next())
    {
      //rs1=ilan no   rs2=il rs3=ilçe rs4=m2  rs5=fiyat  rs6=isim rs7=soyisim rs8=numara rs9= email rs10 = imagelink
       Ilan yeniilan = new Ilan(rs.getString(1),rs.getString(8),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(2),rs.getString(3),Ilan.IlanTipi.Kiralik,rs.getString(10));
       
       ilanlar.add(yeniilan);
    }
    }
    catch(Exception e){
    e.printStackTrace();
    }

}
// ilan numarası için databaseden bir sonraki rakamı döndürüyor
public int ilanNo(){
 connect();
    if(conn==null){
          System.out.println("Veri tabanı bağlı değil, bağlanıyorum");
          connect();
      }
    try{
            
      Statement stmt=conn.createStatement();
      ResultSet rs =stmt.executeQuery("SELECT NUMBER FROM INDEXILAN");
      ResultSetMetaData rsmd=rs.getMetaData();
   
          
    while(rs.next())
    {
        
      lastIlanId=rs.getInt(1);
     
       
    }
    }
    catch(Exception e){
    e.printStackTrace();
    }
   
   
      try
    {
     
  
      Calendar calendar = Calendar.getInstance();
      java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

   
      String query = " insert into indexilan (number)"
        + " values (?)";

   
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setInt (1, lastIlanId+1);
     
     
      preparedStmt.execute();
    
      conn.close();
    }
    catch (Exception e)
    {
      System.err.println("Got an exception!");
      System.err.println(e.getMessage());
    }
    
    
      return lastIlanId+1;
   
}

    
}
