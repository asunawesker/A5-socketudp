/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketsjudith;

import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author judit
 */
public class servidorUDP {
    
    private static final String _IP = "25.72.27.24";
    private static final int _PUERTO = 1234;

    public static void main(String args[]) throws UnknownHostException {
        
        InetAddress ip = InetAddress.getByName(_IP);

        
        SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");

        
        try{
            System.out.println("IP de LocalHost = " + InetAddress.getLocalHost().toString());
            System.out.println("\nEscuchando en: ");
            System.out.println("IP Host = " + ip.getHostAddress());
            System.out.println("Puerto = " + _PUERTO + "\n");

        } catch (UnknownHostException ex){
            System.err.println("No puede saber la direccion IP local: " + ex);
        }

        DatagramSocket dgmSocket = null;
        try{
            dgmSocket = new DatagramSocket(_PUERTO,ip);
        } catch (SocketException se){
            System.err.println("Se ha producido un error al abrir el socket: " + se);
            System.exit(-1);
        }        
        
        while(true) {
            try {
                
                byte bufferEntrada[] = new byte[20];

                DatagramPacket dgmPaquete = new DatagramPacket(bufferEntrada, bufferEntrada.length);
                
                
                dgmSocket.receive(dgmPaquete);
                int puertoCliente = dgmPaquete.getPort();
                InetAddress ipCliente = dgmPaquete.getAddress();
                
                
                ByteArrayInputStream arrayEntrada = new ByteArrayInputStream(bufferEntrada);
                
                
                DataInputStream datosEntrada = new DataInputStream(arrayEntrada);
                
                
                String entrada = datosEntrada.readLine();
                
                
                String[] lstEntradaServidor = entrada.split(",");                
                double tmpCliente = Double.valueOf(lstEntradaServidor[0]);
                double hmdCliente = Double.valueOf(lstEntradaServidor[1]);
                double co2Cliente = Double.valueOf(lstEntradaServidor[2]);
                
                String resultadoTmp = "f";
                String resultadoHmd = "f";
                String resultadoCo2 = "f";
                
                if(tmpCliente<16 || tmpCliente>39){
                    resultadoTmp = "t";
                }

                if(hmdCliente>80){
                    resultadoHmd = "t";
                }

                if(co2Cliente>3000){
                    resultadoCo2 = "t";
                } 
                
                String salida = resultadoTmp + "," + resultadoHmd + "," + resultadoCo2;
                                
                
                ByteArrayOutputStream arraySalida = new ByteArrayOutputStream();
                
                
                DataOutputStream datosSalida = new DataOutputStream(arraySalida);
                
                
                datosSalida.writeChars(salida);
                
                
                datosSalida.close();
                
                 
                dgmPaquete = new DatagramPacket(arraySalida.toByteArray(), 10, ipCliente, puertoCliente);
                
                
                dgmSocket.send(dgmPaquete);
                
                DaoRespuesta dao = new DaoRespuesta();
                
                dao.guardar(new RespuestaPojo(
                        fecha.format(new Date()), hora.format(new Date()), ipCliente.toString(), 
                        String.valueOf(puertoCliente) , String.valueOf(tmpCliente), 
                        String.valueOf(hmdCliente), String.valueOf(co2Cliente), String.valueOf(resultadoTmp), 
                        String.valueOf(resultadoHmd), String.valueOf(resultadoCo2))
                );
                                
                System.out.println(fecha.format(new Date()) + "   " + hora.format(new Date()) +
                     "\tCliente = "+ ipCliente + ":" +
                      puertoCliente + "\tEntrada = " + tmpCliente + ", " + hmdCliente + ", " + co2Cliente +
                      "\tSalida = " + resultadoTmp + ", " + resultadoHmd + ", " + resultadoCo2);
            }catch(IOException | NumberFormatException e){
                Logger.getLogger(servidorUDP.class.getName()).log(Level.SEVERE, null, e);
            }
        }

    }
}
