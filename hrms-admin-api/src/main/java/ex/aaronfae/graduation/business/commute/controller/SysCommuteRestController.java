package ex.aaronfae.graduation.business.commute.controller;

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

import ex.aaronfae.graduation.business.commute.entity.SysCommuteDO;
import ex.aaronfae.graduation.business.commute.service.SysCommuteService;

@RestController
public class SysCommuteRestController {

	@Resource
	private SysCommuteService sysCommuteService;

	@GetMapping("sys/commutes/{id}")
	public SysCommuteDO getOneById(@PathVariable("id") Long id) {
		return sysCommuteService.getOneById(id);
	}

	@GetMapping("sys/commutes")
	public IPage<SysCommuteDO> getAll(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size) {
		Page<SysCommuteDO> page = new Page<>(current, size);
		return sysCommuteService.getAll(page);
	}

	@PostMapping("sys/commutes")
	public int saveSysCommute(@RequestBody SysCommuteDO entity) {
		return sysCommuteService.saveSysCommute(entity);
	}

	@PutMapping("sys/commutes")
	public int updateSysCommute(@RequestBody SysCommuteDO entity) {
		return sysCommuteService.updateSysCommute(entity);
	}

	@DeleteMapping("sys/commutes/{id}")
	public int deleteSysCommuteById(@PathVariable("id") Long id) {
		return sysCommuteService.deleteSysCommuteById(id);
	}

}
