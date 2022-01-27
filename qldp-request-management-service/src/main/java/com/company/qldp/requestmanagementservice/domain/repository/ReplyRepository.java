package com.company.qldp.requestmanagementservice.domain.repository;

import com.company.qldp.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    
    Reply findByPetition_Sender_KeycloakUidAndId(String uid, Integer id);
    
    Reply findByReplier_KeycloakUidAndId(String uid, Integer id);
}
