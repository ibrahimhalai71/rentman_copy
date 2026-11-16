package com.rentmen.app.utils;

import com.rentmen.app.Constants.Constants;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UtilFunctions {
	public UtilFunctions() {
	}

	public static <T> T mergeObjects(T target, T source) throws IllegalAccessException {
		List<Field> baseClazz = Arrays.asList(target.getClass().getSuperclass().getDeclaredFields());
		List<Field> clazz = Arrays.asList(target.getClass().getDeclaredFields());
		List<Field> allFields = (List)Stream.concat(baseClazz.stream(), clazz.stream()).collect(Collectors.toList());
		Iterator var5 = allFields.iterator();

		while(var5.hasNext()) {
			Field field = (Field)var5.next();
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
		Map<Long, Boolean> map = (Map)Arrays.stream(mapAsString.split(",")).map((entry) -> {
			return entry.split("=");
		}).collect(Collectors.toMap((entry) -> {
			return Long.parseLong(entry[0]);
		}, (entry) -> {
			return Boolean.parseBoolean(entry[1]);
		}));
		return map;
	}

	public static String convertWithStream(Map<Long, Boolean> map) {
		String mapAsString = (String)map.keySet().stream().map((key) -> {
			return key + "=" + map.get(key);
		}).collect(Collectors.joining(","));
		return mapAsString;
	}
}
