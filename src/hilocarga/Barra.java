/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hilocarga;

/**
 *
 * @author gilit
 */
public class Barra{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Cargar pre = new Cargar();
        pre.setVisible(true);
        
        try{
            for(int i = 0; i<=100; i++){
                Thread.sleep(40);
                pre.porcentaje.setText(Integer.toString(i)+"%");
                pre.barra.setValue(i);
            }
        }catch(Exception e){
            
        }
    }
    
}
