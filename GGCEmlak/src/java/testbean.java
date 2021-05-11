/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
/**
 *
 * @author Cem
 */
@Named(value = "testbean")
@SessionScoped
public class testbean implements Serializable{

    private String name = "GGC";
    
    public String getName()
    {
        return name;
    }
    
}
