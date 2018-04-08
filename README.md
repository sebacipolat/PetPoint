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
Listado Pets - MainActivity
<br>
Muestra listado de pets, pull to refresh y opcion en toolbar para reordenar el listado
<br>
DetallesPet  - DetailActivity
<br>
PetDetailsFragment:
<br>
Detalles de la mascota: Nombre, disponibilidad, imagen.
<br>
MapStoreFragment:
<br>
Compuesta por un SupportMapFragment muestra la ubicacion de las 2 tiendas mencionadas
y la ubicacion del usuario.
<br>
## Capturas de pantalla:

## Loading 
<img src='https://github.com/sebacipolat/PetPoint/blob/master/images/loading.png' height="350"/>
## Home Listado pets
<img src='https://github.com/sebacipolat/PetPoint/blob/master/images/home.png' height="350"/>
## Ordenamiento 
<img src='https://github.com/sebacipolat/PetPoint/blob/master/images/sort.png' height="350"/>
## Detalles Pet 
<img src='https://github.com/sebacipolat/PetPoint/blob/master/images/map.png' height="350"/>

               
## Detalles 
La aplicacion esta basada en MVP.

Para la interaccion con el API se utilizo Retrofit 2 en conjunto con RxJava + RxAndroid.
Al momento de obtener la ubicacion del usuario se 

Squire is a single OpenSource Example News Reader Android APP.

The data is provide by [The Guardian](https://www.theguardian.com/)

![Imagen ejemplo](https://github.com/sebacipolat/Squire/blob/master/Images/banner.png)

Download 
<p>
<a href='https://play.google.com/store/apps/details?id=com.cipolat.news'>
    <img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' height="100"/>
</a>
</p>  

## Technology used:

The app is based on MVP

Libraries:

RxJava

RxAndroid

okhttp3

Butterknife

picasso

appcompat-v7


## BEFORE RUN

The Guardian Api Service is used as data source.

[The Guardian open platform](http://open-platform.theguardian.com/)

**You should create an api key**

1- Go to https://bonobo.capi.gutools.co.uk/register/developer an create a developer account.

2- Modify 'Constants.java' at /app/src/main/java/com/cipolat/news/Data

   reeplace the API_KEY value with your dev key.



**FEATURES**

The app has only two screens, it's only a demostration with limited features.

**Home**

![Home](https://github.com/sebacipolat/Squire/blob/master/Images/home.png)

The search icon is only for demostration purpose.


**Article Viewer**

![Article](https://github.com/sebacipolat/Squire/blob/master/Images/article.png)



  
**FeedBack**

  Please feel free to report bugs, suggestion,etc. I'll be pending.
  
  If you will made a fork an use on your project let me know! to add to the description.

[@seba_cipolat](http://twitter.com/seba_cipolat)

[www.sebastiancipolat.com](www.sebastiancipolat.com)

[sebastiancipolat Linkedin](www.linkedin.com/in/sebastiancipolat)

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
