package com.paul.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.paul.dao.*;
import com.paul.model.*;
import com.paul.service.AuthService;
import com.paul.service.AuthServiceImpl;
import com.paul.service.BankService;
import com.paul.util.*;

public class BankTest {
	private static BankDAO dao = new BankDAOImpl();
	
	
	Account testAcc1 = new Account(50, 3, 500);
	Account testAcc2 = new Account(51, 3, 300);
	
	private static AuthService as = new AuthServiceImpl();	
	
	@Mock
	BankDAO fakeDao = mock(BankDAOImpl.class); 
	
	@InjectMocks
	BankService bs = mock(BankService.class);
	
	double epsilon = 0.000001d;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		testAcc1.setBalance(500);
		testAcc1.setAccountID(50);
		testAcc2.setBalance(300);
		testAcc2.setAccountID(51);
	}
	
	@Test
	public void commitAccountTest() {
		assertEquals(500, dao.getAccount(4).getBalance(), epsilon);
		assertEquals(300, dao.getAccount(5).getBalance(), epsilon);
	}
	
	@Test
	public void getMyAccountsTest() {
		Client cli = (Client) as.authenticateUser("test", "junit", 1);
		cli.setAccounts(dao.getAccounts(cli.getUserId()));
		assertTrue(2 <= cli.getAccounts().size());
	}
	
	@Test
	public void depositTest() {
		Account rightBal1 = new Account(50, 3, 600);
		Account rightBal2 = new Account(51, 3, 350);
		
		when(fakeDao.getAccount(50)).thenReturn(rightBal1);
		when(fakeDao.getAccount(51)).thenReturn(rightBal2);
		
		
		bs.deposit(50, 100);
		bs.deposit(51, 50);
		assertEquals(600.0, fakeDao.getAccount(50).getBalance(), epsilon);
		assertEquals(350.0, fakeDao.getAccount(51).getBalance(), epsilon);
	}
	
	@Test
	public void withdrawTest() {
		Account rightBal1 = new Account(50, 3, 400);
		Account rightBal2 = new Account(51, 3, 250);
		
		when(fakeDao.getAccount(50)).thenReturn(rightBal1);
		when(fakeDao.getAccount(51)).thenReturn(rightBal2);
		
		
		bs.withdraw(50, 100);
		bs.withdraw(51, 50);
		assertEquals(400.0, fakeDao.getAccount(50).getBalance(), epsilon);
		assertEquals(250.0, fakeDao.getAccount(51).getBalance(), epsilon);
	}
	
	@Test
	public void negativeValuesTest() {
		BankService bs = mock(BankService.class);
		assertFalse(bs.deposit(50, -100));
		assertFalse(bs.withdraw(51, -50));
	}
	
	@Test
	public void overdrawTest() {
		BankService bs = mock(BankService.class);
		assertTrue(bs.withdraw(50, 600.0) == false);
		assertTrue(bs.withdraw(51, 400.0) == false);
	}
	
	@Test
	public void getAccountsDAOTest() {
		ArrayList<Account> accts = dao.getAccounts(3);
		assertEquals(500.0, accts.get(0).getBalance(), epsilon);
		assertEquals(300.0, accts.get(1).getBalance(), epsilon);
	}
	
	@Test
	public void verifyUserTest() {
		Client testClient = new Client(3, "test", PasswdHasher.hashPasswd("junit"));
		assertTrue(dao.verifyUser("test", PasswdHasher.hashPasswd("junit"), User.CLIENT_TYPE).equals(testClient));
		assertFalse(dao.verifyUser("henry", PasswdHasher.hashPasswd("dog"), User.EMPLOYEE_TYPE).equals(testClient));
		assertEquals(null, dao.verifyUser("nonexistent", "null", 1));
	}
	
	@Test
	public void sendMessageTest() {
		Message msg = new Message(3, 1, 777.0);
		dao.sendMessage(msg);
		ArrayList<Message> msgs = dao.getMessageInbox(1);
		assertEquals(1, msgs.size());
		dao.deleteMessage(msgs.get(0).getMessageID());
		msgs = dao.getMessageInbox(1);
		assertEquals(0, msgs.size());
	}
	
	@Test
	public void getAllClientsTest() {
		assertTrue(1 <= dao.getAllClients().size());
	}
}
