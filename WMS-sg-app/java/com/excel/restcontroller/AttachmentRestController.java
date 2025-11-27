package com.excel.restcontroller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class AttachmentRestController {
	
	@GetMapping("/open-attachment")
	public ResponseEntity<Resource> openAttachment(@RequestParam("filename") String fileName) {
		ByteArrayResource bar = null;
		try {
			Path path = Paths.get(fileName);
			System.out.println("File Path"+path);
			bar = new ByteArrayResource(Files.readAllBytes(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.header("Content-Disposition", "attachment;filename=" + fileName.substring
						(fileName.lastIndexOf("/") + 1, fileName.length()))
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(bar);
	}

}
