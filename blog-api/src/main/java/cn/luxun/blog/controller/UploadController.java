package cn.luxun.blog.controller;


import cn.luxun.blog.common.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("upload")
public class UploadController {


	@PostMapping
	public Result upload(@RequestParam("image") MultipartFile file) {


		// 原始文件名称 比如 aa.png
		String originalFilename = file.getOriginalFilename();

		// 唯一的文件名称
		String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");

		return null;
	}

}
