package br.com.erudio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DockerController {

	private static final Logger log = LoggerFactory.getLogger(DockerController.class);

	@RequestMapping("/hello-docker")
	public HelloDocker greeting() {
		log.info("Endpoint /hello-docker is called!!!");
		//var hostName = System.getenv("COMPUTERNAME");
		var hostName = System.getenv("HOSTNAME");
		
		return new HelloDocker(
					"Hello Docker",
					hostName
				);
	}
}
