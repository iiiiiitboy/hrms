package ex.aaronfae.graduation.business.trip.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.trip.entity.BusinessTripDO;
import ex.aaronfae.graduation.business.trip.model.dto.BusinessTripConfirmDTO;
import ex.aaronfae.graduation.business.trip.model.vo.BusinessTripVO;

public interface BusinessTripService {

	int saveBusinessTrip(BusinessTripDO entity);

	List<BusinessTripVO> listMyBusinessTrips(Long employeeId);

	Page<BusinessTripVO> listBusinessTripsByPage(Page<BusinessTripVO> page);

	int saveBusinessTripBatch(List<BusinessTripDO> list);

	int confirmBusinessTrip(BusinessTripConfirmDTO entity);

	int checkInBusinessTrip(BusinessTripConfirmDTO entity);

	int updateBusinessTrip(BusinessTripDO entity);

	int deleteBusinessTripById(Long id);


}
