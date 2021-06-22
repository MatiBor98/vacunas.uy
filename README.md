## Solución web y sistema central para vacunas.uy.

Esta solución se realizó mediante el uso de JakartaEE 8.
En este repositorio se encuentran 3 aplicaciones:

 - sist-central: Sistema central de la aplicación.
 - Vacunatorio: Sistema que será distribuido en los vacunatorios.
 - socio-logistico: Sistema que será distribuido en los socios logísticos.

La configuración del sistema central (módulo web) se hace mediante un archivo llamado *microprofile-config.properties*.
En este archivo se pueden configurar el client ID y el client Secret, así como las redirect uris. Estas deben terminar en /callback y /logout para que el sistema atrape las llamadas. También se debe configurar los endpoints de /authorize y /token correspondientes al provider de openid. A su vez, se debe configurar las secret keys que permiten una comunicación cifrada con los socios logísticos.

En el sub-sistema del Vacunatorio también se configura un *microprofile-config.properties*. Se configura el nombre del vacunatorio, que deberá coincidir con el nombre del vacunatorio al que pertenezca este nodo, para permitir la comunicación con el sistema central. 

En el sub-sistema del Socio logístico el archivo *microprofile-config.properties* . Se configura el nombre del vacunatorio, que deberá coincidir con el nombre del vacunatorio al que pertenezca este nodo, para permitir la comunicación con el sistema central. 

Se pueden sobreescribir estos valores definidos en el archivo ya sea aplicando argumentos a la VM de java con `-DXX=YY ` o con `export XXX=yyy` en bash.

Esta solución contiene la aplicación móvil en https://gitlab.fing.edu.uy/nicolas.san.martin/laboratorio-tse-2021-movil
Esta solucion contiene al socio logistico en https://gitlab.fing.edu.uy/matias.borggio/socio-logistico


Demo móvil:  https://www.youtube.com/watch?v=xfdW-7cR8a8
Demo web: https://youtu.be/gDR38xLWOXs