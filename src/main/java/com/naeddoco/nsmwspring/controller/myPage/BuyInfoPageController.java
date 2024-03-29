package com.naeddoco.nsmwspring.controller.myPage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BuyInfoPageController {
	
	@RequestMapping(value = "/user/buyInfoPage", method = RequestMethod.GET)
	public String buyInfoPageController(Model model) {
		
		System.out.println("[Page이동] 마이페이지 -> 구매내역페이지");
		
		model.addAttribute("pageValue", "구매내역");
			
		return "/user/buyInfo";
	}

}