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

---

## 📖 Acerca del Proyecto

**Mi Garaje** es un proyecto libre desarrollado para la asignatura **Programación Multimedia y Dispositivos Móviles** de **2º de Desarrollo de Aplicaciones Multiplataforma (2º DAM)**.

La aplicación permite al usuario gestionar un garaje personal de vehículos. A través de un formulario, se pueden dar de alta coches indicando su marca, modelo y potencia en caballos de vapor (CV). Los vehículos guardados se muestran en un listado mediante un `RecyclerView`. Al pulsar sobre cualquier coche de la lista, se abre su ficha detallada, mostrando su categoría, una imagen representativa y reproduciendo el sonido real de su motor.

---

## 📱 Características

- **Formulario con validación en tiempo real (`Toast`)**:
  - Comprobación de marca vacía.
  - Comprobación de modelo vacío.
  - Comprobación de potencia vacía.
  - Validación de que la potencia introducida sea un número válido.
  - Comprobación de que la potencia sea un valor estrictamente mayor a 0.
- **Navegación fluida entre 3 pantallas**:
  - `MainActivity`: Formulario de entrada y botón de acceso al garaje.
  - `ListaActivity`: Listado de vehículos registrados en el garaje.
  - `DetalleActivity`: Ficha técnica del vehículo seleccionado.
- **Paso de información entre pantallas**:
  - Comunicación mediante `Intent` con `putExtra` (`MARCA`, `MODELO`, `POTENCIA`) desde el `CocheAdapter` hasta `DetalleActivity`.
- **Listado dinámico e interactivo**:
  - Construido con `RecyclerView`, `CocheAdapter` y la `data class Coche`.
  - Almacenamiento en memoria mediante el objeto singleton `Garaje`.
- **Clasificación por categorías de potencia**:
  - **Utilitario**: menos de 100 CV.
  - **Deportivo**: entre 100 CV y 300 CV.
  - **Superdeportivo**: más de 300 CV.
  - Cada categoría cuenta con su propia imagen descriptiva e icono de lista.
- **Efectos multimedia de audio**:
  - Reproducción del sonido de motor característico usando `MediaPlayer` en `DetalleActivity`.
  - Liberación adecuada de recursos en el ciclo de vida (`onDestroy`).
- **Persistencia de datos**:
  - Guardado de la última marca introducida en `SharedPreferences` para rellenar automáticamente el campo al reabrir la app.
- **Control de estado**:
  - Aviso mediante `Toast` si se intenta acceder al garaje sin haber añadido ningún coche previamente.

---

## ✅ Requisitos de la actividad

| Requisito | Estado | Dónde se cumple |
| :--- | :---: | :--- |
| **Al menos 3 Activities** | ✅ | `MainActivity.kt`, `ListaActivity.kt`, `DetalleActivity.kt` |
| **Intent con putExtra** | ✅ | `CocheAdapter.kt` (envío a `DetalleActivity.kt`) |
| **Formulario con validación Toast** | ✅ | `MainActivity.kt` (validaciones con `when`) |
| **ImageView con sentido** | ✅ | `activity_detalle.xml` (`imagenDetalle`) y `fila_coche.xml` (`imagenFila`) |
| **RecyclerView con Adapter y data class** | ✅ | `ListaActivity.kt`, `CocheAdapter.kt`, `Coche.kt` |
| **Layouts cuidados (margin, padding, gravity)** | ✅ | Layouts XML en `res/layout/` |
| **Sonido con MediaPlayer (Opcional)** | ✅ | `DetalleActivity.kt` (reproducción y `release()` en `onDestroy()`) |
| **SharedPreferences (Opcional)** | ✅ | `MainActivity.kt` (`getSharedPreferences("MiGaraje", ...)` para `ULTIMA_MARCA`) |
| **Categorías con icono distinto (Opcional)** | ✅ | `CocheAdapter.kt` y `DetalleActivity.kt` (imágenes en `res/drawable/`) |

---

## 🛠️ Tecnologías y Herramientas

