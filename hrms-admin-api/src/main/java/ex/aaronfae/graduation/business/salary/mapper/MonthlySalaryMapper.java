package ex.aaronfae.graduation.business.salary.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.salary.entity.MonthlySalaryDO;
import ex.aaronfae.graduation.business.salary.model.vo.MonthlySalaryVO;

public interface MonthlySalaryMapper extends BaseMapper<MonthlySalaryDO> {

	List<MonthlySalaryVO> listMyMonthlySalaryByPage(Page<MonthlySalaryVO> page, @Param("employeeId") Long employeeId);

	List<MonthlySalaryVO> listNoReviewedMonthlySalaryByPage(Page<MonthlySalaryVO> page);

	List<MonthlySalaryVO> listAllMonthlySalaryByPage(Page<MonthlySalaryVO> page);

}
