<div align="center">

# 🚗 Android 2º DAM — Mi Garaje (Proyecto libre)

*Repositorio de prácticas y proyectos de la asignatura Programación Multimedia y Dispositivos Móviles (2º de DAM)*

![Android Studio](https://img.shields.io/badge/Android%20Studio-3DDC84?style=for-the-badge&logo=android-studio&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![RecyclerView](https://img.shields.io/badge/RecyclerView-4285F4?style=for-the-badge&logo=android&logoColor=white)
![Estado](https://img.shields.io/badge/Estado-En%20Desarrollo-orange?style=for-the-badge)

</div>

---

## 📋 Tabla de Contenidos

- 📖 [Acerca del Proyecto](#-acerca-del-proyecto)
- 📱 [Características](#-características)
- ✅ [Requisitos de la actividad](#-requisitos-de-la-actividad)
- 🛠️ [Tecnologías y Herramientas](#️-tecnologías-y-herramientas)
- 📂 [Estructura del Proyecto](#-estructura-del-proyecto)
- 📸 [Capturas](#-capturas)
- 🚀 [Cómo ejecutar el proyecto](#-cómo-ejecutar-el-proyecto)

# GarajeApp — Versión 2

Evolución de **GarajeApp** en Kotlin para Android: combina la gestión local de vehículos (Room, fotografía, GPS y audio por categoría) con la consulta de información de marca desde Internet mediante una API pública y caché local en Room.

## Ficha de la API pública

- **Nombre:** Wikipedia REST API (`/api/rest_v1/page/summary/{title}`)
- **URL base:** `https://es.wikipedia.org/`
- **Acceso:** Público, gratuito y sin necesidad de API key ni registro.
- **Uso en la aplicación:** Consulta el título y el resumen de la marca del vehículo en `DetalleActivity`, guardándolo en Room para poder consultarlo sin conexión.
- **Configuración HTTP:** Cliente Retrofit con OkHttp y cabecera `User-Agent` para evitar bloqueos en las peticiones.

## Funcionalidades

- **Gestión de vehículos con Room:** Alta, consulta y eliminación de vehículos (`Vehiculo`, `VehiculoDao`), con categorías según potencia (`Utilitario`, `Deportivo`, `Superdeportivo`), fotografía guardada en disco (`Fotos`) y coordenadas GPS.
- **Borrado desde el listado:** Cada elemento del `RecyclerView` (`CocheAdapter` y `ListaActivity`) permite eliminar el registro de forma asíncrona y actualizar la lista al instante.
- **Consulta online y caché offline:** Obtiene los datos de la marca desde Wikipedia cuando hay red y los guarda en Room (`MarcaInfo`, `MarcaInfoDao`) para recuperarlos sin conexión sin que la aplicación se cierre.
- **Gestión de estados y errores:** Muestra indicadores de carga, aviso de modo sin conexión cuando los datos salen de caché y mensajes descriptivos cuando no hay conexión ni caché disponible.
- **Detalle y sonido de motor:** Muestra los datos del vehículo, su ubicación GPS, la información de la marca y reproduce el sonido de motor correspondiente a su categoría.

## Estructura del proyecto

Todas las clases del código fuente se encuentran en `app/src/main/java/com/example/garajeapp/` y los diseños en `app/src/main/res/layout/`:

- **Pantallas y adaptador:** `MainActivity.kt`, `ListaActivity.kt`, `CocheAdapter.kt`, `DetalleActivity.kt`, `activity_main.xml`, `activity_lista.xml`, `activity_detalle.xml`, `fila_coche.xml`.
- **Estado y ViewModel:** `DetalleViewModel.kt` y estados de UI (`DetalleUiState`).
- **Red y repositorio:** `WikipediaRepository.kt`, `WikipediaResult.kt`, `RetrofitClient.kt`, `WikipediaApiService.kt`, `WikiSummaryResponse.kt`.
- **Base de datos y utilidades:** `AppDatabase.kt`, `Vehiculo.kt`, `VehiculoDao.kt`, `MarcaInfo.kt`, `MarcaInfoDao.kt`, `Fotos.kt`, `Coche.kt`, `Garaje.kt`.

## Cumplimiento de los hitos

| Hito / Criterio | Requisito de la actividad | Implementación en GarajeApp |
| :--- | :--- | :--- |
| **Hito 1: Conexión online** | Petición HTTP/REST a API pública gratuita sin clave, Retrofit/Gson, corrutinas y estados de carga. | `RetrofitClient`, `WikipediaApiService`, `WikiSummaryResponse` y estados en `DetalleActivity`. |
| **Hito 2: Detalle y caché offline** | Mostrar detalle, guardar datos online en Room y consultarlos sin conexión sin cierres. | `MarcaInfo`, `MarcaInfoDao` y recuperación desde Room cuando falla la red o no hay Internet. |
| **Hito 3: Arquitectura y mejora propia** | `ViewModel` y `Repository`, separación de responsabilidades y funcionalidad propia útil. | `DetalleViewModel`, `WikipediaRepository`, fotos con cámara, GPS, audio y borrado desde la lista. |

## Ejecución y pruebas

1. Abre el proyecto en **Android Studio** y espera a que sincronice Gradle (`libs.versions.toml`).
2. Ejecuta la aplicación en un emulador o dispositivo Android.
3. **Prueba online:** Abre el detalle de un vehículo con conexión a Internet para cargar la información de la marca y guardarla en caché.
4. **Prueba offline:** Desactiva la conexión (Wi-Fi / datos / modo avión) y vuelve a abrir el detalle para comprobar la carga desde Room o el mensaje de error si no hay caché.
