/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */
public class CountThread extends Thread{
    private int base;
    private int tope;
    public CountThread(int i, int j){

        base=i;
        tope=j;
        
    }
    @Override
    public void run() {
      for(int contador=base;contador<=tope;contador++){
          System.out.println(contador);
      }
        
        
    }
    
}
