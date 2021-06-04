/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author Cem
 */
@Named(value = "user")
@SessionScoped
public class UserBean implements Serializable {

    private String name,surname,city,email,number,pass;
    private String adress = " ";
    private String failuyemesaj;
    private boolean girisbasarili = false;
    private String failgirismesaj;
    
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
    
    public String getAdress()
    {
        return this.adress;
    }
    
    public void setAdress(String a)
    {
        this.adress = a;
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
    
    
    public String UyeGirisi()
    {
        
        //Girilen email ile şifreyi kontrol et eğer doğruysa profil sayfasına yönlendir.
        if(this.email.equals("ggc@email") && this.pass.equals("1234"))
        {
            //Giriş başarılı.
            //TODO databaseden bu kullanıcıya ait bilgiler alınıp gerekli değişkenlere atanacak.
            //GEçici olarak elle atanacak.
            this.name = "GGC";
            this.surname = "GGC2";
            this.city = "İstanbul";
            this.number = "12345";
            
            girisbasarili = true;         
            return "myprofile_page";       
        }   
        
        
        //Eğer giriş başarılı değilse yine aynı sayfaya yönlendir.
        //Uyarı mesajını güncelle.
        failgirismesaj = "Girdiğiniz e-mail veya şifre geçersizdir.";
        ParametreleriSifirla();
        return "login_page";
        
        
    }
    
    public void ParametreleriSifirla()
    {
        this.name = "";
        this.surname = "";
        this.email = "";
        this.pass = "";
        this.city = "";
        this.number = "";
        this.adress = "";
    }
    
    
    
    public String KullaniciKayitResponse()
    {
        //TODO kayit olunurken databeseden var olan bilgiler kontrol edilecek aynı email kayıtlı var ise ona göre sonuç döndürülecek.
        
        //Şimdilik bütün kayıtlar geçerli.     
        //Direkt profil sayfasına yönlendiriliyor.
        
        //girilen verileri kendi elle girdiğin geçici verilere göre kontrol ederek 2 farklı sonuç döndür.
        
        if(this.name.equals("GGC"))
        {
            failuyemesaj = "Üyelik Başarılı";
            return "myprofile_page";   
        }
       
        ParametreleriSifirla();
       failuyemesaj = "Üyelik gerçekleştirilemedi";
       return "signin_page";
        
        
       //Üyelik başarısız durumu.
       
        
    }
     
}
