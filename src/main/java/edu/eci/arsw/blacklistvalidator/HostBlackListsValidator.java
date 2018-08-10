/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @return  Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress,int hilos){
        
        LinkedList<Integer> blackListOcurrences=new LinkedList<>();
         ArrayList<BusquedaServidor> busquedas= new ArrayList<BusquedaServidor>() ;
        
        
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        
        int checkedListsCount=0;
        int servidores=skds.getRegisteredServersCount();
        int nDeServidores=servidores/hilos;
        int tope=nDeServidores;
        int base=1;
       
        for(int i=1;i<=hilos;i++){
            BusquedaServidor a=new BusquedaServidor(base, tope,ipaddress);
            try {
                a.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(HostBlackListsValidator.class.getName()).log(Level.SEVERE, null, ex);
            }
            busquedas.add(a);
            a.start();
            
            base=tope;
            if(tope+nDeServidores<=servidores){
                tope=tope+nDeServidores;
            }else{tope=servidores;}
        }
        for(BusquedaServidor bus:busquedas){
        if(bus.retornarOcurrencias()!=null){
                blackListOcurrences.addAll(bus.retornarOcurrencias());
            }
        }
        
        
        
        if (blackListOcurrences.size()>=BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }                
        
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});
        
        return blackListOcurrences;
    }
    
    
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
    
    
    
}
