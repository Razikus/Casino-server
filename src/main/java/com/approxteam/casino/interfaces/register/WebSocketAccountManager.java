/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.interfaces.register;

import com.approxteam.casino.configuration.PropertiesBuilder;
import com.approxteam.casino.configuration.PropertyComment;
import com.approxteam.casino.entities.Account;
import com.approxteam.casino.entities.AccountActivation;
import com.approxteam.casino.entities.AccountPasswordRequest;
import com.approxteam.casino.generalLogic.actions.Action;
import com.approxteam.casino.generalLogic.actions.argsUtils.ActionParameter;
import com.approxteam.casino.generalLogic.actions.argsUtils.ArgUtils;
import com.approxteam.casino.interfaces.Mailer;
import com.approxteam.casino.interfaces.mailer.ActivationMail;
import com.approxteam.casino.interfaces.mailer.MailWrapper;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import com.approxteam.casino.interfaces.AccountManager;
import com.approxteam.casino.interfaces.mailer.PasswordChangeMail;

/**
 *
 * @author adamr
 */
@Stateful
@PropertyComment(desc = "Provide default link")
public class WebSocketAccountManager implements AccountManager{

    private final Properties properties = PropertiesBuilder.getProperties(WebSocketAccountManager.class);
    
    @PersistenceContext(unitName = "casinoPU")
    private EntityManager entityManager;
    
    @EJB
    private Mailer mailer;
    
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(WebSocketAccountManager.class);
    
    @Override
    public boolean register(String nickName, String password, String email){
        Account account = new Account();
        account.setEmail(email);
        account.setPassword(password);
        account.setNickname(nickName);
        
        boolean status = save(account);
        
        String token = getRandomToken();
        AccountActivation accountActivation = new AccountActivation();
        accountActivation.setAccount(account);
        accountActivation.setActivated(false);
        accountActivation.setToken(token);
        
        boolean activationStatus = save(accountActivation);
        if(activationStatus) {
            sendActivationLink(email, nickName, token);
        }
        
        return status;
    }
    
    
    
    private MailWrapper constructActivationEmail(String to, String nickName, String token) {
        MailWrapper wrapper = new ActivationMail(to, constructActivationLink(token, nickName), nickName);
        return wrapper;
    }
    
    private String getRandomToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    private String constructActivationLink(String token, String nickname) {
        return properties.getProperty("appLink") + "index.html?nickname=" + nickname + "&" + "token=" + token;
    }
   
    @Override
    public Account findAccount(String login) {
        return find(login);
        
    }
    
    private String constructPasswordChangeLink(String token, String nickname){
        return properties.getProperty("appLink") + "index.html?nickname=" + nickname + "&" + "token=" + token;
    }
    
     private MailWrapper constructPasswordChangeMail(String to, String nickName, String token) {
        MailWrapper wrapper = new PasswordChangeMail(to, constructPasswordChangeLink(token, nickName), nickName);
        return wrapper;
    }
    
    @Override
    public Account findAccountByEmail(String email){
        return findByEmail(email);
    }
    
    
    
    
     private Account findByEmail(String mail) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> cq = cb.createQuery(Account.class);
        Root<Account> account = cq.from(Account.class);
        ParameterExpression<String> email = cb.parameter(String.class);
        cq.select(account).where(cb.equal(account.get("Email"), email));
        TypedQuery<Account> q = entityManager.createQuery(cq);
        q.setParameter(email, mail);
        try {
            Account result = q.getSingleResult();
            return result;
        } catch(Exception e) {
            return null;
        }
    }
    
    
    
    private Account find(String login) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> cq = cb.createQuery(Account.class);
        Root<Account> account = cq.from(Account.class);
        ParameterExpression<String> name = cb.parameter(String.class);
        cq.select(account).where(cb.equal(account.get("nickname"), name));
        TypedQuery<Account> q = entityManager.createQuery(cq);
        q.setParameter(name, login);
        try {
            Account result = q.getSingleResult();
            return result;
        } catch(Exception e) {
            return null;
        }
    }
    
    private boolean save(Object o) {
        try {
            entityManager.persist(o);
            entityManager.flush();
        } catch(Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void sendActivationLink(String email, String nickName, String token) {
        mailer.send(constructActivationEmail(email, nickName, token));
    }

    @Override
    public boolean activate(String token, String actionNickName) {
        AccountActivation playerActivation = findActivation(token);
        if(playerActivation == null) {
            return false;
        }
        if(!actionNickName.equals(playerActivation.getAccount().getNickname())) {
            return false;
        }
        
        playerActivation.setActivated(true);
        playerActivation.setDateActivated(new Date());
        
        return save(playerActivation);
        
    }

    @Override
    public AccountActivation findActivation(String token) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AccountActivation> cq = cb.createQuery(AccountActivation.class);
        Root<AccountActivation> accountActivation = cq.from(AccountActivation.class);
        ParameterExpression<String> tokenParameter = cb.parameter(String.class);
        cq.select(accountActivation).where(cb.equal(accountActivation.get("token"), tokenParameter));
        TypedQuery<AccountActivation> query = entityManager.createQuery(cq);
        query.setParameter(tokenParameter, token);
        try {
            AccountActivation result = query.getSingleResult();
            return result;
        } catch(Exception e) {
            return null;
        }
    }
    
    
    @Override
    public AccountPasswordRequest findRequest(String token) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AccountPasswordRequest> cq = cb.createQuery(AccountPasswordRequest.class);
        Root<AccountPasswordRequest> accountPassRequest = cq.from(AccountPasswordRequest.class);
        ParameterExpression<String> tokenParameter = cb.parameter(String.class);
        cq.select(accountPassRequest).where(cb.equal(accountPassRequest.get("token"), tokenParameter));
        TypedQuery<AccountPasswordRequest> query = entityManager.createQuery(cq);
        query.setParameter(tokenParameter, token);
        try {
            AccountPasswordRequest result = query.getSingleResult();
            return result;
        } catch(Exception e) {
            return null;
        }
    }
    
    @Override
    public boolean activateNewPassword(String email , String token){
        AccountPasswordRequest acp = findRequest(token);
        Account acc = acp.getAccount();
        
        boolean check = false;
        
        if(acp.getToken().equals(token)){
            if(acc.getEmail().equals(email)){
                acc.setPassword(acp.getNewPassword());
                check = true;
            }
        }
        return check;      
    }
    

    @Override
    public boolean generateAndSendPasswordChangeEmail(String email) {
        AccountPasswordRequest req = new AccountPasswordRequest();
        req.setAccount(findAccountByEmail(email));
        req.setToken(getRandomToken());
        
        boolean status = save(req);
        
        if(status) {
            mailer.send(constructPasswordChangeMail(email, req.getAccount().getNickname(), req.getToken()));     
        }      
        return status;       
    }

    @Override
    public AccountPasswordRequest findRequestByEmail(String email) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AccountPasswordRequest> cq = cb.createQuery(AccountPasswordRequest.class);
        Root<AccountPasswordRequest> accountPassRequest = cq.from(AccountPasswordRequest.class);
        ParameterExpression<String> emailParameter = cb.parameter(String.class);
        cq.select(accountPassRequest).where(cb.equal(accountPassRequest.get("email"), emailParameter));
        TypedQuery<AccountPasswordRequest> query = entityManager.createQuery(cq);
        query.setParameter(emailParameter, email);
        try {
            AccountPasswordRequest result = query.getSingleResult();
            return result;
        } catch(Exception e) {
            return null;
        }
    }
    
    
}
