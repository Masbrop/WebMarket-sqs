package model;

public record MetodoPago(
        Integer id,
        String tipo,
        String banco,
        String fecha,
        Integer capacidad
) {
}
