package com.pedro.andrade.finance.control.resources;

import com.pedro.andrade.finance.control.dto.ReportDTO;
import com.pedro.andrade.finance.control.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/reports")
public class ReportResource {

    @Autowired
    private ReportService reportService;

    @GetMapping(value = "/{year}/{month}")
    public ResponseEntity<ReportDTO> getReportByYearAndMonth(@PathVariable Integer year, @PathVariable Integer month) {
        ReportDTO report = reportService.getReportByYearAndMonth(year, month);
        return ResponseEntity.ok().body(report);
    }
}
