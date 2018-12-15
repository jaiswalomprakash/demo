package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Greeting;
import com.example.demo.model.User;
import com.example.demo.repository.GreetingRepository;
import com.example.demo.repository.UserRepository;

@Service
@Transactional(
        propagation = Propagation.SUPPORTS,
        readOnly = true)
public class GreetingServiceBean implements GreetingService {

    @Autowired
    private GreetingRepository greetingRepository;
    
    @Autowired
    OuterBean outerBean;
    
    @Autowired
    InnerBean innerBean;
    
    @Autowired
    
    UserRepository userRepository;

    @Override
    public Collection<Greeting> findAll() {
       
        Collection<Greeting> greetings =  	makeCollection(greetingRepository.findAll());
        return greetings;
    }
    
    public static <E> Collection<E> makeCollection(Iterable<E> iter) {
        Collection<E> list = new ArrayList<E>();
        for (E item : iter) {
            list.add(item);
        }
        return list;
    }

    @Override
    public Greeting findOne(Long id) {

        Greeting greeting = greetingRepository.findOne(id);

        return greeting;
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false)
    public Greeting create(Greeting greeting) {

        // Ensure the entity object to be created does NOT exist in the
        // repository. Prevent the default behavior of save() which will update
        // an existing entity if the entity matching the supplied id exists.
        if (greeting.getId() != null) {
            // Cannot create Greeting with specified ID value
            return null;
        }
       
        Greeting gr = new Greeting();
        gr.setText("dd kk");
        Greeting savedGreeting = greetingRepository.save(gr);
        try {
        	create2(greeting);
		} catch (RuntimeException e) {
			// TODO: handle exception
		}
        
        
     //   create2(greeting);
        
        // Illustrate Tx Rollback
       /* if (savedGreeting.getId() == 4L) {
            throw new RuntimeException("Roll me back!");
        }*/

        return savedGreeting;
    }
    
   // @Override
    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            readOnly = false)
    public Greeting create2(Greeting greeting) {

        // Ensure the entity object to be created does NOT exist in the
        // repository. Prevent the default behavior of save() which will update
        // an existing entity if the entity matching the supplied id exists.
        if (greeting.getId() != null) {
            // Cannot create Greeting with specified ID value
            return null;
        }

        Greeting savedGreeting = greetingRepository.save(greeting);

        // Illustrate Tx Rollback
        if (savedGreeting.getId() == 4L) {
            throw new RuntimeException("Roll me back!");
        }
        if(true) {
      	  throw new RuntimeException("Roll me back!");
      }

        return savedGreeting;
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false)
    public Greeting update(Greeting greeting) {

        // Ensure the entity object to be updated exists in the repository to
        // prevent the default behavior of save() which will persist a new
        // entity if the entity matching the id does not exist
        Greeting greetingToUpdate = findOne(greeting.getId());
        if (greetingToUpdate == null) {
            // Cannot update Greeting that hasn't been persisted
            return null;
        }

        greetingToUpdate.setText(greeting.getText());
        Greeting updatedGreeting = greetingRepository.save(greetingToUpdate);

        return updatedGreeting;
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false)
    public void delete(Long id) {

        greetingRepository.delete(id);

    }
//https://www.byteslounge.com/tutorials/spring-transaction-propagation-tutorial
    @Override
    @Transactional(propagation=Propagation.REQUIRED)
	public User createUser(User user) {
    	userRepository.save(user);
		try{
			innerBean.testRequiresNew();
    	} catch(Exception e){
    		// catch exception raised from transaction rollback
    	}
    	
		
		return user;
	}
    //@Override
	public User createUser1(User user) {
		try{
    		outerBean.testRequired(user);
    	} catch(Exception e){
    		// catch exception raised from transaction rollback
    	}
    	
    User u=	outerBean.testRequiresNew(user);
		return u;
	}
	
	@Override
	@Transactional(
            propagation = Propagation.REQUIRED
            )
    public Collection<User> findAllUser() {
       
        
        return  makeCollection(userRepository.findAll());
    }
    

}
