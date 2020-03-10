package ex.aaronfae.graduation.business.test.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.test.entity.TestDO;

public interface TestService {

	TestDO getOneById(Long id);

	IPage<TestDO> getAll(Page<TestDO> page);

	int saveTest(TestDO entity);

	int updateTest(TestDO entity);

	int deleteTestById(Long id);

}
