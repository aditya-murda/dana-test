package com.example.demo;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Pengguna;
import com.example.demo.PenggunaRepository;

@Controller
@RequestMapping(path="/app")
public class MainController {
	
	@Autowired
	private PenggunaRepository penggunaRepository;
	
	@RequestMapping(method={RequestMethod.GET}, path="/register")
	public @ResponseBody String addNewUser (@RequestParam String name, @RequestParam String password) {
		
		System.out.printf("name: %s\nemail=%s\n", name, password);
		Pengguna n = new Pengguna();
		n.setName(name);
		n.setPassword(password);
		penggunaRepository.save(n);
		return "Saved";
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<Pengguna> getAllUsers() {
		return penggunaRepository.findAll();
	}
	
	@GetMapping(path="/deactivate")
	public @ResponseBody String deactivatePengguna(@RequestParam String name) {
		ArrayList<Pengguna> penggunas = new ArrayList<Pengguna>();
		(getAllUsers()).forEach(penggunas::add);
		for(int i = 0; i < penggunas.size(); i++) {
			if(penggunas.get(i).getName() == name) {
				penggunaRepository.delete(penggunas.get(i));
				return "success";
			}
		}
		return "user not available";
	}
	
	@Autowired
	private MessageRepository messageRepository;
	
	@RequestMapping(method= {RequestMethod.GET}, path="/sendmsg")
	public @ResponseBody String sendMsg(@RequestParam Integer destId, @RequestParam String msg) {
		if(penggunaRepository.findPenggunaById(destId) != null)
			// TODO: user pengirim belum diatur
			messageRepository.save(new Message(msg, destId, 0));
		return "sent";
	}
	
	@RequestMapping(method= {RequestMethod.GET}, path="/viewallmsg")
	public @ResponseBody Iterable<Message> viewAllMsg(@RequestParam Integer userId,
			@RequestParam Integer fromUserId) {
		ArrayList<Message> res = new ArrayList<Message>();
		ArrayList<Message> messages = new ArrayList<Message>();
		messageRepository.findAll().forEach(messages::add);
		for(int i = 0; i < messages.size(); i++) {
			if(messages.get(i).getDestPenggunaId() == userId &&
					messages.get(i).getFromPenggunaId() == fromUserId) {
				res.add(messages.get(i));
			}
		}
		return res;
	}
	
//	@Autowired
//	private HashMap<Long, Pengguna> loggedInUsers;
//	
//	@RequestMapping(method= {RequestMethod.GET}, path="/login")
//	public @ResponseBody String login(@RequestParam String user, @RequestParam String password) {
//		return null;
//	}
}