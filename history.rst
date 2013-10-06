======
Hola mundo
======
$ android list targets
$ android create project --target 1 --name IPM_Time_App --path ./IPMTimeApp --activity MainActivity --package org.madsgroup.timeapp
$ cd IPMTimeApp/
$ ant debug
$ android avd
$ adb install bin/IPM_Time_App-debug.apk 

$ git commit

=====
Hello Time
=====
- Además del hola mundo, mostramos la hora.
- Hacemos que la hora se actualize al volver del background.
- Hacemos que la hora se actualize mientras estamos en foreground.
- Comprobamos que el handler.postDelayed sigue funcionando cuando estamos en
  background usando el Log
- Borramos la cola del handler antes de irnos a background.

=====
Hello World Time
=====
- Añadimos un recurso con una lista de ciudades y un ListView
- Pasamos del recurso y añadirmos una lista de objetos de tipo City.
  La ListView pasa a mostrar un array de City.

=====
Hello Time, I'm in Internet
=====
- Declaramos permiso para acceder a Internet en el Manifiesto.
- Los datos horarios de la primera ciudad se ponen a 0, y se descargan desde el
  webservice de google al crear la app.
  Problema 1: No se muestra nada en pantalla hasta que termina de cargar los datos.
              Y no se puede interactuar con la app.
- Se descargan los datos de todas las ciudades.
  El problema 1 es todavía peor.
  Problema 2: Si tardarmos demasiado, el sistema puede tirar la aplicación.
  Lanzamos la carga en un thread aparte. Al finalizar actualizamos desde el thread IU.
