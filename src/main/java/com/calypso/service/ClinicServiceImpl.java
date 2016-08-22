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
package com.calypso.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calypso.message.Message;
import com.calypso.message.MessageRepository;
import com.calypso.model.Contact;
import com.calypso.model.Owner;
import com.calypso.model.Party;
import com.calypso.model.Pet;
import com.calypso.model.PetType;
import com.calypso.model.User;
import com.calypso.model.UserProfile;
import com.calypso.model.Vet;
import com.calypso.model.Visit;
import com.calypso.repository.ContactRepository;
import com.calypso.repository.OwnerRepository;
import com.calypso.repository.PartyRepository;
import com.calypso.repository.PetRepository;
import com.calypso.repository.PetTypeRepository;
import com.calypso.repository.UserRepository;
import com.calypso.repository.VetRepository;
import com.calypso.repository.VisitRepository;

@Service("clinicService")
public class ClinicServiceImpl implements ClinicService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetTypeRepository petTypeRepository;

    @Autowired
    private VetRepository vetRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PartyRepository partyRepository;
    
    @Autowired
    private ContactRepository contactRepository;

    @Override
    @Transactional(readOnly = true)
    public Collection<PetType> findPetTypes() throws DataAccessException {
        return petTypeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Owner findOwnerById(long id) throws DataAccessException {
        return ownerRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Owner> findOwnerByLastName(String lastName) throws DataAccessException {
        return ownerRepository.findByLastNameStartingWithIgnoreCase(lastName);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Owner> findOwners() throws DataAccessException {
        return ownerRepository.findAll();
    }

    @Override
    @Transactional
    public void saveOwner(Owner owner) throws DataAccessException {
        ownerRepository.save(owner);
    }

    @Override
    @Transactional
    public void saveVisit(Visit visit) throws DataAccessException {
        visitRepository.save(visit);
    }

    @Override
    @Transactional(readOnly = true)
    public Pet findPetById(long id) throws DataAccessException {
        return petRepository.findById(id);
    }

    @Override
    @Transactional
    public void savePet(Pet pet) throws DataAccessException {
        petRepository.save(pet);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "vets")
    public Collection<Vet> findVets() throws DataAccessException {
        return vetRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Message> findMessages() throws DataAccessException {
        return messageRepository.findAll();
    }

    @Override
    @Transactional
    public Message saveMessage(Message message) throws DataAccessException {
        return messageRepository.save(message);
    }

    @Override
    @Transactional(readOnly = true)
    public Message findMessage(Long id) throws DataAccessException {
        return messageRepository.findMessage(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUser(String useName) {
        return userRepository.findByUsername(useName);
    }

    @Override
    @Transactional(readOnly = true)
    public User createUser() {
        return new User();
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfile createUserProfile(User user) {
        return new UserProfile(user);
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }
	
	@Override
   @Transactional(readOnly = true)
   public Party findPartyById(long id) throws DataAccessException {
       return partyRepository.findById(id);
   }

	@Override
	@Transactional
   public void saveParty(Party party) throws DataAccessException {
       partyRepository.save(party);
   }
	
   @Override
   @Transactional(readOnly = true)
   @Cacheable(value = "parties")
   public Collection<Party> findParties() throws DataAccessException {
       return partyRepository.findAll();
   }

	@Override
	@Transactional
	public void deleteParty(Party party) throws DataAccessException
	{
		partyRepository.delete(party);
	}

	@Override
	@Transactional
	public void saveContact(Contact contact) throws DataAccessException
	{
		contactRepository.save(contact);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Contact findContactById(long contactId)
	{
		return contactRepository.findById(contactId);
	}

}
