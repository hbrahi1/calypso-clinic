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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.calypso.model.Contact;
import com.calypso.model.Party;
import com.calypso.service.ClinicService;

@Controller
@SessionAttributes("contact")
public class ContactController {

    private final ClinicService clinicService;

    @Autowired
    public ContactController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/parties/{partyId}/contacts/new", method = RequestMethod.GET)
    public String initCreationForm(@PathVariable("partyId") int partyId, Model model) {
        Party party = this.clinicService.findPartyById(partyId);
        Contact contact = new Contact();
        party.addContact(contact);
        model.addAttribute("contact", contact);
        return "contacts/contactForm";
    }

    @RequestMapping(value = "/parties/{partyId}/contacts/new", method = RequestMethod.POST)
    public String processCreationForm(@Valid Contact contact, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "contacts/contactForm";
        } else {
            this.clinicService.saveContact(contact);
            status.setComplete();
            return "redirect:/parties/{partyId}";
        }
    }

    @RequestMapping(value = "/parties/*/contacts/{contactId}/edit", method = RequestMethod.GET)
    public String initUpdateForm(@PathVariable("contactId") int contactId, Model model) {
        Contact contact = this.clinicService.findContactById(contactId);
        model.addAttribute("contact", contact);
        return "contacts/contactForm";
    }

    @RequestMapping(value = "/parties/{partyId}/contacts/{contactId}/edit", method = RequestMethod.POST)
    public String processUpdateForm(@Valid Contact contact, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "contacts/contactForm";
        } else {
            this.clinicService.saveContact(contact);
            status.setComplete();
            return "redirect:/parties/{partyId}";
        }
    }
    
    @RequestMapping(value="/parties/{partyId}/contacts/{contactId}/delete", method = RequestMethod.DELETE)
    public String processDelete(@PathVariable("partyId") int partyId, @PathVariable("contactId") int contactId) {
   	 Contact contact = this.clinicService.findContactById(contactId);
    	 
    	 if (contact != null) {
    		this.clinicService.deleteContact(contact);
    	 }
    	 
    	 return "redirect:/parties/{partyId}";
        
    }

}
