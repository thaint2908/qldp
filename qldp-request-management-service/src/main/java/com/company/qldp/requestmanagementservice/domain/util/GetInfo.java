package com.company.qldp.requestmanagementservice.domain.util;

import com.company.qldp.domain.Petition;
import com.company.qldp.domain.Reply;

public class GetInfo {
    
    public static void getPetitionInfo(Petition petition) {
        petition.getSender().hashCode();
    }
    
    public static void getReplyInfo(Reply reply) {
        reply.getReplier().hashCode();
        reply.getPetition().getSender().hashCode();
    }
}
