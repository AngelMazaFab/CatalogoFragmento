# CatalogoFragmento

Un catálogo de productos electrónicos desarrollado para Android utilizando **Fragments** para una interfaz modular y dinámica.

## Propósito
La aplicación permite a los usuarios navegar por un catálogo de artículos tecnológicos (computadoras, accesorios, audio y almacenamiento), ver detalles específicos de cada producto, gestionar un carrito de compras y marcar artículos como favoritos.

## Archivos Críticos

### Modelo de Datos
- **`Articulo.java`**: Define la estructura de los productos (nombre, precio, descripción, stock, categoría, favorito).
- **`ItemCarrito.java`**: Estructura para los elementos añadidos al carrito.

### Lógica de Interfaz (Fragments)
- **`FragmentLista.java`**: Gestiona la visualización de la lista de productos con soporte para búsqueda y filtrado por categorías.
- **`FragmentoDetalle.java`**: Muestra la información detallada de un producto seleccionado y permite añadirlo al carrito.
- **`FragmentCarrito.java`**: Controla la visualización de los artículos seleccionados para compra y el cálculo del total.

### Comunicación y Adaptadores
- **`MainActivity.java`**: Actividad principal que actúa como mediador entre los tres fragmentos mediante interfaces.
- **`ArticuloAdapter.java`** y **`CarritoAdapter.java`**: Adaptadores para gestionar el inflado y vinculación de datos en los RecyclerViews.

### Recursos Visuales
- **`layout/`**: Contiene las definiciones XML para los fragmentos y los elementos de lista.
- **`drawable/`**: Imágenes de los productos (laptop, monitor, mouse, teclado, etc.).

## Objetivos del Proyecto
1.  **Modularidad**: Implementar una arquitectura basada en fragmentos para facilitar la reutilización de componentes UI.
2.  **Filtrado Dinámico**: Permitir al usuario encontrar productos rápidamente mediante una barra de búsqueda y un selector de categorías.
3.  **Gestión de Estado**: Mantener la coherencia de datos entre el catálogo y el carrito de compras.
4.  **Experiencia de Usuario**: Proporcionar una interfaz limpia y responsiva para la navegación de productos.
