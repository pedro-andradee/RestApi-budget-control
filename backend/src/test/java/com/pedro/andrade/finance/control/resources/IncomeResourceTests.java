package com.pedro.andrade.finance.control.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedro.andrade.finance.control.dto.IncomeDTO;
import com.pedro.andrade.finance.control.services.IncomeService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IncomeResource.class)
class IncomeResourceTests {
    @MockBean
    private IncomeService incomeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long existingId;
    private Long nonExistingId;
    private IncomeDTO incomeDTO;
    private List<IncomeDTO> list;
    private Integer year;
    private Integer month;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 2L;
        incomeDTO = Factory.createIncomeDTO();
        list = List.of(incomeDTO);
        year = 2022;
        month = 06;
        when(incomeService.findAllByDescription(anyString())).thenReturn(list);
        when(incomeService.findAllIncomes()).thenReturn(list);
        when(incomeService.findById(existingId)).thenReturn(incomeDTO);
        when(incomeService.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
        when(incomeService.insert(any())).thenReturn(incomeDTO);
        when(incomeService.update(eq(existingId), any())).thenReturn(incomeDTO);
        when(incomeService.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);
        doNothing().when(incomeService).delete(existingId);
        doThrow(ResourceNotFoundException.class).when(incomeService).delete(nonExistingId);
    }

    @Test
    void findAllIncomesShouldReturnListWhenThereIsParameter() throws Exception {
        ResultActions result = mockMvc.perform(get("/incomes")
                .queryParam("description", "salary")
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    void findAllIncomesShouldReturnList() throws Exception {
        ResultActions result = mockMvc.perform(get("/incomes")
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    void findByIdShouldReturnIncomeDTOWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(get("/incomes/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.amount").exists());
    }

    @Test
    void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(get("/incomes/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

    @Test
    void findAllByYearAndMonthShouldReturnList() throws Exception {
        ResultActions result = mockMvc.perform(get("/incomes/{year}/{month}", year, month)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    void insertShouldReturnIncomeDTOCreated() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(incomeDTO);

        ResultActions result = mockMvc.perform(post("/incomes")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.amount").exists());
    }

    @Test
    void updateShouldReturnIncomeDTOWhenIdExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(incomeDTO);

        ResultActions result = mockMvc.perform(put("/incomes/{id}", existingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.amount").exists());
    }

    @Test
    void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(incomeDTO);

        ResultActions result = mockMvc.perform(put("/incomes/{id}", nonExistingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

    @Test
    void deleteShouldReturnNoContentWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(delete("/incomes/{id}", existingId));
        result.andExpect(status().isNoContent());
    }

    @Test
    void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(delete("/incomes/{id}", nonExistingId));
        result.andExpect(status().isNotFound());
    }
}