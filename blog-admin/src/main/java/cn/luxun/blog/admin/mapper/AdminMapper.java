package cn.luxun.blog.admin.mapper;

import cn.luxun.blog.admin.model.Admin;
import cn.luxun.blog.admin.model.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

	@Select("SELECT * FROM ms_permission where id in (select permission_id from ms_admin_permission where admin_id=#{adminId})")
	List<Permission> findPermissionByAdminId(Long adminId);
}
