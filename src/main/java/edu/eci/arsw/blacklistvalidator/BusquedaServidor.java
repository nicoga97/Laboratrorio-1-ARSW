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
    int checkedListsCount = 0;
    private String ipaddress;
    LinkedList<Integer> blackListOcurrences=new LinkedList<>();
    HostBlackListsValidator h;
    
    public BusquedaServidor(int base,int tope,String ipaddress, HostBlackListsValidator h){
     this.base=base;
     this.tope=tope;
     this.ipaddress=ipaddress;
     this.h=h;
    }

    @Override
    public void run() {
        
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        for (int i=base;i<tope ;i++){
            checkedListsCount++;
            if (skds.isInBlackListServer(i, ipaddress)){ 
                h.agregarOcurrencia(i);
                if(h.getBlackListOcurrences().size()>4){
                    break;
                }
                
            }
        }
    }
    
    public LinkedList<Integer>  retornarOcurrencias(){
     return blackListOcurrences;
    }

    public int getCheckedListsCount() {
        return checkedListsCount;
    }
    
    
}
