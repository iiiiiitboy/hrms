package ex.aaronfae.graduation.business.hr.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.base.enumerate.GenderEnum;
import ex.aaronfae.graduation.base.model.vo.ResponseVO;
import ex.aaronfae.graduation.business.hr.entity.ApplicantDO;
import ex.aaronfae.graduation.business.hr.model.dto.LookApplicantsFilterDTO;
import ex.aaronfae.graduation.business.hr.model.vo.ApplicantVO;
import ex.aaronfae.graduation.business.hr.service.ApplicantService;

@RestController
@RequestMapping("api")
public class ApplicantRestController {

	@Resource
	private ApplicantService applicantService;

	/**
	 * 查看应聘者（招聘计划已审核但未完成，应聘者未雇佣）
	 * 
	 * @param current 当前页码
	 * @param size    每页记录数
	 * @return
	 */
	@GetMapping(value = "applicants/applying")
	public ResponseVO lookApplicants(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size) {
		Page<ApplicantVO> page = new Page<>(current, size);
		return ResponseVO.ok("请求成功", applicantService.listNoEmployedApplicantsByPage(page));
	}

	@GetMapping(value = "applicants/filter")
	public ResponseVO lookApplicantsByFilter(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size, LookApplicantsFilterDTO filter) {
		Page<ApplicantVO> page = new Page<>(current, size);
		return ResponseVO.ok("请求成功", applicantService.listApplicantsByFilter(page, filter));
	}

	/**
	 * 办理入职
	 * 
	 * @param current 当前页码
	 * @param size    每页记录数
	 * @return
	 */
	@GetMapping(value = "applicants/page")
	public ResponseVO listApplicantsByPage(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size) {
		Page<ApplicantVO> page = new Page<>(current, size);
		return ResponseVO.ok("请求成功", applicantService.listApplicantsByPage(page));
	}

	// 根据ID 下载简历
	// @GetMapping(value = "applicants/{id}/resume")
	public void downloadResumeById(@PathVariable("id") Long id, HttpServletRequest request,
			HttpServletResponse response) {
		String resumePath = applicantService.getResumePathById(id);
		if (resumePath != null || "".equals(resumePath)) {
			// 获取file绝对路径
			resumePath = "2019-3-1-9-8-14-简历模板.pdf";// mock
			File file = new File("D:/upload", resumePath);
			OutputStream out = null;
			if (file.exists()) {
				response.setContentType("application/octet-stream;charset=UTF-8");
				// 设置文件名
				try {
					response.setHeader("Content-Disposition", "attachment;filename="
							+ URLEncoder.encode(resumePath.substring(resumePath.lastIndexOf("-") + 1), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				try {
					out = response.getOutputStream();
					out.write(FileUtils.readFileToByteArray(file));
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (out != null) {
						try {
							out.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	/**
	 * 保存一条“应聘者”个人信息
	 * 
	 * @param name
	 * @param tel
	 * @param age
	 * @param gender
	 * @param email
	 * @param recruitmentId
	 * @param resume
	 * @return
	 */
	@PostMapping(value = "applicant")
	public int saveApplicant(String name, String tel, Integer age, Integer gender, String email, Long recruitmentId,
			MultipartFile resume) {
		if (!resume.isEmpty()) {
			Calendar calendar = Calendar.getInstance();
			String filename = calendar.get(1) + "-" + (calendar.get(2) + 1) + "-" + calendar.get(5) + "-"
					+ calendar.get(11) + "-" + calendar.get(12) + "-" + calendar.get(13) + "-"
					+ resume.getOriginalFilename();
			File dest = new File("D:/upload/resume", filename);
			try {
				ApplicantDO applicant = new ApplicantDO(name, tel, age, GenderEnum.values()[gender], email, filename,
						recruitmentId, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
				applicant.setGmtCreate(calendar.getTime());
				applicant.setGmtModified(calendar.getTime());
				applicant.setLogicDeleted(Boolean.FALSE);
				resume.transferTo(dest);
				return applicantService.saveApplicant(applicant);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * 根据ID更新“应聘者”
	 * 
	 * @param entity 应聘者
	 */
	@PutMapping(value = "applicants")
	public ResponseVO updateApplicant(@RequestBody ApplicantDO entity) {
		int result = applicantService.updateApplicant(entity);
		if (result > 0) {
			return ResponseVO.ok("更新成功");
		}
		return ResponseVO.error("更新失败", result);
	}

	/**
	 * 根据参数 id 逻辑删除“应聘者”
	 * 
	 * @param id 主键
	 * @return
	 */
	@DeleteMapping(value = "applicant")
	public int delApplicantById(Long id) {
		return applicantService.delApplicantById(id);
	}

}
