/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketsjudith;

import java.net.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Arrays;

public class clienteUDP { 

    private static final int _PUERTO = 1234;
    public static void main(String args[]){

        InetAddress ipServidor = null;
        try{
            ipServidor = InetAddress.getByName(args[0]);
        } catch (UnknownHostException uhe){
            System.err.println("Host no encontrado : " + uhe);
            System.exit(-1);
        }
        
        DatagramSocket dgmSocket = null;
        try{
            dgmSocket = new DatagramSocket();
        } catch (SocketException se){
            System.err.println("Error al abrir el socket : " + se);
            System.exit(-1);
        }       
                
        try{           
            String salidaCliente = "";

            double tmp = ((-15) + Math.random() * (45 - (-15)));
            String temperatura = new DecimalFormat("##.##").format(tmp);

            double hmd = (50 + Math.random() * (100 - 50));
            String humedad = new DecimalFormat("##.##").format(hmd);

            double co2 = (700 + Math.random() * (5000 - 700));
            String nivelesCo2 = new DecimalFormat("##.##").format(co2);

            
            salidaCliente = temperatura + "," + humedad + "," + nivelesCo2;
            System.out.println(salidaCliente);
            
            byte[] datosEnvio = salidaCliente.getBytes();

            
            DatagramPacket dgmPaquete = new DatagramPacket(datosEnvio, salidaCliente.length(), ipServidor, _PUERTO);
            
            dgmSocket.send(dgmPaquete);

            
            byte bufferEntrada[] = new byte[10];
                        
            dgmPaquete = new DatagramPacket(bufferEntrada, bufferEntrada.length);
            
            dgmSocket.receive(dgmPaquete);

            
            ByteArrayInputStream arrayRecepcion = new ByteArrayInputStream(bufferEntrada);
            DataInputStream datosEntrada = new DataInputStream(arrayRecepcion);
            
            String resultadoServidor = datosEntrada.readLine();
            
            String[] lstEntradaServidor = resultadoServidor.split(",");    
            
            String tmpCliente = lstEntradaServidor[0];
            String hmdCliente = lstEntradaServidor[1];
            String co2Cliente = lstEntradaServidor[2];   
            
            System.out.println("Enviado = " + temperatura + ", " + humedad + ", " + nivelesCo2 + 
                    "\tRecibido = " + tmpCliente + ", " + hmdCliente + ", " + co2Cliente);
        } catch (Exception e){
            System.err.println("Se ha producido un error: " + e);
        }        
    }
}
