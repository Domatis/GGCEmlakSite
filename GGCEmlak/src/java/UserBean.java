/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.inject.Inject;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Cem
 */
@Named(value = "user")
@SessionScoped
public class UserBean implements Serializable {

    private String name,surname,city,email,number,pass;
    private String failuyemesaj;
    private String faililanmesaj;
    private String onerionaymesaj;
    private String istekmesaj;
    private boolean girisbasarili = false;
    private String failgirismesaj;
    private ArrayList<Ilan> ilanlarım = new ArrayList<Ilan>();
    @Inject
    private IlanManager ilanmanager;    
    @Inject
    private DatabaseManager dbmanager;
    
    private String ilanil,ilanilce,ilanimage,ilanm2,ilanfiyat,ilantip;

    
    public String getOnerionaymesaj()
    {
        String temp = onerionaymesaj;
        onerionaymesaj = "";
        return temp;
    }
    
    
    public String getIstekmesaj()
    {
        return istekmesaj;
    }
    
    public void setIstekmesaj(String val)
    {
        istekmesaj = val;
    }
    
    public String getFaililanmesaj()
    {
        String temp = faililanmesaj;  
        faililanmesaj = "";
        return temp;
    }
    
    public void setFaililanmesaj(String val)
    {
        
        faililanmesaj = val;
    }
    
    public String getIlanil()
    {
        return ilanil;
    }
    
    public void setIlanil(String val)
    {
        ilanil = val;
    }
    
    public String getIlanilce()
    {
        return ilanilce;
    }
    
    public void setIlanilce(String val)
    {
        ilanilce = val;
    }
    
    public String getIlanimage()
    {
        return ilanimage;
    }
    
    public void setIlanimage(String val)
    {
        ilanimage = val;
    }
    
    public String getIlanm2()
    {
        return ilanm2;
    }
    
    public void setIlanm2(String val)
    {
        ilanm2 = val;
    }
    
    public String getIlanfiyat()
    {
        return ilanfiyat;
    }
    
    public void setIlanfiyat(String val)
    {
        ilanfiyat =val;
    }
    
    public String getIlantip()
    {
        return ilantip;
    }
    
    public void setIlantip(String val)
    {
        ilantip = val;
    }
    
    public ArrayList<Ilan> getIlanlarım()
    {
        return ilanlarım;
    }
    
    
    public boolean getGirisbasarili()
    {
        return this.girisbasarili;
    }
    
    public void setGirisbasarili(boolean b)
    {
        this.girisbasarili = b;
    }
     
    public String getName()
    {
        return this.name;
    }
    
    public void setName(String n)
    {
        this.name = n;
    }
    
    public String getSurname()
    {
        return this.surname;
    }
    
    public void setSurname(String s)
    {
        this.surname = s;
    }
    
    public String getCity()
    {
        return this.city;
    }
    
    public void setCity(String c)
    {
        this.city = c;
    }
    
    public String getEmail()
    {
        return this.email;
    }
    
    public void setEmail(String e)
    {
        this.email = e;
    }
    
    
    public String getNumber()
    {
        return this.number;
    }
    
    public void setNumber(String n)
    {
        this.number = n;
    }
    
    public String getPass()
    {
        return this.pass;
    }
    
    public void setPass(String p)
    {
        this.pass = p;
    }
    
    public String getFailuyemesaj()
    {
        String temp = this.failuyemesaj;
        failuyemesaj = "";              //Mesaj 1 kere alındıktan sonra kendini sıfırlasın diye.
        return temp;
    }
    
    public void setFailuyemesaj(String s)
    {
        this.failuyemesaj = s;
    }
    
    public String getFailgirismesaj()
    {     
        String temp = this.failgirismesaj;
        failgirismesaj = "";
        return temp;
    }
    
    public void setFailgirismesaj(String s)
    {     
        this.failgirismesaj = s;
    }
    
    public String OneriGonder()
    {
        //Databasee öneri mesajını gönder.
        dbmanager.mesajEkle(istekmesaj); 
        //Öneri onay mesajı oluştur.
        onerionaymesaj = "Mesajınız başarıyla iletilmiştir";     
        istekmesaj = "";
        return "hakkimizda_page";
    }
    
    public void IlanSil(ActionEvent event)
    {
        //İlan numarasının alınması.
        String ilanno = (String)event.getComponent().getAttributes().get("ilanno");
        String ilantip = (String)event.getComponent().getAttributes().get("ilantipname");
        
        //Kiralık için.
        if(ilantip.equals("Kiralık"))
        {
            //Databaseden sil.
            dbmanager.kiralikDelete(ilanno);
            
        }
        
        else //Satılık için.
        {
            //Databaseden sil.
            dbmanager.satilikDelete(ilanno);
            
        }
   
        
        //Anlık ilanlarımı güncelle.
        ilanlarım.clear();
        dbmanager.UserIlanlarıAl(email, ilanlarım);
        
        //Ilanmanager listelerini güncelle.
        ilanmanager.UpdateIlanlar();
        
    }
    
