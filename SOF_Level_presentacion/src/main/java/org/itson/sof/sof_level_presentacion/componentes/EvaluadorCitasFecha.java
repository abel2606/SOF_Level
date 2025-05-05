package org.itson.sof.sof_level_presentacion.componentes;

import com.toedter.calendar.IDateEvaluator;
import java.awt.Color;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class EvaluadorCitasFecha implements IDateEvaluator {
    private final Map<String, Integer> citasPorDia;

    public EvaluadorCitasFecha(Map<String, Integer> citasPorDia) {
        this.citasPorDia = citasPorDia;
    }

    @Override
    public boolean isSpecial(Date date) {
        String key = formatearFecha(date);
        boolean resultado=citasPorDia.containsKey(key);
        return resultado;
    }

    @Override
    public Color getSpecialForegroundColor() {
        return Color.BLACK;
    }

    @Override
    public Color getSpecialBackroundColor() {
        return Color.GREEN;
    }

    @Override
    public String getSpecialTooltip() {
        return "Citas: " + getCitaCount();
    }

    @Override
    public boolean isInvalid(Date date) {
        return false;
    }

    @Override
    public Color getInvalidForegroundColor() {
        return null;
    }

    @Override
    public Color getInvalidBackroundColor() {
        return null;
    }

    @Override
    public String getInvalidTooltip() {
        return null;
    }

    private Color getColorForDay() {
        // Obtener la fecha actual y crear la clave para comparar
        Calendar cal = Calendar.getInstance();
        String key = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH);

        // Obtener el número de citas de ese día específico
        int citaCount = citasPorDia.getOrDefault(key, 0);

        // Determinar el color basado en el número de citas
        if (citaCount == 1) {
            return Color.GREEN;
        } else if (citaCount == 2) {
            return Color.YELLOW;
        } else if (citaCount >= 3) {
            return Color.RED;
        }
        return Color.WHITE;
    }
    
    private String formatearFecha(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH);
    }

    private int getCitaCount() {
        Calendar cal = Calendar.getInstance();
        String key = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        
        return citasPorDia.getOrDefault(key, 0); // Devuelve la cantidad de citas para el día
    }
}