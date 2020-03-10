package ex.aaronfae.graduation.business.commute.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import ex.aaronfae.graduation.business.commute.entity.SysCommuteDO;
import ex.aaronfae.graduation.business.commute.mapper.SysCommuteMapper;
import ex.aaronfae.graduation.business.commute.service.SysCommuteService;

@Service
@Transactional
public class SysCommuteServiceImpl extends ServiceImpl<SysCommuteMapper, SysCommuteDO> implements SysCommuteService {

	@Override
	public SysCommuteDO getOneById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
	public IPage<SysCommuteDO> getAll(Page<SysCommuteDO> page) {
		return baseMapper.selectPage(page, null);
	}

	@Override
	public int saveSysCommute(SysCommuteDO entity) {
		Date newDate = new Date();
		entity.setGmtCreate(newDate);
		entity.setGmtModified(newDate);
		entity.setLogicDeleted(Boolean.FALSE);
		return baseMapper.insert(entity);
	}

	@Override
	public int updateSysCommute(SysCommuteDO entity) {
		Date newDate = new Date();
		if (entity.getEnabled()) {
			// 启用规则的话，先把已启用的取消
			SysCommuteDO enabledOne = getEnabledRecord();
			enabledOne.setEnabled(Boolean.FALSE);
			enabledOne.setGmtModified(newDate);
			updateSysCommute(enabledOne);
			
			entity.setGmtModified(newDate);
		}
		return baseMapper.updateById(entity);
	}

	@Override
	public int deleteSysCommuteById(Long id) {
		return baseMapper.deleteById(id);
	}
	
	@Override
	public SysCommuteDO getEnabledRecord() {
		return baseMapper.selectOne(new QueryWrapper<SysCommuteDO>().eq("enabled", 1));
	}

}
