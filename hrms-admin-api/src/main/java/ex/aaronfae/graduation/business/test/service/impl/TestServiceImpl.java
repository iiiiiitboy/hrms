package ex.aaronfae.graduation.business.test.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import ex.aaronfae.graduation.business.test.entity.TestDO;
import ex.aaronfae.graduation.business.test.mapper.TestMapper;
import ex.aaronfae.graduation.business.test.service.TestService;

@Service
@Transactional
public class TestServiceImpl extends ServiceImpl<TestMapper, TestDO> implements TestService {

	@Override
	public TestDO getOneById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
	public IPage<TestDO> getAll(Page<TestDO> page) {
		return baseMapper.selectPage(page, null);
	}

	@Override
	public int saveTest(TestDO entity) {
		Date newDate = new Date();
		entity.setGmtCreate(newDate);
		entity.setGmtModified(newDate);
		entity.setLogicDeleted(Boolean.FALSE);
		return baseMapper.insert(entity);
	}

	@Override
	public int updateTest(TestDO entity) {
		entity.setGmtModified(new Date());
		return baseMapper.updateById(entity);
	}

	@Override
	public int deleteTestById(Long id) {
		return baseMapper.deleteById(id);
	}

}
