package com.wc.single.sky.disclosures.mapper;

import com.wc.single.sky.disclosures.vo.user.SysUserRoleListVO;
import com.wc.single.sky.system.domain.dto.SysUserRoleListDTO;

import java.util.List;

public interface SysRoleMapper {
    List<SysUserRoleListVO> userList(SysUserRoleListDTO sysRole);
}
