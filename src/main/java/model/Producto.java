package model;

public record Producto(
        Integer id,
        String nombre,
        String categoria,
        Integer valor,
        Boolean ciudad
) {
}
