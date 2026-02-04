# 📱 Mascotas Android (Client App)

[![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Retrofit](https://img.shields.io/badge/Retrofit-005571?style=for-the-badge&logo=square&logoColor=white)](https://square.github.io/retrofit/)

Cliente móvil desarrollado para el **Sistema de Gestión Veterinaria**. La aplicación permite la administración completa de mascotas y razas a través de una interfaz moderna y reactiva construida con **Jetpack Compose**.

---

## 🏗️ Arquitectura del Proyecto
La aplicación sigue los principios de **Clean Architecture** y el patrón **MVVM** (Model-View-ViewModel):

* **UI:** Pantallas construidas 100% en Jetpack Compose.
* **Domain (Use Cases):** Lógica de negocio aislada para cada acción (Registrar, Eliminar, etc.).
* **Data (Repository/Service):** Gestión de peticiones remotas mediante Retrofit.
* **Core:** Inyección de dependencias y configuración centralizada.

---

## 📂 Estructura de Directorios

```text
com.example.mascotas
├── api
│   ├── client          # Configuración de Retrofit (VeterinariaClient)
│   ├── domain          # Casos de uso (Lógica de negocio por entidad)
│   ├── model           # Clases de datos (DTOs y Requests)
│   ├── repository      # Implementación del patrón Repository
│   └── service         # Definición de interfaces de la API
├── core                # Módulos de provisión y configuración global
├── ui
│   ├── screen          # Pantallas (Compose), Navegación y ViewModels
│   └── theme           # Configuración de estilos, colores y tipografía
└── MainActivity.kt     # Punto de entrada de la aplicación
```

## 🛠️ Catálogo de Casos de Uso (Lógica de Negocio)

### 🐕 Dominio Mascota
* **ObtenerListadoMascotasUseCase:** Recupera todos los pacientes.
* **RegistrarMascotaUseCase:** Valida y envía nuevos registros al backend.
* **ActualizarMascotaUseCase / EliminarMascotaUseCase:** Gestión del ciclo de vida.

### 🐈 Dominio Raza
* **ObtenerRazasUseCase:** Listado completo de razas.
* **ObtenerListadoEspeciesUseCase:** Filtro dinámico de especies.
* **ObtenerRazasPorEspecieUseCase:** Consultas filtradas según selección del usuario.

---

## ⚙️ Configuración del Servidor
Para que la aplicación se conecte correctamente al backend dockerizado, asegúrate de configurar la red:

1. Localiza el archivo `VeterinariaClient.kt`.
2. Configura la `BASE_URL`:
   - **Emulador Android:** `http://10.0.2.2:8000/`
   - **Dispositivo Físico:** `http://<TU_IP_LOCAL>:8000/`

> [!IMPORTANT]
> Se requiere el permiso `android.permission.INTERNET` y la propiedad `android:usesCleartextTraffic="true"` en el `AndroidManifest.xml` debido a que el servidor local utiliza HTTP.

---

## 🚀 Cómo empezar
1. 🌐 Backend
Este proyecto requiere que la API esté en ejecución:
[![API Repo](https://img.shields.io/badge/Backend_Repo-FastAPI-005571?style=flat&logo=fastapi)](https://github.com/cristianS97/ApiMascotas)
2. **Clona este repositorio:**
   ```bash
   git clone [https://github.com/cristianS97/MascotasAndroid](https://github.com/cristianS97/MascotasAndroid)