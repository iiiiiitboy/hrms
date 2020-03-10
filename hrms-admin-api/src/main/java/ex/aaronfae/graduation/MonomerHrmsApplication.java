package ex.aaronfae.graduation;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MonomerHrmsApplication {

	public static void main(String[] args) {
		File reimbursePictureDir = new File("D:/upload/reimburse-picture");
		if (!reimbursePictureDir.exists()) {
			reimbursePictureDir.mkdirs();
		}
		File resumeDir = new File("D:/upload/resume");
		if (!resumeDir.exists()) {
			resumeDir.mkdirs();
		}
		SpringApplication.run(MonomerHrmsApplication.class, args);
	}

}
