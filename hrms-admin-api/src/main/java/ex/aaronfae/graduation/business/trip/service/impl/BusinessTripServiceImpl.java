package ex.aaronfae.graduation.business.trip.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import ex.aaronfae.graduation.base.enumerate.WorkingStatusEnum;
import ex.aaronfae.graduation.business.hr.entity.EmployeeDO;
import ex.aaronfae.graduation.business.hr.service.EmployeeService;
import ex.aaronfae.graduation.business.trip.entity.BusinessTripDO;
import ex.aaronfae.graduation.business.trip.mapper.BusinessTripMapper;
import ex.aaronfae.graduation.business.trip.model.dto.BusinessTripConfirmDTO;
import ex.aaronfae.graduation.business.trip.model.vo.BusinessTripVO;
import ex.aaronfae.graduation.business.trip.service.BusinessTripService;

@Service
@Transactional
public class BusinessTripServiceImpl extends ServiceImpl<BusinessTripMapper, BusinessTripDO>
		implements BusinessTripService {

	@Resource
	private EmployeeService employeeService;

	@Override
	public int saveBusinessTrip(BusinessTripDO entity) {
		Date newDate = new Date();
		entity.setReviewed(Boolean.FALSE);
		entity.setHolding(Boolean.TRUE);
		entity.setFinished(Boolean.FALSE);
		entity.setReimbursed(Boolean.FALSE);
		entity.setGmtCreate(newDate);
		entity.setGmtModified(newDate);
		entity.setLogicDeleted(Boolean.FALSE);
		return baseMapper.insert(entity);
	}

	@Override
	public List<BusinessTripVO> listMyBusinessTrips(Long employeeId) {
		return baseMapper.listMyBusinessTrips(employeeId);
	}

	@Override
	public Page<BusinessTripVO> listBusinessTripsByPage(Page<BusinessTripVO> page) {
		return page.setRecords(baseMapper.listBusinessTripsByPage(page));
	}

	@Override
	public int saveBusinessTripBatch(List<BusinessTripDO> list) {
		if (saveBatch(list)) {
			return 1;
		}
		return 0;
	}

	@Override
	public int confirmBusinessTrip(BusinessTripConfirmDTO entity) {
		// 更新员工为出差状态，更新出差状态为出差
		EmployeeDO employee = new EmployeeDO();
		employee.setId(entity.getEmployeeId());
		employee.setWorkingStatus(WorkingStatusEnum.BUSINESS_TRIP);
		employeeService.updateEmployee(employee);
		BusinessTripDO businessTrip = new BusinessTripDO();
		businessTrip.setId(entity.getId());
		businessTrip.setHolding(Boolean.FALSE);
		return updateBusinessTrip(businessTrip);
	}

	@Override
	public int checkInBusinessTrip(BusinessTripConfirmDTO entity) {
		// 更新员工的状态为在职，更新出差状态为完成
		EmployeeDO employee = new EmployeeDO();
		employee.setId(entity.getEmployeeId());
		employee.setWorkingStatus(WorkingStatusEnum.ONLINE);
		employeeService.updateEmployee(employee);
		BusinessTripDO businessTrip = new BusinessTripDO();
		businessTrip.setId(entity.getId());
		businessTrip.setFinished(Boolean.TRUE);
		return updateBusinessTrip(businessTrip);
	}

	@Override
	public int updateBusinessTrip(BusinessTripDO entity) {
		entity.setGmtModified(new Date());
		return baseMapper.updateById(entity);
	}

	@Override
	public int deleteBusinessTripById(Long id) {
		return baseMapper.deleteById(id);
	}

}
