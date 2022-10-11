package com.pedro.andrade.finance.control.resources;

import com.pedro.andrade.finance.control.dto.ReportDTO;
import com.pedro.andrade.finance.control.services.ReportService;
import com.pedro.andrade.finance.control.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ReportResource.class)
class ReportResourceTests {
    @MockBean
    private ReportService reportService;

    @Autowired
    private MockMvc mockMvc;

    private Integer year;
    private Integer month;
    private ReportDTO reportDTO;

    @BeforeEach
    void setUp() {
        year = 2022;
        month = 06;
        reportDTO = Factory.createReportDTO();
        when(reportService.getReportByYearAndMonth(year, month)).thenReturn(reportDTO);
    }

    @Test
    void getReportByYearAndMonthShouldReturnReportDtoOk() throws Exception {
        ResultActions result = mockMvc.perform(get("/reports/{year}/{month}", year, month)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }
}