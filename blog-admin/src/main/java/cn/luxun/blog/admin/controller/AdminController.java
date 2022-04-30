package cn.luxun.blog.admin.controller;


import cn.luxun.blog.admin.common.Result;
import cn.luxun.blog.admin.model.Permission;
import cn.luxun.blog.admin.model.params.PageParam;
import cn.luxun.blog.admin.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("admin")
public class AdminController {


	@Autowired
	private PermissionService permissionService;

	@PostMapping("permission/permissionList")
	public Result listPermission(@RequestBody PageParam param) {
		return permissionService.listPermission(param);
	}


	@PostMapping("permission/add")
	public Result add(@RequestBody Permission permission) {
		return permissionService.add(permission);
	}

	@PostMapping("permission/update")
	public Result update(@RequestBody Permission permission) {
		return permissionService.update(permission);
	}

	@GetMapping("permission/delete/{id}")
	public Result delete(@PathVariable("id") Long id) {
		return permissionService.delete(id);
	}

}
