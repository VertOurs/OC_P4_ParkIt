package com.parkit.parkingsystem.model;

import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class TicketTest {




    @Test
    public void checkGetIDReturnNUll(){
        Ticket ticket = new Ticket();
        assertEquals(0,ticket.getId());
    }
    @Test
    public void checksetIDReturn5(){
        Ticket ticket = new Ticket();
        ticket.setId(5);
        assertEquals(5, ticket);
    }


}