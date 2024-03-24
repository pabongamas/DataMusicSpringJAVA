package com.datamusic.datamusic.domain.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.PriorityQueue;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

@Service
public class ImageProcess {
    /**
     * 
     * @param pathImage    La ruta de la imagen de la cual extraer los colores.
     * @param numberColors El número de colores principales que se deben extraer.
     * @return Una lista de mapas, donde cada mapa contiene la frecuencia y el valor
     *         hexadecimal de un color.
     */
    public List<Map<String, Object>> colorsOfImage(String pathImage, int numberColors) {
        List<Map<String, Object>> colors = new ArrayList<>();

        try {
            BufferedImage image = ImageIO.read(new File(pathImage));

            // Calcular la frecuencia de los colores
            Map<Integer, Integer> colorFrequency = getColorFrequency(image);

            // Seleccionar los 5 colores principales
            PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>(numberColors,
                    Comparator.comparingInt(Map.Entry::getValue));
            for (Map.Entry<Integer, Integer> entry : colorFrequency.entrySet()) {
                pq.offer(entry);
                if (pq.size() > numberColors) {
                    pq.poll();
                }
            }

            while (!pq.isEmpty()) {
                Map.Entry<Integer, Integer> entry = pq.poll();
                String hexColor = toHexString(entry.getKey());
                Map<String, Object> colorFrequencyValues = new HashMap<String, Object>();
                colorFrequencyValues.put("colorHex", hexColor);
                colorFrequencyValues.put("frecuencia", entry.getValue());
                colors.add(colorFrequencyValues);
            }
            Collections.sort(colors, new Comparator<Map<String, Object>>() {

                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    int frecuencia1 = (int) o1.get("frecuencia");
                    int frecuencia2 = (int) o2.get("frecuencia");
                    // Ordenar en orden descendente (de mayor a menor)
                    return Integer.compare(frecuencia2, frecuencia1);
                }

            });

        } catch (IOException e) {
        }
        return colors;
    }

    private static Map<Integer, Integer> getColorFrequency(BufferedImage image) {
        Map<Integer, Integer> colorFrequency = new HashMap<>();

        // Iterar sobre los píxeles de la imagen
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                // Obtener el color del píxel
                Color color = new Color(rgb);
                // Obtener el valor de color único (en este caso, el valor RGB)
                int uniqueColorValue = color.getRGB();

                // Contabilizar la frecuencia de este color
                colorFrequency.put(uniqueColorValue, colorFrequency.getOrDefault(uniqueColorValue, 0) + 1);
            }
        }
        return colorFrequency;
    }

    private static String toHexString(int colorValue) {
        return String.format("#%06X", (0xFFFFFF & colorValue));
    }
}