    public String UyeGirisi()
    {
        
        //Girilen email ile şifreyi kontrol et eğer doğruysa profil sayfasına yönlendir.
        
        boolean kullanicigirisbasarili = dbmanager.KullaniciGirisKontrol(email, pass);
        
        
        if(kullanicigirisbasarili)  
        {
            //Giriş başarılı.
                 
            UserInfo currentuserinfo = dbmanager.KullanıcıInfoAl(email);
            
            if(currentuserinfo == null) //Hata sonucu null döndüğü durum.
            {
                girisbasarili = false;
                    failgirismesaj = "Database Hatası Oluştu";
                    UserParametreleriSifirla();
                    return "login_page";
            }
            
            this.name = currentuserinfo.name;
            this.surname = currentuserinfo.surname;
            this.city = currentuserinfo.city;
            this.number = currentuserinfo.number;
            girisbasarili = true;        
            
            
             //Databasende kullanıcıya göre ilanlar alınacak ve ilanlarım listesi güncellenecek.
            dbmanager.UserIlanlarıAl(email, ilanlarım);
            
      
            return "myprofile_page";       
        }   
        
        
        //Eğer giriş başarılı değilse yine aynı sayfaya yönlendir.
        //Uyarı mesajını güncelle.
        girisbasarili = false;
        failgirismesaj = "Girdiğiniz e-mail veya şifre geçersizdir.";
        UserParametreleriSifirla();
        return "login_page";
        
        
    }
    
    public void IlanBilgiSıfırla()
    {
        ilanilce = "";
        ilanimage = "";
        ilanil = "";
        ilanm2 = "";
        ilanfiyat = "";
        ilantip = "";
    }
    
    public void UserParametreleriSifirla()
    {
        this.name = "";
        this.surname = "";
        this.email = "";
        this.pass = "";
        this.city = "";
        this.number = "";
        ilanlarım.clear();
    }
    
    
    public String logOut()
    {

        //Kullanıcı logout olduğundan bütün parametreler sıfırlanacak.
        UserParametreleriSifirla();
        girisbasarili = false;
        return"main_page";
    }
    
    
    public String IlanEkle()
    {
        
        //Verilen parametrelerden yeni ilan oluştur hem şuanki listeye ekle hem databasee gönder.
        
        if(ilantip == null)
        {
            faililanmesaj = "Lütfen ilan tipi seçiniz";
            return "ilan_ekle_page";
        }    //Boş bırakıldığında veri null olarak kabul ediliyor.
        
        if(ilantip.equals("kiralik"))
        {
            
            StringBuilder returnmessage = new StringBuilder();
            //Oluşturulan ilanın database'e eklenmesi.
            boolean ilaneklendi = dbmanager.kiralikEkle(ilanil, ilanilce, ilanm2, ilanfiyat, name, surname, number, email, ilanimage,returnmessage);
            
            
            
            if(ilaneklendi)
            {
                 //Kullanıcı için oluşturulan ilanın şuanki ilanlarım listesine eklenmesi.
            String tempilanno = String.valueOf(dbmanager.getLastIlanId()+1);
            Ilan newilan = new Ilan(tempilanno,number,ilanm2,ilanfiyat,name,surname,ilanil,ilanilce,Ilan.IlanTipi.Kiralik,ilanimage);
            
            ilanlarım.add(newilan);
            ilanmanager.UpdateKiralikIlanlar(newilan);
            
            faililanmesaj = "";
            
            IlanBilgiSıfırla();
            return "ilanlarim_page";
            }
            
            //Database ilan eklerken error vermesi durumu.
            else
            {
                IlanBilgiSıfırla();
                faililanmesaj = returnmessage.toString();
                return "ilan_ekle_page";
            }
            
           
        }
            
        else if (ilantip.equals("satilik"))
        {
            //Oluşturulan ilanın database'e eklenmesi.
            StringBuilder returnmessage = new StringBuilder();
            boolean ilaneklendi = dbmanager.satilikEkle(ilanil, ilanilce, ilanm2, ilanfiyat, name, surname, number, email, ilanimage,returnmessage);
            
            if(ilaneklendi)
            {
                //Kullanıcı için oluşturulan ilanın şuanki ilanlarım listesine eklenmesi.
            String tempilanno = String.valueOf(dbmanager.getLastIlanId()+1);
            Ilan newilan = new Ilan(tempilanno,number,ilanm2,ilanfiyat,name,surname,ilanil,ilanilce,Ilan.IlanTipi.Satilik,ilanimage);
            
            ilanlarım.add(newilan);
            ilanmanager.UpdateSatilikİlanlar(newilan);
            
            faililanmesaj = "";
            IlanBilgiSıfırla();
             return "ilanlarim_page";
                
            }
            
            else 
            {
                IlanBilgiSıfırla();
                faililanmesaj = returnmessage.toString();
                return "ilan_ekle_page";
            }
            
            
        }
        
        else    return "ilan_ekle_page";
                   
    }
    
    public String KullaniciKayitResponse()
    {
        //kayit olunurken databeseden var olan bilgiler kontrol edilecek aynı email kayıtlı var ise ona göre sonuç döndürülecek.
        StringBuilder message = new StringBuilder();
        
        boolean emailkayitli = dbmanager.emailKontrol(this.email,message);
        
        if(emailkayitli)    //Girilen email databasede mevcut üyelik başarısız.
        {
            UserParametreleriSifirla();
            failuyemesaj = message.toString();
            return "signin_page";    
        }
        
        else        //Yeni üyelik başarılı 
        {
            //Yeni üye bilgisinin database'e kaydedilmesi.
            dbmanager.ElemanEkle(email, name, surname, number, city, pass);
            failuyemesaj ="";
            girisbasarili = true;
            return "signin_ok_page";          
        }
       
        
    }
     
}
