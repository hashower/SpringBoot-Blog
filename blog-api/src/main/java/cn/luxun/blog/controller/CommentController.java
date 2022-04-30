package cn.luxun.blog.controller;


import cn.luxun.blog.common.Result;
import cn.luxun.blog.service.CommentService;
import cn.luxun.blog.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
public class CommentController {


	@Autowired
	private CommentService commentService;

	@GetMapping("article/{id}")
	public Result commments(@PathVariable("id") Long id) {
		return commentService.commentsByArticleId(id);
	}

	@PostMapping("create/change")
	private Result comment(@RequestBody CommentParam commentParam) {
		return commentService.comment(commentParam);

	}
}
