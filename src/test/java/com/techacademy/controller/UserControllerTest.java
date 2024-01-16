package com.techacademy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.techacademy.entity.User;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    private MockMvc mockMvc;


    private final WebApplicationContext webApplicationContext;

    UserControllerTest(WebApplicationContext context) {
        this.webApplicationContext = context;
    }

    @BeforeEach
    void beforeEach() {
        // Spring Securityを有効にする
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    @Test
    @DisplayName("User更新画面")
    @WithMockUser
    void testGetUser(ApplicationContext user1, ApplicationContext user2, ApplicationContext user3) throws Exception {
        // HTTPリクエストに対するレスポンスの検証
        MvcResult result = mockMvc.perform(get("/user/update/1/")) // URLにアクセス
            .andExpect(status().isOk()) // ステータスを確認
            .andExpect(model().attributeExists("user")) // Modelの内容を確認
            .andExpect(model().hasNoErrors()) // Modelのエラー有無の確認
            .andExpect(view().name("user/update")) // viewの確認
            .andReturn(); // 内容の取得

        // userの検証
        // Modelからuserを取り出す
        User user = (User)result.getModelAndView().getModel().get("user");
        assertEquals(user.getId(), 1);
        assertEquals(user.getName(), "キラメキ太郎");
     // HTTPリクエストに対するレスポンスの検証
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userlist"))
                .andExpect(model().attribute("userlist", Matchers.hasSize(3))) // 3つの要素があることを確認
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeDoesNotExist("error"));

        // Modelのテスト
        MvcResult mvcResult = mockMvc.perform(get("/user/list")).andReturn();
        Object userListAttribute = mvcResult.getModelAndView().getModel().get("userlist");

        // userListAttributeがList<User>型であることを検証
        assertTrue(userListAttribute instanceof List);

        // 1行目の検証
        @SuppressWarnings("unchecked")
        List<User> retrievedUserList = (List<User>) userListAttribute;
        User actualUser1 = retrievedUserList.get(0);
        assertEquals(user1.getId(), actualUser1.getId());
        assertEquals(user1.getDisplayName(), actualUser1.getName());

        // 2行目の検証
        User actualUser2 = retrievedUserList.get(1);
        assertEquals(user2.getId(), actualUser2.getId());
        assertEquals(user2.getDisplayName(), actualUser2.getName());

        // 3行目の検証
        User actualUser3 = retrievedUserList.get(2);
        assertEquals(user3.getId(), actualUser3.getId());
        assertEquals(user3.getDisplayName(), actualUser3.getName());
        
    }
    }
