/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author 2129175
 */
public class BusquedaServidor extends Thread {
    private int base;
    private int tope;
    private String ipaddress;
    LinkedList<Integer> blackListOcurrences=new LinkedList<>();
    
    public BusquedaServidor(int base,int tope,String ipaddress){
     this.base=base;
     this.tope=tope;
     this.ipaddress=ipaddress;
    }

    @Override
    public void run() {
        
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        for (int i=base;i<tope ;i++){
            if (skds.isInBlackListServer(i, ipaddress)){ 
                blackListOcurrences.add(i);
            }
        }
    }
    
    public LinkedList<Integer>  retornarOcurrencias(){
        System.out.println(blackListOcurrences);
     return blackListOcurrences;
    }
    
}
