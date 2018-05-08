package org.miw.es;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WordController {

	private static final Log log = LogFactory.getLog(WordController.class);
	
	@GetMapping("/word")
    public String word() throws Exception {
       
		log.info("Word Controller Running");
		
		String[] str = new String[1];
		Wordcount.main(str);
		log.info("Word Controller Finish");
		
		return "word";
    }
}
