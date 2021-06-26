/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;


/**
 *
 * @author Cem
 */
@Named(value = "ilanmanager")
@SessionScoped
public class IlanManager implements Serializable {

    private ArrayList<Ilan> kiralikilanlar = new ArrayList<Ilan>();
    private ArrayList<Ilan> satilikilanlar = new ArrayList<Ilan>();
    
    
    private boolean ilanlaralindi = false;
    
    @Inject
    private DatabaseManager dbmanager;
    
    
    @PostConstruct
    public void UpdateIlanlar()
    {
        kiralikilanlar.clear();
        satilikilanlar.clear();
        
        dbmanager.kiralikList(kiralikilanlar);
        dbmanager.satilikkList(satilikilanlar);
        ilanlaralindi = true;
    }
    
    
    public ArrayList<Ilan> getKiralikilanlar()
    {
        if(!ilanlaralindi)
        {
            UpdateIlanlar();        
        }
        
        return kiralikilanlar;
    }
    
    public ArrayList<Ilan> getSatilikilanlar()
    {
        if(!ilanlaralindi)
        {
            UpdateIlanlar();         
        }
        
        return satilikilanlar;
    }
    
    public void UpdateKiralikIlanlar(Ilan ilan)
    {
        kiralikilanlar.add(ilan);
    }
    
    public void UpdateSatilikİlanlar(Ilan ilan)
    {
        satilikilanlar.add(ilan);
    }
}
