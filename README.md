# Socket UDP

Este proyecto se realizó en netbeans y se puede ejecutar desde ahí o desde la terminal. Para la terminal las siguientes instrucciones guían como ejecutarlo correctamente.

## Compilar
Se compila la solución y si se codifica de forma correcta, se generan archivos .class de cada archivo .java
```console
your@terminal:~$ cd A5-socketudp/E4
your@terminal:~$ javac *.java
```

## Terminal 2
Se inicia el servidor
```console
your@terminal:~$ cd A5-socketudp/E4
your@terminal:~$ java ServidorUDP
```

## Terminal 3
Se inicia el cliente y se agrega la dirección IP del servidor a conectarse
```console
your@terminal:~$ cd A5-socketudp/E4
your@terminal:~$ java ClienteUDP <<DIRECCION_IP>>
```
