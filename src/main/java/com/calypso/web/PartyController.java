/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.calypso.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.calypso.model.Party;
import com.calypso.service.ClinicService;

@Controller
@SessionAttributes("party")
@RequestMapping(value = "/parties")
public class PartyController
{

	private final ClinicService clinicService;

	@Autowired
	public PartyController(ClinicService clinicService)
	{
		this.clinicService = clinicService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder)
	{
		dataBinder.setDisallowedFields("id");
	}

	//********************** FIND
   @RequestMapping(value = "/find", method = RequestMethod.GET)
   public String initFind(Model model) {
       model.addAttribute("party", new Party());
       return "parties/findParties";
   }
	
   //********************** LIST
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String processList(Model model)
	{
		Collection<Party> parties = this.clinicService.findParties();
		model.addAttribute("parties", parties);
		return "parties/partyList";
	}
	
	//********************** VIEW
   @RequestMapping("/{partyId}")
   public ModelAndView processView(@PathVariable("partyId") int partyId) {
       ModelAndView mav = new ModelAndView("parties/partyDetails");
       mav.addObject(this.clinicService.findPartyById(partyId));
       return mav;
   }
 
   //********************** ADD 
   @RequestMapping(value = "/new", method = RequestMethod.GET)
   public String initCreation(Model model) {
       Party party = new Party();
       model.addAttribute(party);
       return "parties/partyForm";
   }
   
   @RequestMapping(value = "/new", method = RequestMethod.POST)
   public String processCreation(@Valid Party party, BindingResult result, SessionStatus status) {
       if (result.hasErrors()) {
           return "parties/partyForm";
       } else {
           this.clinicService.saveParty(party);
           status.setComplete();
           return "redirect:/parties/list";
       }
   }

   //**************************** UPDATE 
   @RequestMapping(value = "/{partyId}/edit", method = RequestMethod.GET)
   public String initUpdate(@PathVariable("partyId") int partyId, Model model) {
       Party party = this.clinicService.findPartyById(partyId);
       model.addAttribute(party);
       return "parties/partyForm";
   }

   @RequestMapping(value = "/{partyId}/edit", method = RequestMethod.POST)
   public String processUpdate(@Valid Party party, BindingResult result, SessionStatus status) {
       if (result.hasErrors()) {
           return "parties/partyForm";
       } else {
           this.clinicService.saveParty(party);
           status.setComplete();
           return "redirect:/parties/{partyId}";
       }
   }

	//********************** DELETE
   @RequestMapping(value="/{partyId}/delete", method = RequestMethod.DELETE)
   public String processDelete(@PathVariable("partyId") int partyId) {
   	 Party party = this.clinicService.findPartyById(partyId);
   	 
   	 if (party != null) {
   		this.clinicService.deleteParty(party);
   	 }
   	 
   	 return "redirect:/parties/list";
       
   }

}
