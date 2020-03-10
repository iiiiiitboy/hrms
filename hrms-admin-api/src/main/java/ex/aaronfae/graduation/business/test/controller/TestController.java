package ex.aaronfae.graduation.business.test.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.test.entity.TestDO;
import ex.aaronfae.graduation.business.test.service.TestService;

@RestController
public class TestController {

	@Resource
	private TestService testService;

	@GetMapping("tests/{id}")
	public TestDO getOneById(@PathVariable("id") Long id) {
		return testService.getOneById(id);
	}

	@GetMapping("tests")
	public IPage<TestDO> getAll(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size) {
		Page<TestDO> page = new Page<>(current, size);
		return testService.getAll(page);
	}

	@PostMapping("tests")
	public int saveTest(@RequestBody TestDO entity) {
		return testService.saveTest(entity);
	}

	@PutMapping("tests")
	public int updateTest(@RequestBody TestDO entity) {
		return testService.updateTest(entity);
	}

	@DeleteMapping("tests/{id}")
	public int deleteTestById(@PathVariable("id") Long id) {
		return testService.deleteTestById(id);
	}

}
