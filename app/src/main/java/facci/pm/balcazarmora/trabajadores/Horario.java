package facci.pm.balcazarmora.trabajadores;

public class Horario {

    private String fecha;
    private String hora;

    public Horario(String fecha, String hora) {
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }
}
