package com.pedro.andrade.finance.control.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedro.andrade.finance.control.dto.ExpenseDTO;
import com.pedro.andrade.finance.control.services.ExpenseService;
import com.pedro.andrade.finance.control.services.exceptions.ResourceNotFoundException;
import com.pedro.andrade.finance.control.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExpenseResource.class)
class ExpenseResourceTests {

    @MockBean
    private ExpenseService expenseService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long existingId;
    private Long nonExistingId;
    private ExpenseDTO expenseDTO;
    private List<ExpenseDTO> list;
    private Integer year;
    private Integer month;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 2L;
        expenseDTO = Factory.createExpenseDTO();
        list = List.of(expenseDTO);
        year = 2022;
        month = 06;
        when(expenseService.findAllByDescription(anyString())).thenReturn(list);
        when(expenseService.findAllExpenses()).thenReturn(list);
        when(expenseService.findById(existingId)).thenReturn(expenseDTO);
        when(expenseService.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
        when(expenseService.findAllByYearAndMonth(year, month)).thenReturn(list);
        when(expenseService.insert(any())).thenReturn(expenseDTO);
        when(expenseService.update(eq(existingId), any())).thenReturn(expenseDTO);
        when(expenseService.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);
        doNothing().when(expenseService).delete(existingId);
        doThrow(ResourceNotFoundException.class).when(expenseService).delete(nonExistingId);
    }

    @Test
    void findAllExpensesShouldReturnListWhenThereIsParameter() throws Exception {
        ResultActions result = mockMvc.perform(get("/expenses")
                .queryParam("description", "groceries")
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    void findAllExpensesShouldReturnList() throws Exception {
        ResultActions result = mockMvc.perform(get("/expenses")
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    void findByIdShouldReturnExpenseDTOWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(get("/expenses/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.amount").exists());
    }

    @Test
    void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(get("/expenses/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

    @Test
    void findAllByYearAndMonthShouldReturnList() throws Exception {
        ResultActions result = mockMvc.perform(get("/expenses/{year}/{month}", year, month)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    void insertShouldReturnExpenseDTOCreated() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(expenseDTO);

        ResultActions result = mockMvc.perform(post("/expenses")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.amount").exists());
    }

    @Test
    void updateShouldReturnExpenseDTOWhenIdExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(expenseDTO);

        ResultActions result = mockMvc.perform(put("/expenses/{id}", existingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.amount").exists());
    }

    @Test
    void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(expenseDTO);

        ResultActions result = mockMvc.perform(put("/expenses/{id}", nonExistingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

    @Test
    void deleteShouldReturnNoContentWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(delete("/expenses/{id}", existingId));
        result.andExpect(status().isNoContent());
    }

    @Test
    void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(delete("/expenses/{id}", nonExistingId));
        result.andExpect(status().isNotFound());
    }
}