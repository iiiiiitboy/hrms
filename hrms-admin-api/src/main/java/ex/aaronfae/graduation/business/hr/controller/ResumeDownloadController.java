package ex.aaronfae.graduation.business.hr.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ex.aaronfae.graduation.business.hr.service.ApplicantService;

@Controller
public class ResumeDownloadController {

	@Resource
	private ApplicantService applicantService;

	@GetMapping(value = "applicants/{id}/resume")
	public void downloadResumeById(@PathVariable("id") Long id, HttpServletRequest request,
			HttpServletResponse response) {
		String resumePath = applicantService.getResumePathById(id);
		resumePath = "2019-3-1-9-8-14-简历模板.pdf";// mock
		if (resumePath != null || !"".equals(resumePath)) {
			// 获取file绝对路径
			File file = new File("D:/upload/resume", resumePath);
			OutputStream out = null;
			if (file.exists()) {
				response.setContentType("application/pdf");
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

}
