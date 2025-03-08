# Practica2EvalJava
## Práctica final del 2º timestre.
Ejercicio Práctico para la Segunda Evaluación.
Web para compartir experiencias de viajes.
Se pretende desarrollar una aplicación web con tecnología JavaEE para que los usuarios puedan
compartir experiencias de viajes (vacaciones, viajes de intercambio, etc).
Un usuario podrá crear tantas experiencias de viajes como desee, y también podrá acceder
mediante una búsqueda a las experiencias compartidas por otros usuarios. Las experiencias podrán
hacerse públicas o privadas en cualquier momento.
Una experiencia contará con un título, una fecha de inicio y una descripción. Una vez creada se
le podrán añadir actividades (visitas a ciudades, visitas a museos, actividades en la naturaleza, etc).
Cada actividad contará con un título, una fecha y una descripción, y se le podrán añadir tantas
imágenes como se quiera. También se podrán editar / eliminar las experiencias de viaje o sus
actividades.
Existirá un administrador de la aplicación que podrá gestionar usuarios y consultar estadísticas
de uso de la aplicación. Los usuarios podrán registrarse mediante un formulario de registro
indicando su nombre, apellidos, e-mail y contraseña. Una vez registrado un usuario deberá ser
activado por el administrador para poder entrar en la aplicación. Cuando el administrador active un
usuario, éste recibirá un e-mail automático para avisarle de que ya está activo. El administrador
también podrá editar un usuario (nombre, apellidos, contraseña, activo) o eliminarlo si no tiene
experiencias compartidas.
Los usuarios podrán añadir comentarios a las experiencias de otros .
Las experiencias se podrán buscar en base a sus títulos o a sus descripciones.
En la consulta de datos estadísticos por parte del administrador se mostrarán estadísticas de
número de experiencias por cada usuario y se podrán consultar las experiencias con sus respectivas
actividades entre dos fechas determinadas. Se mostrarán gráficas con dichos datos.
No se podrán eliminar datos que tengan relación con otra información existente..

Se deberá internacionalizar la parte dedicada a la búsqueda de experiencias, para que responda
en español o en inglés, según la configuración del usuario. Las fechas deben aparecer correctamente
formateadas.

Con el fin de automatizar las comunicaciones con una futura aplicación móvil, se creará
también un servicio REST que permitirá consultar los datos de todas las experiencias con sus
actividades.

El acceso a las bases de datos se podrá realizar con JPA (recomendado) o con JDBC.


Se valorará la separación entre modelo, vista y controlador, así como el uso de estructuras de
alto nivel (taglibs y expresiones EL) y en general, la limpieza del código.
Se recomienda hacer copia de seguridad de toda la aplicación después de cada sesión, y en
especial, guardar copias controladas antes de una modificación estratégica.
Para la defensa de la práctica se introducirán datos significativos y realistas.
