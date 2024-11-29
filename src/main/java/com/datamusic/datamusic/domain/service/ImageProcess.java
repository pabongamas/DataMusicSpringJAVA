package com.datamusic.datamusic.domain.service;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.PriorityQueue;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public void generateThumbnailOfImage(String pathImage, String uploadDirectoryThumbsAlbum) {
        try {
            // Cargar la imagen original
            File file = new File(pathImage);
            BufferedImage originalImage = ImageIO.read(file);

            // Definir el tamaño de la miniatura
            int thumbnailWidth = 100;
            int thumbnailHeight = 100;

            // Crear una nueva imagen con el tamaño de la miniatura
            Image thumbnail = originalImage.getScaledInstance(thumbnailWidth, thumbnailHeight, Image.SCALE_SMOOTH);
            BufferedImage bufferedThumbnail = new BufferedImage(thumbnailWidth, thumbnailHeight,
                    BufferedImage.TYPE_INT_RGB);

            // Dibujar la miniatura en el BufferedImage
            Graphics2D g2d = bufferedThumbnail.createGraphics();
            g2d.drawImage(thumbnail, 0, 0, null);
            g2d.dispose();

            // Guardar la miniatura

            // Creo el directorio por si no esta
            Path uploadPathAlbumThumb = Path.of(uploadDirectoryThumbsAlbum);
            if (!Files.exists(uploadPathAlbumThumb)) {
                Files.createDirectories(uploadPathAlbumThumb);
            }

            File thumbnailFile = new File(uploadDirectoryThumbsAlbum + file.getName());
            ImageIO.write(bufferedThumbnail, "jpg", thumbnailFile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public ByteArrayOutputStream generateThumbnailMultiPartFile(MultipartFile image, String pathThumbTemp)
            throws IOException {
        ByteArrayOutputStream thumbnailOutputStream = new ByteArrayOutputStream();
        // Definir el tamaño de la miniatura
        int thumbnailWidth = 100;
        int thumbnailHeight = 100;
        BufferedImage originalImage = ImageIO.read(image.getInputStream());

        if (originalImage == null) {
            throw new IllegalArgumentException("El archivo no es una imagen válida.");
        }

        // Crear una nueva imagen con el tamaño de la miniatura
        Image thumbnail = originalImage.getScaledInstance(thumbnailWidth, thumbnailHeight, Image.SCALE_SMOOTH);
        BufferedImage bufferedThumbnail = new BufferedImage(thumbnailWidth, thumbnailHeight,
                BufferedImage.TYPE_INT_RGB);

        // Dibujar la miniatura en el BufferedImage
        Graphics2D g2d = bufferedThumbnail.createGraphics();
        g2d.drawImage(thumbnail, 0, 0, null);
        g2d.dispose();

        File thumbnailFile = new File(pathThumbTemp +"/"+ image.getOriginalFilename() + "_thumbnail.jpg");
        ImageIO.write(bufferedThumbnail, "jpg", thumbnailFile);

        // También escribir la miniatura en el ByteArrayOutputStream
        ImageIO.write(bufferedThumbnail, "jpg", thumbnailOutputStream);

        return thumbnailOutputStream;
    }

    public File convertByteArrayOutputStreamToFile(ByteArrayOutputStream byteArrayOutputStream, String fileName)
            throws IOException {
        // Crear un archivo temporal con el nombre proporcionado
        File file = new File(System.getProperty("java.io.tmpdir"), fileName);

        // Escribir los datos del ByteArrayOutputStream al archivo
        try (FileOutputStream fos = new FileOutputStream(file)) {
            byteArrayOutputStream.writeTo(fos);
        }

        return file;
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
