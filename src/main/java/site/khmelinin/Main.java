package site.khmelinin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            LocalTime currentTime = LocalTime.now();
            List<LocalTime> targetTimes = Arrays.asList(
                    LocalTime.of(7, 30),
                    LocalTime.of(8, 0),
                    LocalTime.of(8, 30),
                    LocalTime.of(9, 0),
                    LocalTime.of(9, 30),
                    LocalTime.of(10, 0),
                    LocalTime.of(10, 30),
                    LocalTime.of(11, 0),
                    LocalTime.of(11, 30),
                    LocalTime.of(12, 0),
                    LocalTime.of(12, 30),
                    LocalTime.of(13, 0),
                    LocalTime.of(13, 30),
                    LocalTime.of(14, 0),
                    LocalTime.of(14, 30),
                    LocalTime.of(15, 0),
                    LocalTime.of(15, 30),
                    LocalTime.of(16, 0),
                    LocalTime.of(16, 30),
                    LocalTime.of(17, 0),
                    LocalTime.of(17, 30),
                    LocalTime.of(18, 0),
                    LocalTime.of(18, 30),
                    LocalTime.of(19, 0),
                    LocalTime.of(19, 30),
                    LocalTime.of(20, 0),
                    LocalTime.of(20, 30),
                    LocalTime.of(21, 0),
                    LocalTime.of(21, 30)

            );
            LocalTime roundedCurrentTime = currentTime.truncatedTo(ChronoUnit.MINUTES);
            if (targetTimes.contains(roundedCurrentTime)) {
                System.out.println("Task completed: " + currentTime);
                saveImage();
            }
        };
        executorService.scheduleAtFixedRate(task, 0, 1, TimeUnit.MINUTES);
    }

    private static void saveImage() {
        try {
            String imageUrl = "http://188.244.38.238:8080/?action=snapshot";

            URL url = new URL(imageUrl);
            BufferedImage image = ImageIO.read(url);

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
            String formattedNow = now.format(formatter);

            System.out.println(formattedNow);

            File dir = new File("images");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            ImageIO.write(image, "jpg", new File("images/snapshot_" + formattedNow + ".jpg"));
            System.out.println("snapshot_" + formattedNow + ".jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
