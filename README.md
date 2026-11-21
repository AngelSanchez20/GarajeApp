 <div align="center">

# 🚗 GarajeApp — Android 2.º DAM · Versión 2

*Aplicación Android para la gestión de vehículos, desarrollada en Kotlin. Integra almacenamiento local con Room y consultas a una API pública mediante Retrofit.*

![Android Studio](https://img.shields.io/badge/Android%20Studio-3DDC84?style=for-the-badge&logo=android-studio&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Room](https://img.shields.io/badge/Room-4285F4?style=for-the-badge&logo=android&logoColor=white)
![Retrofit](https://img.shields.io/badge/Retrofit-009688?style=for-the-badge&logo=android&logoColor=white)
![Versión](https://img.shields.io/badge/Versión-2-blue?style=for-the-badge)

</div>

---

## 📋 Tabla de contenidos

- [📖 Acerca del proyecto](#-acerca-del-proyecto)
- [🌐 API pública utilizada](#-api-pública-utilizada)
- [📱 Características](#-características)
- [✅ Cumplimiento de los hitos](#-cumplimiento-de-los-hitos)
- [🛠️ Tecnologías y herramientas](#️-tecnologías-y-herramientas)
- [📂 Estructura del proyecto](#-estructura-del-proyecto)
- [🚀 Cómo ejecutar el proyecto](#-cómo-ejecutar-el-proyecto)

---

## 📖 Acerca del proyecto

**GarajeApp — Versión 2** es una aplicación Android desarrollada en Kotlin para la asignatura de Programación Multimedia y Dispositivos Móviles de 2.º de Desarrollo de Aplicaciones Multiplataforma (DAM).

Esta versión amplía la aplicación original de gestión de vehículos incorporando conexión a Internet mediante una API pública. De esta forma, combina los datos introducidos por el usuario con información obtenida de servicios externos.

La aplicación mantiene las funcionalidades anteriores, como el registro y almacenamiento de vehículos, la gestión de fotografías, la geolocalización GPS y la reproducción de sonidos de motor.

Como nueva funcionalidad, permite consultar información descriptiva sobre la marca de un vehículo mediante Wikipedia REST API. Los resultados obtenidos se almacenan en una base de datos local Room, permitiendo recuperar la información cuando no existe conexión a Internet.

## 🌐 API pública utilizada

- **Nombre:** Wikipedia REST API.
- **Dirección web:** [https://es.wikipedia.org/](https://es.wikipedia.org/)
- **Endpoint:** `https://es.wikipedia.org/api/rest_v1/page/summary/{title}`
- **Acceso:** Público y gratuito, sin necesidad de clave API ni registro.
- **Tipo de comunicación:** HTTP/REST.
- **Formato de respuesta:** JSON.

### Finalidad dentro de GarajeApp

La API se utiliza para obtener información descriptiva sobre la marca del vehículo que el usuario ha registrado.

Al acceder a la pantalla de detalle de un coche, la aplicación realiza una consulta a Wikipedia utilizando el nombre de su marca. La respuesta puede incluir:

- Título de la página correspondiente.
- Descripción o resumen de la marca.
- URL de una imagen representativa, cuando está disponible.

La información obtenida se almacena en Room para poder consultarla posteriormente sin conexión a Internet.

**Importante:** la API proporciona información general sobre la marca, no una ficha técnica específica de cada modelo o motorización.

## 📱 Características

- **Gestión de vehículos:** Registro, consulta y eliminación de coches mediante una base de datos local Room.
- **Listado de vehículos:** Visualización de los coches almacenados mediante `RecyclerView`.
- **Eliminación desde el listado:** Posibilidad de borrar vehículos directamente desde su fila correspondiente.
- **Fotografías:** Captura de imágenes con la cámara y selección desde la galería.
- **Geolocalización GPS:** Obtención y almacenamiento de las coordenadas de ubicación del vehículo.
- **Consulta online:** Obtención de información descriptiva de las marcas mediante Wikipedia REST API.
- **Caché local:** Almacenamiento de los resultados de las consultas online en Room.
- **Modo sin conexión:** Recuperación de información previamente guardada cuando no hay conexión a Internet.
- **Gestión de estados:** Indicador de carga durante las consultas y mensajes informativos cuando se producen errores o no existen datos disponibles.
- **Detalle del vehículo:** Visualización de los datos del coche, fotografía, ubicación GPS e información online de su marca.
- **Sonidos de motor:** Reproducción de sonidos según la categoría del vehículo: utilitario, deportivo o superdeportivo.

## ✅ Cumplimiento de los hitos

| Hito | Requisitos de la actividad | Implementación |
|---|---|---|
| **Hito 1 · Lista online** | Conexión a una API pública, interpretación de JSON, presentación de resultados y gestión de estados. | `RetrofitClient`, `WikipediaApiService`, `WikiSummaryResponse` y estados de carga en `DetalleActivity`. |
| **Hito 2 · Detalle y caché** | Mostrar información ampliada, guardar los datos online en Room y permitir su consulta sin conexión. | `MarcaInfo`, `MarcaInfoDao` y `WikipediaRepository`, que recupera los datos almacenados cuando falla la consulta online. |
| **Hito 3 · Arquitectura y mejora propia** | Incorporar ViewModel y Repository, separar responsabilidades y añadir una funcionalidad propia. | `DetalleViewModel` y `WikipediaRepository`, manteniendo funcionalidades como cámara, GPS y gestión de vehículos. |

## 🛠️ Tecnologías y herramientas

| Tecnología | Utilización |
|---|---|
| **Kotlin** | Lenguaje principal de programación. |
| **Android Studio** | Entorno de desarrollo. |
| **XML** | Diseño de las interfaces de usuario. |
| **Room** | Almacenamiento local de vehículos e información de marcas. |
| **Retrofit** | Realización de peticiones HTTP a Wikipedia. |
| **Gson** | Interpretación de las respuestas JSON. |
| **OkHttp** | Cliente HTTP utilizado por Retrofit. |
| **Corrutinas Kotlin** | Ejecución de operaciones asíncronas. |
| **ViewModel** | Gestión de los datos y estados de la pantalla de detalle. |
| **Repository** | Gestión del acceso a la API y a la base de datos local. |
| **RecyclerView** | Visualización del listado de vehículos. |
| **MediaPlayer** | Reproducción de sonidos de motor. |
| **Picasso** | Dependencia incluida para la gestión de imágenes. |

---

## 📂 Estructura del proyecto

El código fuente principal se encuentra en:

`app/src/main/java/com/example/garajeapp/`

Los diseños de las interfaces están ubicados en:

`app/src/main/res/layout/`

```text
GarajeApp/
│
└── app/
    │
    ├── build.gradle.kts
    │
    └── src/
        └── main/
            │
            ├── AndroidManifest.xml
            │
            ├── java/
            │   └── com/example/garajeapp/
            │       │
            │       ├── AppDatabase.kt
            │       │
            │       ├── Coche.kt
            │       ├── Garaje.kt
            │       ├── Vehiculo.kt
            │       ├── VehiculoDao.kt
            │       │
            │       ├── MainActivity.kt
            │       ├── ListaActivity.kt
            │       ├── DetalleActivity.kt
            │       ├── CocheAdapter.kt
            │       │
            │       ├── Fotos.kt
            │       │
            │       ├── MarcaInfo.kt
            │       ├── MarcaInfoDao.kt
            │       │
            │       ├── RetrofitClient.kt
            │       ├── WikipediaApiService.kt
            │       ├── WikiSummaryResponse.kt
            │       ├── WikipediaResult.kt
            │       ├── WikipediaRepository.kt
            │       └── DetalleViewModel.kt
            │
            └── res/
                │
                ├── drawable/
                │   ├── coche_deportivo.jpg
                │   ├── coche_super.jpg
                │   └── coche_utilitario.jpg
                │
                ├── layout/
                │   ├── activity_main.xml
                │   ├── activity_lista.xml
                │   ├── activity_detalle.xml
                │   └── fila_coche.xml
                │
                ├── raw/
                │   ├── motor_deportivo.mp3
                │   ├── motor_super.mp3
                │   └── motor_utilitario.mp3
                │
                ├── values/
                │   ├── colors.xml
                │   ├── strings.xml
                │   └── themes.xml
                │
                └── xml/
                    ├── backup_rules.xml
                    ├── data_extraction_rules.xml
                    └── file_paths.xml
```

### Descripción de las principales clases

**Gestión de vehículos**
- `MainActivity.kt`: formulario principal para registrar vehículos.
- `ListaActivity.kt`: muestra los vehículos almacenados.
- `DetalleActivity.kt`: presenta la información completa del vehículo.
- `CocheAdapter.kt`: adapta los datos para mostrarlos en el RecyclerView.
- `Vehiculo.kt`: entidad Room que representa un vehículo.
- `VehiculoDao.kt`: operaciones de consulta, inserción y eliminación.
- `AppDatabase.kt`: configuración de la base de datos local.
- `Coche.kt` y `Garaje.kt`: clases auxiliares del modelo de coches.

**Fotografías y ubicación**
- `Fotos.kt`: funciones auxiliares para crear, copiar y cargar fotografías.
- `file_paths.xml`: configuración de las rutas utilizadas por FileProvider.

**Conexión a Internet y caché**
- `RetrofitClient.kt`: configuración del cliente Retrofit.
- `WikipediaApiService.kt`: definición de las peticiones a Wikipedia REST API.
- `WikiSummaryResponse.kt`: modelo de datos para interpretar la respuesta JSON.
- `MarcaInfo.kt`: entidad Room para almacenar la información de las marcas.
- `MarcaInfoDao.kt`: operaciones de acceso a la información almacenada.
- `WikipediaRepository.kt`: gestiona las consultas online y la recuperación de datos locales.
- `WikipediaResult.kt`: resultado de la consulta, incluyendo la información obtenida y el estado de conexión.
- `DetalleViewModel.kt`: gestiona los estados de carga, éxito y error que utiliza la pantalla de detalle.

---

## 🚀 Cómo ejecutar el proyecto

### Requisitos previos

- Android Studio instalado.
- JDK compatible con la configuración de Gradle del proyecto.
- Emulador Android o dispositivo físico.
- Conexión a Internet para realizar las consultas online.

### Instalación y ejecución

1. Clona el repositorio de GitHub o descarga el proyecto.
2. Abre Android Studio.
3. Selecciona **Open** y busca la carpeta raíz del proyecto, donde se encuentra `settings.gradle.kts`.
4. Espera a que Gradle sincronice el proyecto y descargue las dependencias necesarias.
5. Selecciona un emulador Android o conecta un dispositivo físico.
6. Pulsa **Run ▶** para compilar y ejecutar la aplicación.

### Pruebas de funcionamiento

**1. Consulta online**

1. Comprueba que el dispositivo tiene conexión a Internet.
2. Registra un vehículo indicando una marca que disponga de una página en Wikipedia.
3. Accede al detalle del vehículo.
4. Comprueba que aparece la información obtenida desde Wikipedia.
5. La información recibida queda almacenada en Room.

**2. Consulta sin conexión**

1. Realiza primero una consulta online para guardar la información en Room.
2. Desactiva el Wi-Fi y los datos móviles o activa el modo avión.
3. Vuelve a acceder al detalle del mismo vehículo.
4. Comprueba que se recupera la información desde la caché local.

**3. Comprobación de errores**

1. Desactiva la conexión a Internet.
2. Accede al detalle de una marca que todavía no se haya consultado.
3. Comprueba que aparece un mensaje informativo indicando que no hay conexión ni información guardada.
4. Verifica que la aplicación continúa funcionando correctamente.

---

<div align="center">

**Proyecto académico · 2.º DAM**

*Programación Multimedia y Dispositivos Móviles*

</div>
