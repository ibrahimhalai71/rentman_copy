package com.rentmen.app.utils;

import com.rentmen.app.Constants.Constants;
import com.rentmen.app.entities.Client;
import com.rentmen.app.entities.Job;
import com.rentmen.app.entities.ServiceProvider;
import com.rentmen.app.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UtilFunctions {
    public UtilFunctions() {
    }

    public static <T> T mergeObjects(T target, T source) throws IllegalAccessException {
        List<Field> baseClazz = Arrays.asList(target.getClass().getSuperclass().getDeclaredFields());
        List<Field> clazz = Arrays.asList(target.getClass().getDeclaredFields());
        List<Field> allFields = (List) Stream.concat(baseClazz.stream(), clazz.stream()).collect(Collectors.toList());
        Iterator var5 = allFields.iterator();

        while (var5.hasNext()) {
            Field field = (Field) var5.next();
            field.setAccessible(true);
            Object sourceValue = field.get(source);
            if (sourceValue != null && !Modifier.isFinal(field.getModifiers()) && !Collection.class.isAssignableFrom(field.getType())) {
                field.set(target, sourceValue);
            }
        }

        return target;
    }

    public static String saveMultipartFileToPath(MultipartFile imageFile, String fileName, Integer docType) {
        StringBuilder filePath = new StringBuilder();
        if (docType == 1) {
            filePath.append(Constants.PROFILE_IMAGE_PATH);
        } else if (docType == 2) {
            filePath.append(Constants.AGREEMENT_PATH);
        }

        File image = new File(filePath.toString());
        if (!image.exists() && image.mkdirs()) {
            System.out.println("Directory created successfully: " + filePath.toString());
        }

        filePath.append("/" + fileName);
        image = new File(filePath.toString());

        try {
            imageFile.transferTo(image);
        } catch (IllegalStateException var6) {
            IllegalStateException e = var6;
            e.printStackTrace();
        } catch (IOException var7) {
            IOException e = var7;
            e.printStackTrace();
        }

        return filePath.toString();
    }

    private static String getFileExtension(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType.split("/")[1];
    }

    public static String createFileName(MultipartFile imageFile, String name) {
        return name + "_" + UUID.randomUUID().toString() + "." + getFileExtension(imageFile);
    }

    public static byte[] downloadFileFromFileSystem(String fileName, Integer docType) throws IOException {
        StringBuilder filePath = new StringBuilder();
        if (docType == 1) {
            filePath.append(Constants.PROFILE_IMAGE_PATH);
        } else if (docType == 2) {
            filePath.append(Constants.AGREEMENT_PATH);
        }

        filePath.append("/" + fileName);
        byte[] images = Files.readAllBytes((new File(filePath.toString())).toPath());
        return images;
    }

    public static void deleteFile(String filePath) throws Exception {
        File fileToDelete = new File(filePath);
        if (fileToDelete.exists()) {
            if (fileToDelete.delete()) {
                System.out.println("File deleted successfully: " + filePath);
            } else {
                System.out.println("Failed to delete file: " + filePath);
                throw new Exception("Failed to delete file: " + filePath);
            }
        } else {
            System.out.println("File does not exist: " + filePath);
            throw new Exception("File does not exist: " + filePath);
        }
    }

    public static Map<Long, Boolean> convertWithStream(String mapAsString) {
        mapAsString = mapAsString.replace("{", "").replace("}", "");
        Map<Long, Boolean> map = (Map) Arrays.stream(mapAsString.split(",")).map((entry) -> {
            return entry.split("=");
        }).collect(Collectors.toMap((entry) -> {
            return Long.parseLong(entry[0]);
        }, (entry) -> {
            return Boolean.parseBoolean(entry[1]);
        }));
        return map;
    }

    public static String convertWithStream(Map<Long, Boolean> map) {
        String mapAsString = (String) map.keySet().stream().map((key) -> {
            return key + "=" + map.get(key);
        }).collect(Collectors.joining(","));
        return mapAsString;
    }

    public static Double clientsRateOnEventTypeAndJobTime(Job job, ServiceProvider sp) {

        DayOfWeek startDay = job.getStartDate().getDayOfWeek();
        DayOfWeek endDay = job.getEndDate().getDayOfWeek();
        boolean isWeekend = false;
        boolean isNight = false;
        boolean isEvent = false;
        boolean isRailWork = false;

        if (startDay == DayOfWeek.SATURDAY || startDay == DayOfWeek.SUNDAY ||
                endDay == DayOfWeek.SATURDAY || endDay == DayOfWeek.SUNDAY) {
            isWeekend = true;
        }
        if (isNightShift(job.getStartTime(), job.getEndTime())) {
            isNight = true;
        }
        Client client = job.getClient();
        if (client.getClientEventRateType().equals(ClientEventRateType.EVENT)) {
            isEvent = true;
            isRailWork = false;
        }
        if (client.getClientEventRateType().equals(ClientEventRateType.RAILWORK)) {
            isRailWork = true;
            isEvent = false;
        }

//        if (client.getRateType().equals(User.RateType.DAY)) {
//            if (isWeekend && isEvent) {
//                return client.getWeekendRate();
//            } else if (isNight && isEvent) {
//                return client.getNightRate();
//            } else if (!isWeekend && !isNight && isEvent) {
//                return client.getDiscussedRate();
//            }
//            if (isWeekend && isRailWork) {
//                return client.getWeekendRateRW();
//            } else if (isNight && isRailWork) {
//                return client.getNightRateRW();
//            } else if (!isWeekend && !isNight && isRailWork) {
//                return client.getDiscussedRateRW();
//            }
//        } else if (client.getRateType().equals(User.RateType.HOURLY)) {
//            if (isWeekend && isEvent) {
//                return client.getWeekendRate();
//            } else if (isNight && isEvent) {
//                return client.getNightRate();
//            } else if (!isWeekend && !isNight && isEvent) {
//                return client.getHourRate();
//            }
//            if (isWeekend && isRailWork) {
//                return client.getWeekendRateRW();
//            } else if (isNight && isRailWork) {
//                return client.getNightRateRW();
//            } else if (!isWeekend && !isNight && isRailWork) {
//                return client.getHourRateRW();
//            }
//        }
        if (client.getRateType().equals(User.RateType.DAY)) {

            if (isWeekend && isEvent) {
                return client.getWeekendRate() != null ? client.getWeekendRate() : client.getDiscussedRate();
            } else if (isNight && isEvent) {
                return client.getNightRate() != null ? client.getNightRate() : client.getDiscussedRate();
            } else if (!isWeekend && !isNight && isEvent) {
                return client.getDiscussedRate();
            }
            if (isWeekend && isRailWork) {
                return client.getWeekendRateRW() != null
                        ? client.getWeekendRateRW()
                        : (client.getDiscussedRateRW() != null
                        ? client.getDiscussedRateRW()
                        : client.getDiscussedRate());
            } else if (isNight && isRailWork) {
                return client.getNightRateRW() != null
                        ? client.getNightRateRW()
                        : (client.getDiscussedRateRW() != null
                        ? client.getDiscussedRateRW()
                        : client.getDiscussedRate());
            } else if (!isWeekend && !isNight && isRailWork) {
                return client.getDiscussedRateRW() != null ? client.getDiscussedRateRW() : client.getDiscussedRate();
            }
        } else if (client.getRateType().equals(User.RateType.HOURLY)) {
            if (isWeekend && isEvent) {
                return sp.getWeekendRate() != null
                        ? sp.getWeekendRate()
                        : (sp.getHourRate() != null
                        ? sp.getHourRate()
                        : sp.getDiscussedRate());
            } else if (isNight && isEvent) {
                return sp.getNightRate() != null
                        ? sp.getNightRate()
                        : (sp.getHourRate() != null
                        ? sp.getHourRate()
                        : sp.getDiscussedRate());
            } else if (!isWeekend && !isNight && isEvent) {
                return sp.getHourRate() != null ? sp.getHourRate() : sp.getDiscussedRate();
            }
            if (isWeekend && isRailWork) {
                return client.getWeekendRateRW() != null
                        ? client.getWeekendRateRW()
                        : (client.getHourRateRW() != null
                        ? client.getHourRateRW()
                        : (client.getDiscussedRateRW() != null
                        ? client.getDiscussedRateRW() :
                        client.getDiscussedRate()));
            } else if (isNight && isRailWork) {
                return client.getNightRateRW() != null
                        ? client.getNightRateRW()
                        : (client.getHourRateRW() != null
                        ? client.getHourRateRW()
                        : (client.getDiscussedRateRW() != null
                        ? client.getDiscussedRateRW() :
                        client.getDiscussedRate()));
            } else if (!isWeekend && !isNight && isRailWork) {
                return client.getHourRateRW() != null ? client.getHourRateRW() : (client.getDiscussedRateRW() != null
                        ? client.getDiscussedRateRW() :
                        client.getDiscussedRate());
            }
        }
        return client.getHourRate();
    }

    public static boolean isNightShift(LocalTime startTime, LocalTime endTime) {
        // Convert everything to minutes since midnight
        int shiftStart = startTime.toSecondOfDay() / 60;
        int shiftEnd = endTime.toSecondOfDay() / 60;

        // If the shift wraps around midnight (e.g., 22:00 to 06:00)
        if (shiftEnd <= shiftStart) {
            shiftEnd += 24 * 60;
        }

        int totalShiftMinutes = shiftEnd - shiftStart;

        // Night window from 23:00 to 05:00 next day
        int nightStart = 23 * 60;        // 1380
        int nightEnd = (24 + 5) * 60;    // 1740

        // Calculate overlap
        int overlapStart = Math.max(shiftStart, nightStart);
        int overlapEnd = Math.min(shiftEnd, nightEnd);
        int overlapMinutes = Math.max(0, overlapEnd - overlapStart);

        // Now, handle case when shift spans both before and after midnight,
        // and the night window wraps too
        if (shiftStart < (5 * 60)) {
            // If shift starts before 5:00 AM, consider night window as previous day 23:00 - current 5:00
            overlapStart = Math.max(shiftStart + 24 * 60, nightStart); // adjust shiftStart to next day
            overlapEnd = Math.min(shiftEnd + 24 * 60, nightEnd + 24 * 60);
            overlapMinutes = Math.max(overlapMinutes, overlapEnd - overlapStart);
        }

        double percentage = (overlapMinutes * 100.0) / totalShiftMinutes;
        return percentage >= 70.0;
    }


    public static Double studentsRateBasedOnEventTypeAndJobTime(Job job, ServiceProvider sp) {

        DayOfWeek startDay = job.getStartDate().getDayOfWeek();
        DayOfWeek endDay = job.getEndDate().getDayOfWeek();
        boolean isWeekend = false;
        boolean isNight = false;
        boolean isEvent = false;
        boolean isRailWork = false;

        if (startDay == DayOfWeek.SATURDAY || startDay == DayOfWeek.SUNDAY ||
                endDay == DayOfWeek.SATURDAY || endDay == DayOfWeek.SUNDAY) {
            isWeekend = true;
        }
        if (isNightShift(job.getStartTime(), job.getEndTime())) {
            isNight = true;
        }
        Client client = job.getClient();
        if (client.getClientEventRateType().equals(ClientEventRateType.EVENT)) {
            isEvent = true;
            isRailWork = false;
        }
        if (client.getClientEventRateType().equals(ClientEventRateType.RAILWORK)) {
            isRailWork = true;
            isEvent = false;
        }

        if (client.getRateType().equals(User.RateType.DAY)) {
            if (isWeekend && isEvent) {
                return sp.getWeekendRate() != null ? sp.getWeekendRate() : sp.getDiscussedRate();
            } else if (isNight && isEvent) {
                return sp.getNightRate() != null ? sp.getNightRate() : sp.getDiscussedRate();
            } else if (!isWeekend && !isNight && isEvent) {
                return sp.getDiscussedRate();
            }
            if (isWeekend && isRailWork) {
                return sp.getWeekendRateRW() != null
                        ? sp.getWeekendRateRW()
                        : (sp.getDiscussedRateRW() != null
                        ? sp.getDiscussedRateRW()
                        : sp.getDiscussedRate());
            } else if (isNight && isRailWork) {
                return sp.getNightRateRW() != null
                        ? sp.getNightRateRW()
                        : (sp.getDiscussedRateRW() != null
                        ? sp.getDiscussedRateRW()
                        : sp.getDiscussedRate());
            } else if (!isWeekend && !isNight && isRailWork) {
                return sp.getDiscussedRateRW() != null ? sp.getDiscussedRateRW() : sp.getDiscussedRate();
            }
        } else if (client.getRateType().equals(User.RateType.HOURLY)) {
            if (isWeekend && isEvent) {
                return sp.getWeekendRate() != null
                        ? sp.getWeekendRate()
                        : (sp.getHourRate() != null
                        ? sp.getHourRate()
                        : sp.getDiscussedRate());
            } else if (isNight && isEvent) {
                return sp.getNightRate() != null
                        ? sp.getNightRate()
                        : (sp.getHourRate() != null
                        ? sp.getHourRate()
                        : sp.getDiscussedRate());
            } else if (!isWeekend && !isNight && isEvent) {
                return sp.getHourRate() != null ? sp.getHourRate() : sp.getDiscussedRate();
            }
            if (isWeekend && isRailWork) {
                return sp.getWeekendRateRW() != null
                        ? sp.getWeekendRateRW()
                        : (sp.getHourRateRW() != null
                        ? sp.getHourRateRW()
                        : (sp.getDiscussedRateRW() != null
                        ? sp.getDiscussedRateRW() :
                        sp.getDiscussedRate()));
            } else if (isNight && isRailWork) {
                return sp.getNightRateRW() != null
                        ? sp.getNightRateRW()
                        : (sp.getHourRateRW() != null
                        ? sp.getHourRateRW()
                        : (sp.getDiscussedRateRW() != null
                        ? sp.getDiscussedRateRW() :
                        sp.getDiscussedRate()));
            } else if (!isWeekend && !isNight && isRailWork) {
                return sp.getHourRateRW() != null ? sp.getHourRateRW() : (sp.getDiscussedRateRW() != null
                        ? sp.getDiscussedRateRW() :
                        sp.getDiscussedRate());
            }
        }
        return sp.getHourRate();
    }
}
