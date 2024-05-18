package com.rentmen.app.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.web.multipart.MultipartFile;

import com.rentmen.app.Constants.Constants;

public class UtilFunctions {
	public static <T> T mergeObjects(T target, T source) throws IllegalAccessException {
        List<Field> baseClazz = Arrays.asList(target.getClass().getSuperclass().getDeclaredFields());
        List<Field> clazz = Arrays.asList(target.getClass().getDeclaredFields());
        List<Field> allFields = Stream.concat(baseClazz.stream(), clazz.stream())
            .collect(Collectors.toList());
        for (Field field : allFields) {
            field.setAccessible(true);
            Object sourceValue = field.get(source);
			if (sourceValue != null && !Modifier.isFinal(field.getModifiers())
					&& !Collection.class.isAssignableFrom(field.getType())) {
				
				field.set(target, sourceValue);
				
			}
        }
        return target;
    }
	
	public static String saveMultipartFileToPath(MultipartFile imageFile, String fileName) {
		StringBuilder filePath = new StringBuilder();
		filePath.append(Constants.PROFILE_IMAGE_PATH);
		File image = new File(filePath.toString());
		if(!image.exists()) {
			if(image.mkdirs()) {
				System.out.println("Directory created successfully: " + filePath.toString());
			}
		}
		filePath.append("/"+fileName);
		image = new File(filePath.toString());
        try {
        	imageFile.transferTo(image);
		
        } catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filePath.toString();
	}
	private static String getFileExtension(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType.split("/")[1];
    }
	public static String createFileName(MultipartFile imageFile,String name) {
		return name+"_"+UUID.randomUUID().toString() + "." + getFileExtension(imageFile);
		
	}
	public static byte[] downloadImageFromFileSystem(String fileName) throws IOException {
		StringBuilder filePath = new StringBuilder();
		filePath.append(Constants.PROFILE_IMAGE_PATH);
		filePath.append("/"+fileName);
        byte[] images = Files.readAllBytes(new File(filePath.toString()).toPath());
        return images;
    }
}
