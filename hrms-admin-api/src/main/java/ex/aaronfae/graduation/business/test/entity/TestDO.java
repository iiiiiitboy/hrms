package ex.aaronfae.graduation.business.test.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import ex.aaronfae.graduation.base.entity.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("test")
public class TestDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = 6206499883711516936L;

	private String name;

}
