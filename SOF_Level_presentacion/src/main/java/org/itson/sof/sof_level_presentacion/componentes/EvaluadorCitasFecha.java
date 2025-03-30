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
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        String key = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        
        return citasPorDia.containsKey(key);
    }

    @Override
    public Color getSpecialForegroundColor() {
        return Color.WHITE;
    }

    @Override
    public Color getSpecialBackroundColor() {
        return getColorForDay();
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
            System.out.println("Verde");
            return Color.GREEN;  // 1 cita → Verde
        } else if (citaCount == 2) {
            System.out.println("Naranja");
            return Color.ORANGE; // 2 citas → Naranja
        } else if (citaCount >= 3) {
            System.out.println("");
            return Color.RED;    // 3 o más citas → Rojo
        }
        return Color.GREEN; // Si no hay citas, no se destaca
    }

    private int getCitaCount() {
        Calendar cal = Calendar.getInstance();
        String key = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        
        return citasPorDia.getOrDefault(key, 0); // Devuelve la cantidad de citas para el día
    }
}