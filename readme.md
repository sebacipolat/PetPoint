# PetPoint
<br>
<img src='https://github.com/sebacipolat/PetPoint/blob/master/app/src/main/res/mipmap-xxhdpi/ic_launcher.png' height="100"/>

Solucion Desafío Técnico Android - FluxIT2018

###### by Sebastian Cipolat
[www.sebastiancipolat.com](www.sebastiancipolat.com)
[sebastiancipolat Linkedin](www.linkedin.com/in/sebastiancipolat)

La aplicacion se llama "PetPoint" es el nombre que le asigne a la app para una tienda de mascotas
que se pidio en el desafio tecnico.

Esta compuesta por 2 pantallas:
<br>
**Listado Pets - MainActivity**
<br>
Muestra listado de pets, pull to refresh y opcion en toolbar para reordenar el listado
<br>
**DetallesPet  - DetailActivity**
<br>
-PetDetailsFragment:
<br>
Detalles de la mascota: Nombre, disponibilidad, imagen.
<br>
-MapStoreFragment:
<br>
Compuesta por un SupportMapFragment muestra la ubicacion de las 2 tiendas mencionadas
y la ubicacion del usuario.
<br>
## Capturas de pantalla:

## Loading 
<img src='https://github.com/sebacipolat/PetPoint/blob/master/images/loading.png' height="350"/>

## Home Listado pets
<img src='https://github.com/sebacipolat/PetPoint/blob/master/images/home.png' height="350"/>

## Pull to refresh
<img src='https://github.com/sebacipolat/PetPoint/blob/master/images/pulltorefresh.png' height="350"/>


## Ordenamiento 
<img src='https://github.com/sebacipolat/PetPoint/blob/master/images/sort.png' height="350"/>

## Detalles Pet 
<img src='https://github.com/sebacipolat/PetPoint/blob/master/images/map.png' height="350"/>

               
## Descripcion: 
La aplicacion esta basada en MVP.
<br>
El ordenamiento de las carpetas esta agrupado por pantalla.
<br>
Se utilizo inyeccion de vistas usando Butterknife
<br>
Para la interaccion con el API se utilizo Retrofit 2 en conjunto con RxJava + RxAndroid.
<br>
Al momento de obtener la ubicacion del usuario en la pantalla detalles se utilizo Google FusedLocation API.


## Librerias usadas
- Butterknife
- RxJava
- RxAndroid
- okhttp3
- Glide
- Retrofit2
- Gson
- play-services-location
- appCompat v4/7
- Design support
- ConstraintLayout
- circleimageview

## ANTES DE COMENZAR
Borrar la carpeta images (contiene las imagenes q se ven aqui)

## PROBLEMA DE PERFOMANCE EN API
Se aclara que el api asignada http://petstore.swagger.io
no permite paginado por lo que el primer request en ocasiones suele tardar unos segs dependiendo de la conexion
ya que el response contiene alrededor de 5000 pets.

## License
    Copyright 2018 sebastian cipolat

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
