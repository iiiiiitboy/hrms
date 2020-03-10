package ex.aaronfae.graduation.business.trip.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.trip.entity.BusinessTripDO;
import ex.aaronfae.graduation.business.trip.model.vo.BusinessTripVO;

public interface BusinessTripMapper extends BaseMapper<BusinessTripDO> {

	List<BusinessTripVO> listMyBusinessTrips(Long employeeId);

	List<BusinessTripVO> listBusinessTripsByPage(Page<BusinessTripVO> page);

}
