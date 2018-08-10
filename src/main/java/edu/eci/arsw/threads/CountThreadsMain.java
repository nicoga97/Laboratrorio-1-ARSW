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
public class CountThreadsMain {
    
    public static void main(String a[]){
        /*START
       new CountThread(0, 99).start();
       new CountThread(99, 199).start();
       new CountThread(200, 299).start();
        RUN */
       new CountThread(0, 99).run();
       new CountThread(99, 199).run();
       new CountThread(200, 299).run();
        
    }
    
}
