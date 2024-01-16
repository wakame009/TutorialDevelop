package com.techacademy.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;

import com.techacademy.service.UserService;

import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class SpringTest {

    //@Test
    //void test() {
        //fail("まだ実装されていません");
    
    @WebMvcTest(UserController.class)
    public class UserControllerTests {
        public class User {
            private Long id;
            public Long getId() {
                return id;
            }
        }

        @Autowired
        private MockMvc mockMvc;

        @BeforeEach
        void beforeEach(WebApplicationContext webApplicationContext) {
            // Spring Securityを有効にする
            mockMvc = MockMvcBuilders
                    .webAppContextSetup(webApplicationContext)
                    .apply(springSecurity()).build();
        }

        @MockBean
        private UserService userService;

        @SuppressWarnings({ "null", "unlikely-arg-type" })
        @Test
        public void testGetList() throws Exception {


            List<com.techacademy.entity.User> userList = null;
            // UserServiceのモック設定
            when(userService.getUserList()).thenReturn(userList);

            // コントローラーの動作をテスト
            mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("user/list"))
                    .andExpect(model().attributeExists("userlist"))
                    .andExpect(model().attribute("userlist", userList))
                    .andExpect(model().attributeDoesNotExist("error"));

            // Modelのテスト
            Model model = (Model) mockMvc.perform(MockMvcRequestBuilders.get("/user/list")).andReturn().getModelAndView().getModel();
            @SuppressWarnings("unchecked")
            List<User> retrievedUserList = (List<User>) model.getAttribute("userlist");

            // 件数が3件であること
            assert retrievedUserList != null;
            assert retrievedUserList.size() == 3;

         // userlistから1件ずつ取り出し、idとnameを検証する
            for (User actualUser : retrievedUserList) {
                userList.stream()
                        .filter(u -> u.getId().equals(actualUser.getId()))
                        .findFirst()
                        .orElse(null);


            }
        }
    }
    }

    
    
    
    

