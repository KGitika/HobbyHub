package com.hobbyhub.hobbyhub.controller;

import com.hobbyhub.hobbyhub.entity.Group;
import com.hobbyhub.hobbyhub.entity.User;
import com.hobbyhub.hobbyhub.service.EventService;
import com.hobbyhub.hobbyhub.service.GroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.authentication.TestingAuthenticationToken;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GroupController.class)
@AutoConfigureMockMvc(addFilters = false)
class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;
    @MockBean
    private EventService eventService;

    @Test
    void getAllGroups_returnsOk() throws Exception {
        when(groupService.getAllGroups()).thenReturn(List.of(new Group()));
        mockMvc.perform(get("/groups").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createGroup_returnsCreated() throws Exception {
        Group group = new Group();
        when(groupService.createGroup(anyString(), anyString(), anyString(), any(User.class))).thenReturn(group);

        User user = new User();
        TestingAuthenticationToken auth = new TestingAuthenticationToken(user, null);

        String body = "{\"name\":\"Astro Photographers\",\"hobby\":\"Photography\",\"description\":\"For night sky enthusiasts.\"}";
        mockMvc.perform(post("/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .principal(auth))
                .andExpect(status().isCreated());
    }

    @Test
    void leaveGroup_returnsIsMemberFalse() throws Exception {
        User user = new User();
        TestingAuthenticationToken auth = new TestingAuthenticationToken(user, null);

        mockMvc.perform(delete("/groups/{id}/leave", 1L).principal(auth))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isMember").value(false));
    }
}
