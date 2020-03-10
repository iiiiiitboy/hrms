package ex.aaronfae.graduation.business.commute.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.commute.entity.SysCommuteDO;

public interface SysCommuteService {

	SysCommuteDO getOneById(Long id);

	IPage<SysCommuteDO> getAll(Page<SysCommuteDO> page);

	int saveSysCommute(SysCommuteDO entity);

	int updateSysCommute(SysCommuteDO entity);

	int deleteSysCommuteById(Long id);

	SysCommuteDO getEnabledRecord();

}
