package ex.aaronfae.graduation.business.reimbursement.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import ex.aaronfae.graduation.base.entity.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_reimbursement_picture")
public class ReimbursementPictureDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = 288786518673592694L;

	private String path;
	private Long reimbursementId;

}