| Categoría | Herramienta / Tecnología |
| :--- | :--- |
| **Lenguaje** | Kotlin |
| **Entorno de Desarrollo** | Android Studio |
| **Interfaz de Usuario** | XML Layouts con `LinearLayout` y vistas clásicas con `findViewById` (sin Compose) |
| **Listas** | `RecyclerView` (`androidx.recyclerview:recyclerview:1.3.2`) |
| **Navegación** | `Intent` con `putExtra` / `getStringExtra` / `getIntExtra` |
| **Multimedia** | `ImageView` y `MediaPlayer` |
| **Almacenamiento** | `SharedPreferences` (última marca) y memoria RAM (`mutableListOf`) |
| **Patrones de Diseño** | Singleton (`object Garaje`), Data Class (`Coche`), Adapter (`CocheAdapter`) |
| **Compatibilidad** | `minSdk = 24` (Android 7.0 Nougat o superior) |

---

## 📂 Estructura del Proyecto

```
GarajeApp/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/garajeapp/
│   │       │   ├── Coche.kt            # Data class del modelo de datos (marca, modelo, potencia)
│   │       │   ├── Garaje.kt           # Object singleton para mantener la lista en memoria
│   │       │   ├── CocheAdapter.kt     # Adaptador personalizado para el RecyclerView
│   │       │   ├── MainActivity.kt     # Formulario de alta, SharedPreferences y validaciones Toast
│   │       │   ├── ListaActivity.kt    # Pantalla del garaje con el RecyclerView
│   │       │   └── DetalleActivity.kt  # Ficha técnica del coche con imagen y sonido de motor
│   │       ├── res/
│   │       │   ├── drawable/
│   │       │   │   ├── coche_utilitario.jpg  # Imagen de vehículo utilitario (< 100 CV)
│   │       │   │   ├── coche_deportivo.jpg   # Imagen de vehículo deportivo (100 - 300 CV)
│   │       │   │   └── coche_super.jpg       # Imagen de vehículo superdeportivo (> 300 CV)
│   │       │   ├── layout/
│   │       │   │   ├── activity_main.xml     # Layout del formulario principal
│   │       │   │   ├── activity_lista.xml    # Layout de la pantalla del garaje
│   │       │   │   ├── activity_detalle.xml  # Layout de la ficha detallada del coche
│   │       │   │   └── fila_coche.xml        # Layout de cada elemento del RecyclerView
│   │       │   └── raw/
│   │       │       ├── motor_utilitario.mp3  # Sonido de motor utilitario
│   │       │       ├── motor_deportivo.mp3   # Sonido de motor deportivo
│   │       │       └── motor_super.mp3       # Sonido de motor superdeportivo
│   │       └── AndroidManifest.xml       # Registro de las 3 Activities del proyecto
│   └── build.gradle.kts                  # Configuración del módulo de la aplicación (minSdk 24)
└── README.md                             # Documentación principal del proyecto
```

---

## 📸 Capturas

| Formulario (`MainActivity`) | Garaje (`ListaActivity`) | Ficha (`DetalleActivity`) |
| :---: | :---: | :---: |
| ![Formulario](docs/formulario.png) | ![Garaje](docs/garaje.png) | ![Ficha](docs/ficha.png) |

---

## 🚀 Cómo ejecutar el proyecto

1. **Clonar el repositorio**:
   <!-- Reemplazar NOMBRE-DEL-REPO por el nombre real del repositorio en GitHub -->
   ```bash
   git clone https://github.com/AngelSanchez20/NOMBRE-DEL-REPO.git
   ```

2. **Abrir el proyecto en Android Studio**:
   - Inicia Android Studio.
   - Selecciona **Open** y navega hasta la carpeta donde clonaste el repositorio.

3. **Sincronizar Gradle**:
   - Espera a que Android Studio descargue las dependencias y sincronice los archivos de Gradle.

4. **Ejecutar la aplicación**:
   - Conecta un dispositivo físico mediante depuración USB o inicia un emulador de Android (AVD).
   - Haz clic en el botón **Run** (o presiona `Shift + F10`).
