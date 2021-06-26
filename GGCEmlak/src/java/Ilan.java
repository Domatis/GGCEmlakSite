/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cem
 */



public class Ilan 
{
    public enum IlanTipi
{
    Satilik,Kiralik
}
    
    private String ilanno;
    private String iletisimno;         
    private String m2;
    private String fiyat;
    private String name;
    private String surname;
    private String sehir;
    private String ilce; 
    private String imagelink;
    private String tipname;
    private String fullname;
    private IlanTipi tip;
   
    
    public Ilan(String ilanno,String iletsimno,String m2,String fiyat,String ad,String soyad,String sehir,String ilce,IlanTipi tp,String link)
    {
        this.ilanno = ilanno;
        this.iletisimno = iletsimno;
        this.m2 = m2;
        this.fiyat = fiyat;
        this.name = ad;
        this.surname = soyad;
        this.sehir = sehir;
        this.ilce = ilce;
        this.tip = tp;
        this.imagelink  = link;
        
        if(tp == IlanTipi.Satilik) tipname = "Satılık";
        else tipname = "Kiralık";
        
        fullname = ad +" " +  soyad;
       
    }
    
    public String getFullname()
    {
        return fullname;
    }
    
    public String getTipname()
    {
        return tipname;
    }
    
    public void setTipname(String val)
    {
        tipname = val;
    }
    public String getImageLink()
    {
        return imagelink;
    }
    
    public void setImageLink(String val)
    {
        imagelink = val;
    }
    
    public String getSurname()
    {
        return surname;
    }
    
    public void setSurname(String val)
    {
        surname = val;
    }
        
    public String getIlanNo()
    {
        return ilanno;
    }
    
    public void setIlanNo(String val)
    {
        ilanno = val;
    }
    
    public String getIletisimNo()
    {
        return iletisimno;
    }
    
    public void setIletisimNo(String val)
    {
        iletisimno  = val;
    }
    
    public String getM2()
    {
        return m2;
    }
    
    public void setM2(String val)
    {
        m2 = val;
    }

    public String getFiyat()
    {
        return fiyat;
    }
    
    public void setFiyat(String val)
    {
        fiyat = val;
    }

    
    public String getName()
    {
        return name;
    }
    
    public void setName(String val)
    {
        name = val;
    }
    
    public String getSehir()
    {
        return sehir;
    }
    
    public void getSehir(String val)
    {
        sehir = val;
    }
    
    public String getIlce()
    {
        return ilce;
    }
       
    public void setIlce(String val)
    {
        ilce = val;
    }
    
    public IlanTipi getIlanTipi()
    {
        return tip;
    }
    
    public void getIlanTıpı(IlanTipi val)
    {
        tip = val;
    }
}
