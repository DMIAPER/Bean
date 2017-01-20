/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RelojDigital;

import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Calendar;
import java.util.EventListener;
import java.util.GregorianCalendar;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author APOYO
 */
public class RelojDigitalBean extends JLabel implements ActionListener,Serializable {
    
    
    public class AlarmaEvent extends java.util.EventObject{
        
        public AlarmaEvent(Object source){
                super(source);
        }
    }
    
    public interface AlarmaListener extends EventListener{
        void capturarAlarma(AlarmaEvent ev);
    }
    
    private Calendar calendario = new GregorianCalendar();
    private int hora;
    public static final String PROP_HORA ="hora";
    private int minute;
    public static final String PROP_MINUTE ="minute";
    private int second;
    public static final String PROP_SECOND ="second";
    private String alarma;
    public static final String PROP_ALARMA ="alarma";
    private boolean formato;
    private Timer t;
    private AlarmaListener receptor;

    //metodos Setter and Getter*******************************
    
    //hora**********************************
    /**
     * Get the value of hora
     *
     * @return the value of hora
     */
    public int getHora() {
        return hora;
    }

    /**
     * Set the value of hora
     *
     * @param hora new value of hora
     */
    public void setHora(int hora) {
        this.hora = hora;
        setText(Integer.toString(hora));
        repaint();
    }
    
    //Minutos********************************
    /**
     * Get the value of minute
     *
     * @return the value of minut
     */
    public int getMinute() {
        return minute;
        
    }

    /**
     * Set the value of minute
     *
     * @param minut new value of minut
     */
    public void setMinute(int minut) {
        this.minute = minut;
        setText(Integer.toString(minute));
        repaint();
    }

    //Segundos********************************
    /**
     * Get the value of second
     *
     * @return the value of second
     */
    public int getSecond() {
        return second;
    }

    /**
     * Set the value of second
     *
     * @param second new value of second
     */
    public void setSecond(int second) {
        this.second = second;
        setText(Integer.toString(second));
        repaint();
    }
    

    //Alarma********************************
    /**
     * Get the value of alarma
     *
     * @return the value of alarma
     */
    public String getAlarma() {
        return alarma;
    }

    /**
     * Set the value of alarma
     *
     * @param alarma new value of second
     */
    public void setAlarma(String alarma) {
        this.alarma = alarma;
        repaint();
    }
    
    //Formato 12/24 horas**********************
    /**
     * Get the value of formato
     *
     * @return the value of formato
     */
    public boolean isFormato() {
        return formato;
    }

    /**
     * Set the value of formato
     *
     * @param formato new value of formato
     */
    public void setFormato(boolean formato) {
        this.formato = formato;
    }
        
    
    public final void setActivo(boolean valor) {
        if (valor == true)
            t.start();
        else
            t.stop();
    }
    
    public boolean getActivo() {
        return t.isRunning();
    }
    
    public RelojDigitalBean() {
        //propertySupport = new PropertyChangeSupport(this);      
        formato =true;
        
        java.util.Date actual = new java.util.Date(); 
        String min;
        String sec;
        String tipo;
        
        calendario.setTime(actual); 
        if(formato==true){
            hora=calendario.get(Calendar.HOUR_OF_DAY);
            tipo = "24 H";
        }else{
            if(calendario.get(Calendar.HOUR_OF_DAY)>12){
                hora=calendario.get(Calendar.HOUR_OF_DAY)-12;
                tipo = "PM";
            }else{
                hora=calendario.get(Calendar.HOUR_OF_DAY);
                tipo = "AM";
                }
        }
                
        minute = calendario.get(Calendar.MINUTE);
        if(minute<10){
            min = "0"+Integer.toString(minute);
        }else{
            min = Integer.toString(minute);
        }
                
        second = calendario.get(Calendar.SECOND);
        if(second<10){
            sec = "0"+Integer.toString(second);
        }else{
            sec = Integer.toString(second);
        }
        
        t = new Timer(1000, this);
        setText(Integer.toString(hora)+":"+min+":"+sec+" - "+tipo);
        setActivo(true);
    }
    
    
    @Override
    public void actionPerformed(java.awt.event.ActionEvent ae){ 
        java.util.Date actual = new java.util.Date(); 
        String min;
        String sec;
        String tipo;
        calendario.setTime(actual); 
        if(formato==true){
            hora=calendario.get(Calendar.HOUR_OF_DAY);
            tipo = "24 H";
        }else{
            if(calendario.get(Calendar.HOUR_OF_DAY)>12){
                hora=calendario.get(Calendar.HOUR_OF_DAY)-12;
                tipo = "PM";
            }else{
                hora=calendario.get(Calendar.HOUR_OF_DAY);
                tipo = "AM";
            }
        }
                
        minute = calendario.get(Calendar.MINUTE);
            if(minute<10){
            min = "0"+Integer.toString(minute);
        }else{
            min = Integer.toString(minute);
        }
                
        second = calendario.get(Calendar.SECOND);
        if(second<10){
            sec = "0"+Integer.toString(second);
        }else{
            sec = Integer.toString(second);
        }
                
        String horaActual = Integer.toString(hora)+Integer.toString(minute);
        setText(Integer.toString(hora)+":"+min+":"+sec+" - "+tipo);
        if(horaActual.equals(alarma)){
            alarma ="";
            receptor.capturarAlarma(new AlarmaEvent(this));
        }
    } 
    
    public void addAlarmaListener(AlarmaListener receptor){
        this.receptor = receptor;
    }
    
    public void removeAlarmaListener(AlarmaListener receptor){
        this.receptor=null;
    }
}
