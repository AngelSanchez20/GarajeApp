<div align="center">

# 🚗 Android 2º DAM — GarajeApp (Versión 2)

*Aplicación de gestión de garaje en Kotlin para la asignatura Programación Multimedia y Dispositivos Móviles (2º de DAM), conectada a Internet y con caché local en Room*

![Android Studio](https://img.shields.io/badge/Android%20Studio-3DDC84?style=for-the-badge&logo=android-studio&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Room](https://img.shields.io/badge/Room%20DB-4285F4?style=for-the-badge&logo=android&logoColor=white)
![Retrofit](https://img.shields.io/badge/Retrofit-009688?style=for-the-badge&logo=android&logoColor=white)
![Estado](https://img.shields.io/badge/Estado-Completado%20V2-success?style=for-the-badge)

</div>

---

## 📋 Tabla de Contenidos

- 📖 [Acerca del Proyecto](#-acerca-del-proyecto)
- 🌐 [Ficha de la API Pública](#-ficha-de-la-api-pública)
- 📱 [Características](#-características)
- ✅ [Cumplimiento de los Hitos](#-cumplimiento-de-los-hitos)
- 🛠️ [Tecnologías y Herramientas](#️-tecnologías-y-herramientas)
- 📂 [Estructura del Proyecto](#-estructura-del-proyecto)
- 🚀 [Cómo Ejecutar el Proyecto](#-cómo-ejecutar-el-proyecto)

---

## 📖 Acerca del Proyecto

**GarajeApp — Versión 2** evoluciona la aplicación de gestión de vehículos desarrollada en Kotlin para Android, combinando la información local gestionada por el usuario con datos obtenidos desde un servicio web público en Internet.

La aplicación mantiene las funcionalidades previas de persistencia local, fotografía, geolocalización GPS y reproducción de sonido según la potencia del vehículo, añadiendo consulta en tiempo real de información sobre la marca del coche y almacenamiento en caché con **Room** para poder consultarla posteriormente sin conexión sin que la aplicación se cierre.

---

## 🌐 Ficha de la API Pública

- **Nombre de la API:** Wikipedia REST API (`/api/rest_v1/page/summary/{title}`)
- **URL Base:** `https://es.wikipedia.org/`
- **Acceso:** Público, gratuito y sin necesidad de clave de API ni registro previo.
- **Finalidad en la App:** Obtener el título y el resumen descriptivo de la marca del vehículo al abrir su pantalla de detalle (`DetalleActivity`), guardándolo en Room para reutilizarlo sin conexión.
- **Configuración HTTP:** Cliente Retrofit con OkHttp y cabecera personalizada `User-Agent` para evitar bloqueos en las peticiones.

---

## 📱 Características

- **Gestión de Vehículos con Room:** Alta, consulta y eliminación de vehículos (`Vehiculo`, `VehiculoDao`) en base de datos local, con clasificación por potencia (`Utilitario`, `Deportivo`, `Superdeportivo`).
- **Eliminación Directa desde el Listado:** Cada fila del `RecyclerView` (`CocheAdapter` y `ListaActivity`) incorpora un botón de eliminar para borrar registros de forma asíncrona y actualizar la lista al instante.
- **Fotografía y Geolocalización GPS:** Guardado de fotografías del vehículo en almacenamiento local (`Fotos`) y registro/visualización de coordenadas GPS cuando están disponibles.
- **Consulta Online y Caché Offline:** Consulta los datos de la marca desde Wikipedia cuando hay conexión y los guarda en Room (`MarcaInfo`, `MarcaInfoDao`) para recuperarlos sin conexión sin cierres inesperados.
- **Gestión de Estados y Errores:** Indicadores de carga, aviso de modo sin conexión al cargar datos desde caché y mensajes claros cuando no hay conexión ni datos guardados.
- **Detalle y Sonido de Motor:** Pantalla de detalle con los datos del vehículo, ubicación GPS, historial de la marca y reproducción de sonido de motor según la categoría.

---

## ✅ Cumplimiento de los Hitos

| Hito / Criterio | Requisito de la Actividad | Implementación en GarajeApp |
| :--- | :--- | :--- |
| **Hito 1: Conexión Online** | Consulta HTTP/REST a una API pública gratuita sin clave, usando Retrofit/Gson, corrutinas y estados de carga. | `RetrofitClient`, `WikipediaApiService`, `WikiSummaryResponse` y estados en `DetalleActivity`. |
| **Hito 2: Detalle y Caché Offline** | Mostrar detalle ampliado, guardar datos online en Room y consultarlos sin conexión sin cierres. | `MarcaInfo`, `MarcaInfoDao` y recuperación desde Room cuando falla la red o no hay Internet. |
| **Hito 3: Arquitectura y Mejora Propia** | Uso de `ViewModel` y `Repository`, separación de responsabilidades y funcionalidad propia útil. | `DetalleViewModel`, `WikipediaRepository`, fotos con cámara, GPS, audio y borrado desde la lista. |

---

## 🛠️ Tecnologías y Herramientas

- **Lenguaje:** Kotlin
- **Entorno de Desarrollo:** Android Studio
- **Persistencia Local:** Room (`AppDatabase`, `VehiculoDao`, `MarcaInfoDao`)
- **Red y JSON:** Retrofit, OkHttp y Gson
- **Concurrencia:** Corrutinas de Kotlin
- **Interfaz:** XML Layouts, `RecyclerView`, `ImageButton`, `ProgressBar` y `MediaPlayer`

---

🚀 Cómo Ejecutar el Proyecto
Abre el proyecto en Android Studio y espera a que Gradle sincronice las dependencias (libs.versions.toml).

Selecciona un emulador Android o conecta un dispositivo físico y compila/ejecuta la aplicación (app).

Prueba online: Abre el detalle de un vehículo con conexión a Internet para cargar la información de la marca y guardarla en la caché de Room.

Prueba offline: Desactiva el Wi-Fi / datos móviles o activa el modo avión y vuelve a abrir el detalle para comprobar la carga desde caché o el mensaje de error si no existe registro previo.

---

## 📂 Estructura del Proyecto

El código fuente principal se encuentra en `app/src/main/java/com/example/garajeapp/` y los diseños de interfaz en `app/src/main/res/layout/`:

```text
GarajeApp/
├── app/
│   ├── build.gradle.kts
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── java/com/example/garajeapp/
│           │   ├── AppDatabase.kt
│           │   ├── Coche.kt
│           │   ├── CocheAdapter.kt
│           │   ├── DetalleActivity.kt
│           │   ├── DetalleViewModel.kt
│           │   ├── Fotos.kt
│           │   ├── Garaje.kt
│           │   ├── ListaActivity.kt
│           │   ├── MainActivity.kt
│           │   ├── MarcaInfo.kt
│           │   ├── MarcaInfoDao.kt
│           │   ├── RetrofitClient.kt
│           │   ├── Vehiculo.kt
│           │   ├── VehiculoDao.kt
│           │   ├── WikiSummaryResponse.kt
│           │   ├── WikipediaApiService.kt
│           │   ├── WikipediaRepository.kt
│           │   └── WikipediaResult.kt
│           └── res/
│               ├── drawable/
│               ├── layout/
│               │   ├── activity_detalle.xml
│               │   ├── activity_lista.xml
│               │   ├── activity_main.xml
│               │   └── fila_coche.xml
│               ├── raw/
│               └── values/
├── build.gradle.kts
├── gradle/libs.versions.toml
└── settings.gradle.kts


Prueba online: Abre el detalle de un vehículo con conexión a Internet para cargar la información de la marca y guardarla en la caché de Room.

Prueba offline: Desactiva el Wi-Fi / datos móviles o activa el modo avión y vuelve a abrir el detalle para comprobar la carga desde caché o el mensaje de error si no existe registro previo.
