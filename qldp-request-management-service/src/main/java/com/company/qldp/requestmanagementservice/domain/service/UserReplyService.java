package com.company.qldp.requestmanagementservice.domain.service;

import com.company.qldp.domain.Reply;
import com.company.qldp.requestmanagementservice.domain.repository.ReplyRepository;
import com.company.qldp.requestmanagementservice.domain.util.GetInfo;
import com.company.qldp.userservice.domain.exception.UserNotFoundException;
import com.company.qldp.userservice.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserReplyService {
    
    private ReplyRepository replyRepository;
    private UserRepository userRepository;
    
    @Autowired
    public UserReplyService(
        ReplyRepository replyRepository,
        UserRepository userRepository
    ) {
        this.replyRepository = replyRepository;
        this.userRepository = userRepository;
    }
    
    @Transactional
    public Reply getReply(String keycloakUid, Integer id) {
        if (userNotExists(keycloakUid)) {
            throw new UserNotFoundException();
        }
        
        Reply reply = replyRepository.findByPetition_Sender_KeycloakUidAndId(keycloakUid, id);
        GetInfo.getReplyInfo(reply);
        
        return reply;
    }
    
    private boolean userNotExists(String keycloakUid) {
        return userRepository.findByKeycloakUid(keycloakUid) == null;
    }
}
