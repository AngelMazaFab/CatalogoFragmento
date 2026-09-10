# Corregir FragmentLista y mejorar la comunicación entre fragmentos

Se detectaron errores de compilación en `FragmentLista.java` y falta de funcionalidad en `MainActivity` y `FragmentoDetalle` para mostrar los detalles del artículo seleccionado.

## Cambios Propuestos

### [Componente] :app

#### [MODIFY] [FragmentLista.java](file:///C:/Escuela/Octavo/CatalogoFragmento/app/src/main/java/com/example/catalogofragmento/FragmentLista.java)
- Corregir importaciones faltantes (`Context`, `NonNull`, `Nullable`, `ArrayAdapter`).
- Corregir el nombre de la clase en el método `newInstance` (de `FragmentList` a `FragmentLista`).
- Corregir la referencia al layout (de `R.layout.fragment_list` a `R.layout.fragment_lista`).
- Añadir la anotación `@Override` a `onAttach`.

#### [MODIFY] [FragmentoDetalle.java](file:///C:/Escuela/Octavo/CatalogoFragmento/app/src/main/java/com/example/catalogofragmento/FragmentoDetalle.java)
- Añadir referencias a los `TextView` definidos en `fragment_detalle.xml`.
- Implementar un método `mostrarDetalle(Articulo articulo)` para actualizar la interfaz con la información del artículo.

#### [MODIFY] [MainActivity.java](file:///C:/Escuela/Octavo/CatalogoFragmento/app/src/main/java/com/example/catalogofragmento/MainActivity.java)
- Implementar la interfaz `FragmentLista.OnArticuloSeleccionadoListener`.
- Sobrescribir `onArticuloSeleccionado` para buscar el `FragmentoDetalle` y llamar a su método `mostrarDetalle`.

## Plan de Verificación

### Pruebas Manuales
1. Compilar y ejecutar la aplicación.
2. Verificar que la lista de artículos se muestra correctamente en la parte superior.
3. Al hacer clic en un artículo de la lista, verificar que los detalles (nombre, descripción y precio) se actualizan en la parte inferior.
