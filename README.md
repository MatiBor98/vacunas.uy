# Vacunas.uy
Este trabajo se plantea en el marco de la edición 2021 de Taller de Sistemas de Empresariales.

La pandemia del Coronavirus está afectando a la población de Uruguay desde
hace ya más de un año. Este evento a nivel mundial, que únicamente en nuestro
país ha tenido un enorme impacto de manera directa en muchas personas y de
manera indirecta en toda la población, afectando no solamente la situación
sanitaria, sino también la económica y social.
Considerando que este tipo de situaciones puede repetirse en un futuro con
otras enfermedades, se están desarrollando soluciones enfocadas en su seguimiento y control. En particular, es de interés abordar una etapa muy importante de este ciclo que es la inoculación de la población, por ejemplo, mediante la aplicación de vacunas.

Para la gestión de todo este proceso, es necesaria la creación de una plataforma que brinde soporte con el fin de facilitar la organización y la logística de la vacunación.

Esta solución debe proveer un sitio web en el cual los usuarios puedan agendar vacunaciones y obtener información sobre los planes de vacunación. Además se debe construir una aplicación móvil android para que los usuarios puedan obtener información desde la misma.

Demo móvil:  https://www.youtube.com/watch?v=xfdW-7cR8a8
Demo web: https://youtu.be/gDR38xLWOXs

##  Descripcion General de la Plataforma
La solucion vacunas.uy cuenta con un componente central y un componente movil. Inter-
actua con nodos externos (PDI y Salud.uy) y perifericos (Vacunatorios y Socios log ́ısticos). La
plataforma brindara servicios a los ciudadanos, autoridades y vacunadores. Ademas contara
con administradores para asegurar el funcionamiento esperado.
Los ciudadanos interactuaran con la plataforma a traves de un frontoffice web y de un com-
ponente movil. Se podra obtener informacion de la ejecucion del plan de vacunacion, gestionar
su agenda de vacunacion y consultar los vacunatorios disponibles. La aplicacion m ́ovil notifica
al ciudadano y permite obtener certificados de vacunacion.
Las autoridades podran tener acceso a las cuestiones administrativas de los planes de va-
cunaciones, sus etapas, las agendas disponibles, las dosis, coordinacion de envio de estas, ası
como asignacion de los vacunadores. Ademas tendran disponible reportes de datos recabados
por la plataforma sobre las agendas e interacciones de los ciudadanos.
Los administradores podran gestionar los usuarios, roles y nodos perifericos, como por ejem-
plo vacunatorios o socios logısticos.
Los vacunadores obtendran informacion de su agenda de vacunacion, como los vacunatorios
y puesto de vacunacion asignados.


![fig1](https://user-images.githubusercontent.com/59084107/215112960-6957409b-915a-47e5-8248-411d6a8501ec.png)



## Solución web y sistema central para vacunas.uy.

Esta solución se realizó mediante el uso de JakartaEE 8.
En este repositorio se encuentran 2 aplicaciones:

 - sist-central: Sistema central de la aplicación.
 - Vacunatorio: Sistema que será distribuido en los vacunatorios.

La configuración del sistema central (módulo web) se hace mediante un archivo llamado *microprofile-config.properties*.
En este archivo se pueden configurar el client ID y el client Secret, así como las redirect uris. Estas deben terminar en /callback y /logout para que el sistema atrape las llamadas. También se debe configurar los endpoints de /authorize y /token correspondientes al provider de openid. A su vez, se debe configurar las secret keys que permiten una comunicación cifrada con los socios logísticos.

En el sub-sistema del Vacunatorio también se configura un *microprofile-config.properties*. Se configura el nombre del vacunatorio, que deberá coincidir con el nombre del vacunatorio al que pertenezca este nodo, para permitir la comunicación con el sistema central. 

En el sub-sistema del Socio logístico el archivo *microprofile-config.properties* . Se configura el nombre del vacunatorio y un secrey key, que deberá coincidir con el nombre y secret del sist-central al que pertenezca este nodo, para permitir la comunicación con el sistema central. 

Se pueden sobreescribir estos valores definidos en el archivo ya sea aplicando argumentos a la VM de java con `-DXX=YY ` o con `export XXX=yyy` en bash.

Esta solución contiene la aplicación móvil en https://gitlab.fing.edu.uy/nicolas.san.martin/laboratorio-tse-2021-movil

Esta solucion contiene al socio logistico en https://gitlab.fing.edu.uy/matias.borggio/socio-logistico

